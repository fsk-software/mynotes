package com.fsk.mynotes.presentation.animators;

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.view.View
import android.view.ViewAnimationUtils

/**
 * Created by me on 5/23/2016.
 */
open class ColorPaletteAnimatorHelper(val reveal: Boolean) {

    /**
     * The target view that will be setup to setup its outline.
     */
    var target: View? = null;

    var originX: Int = 0;
    var originY: Int = 0;


    var animatorListener: AnimatorListener? = null;


    fun setOriginPoint(x: Int,
                       y: Int) {
        originX = x
        originY = y
    }


    /**
     * Sets the outline provider and ensure the target view clips to the outline.
     */
    open fun build(): Animator {
        require(target != null);

        val targetRadius = (target?.measuredWidth ?: 0) / 2;
        val startRadius: Float;
        val endRadius: Float;
        if (reveal) {
            startRadius = 0f;
            endRadius = targetRadius.toFloat();
        }
        else {
            startRadius = targetRadius.toFloat();
            endRadius = 0f;
        }

        val returnValue = ViewAnimationUtils.createCircularReveal(target,
                                                                  originX, originY,
                                                                  startRadius, endRadius);
        if (animatorListener != null) {
            returnValue.addListener(animatorListener);
        }

        return returnValue;

    }
}
