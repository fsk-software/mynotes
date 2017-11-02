package com.fsk.mynotes.presentation.animators;

import android.animation.Animator
import android.animation.AnimatorInflater

/**
 * Created by me on 5/23/2016.
 */
class ColorPaletteAnimatorHelperCompat(reveal: Boolean) : ColorPaletteAnimatorHelper(reveal) {

    /**
     * Sets the outline provider and ensure the target view clips to the outline.
     */
    override fun build(): Animator {
        require(target != null);

        val animatorId = if (reveal) com.fsk.common.R.animator.slide_down else com.fsk.common.R.animator.slide_up;
        val returnValue = AnimatorInflater.loadAnimator(target?.context, animatorId);
        returnValue.setTarget(target);

        if (animatorListener != null) {
            returnValue.addListener(animatorListener);
        }

        return returnValue;
    }
}
