package com.fsk.common.presentation.utils.animations;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.test.AndroidTestCase;

import com.fsk.common.presentation.components.CheckableImageView;

/**
 * Tests {@link CheckableImageView}
 */
public class SimpleAnimatorListenerTest extends AndroidTestCase {


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
