package com.fsk.mynotes.presentation.animations;


import android.os.Build;
import android.test.AndroidTestCase;

import com.fsk.mynotes.presentation.animations.filter_toolbar.ToolbarAnimatorBuilder;
import com.fsk.mynotes.presentation.animations.filter_toolbar.ToolbarAnimatorBuilderCompat;

/**
 * Tests {@link com.fsk.mynotes.presentation.animations.AnimationBuilderFactory}
 */
public class AnimationBuilderFactoryTest extends AndroidTestCase {


    public void testGeToolbarFilterAnimation() {
        ToolbarAnimatorBuilder actual = AnimationBuilderFactory.getFilterToolbarAnimatorBuilder();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            assertFalse(actual instanceof ToolbarAnimatorBuilderCompat);
        }
        else {
            assertTrue(actual instanceof ToolbarAnimatorBuilderCompat);
        }
    }
}