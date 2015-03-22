package com.fsk.mynotes.presentation.animations.filter_toolbar;


import android.animation.Animator;
import android.test.AndroidTestCase;
import android.view.View;

/**
 * Test the {@link com.fsk.mynotes.data.NotesManager}.
 */
public class ToolbarAnimatorListenerTest extends AndroidTestCase {


    public void testAnimatorListenerWithNullTarget() {
        try {
            new ToolbarAnimatorListener(View.GONE, null);
            assert false;
        }
        catch (NullPointerException e) {
            assert true;
        }

    }

    public void testAnimatorListenerWithInvalidVisibility() {
        try {
            new ToolbarAnimatorListener(1000, new View(getContext()));
            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
    }


    public void testAnimatorListenerSunnyDay() {
        View view = new View(getContext());
        view.setVisibility(View.INVISIBLE);

        MockListener listener = new MockListener(View.GONE, view);
        listener.setExpectedVisibilities(View.VISIBLE, -1, View.GONE);
        view.animate().scaleX(2f).setDuration(10).setListener(listener).start();

    }



    public void testAnimatorListenerSunnyDayOnCancel() {
        View view = new View(getContext());
        view.setVisibility(View.INVISIBLE);

        MockListener listener = new MockListener(View.GONE, view);
        listener.setExpectedVisibilities(View.VISIBLE, View.GONE, View.GONE);
        view.animate().scaleX(2f).setDuration(10).setListener(listener).start();
        view.animate().scaleX(2f).setDuration(10).start();
    }


    /**
     * Mock version of the {@link ToolbarAnimatorListener} that validates visibilities
     */
    private class MockListener extends ToolbarAnimatorListener {

        private int mExpectedStartVisibility;
        private int mExpectedEndVisibility;
        private int mExpectedCancelVisibility;


        private MockListener(final int endVisibility, final View target) {
            super(endVisibility, target);
        }

        public void setExpectedVisibilities(int start, int cancel, int end) {
            mExpectedStartVisibility = start;
            mExpectedCancelVisibility = cancel;
            mExpectedEndVisibility = end;
        }

        @Override
        public void onAnimationStart(final Animator animation) {
            super.onAnimationStart(animation);
            assertEquals(mExpectedStartVisibility, mTarget.getVisibility());
        }


        @Override
        public void onAnimationEnd(final Animator animation) {
            super.onAnimationEnd(animation);
            assertEquals(mExpectedEndVisibility, mTarget.getVisibility());
        }


        @Override
        public void onAnimationCancel(final Animator animation) {
            super.onAnimationCancel(animation);
            assertEquals(mExpectedCancelVisibility, mTarget.getVisibility());
        }
    }
}