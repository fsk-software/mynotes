package com.fsk.common.presentation.utils.animations;


import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * A helper class to help with background drawable animators
 */
public class BackgroundAnimatorHelper {

    /**
     * Animate a transition from one color to the next.
     *
     * @param view
     *         The view to animate.
     * @param fromColor
     *         the initial color for the animation.
     * @param toColor
     *         the final color for the animation.
     * @param duration
     *         The time in milliseconds for the animation.
     * @param animatorListener
     *         The listener for the animation.
     */
    public static void crossBlendColors(@NonNull View view, int fromColor, int toColor,
                                        int duration, Animator.AnimatorListener animatorListener) {

        view.animate().cancel();

        ObjectAnimator animator = ObjectAnimator
                .ofObject(view, "backgroundColor", new ArgbEvaluator(), fromColor, toColor);
        if (animatorListener != null) {
            animator.addListener(animatorListener);
        }
        animator.setDuration(duration);
        animator.start();
    }


    /**
     * Animate a transition from one color resource to the next.
     *
     * @param view
     *         The view to animate.
     * @param fromColorResource
     *         the initial color resource for the animation.
     * @param toColorResource
     *         the final color resource for the animation.
     * @param duration
     *         The time in milliseconds for the animation.
     * @param animatorListener
     *         The listener for the animation.
     */
    public static void crossBlendColorResource(@NonNull View view, int fromColorResource,
                                               int toColorResource, int duration,
                                               Animator.AnimatorListener animatorListener) {

        Resources resources = view.getResources();
        int fromRgb = resources.getColor(fromColorResource);
        int toRgb = resources.getColor(toColorResource);
        crossBlendColors(view, fromRgb, toRgb, duration, animatorListener);
    }
}
