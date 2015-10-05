package com.fsk.common.utils.threads;


import android.os.Looper;
import android.support.annotation.NonNull;

/**
 * Utilities to check that the current thread of control is valid.
 */
public class ThreadUtils {

    /**
     * Check that the current execution thread is the expected thread.
     *
     * @param thread
     *         The expected thread
     *
     * @throws com.fsk.common.utils.threads.ThreadException
     *         when the execution thread is not the expected thread.
     */
    public void checkOnThread(@NonNull Thread thread) {

        Thread currentThread = Thread.currentThread();
        if (currentThread != thread) {
            throw new ThreadException("Running on invalid thread " + currentThread);
        }
    }


    /**
     * Check that the current execution thread is not the specified thread.
     *
     * @param thread
     *         The thread that should be currently executing the method.
     *
     * @throws com.fsk.common.utils.threads.ThreadException
     *         when the execution thread is the specified thread.
     */
    public void checkOffThread(@NonNull Thread thread) {
        Thread currentThread = Thread.currentThread();
        if (currentThread == thread) {
            throw new ThreadException("Running on invalid thread " + currentThread);
        }
    }


    /**
     * Check that the current execution thread is not the UI thread.
     *
     * @throws com.fsk.common.utils.threads.ThreadException
     *         when the execution thread is the UI thread.
     */
    public void checkOffUIThread() {
        checkOffThread(getUIThread());
    }


    /**
     * Check that the current execution thread is the UI thread.
     *
     * @throws com.fsk.common.utils.threads.ThreadException
     *         when the execution thread is not the UI thread.
     */
    public void checkOnUIThread() {
        checkOnThread(getUIThread());
    }


    /**
     * Get the UI thread.
     *
     * @return The UI thread derived from the Main Looper.
     */
    public Thread getUIThread() {
        return Looper.getMainLooper().getThread();
    }
}
