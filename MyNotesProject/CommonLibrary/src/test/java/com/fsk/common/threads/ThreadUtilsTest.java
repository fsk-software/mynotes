package com.fsk.common.threads;


import android.os.Looper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Unit Tests for the {@link ThreadUtils}
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Looper.class)
public class ThreadUtilsTest {

    @Mock
    private Looper mMockLooper;

    @Mock
    private Thread mMockThread;

    @Before
    public void prepareForTest() {
        mockStatic(Looper.class);
        when(Looper.getMainLooper()).thenReturn(mMockLooper);
    }

    @Test
    public void testCheckOffThreadTrue() {
        new ThreadUtils().checkOffThread(new Thread());
    }

    @Test
    public void testCheckOffThreadFalse() {

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    new ThreadUtils().checkOffThread(Thread.currentThread());
                    assert false;
                }
                catch (ThreadException e) {}
            }
        }.run();
    }


    @Test
    public void testCheckOnThreadTrue() {
        new ThreadUtils().checkOnThread(Thread.currentThread());
    }

    @Test
    public void testCheckOnThreadFalse() {

                try {
                    new ThreadUtils().checkOnThread(new Thread());
                    assert false;
                }
                catch (ThreadException e) {}
    }

    @Test
    public void testCheckOffUiThreadIsTrue() {
        when(mMockLooper.getThread()).thenReturn(mMockThread);
        new ThreadUtils().checkOffUIThread();
    }


    @Test
    public void testCheckOffUiThreadIsFalse() {
        when(mMockLooper.getThread()).thenReturn(Thread.currentThread());
        try {
            new ThreadUtils().checkOffUIThread();
            assert false;
        }
        catch (ThreadException e) {}
    }

    @Test
    public void testCheckOnUiThreadIsTrue() {
        when(mMockLooper.getThread()).thenReturn(Thread.currentThread());
        new ThreadUtils().checkOnUIThread();
    }


    @Test
    public void testCheckOnUiThreadIsFalse() {
        when(mMockLooper.getThread()).thenReturn(mMockThread);
        try {
            new ThreadUtils().checkOnUIThread();
            assert false;
        }
        catch (ThreadException e) {}
    }


    /**
     * Test the {@link ThreadUtils#getUIThread()} method.
     */
    @Test
    public void testVerifyUIThreadGetter() {

        when(mMockLooper.getThread()).thenReturn(Thread.currentThread());
        assertThat(Thread.currentThread(), is( new ThreadUtils().getUIThread()));
    }
}
