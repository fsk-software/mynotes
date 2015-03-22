package com.fsk.mynotes.presentation.animations.filter_toolbar;


import android.animation.Animator;
import android.view.View;

import com.fsk.mynotes.presentation.animations.SimpleAnimatorListener;
import com.google.common.base.Preconditions;

/**
 * A listener to react to changes in the {@link Animator} states.
 */
public class ToolbarAnimatorListener extends SimpleAnimatorListener {

    /**
     * The final visibility for the {@link #mTarget} that will be set upon the animation ending.
     */
    protected final int mEndVisibility;


    /**
     * The target for the animation.
     */
    protected final View mTarget;


    /**
     * Constructor.
     *
     * @param endVisibility the visibility for the target when the animator completes. This must be
     *                      {@link android.view.View#GONE}, {@link android.view.View#VISIBLE},
     *                      or {@link android.view.View#INVISIBLE}.
     * @param target The view being animated.
     */
    public ToolbarAnimatorListener(final int endVisibility, final View target) {
        Preconditions.checkNotNull(target);
        Preconditions.checkArgument((endVisibility == View.VISIBLE) ||
                                    (endVisibility ==View.INVISIBLE) ||
                                    (endVisibility == View.GONE));

        mEndVisibility = endVisibility;
        mTarget = target;
    }


    @Override
    public void onAnimationStart(final Animator animation) {
        super.onAnimationStart(animation);
        mTarget.setVisibility(View.VISIBLE);
    }


    @Override
    public void onAnimationEnd(final Animator animation) {
        super.onAnimationEnd(animation);
        mTarget.setVisibility(mEndVisibility);
    }


    @Override
    public void onAnimationCancel(final Animator animation) {
        super.onAnimationCancel(animation);
        mTarget.setVisibility(mEndVisibility);
    }
}

