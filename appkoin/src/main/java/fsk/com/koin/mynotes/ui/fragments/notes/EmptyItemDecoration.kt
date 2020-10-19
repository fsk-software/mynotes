package fsk.com.koin.mynotes.ui.fragments.notes

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import fsk.com.koin.mynotes.R

/**
 * Decoration that displays a message to the user when there are not notes in the recycler view.
 */
internal class EmptyItemDecoration : RecyclerView.ItemDecoration() {

    private var view: View? = null
    private var lastParentHeight: Int = -1
    private var lastParentWidth: Int = -1
    private var bitmap: Bitmap? = null
    private var canvas: Canvas? = null
    private val paint = Paint()

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (parent.adapter?.itemCount == 0) {
            drawEmptyView(c, parent)
        }
    }

    private fun drawEmptyView(parentCanvas: Canvas, parent: RecyclerView) {
        if (view == null) {
            view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_empty_note, parent, false)
        }

        if (bitmap == null || parent.height != lastParentHeight || parent.width != lastParentWidth) {
            bitmap = Bitmap.createBitmap(parent.width, parent.height, Bitmap.Config.ARGB_8888)
            canvas = Canvas(bitmap!!)

            val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
            val heightSpec =
                View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.EXACTLY)
            view?.apply {
                measure(widthSpec, heightSpec)
                layout(0, 0, parent.width, parent.height)
            }
            lastParentHeight = parent.height
            lastParentWidth = parent.width
        }

        view?.draw(canvas)
        parentCanvas.drawBitmap(bitmap!!, 0.0f, 0.0f, paint)
    }
}