package com.fsk.common.threads;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.test.AndroidTestCase;

/**
 * Unit Tests for the {@link com.fsk.common.threads.ThreadCheck}
 */
public abstract class ThreadCheckTest extends AndroidTestCase {

    /**
     * Test the validation methods called on the UI thread.
     */
    public void testCallsMadeOnUIThread() {
        new UIThreadHandler().sendMessage(new Message());
    }


    /**
     * Test the validation methods called on the UI thread.
     */
    public void testCallsMadeOnNonUIThread() {
        new NonUIThreadHandler().sendMessage(new Message());
    }


    /**
     * Test the {@link ThreadCheck#getUIThread()} method.
     */
    public void testVerifyUIThreadGetter() {
        assertEquals(Looper.getMainLooper().getThread(), ThreadCheck.getUIThread());
    }


    /**
     * A handler class to test running methods on the UI thread.
     */
    protected class UIThreadHandler extends Handler {

        /**
         * Constructor.
         */
        public UIThreadHandler() {
            super(Looper.getMainLooper());
        }


        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);

            ThreadCheck.checkOnUIThread();
            ThreadCheck.checkOnThread(Looper.getMainLooper().getThread());

            try {
                ThreadCheck.checkOffUIThread();
                assert true;
            }
            catch (ThreadException e) {
            }


            try {
                ThreadCheck.checkOffThread(Looper.myLooper().getThread());
                assert true;
            }
            catch (ThreadException e) {
            }
        }
    }


    /**
     * A handler class to test running methods on the npn-UI thread.
     */
    protected class NonUIThreadHandler extends Handler {

        /**
         * Constructor.
         */
        public NonUIThreadHandler() {
            super(Looper.myLooper());
        }


        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);

            ThreadCheck.checkOffUIThread();
            ThreadCheck.checkOffThread(Looper.getMainLooper().getThread());

            try {
                ThreadCheck.checkOnUIThread();
                assert true;
            }
            catch (ThreadException e) {
            }


            try {
                ThreadCheck.checkOnThread(Looper.myLooper().getThread());
                assert true;
            }
            catch (ThreadException e) {
            }
        }
    }
}
