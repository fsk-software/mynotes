package com.fsk.common.presentation.utils.animations;


import android.animation.Animator;
import android.animation.ObjectAnimator;

import com.fsk.common.presentation.components.CheckableImageView;

import org.junit.Test;

/**
 * Tests {@link CheckableImageView}
 */
public class SimpleAnimatorListenerTest {

    @Test
    public void testEmptyMethods() {
        SimpleAnimatorListener listener = new SimpleAnimatorListener() {};
        Animator animator = new ObjectAnimator();
        listener.onAnimationStart(animator);
        listener.onAnimationCancel(animator);
        listener.onAnimationEnd(animator);
        listener.onAnimationRepeat(animator);
        //Basically, just making sure nothing crashes.
    }
}
