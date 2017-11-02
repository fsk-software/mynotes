@file:JvmName("BkgAnimatorHelper")

package com.fsk.common.presentation.animations

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.support.v4.content.ContextCompat
import android.view.View


/**
 * Animate a transition from one color to the next.

 *
 * @param fromColor
 * *         the initial color for the animation.
 * *
 * @param toColor
 * *         the final color for the animation.
 * *
 * @param duration
 * *         The time in milliseconds for the animation.
 * *
 * @param animatorListener
 * *         The listener for the animation.
 */
fun View.crossBlendColors(fromColor: Int,
                          toColor: Int,
                          delay: Int,
                          duration: Int,
                          animatorListener: Animator.AnimatorListener?) {

    animate().cancel()

    val animator = ObjectAnimator
            .ofObject(this, "backgroundColor", ArgbEvaluator(), fromColor, toColor)
    if (animatorListener != null) {
        animator.addListener(animatorListener)
    }
    animator.startDelay = delay.toLong()
    animator.duration = duration.toLong()
    animator.start()
}


/**
 * Animate a transition from one color resource to the next.

 * @param fromColorResource
 * *         the initial color resource for the animation.
 * *
 * @param toColorResource
 * *         the final color resource for the animation.
 * *
 * @param duration
 * *         The time in milliseconds for the animation.
 * *
 * @param animatorListener
 * *         The listener for the animation.
 */
fun View.crossBlendColorResource(fromColorResource: Int,
                                 toColorResource: Int,
                                 delay: Int,
                                 duration: Int,
                                 animatorListener: Animator.AnimatorListener?) {

    val fromRgb = ContextCompat.getColor(context, fromColorResource);
    val toRgb = ContextCompat.getColor(context, toColorResource);
    crossBlendColors(fromRgb, toRgb, delay, duration, animatorListener)
}
