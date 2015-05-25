package com.fsk.common.presentation.utils.outline_provider;


import android.graphics.Outline;

/**
 * A builder to setup the outline for a target view.  This builder is compatible with pre-Lollipop
 * builds.
 */
public class OutlineHelperCompat extends OutlineHelper {

    @Override
    public void setup() {
    }


    @Override
    public void invalidateOutline() {
    }


    @Override
    protected void updateViewOutline(final Outline outline) {}
}
