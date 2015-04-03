package com.fsk.mynotes.presentation.animations;


import android.os.Build;

import com.fsk.mynotes.presentation.animations.filter_toolbar.ToolbarAnimatorBuilder;
import com.fsk.mynotes.presentation.animations.filter_toolbar.ToolbarAnimatorBuilderCompat;

/**
 * A factory to return Animation Builders.
 */
public enum AnimationBuilderFactory {
    ;


    /**
     * Creates the correct version of the {@link ToolbarAnimatorBuilder} to support the current
     * SDK.
     *
     * @return the correct version of the {@link ToolbarAnimatorBuilder} to support the current
     * SDK.
     */
    public static ToolbarAnimatorBuilder getFilterToolbarAnimatorBuilder() {
        return(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) ?
                                         new ToolbarAnimatorBuilder() :
                                         new ToolbarAnimatorBuilderCompat();
    }
}
