package com.fsk.mynotes.presentation.animations.filter_toolbar;


import android.animation.Animator;
import android.animation.AnimatorInflater;

import com.fsk.mynotes.R;

/**
 * A builder to create the toolbar opening and closing animations for Pre-Lollipop devices.
 */
public class ToolbarAnimatorBuilderCompat extends ToolbarAnimatorBuilder {
    @Override
    protected Animator createAnimator() {
        int animatorResource =
                (mReveal) ? R.animator.filter_toolbar_open : R.animator.filter_toolbar_close;

        int pivotX = (int) mTarget.getX() + mTarget.getWidth();
        mTarget.setPivotX(pivotX);
        mTarget.setPivotY(0);

        return AnimatorInflater.loadAnimator(mTarget.getContext(), animatorResource);
    }
}
