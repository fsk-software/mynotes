@file:JvmName("Screenshot")

package com.fsk.common.presentation;

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.Bitmap.Config
import android.graphics.Canvas
import android.os.AsyncTask
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.support.v4.util.AtomicFile
import android.view.View
import com.fsk.common.safeClose
import com.fsk.common.isAtLeastVersion
import com.fsk.common.validateIsAtLeastVersion
import java.io.File
import java.io.FileOutputStream

interface Callback {
    fun onWriteComplete(file: File,
                        success: Boolean);
}

fun captureImage(view: View): Bitmap {
    val bitmap: Bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
    val canvas: Canvas = Canvas(bitmap);
    view.draw(canvas);
    return bitmap;
}


fun captureImage(activity: Activity): Bitmap {
    return captureImage(activity.findViewById<View>(android.R.id.content).getRootView());
}


fun captureAndWriteImage(activity: Activity,
                         destination: File,
                         callback: Callback) {
    WriteThread(activity, destination, callback, captureImage(activity))
            .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
}

private class WriteThread(val context: Context,
                          val file: File,
                          val callback: Callback,
                          val bitmap: Bitmap) : AsyncTask<Void, Void, Boolean>() {
    init {
        validateIsAtLeastVersion(Build.VERSION_CODES.JELLY_BEAN_MR1);
    }


    override fun doInBackground(vararg params: Void): Boolean {
        validateIsAtLeastVersion(Build.VERSION_CODES.JELLY_BEAN_MR1);

        //todo: reimplement when target apis are better supported.
        // if check is purely to shut the compiler up.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val output: Bitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                                                     bitmap.getConfig());

            val renderScript: RenderScript = RenderScript.create(context);
            val script: ScriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript,
                                                                         Element.U8_4(
                                                                                 renderScript));
            val inAlloc: Allocation = Allocation.createFromBitmap(renderScript, bitmap,
                                                                  Allocation.MipmapControl.MIPMAP_NONE,
                                                                  Allocation.USAGE_GRAPHICS_TEXTURE);
            val outAlloc: Allocation = Allocation.createFromBitmap(renderScript, output);
            script.setRadius(2.5f);
            script.setInput(inAlloc);
            script.forEach(outAlloc);
            outAlloc.copyTo(output);

            renderScript.destroy();

            val atomicFile = AtomicFile(file);
            var outputStream: FileOutputStream? = null;
            try {
                outputStream = atomicFile.startWrite();
                bitmap.compress(CompressFormat.PNG, 0, outputStream);
                outputStream.flush();
                atomicFile.finishWrite(outputStream);
                return true;
            }
            catch (e: Exception) {
                e.printStackTrace();
                atomicFile.failWrite(outputStream);
                return false;
            }
            finally {
               outputStream?.safeClose();
            }
        }

        return false;
    }


    override fun onPostExecute(result: Boolean) {
        super.onPostExecute(result);
        callback.onWriteComplete(file, result);
    }
}
