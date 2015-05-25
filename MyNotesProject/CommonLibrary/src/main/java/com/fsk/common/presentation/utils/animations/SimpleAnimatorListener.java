package com.fsk.common.presentation.utils.animations;


import android.animation.Animator;

/**
 * Animator Listener that defaults the Interface methods to be empty. This allows this classes
 * children to only implement the methods they want.
 */
public abstract class SimpleAnimatorListener implements Animator.AnimatorListener {


    @Override
    public void onAnimationStart(final Animator animation) {
        //Space Available For Rent
    }


    @Override
    public void onAnimationEnd(final Animator animation) {
        //Space Available For Rent
    }


    @Override
    public void onAnimationCancel(final Animator animation) {
        //Space Available For Rent
    }


    @Override
    public void onAnimationRepeat(final Animator animation) {
        //Space Available For Rent
    }
}