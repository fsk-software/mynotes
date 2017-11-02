package com.fsk.common.presentation.animations

import android.animation.Animator

/**
 * Animator Listener that defaults the Interface methods to be empty. This allows this classes
 * children to only implement the methods they want.
 */
open class SimpleAnimatorListener : Animator.AnimatorListener {

    override fun onAnimationStart(animation: Animator) {
        //Space Available For Rent
    }


    override fun onAnimationEnd(animation: Animator) {
        //Space Available For Rent
    }


    override fun onAnimationCancel(animation: Animator) {
        //Space Available For Rent
    }


    override fun onAnimationRepeat(animation: Animator) {
        //Space Available For Rent
    }
}