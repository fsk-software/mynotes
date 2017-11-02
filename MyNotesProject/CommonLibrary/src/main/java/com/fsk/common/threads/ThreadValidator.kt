package com.fsk.common.threads;


import android.os.Looper

/**
 * Validator to check that the current thread of control is valid.
 */

    /**
     * Check that the current execution thread is the expected thread.
     *
     * @param thread
     *         The expected thread
     *
     * @throws com.fsk.common.utils.threads.ThreadException
     *         when the execution thread is not the expected thread.
     */
   fun requireActiveThreadIs(thread : Thread) {
        val currentThread : Thread = Thread.currentThread();
        if (currentThread !== thread) {
            throw ThreadException("Running on invalid thread " + currentThread);
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
    fun requireActiveThreadIsNot(thread : Thread) {
        val currentThread : Thread = Thread.currentThread();
        if (currentThread === thread) {
            throw ThreadException("Running on invalid thread " + currentThread);
        }
    }


    /**
     * Check that the current execution thread is not the main thread.
     *
     * @throws com.fsk.common.utils.threads.ThreadException
     *         when the execution thread is the main thread.
     */
   fun requireNotMainThread() {
        requireActiveThreadIsNot(getMainThread());
    }


    /**
     * Check that the current execution thread is the main thread.
     *
     * @throws com.fsk.common.utils.threads.ThreadException
     *         when the execution thread is not the main thread.
     */
    fun requireMainThread() {
        requireActiveThreadIs(getMainThread());
    }


    /**
     * Get the main thread.
     *
     * @return The main thread derived from the Main Looper.
     */
    fun getMainThread() : Thread {
        return Looper.getMainLooper().thread;
    }
