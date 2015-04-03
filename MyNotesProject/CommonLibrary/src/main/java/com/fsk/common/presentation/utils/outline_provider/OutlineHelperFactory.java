package com.fsk.common.presentation.utils.outline_provider;


import android.os.Build;

/**
 * A singleton factory to return Outline Helpers.
 */
public class OutlineHelperFactory {


    /**
     * Creates the correct version of the {@link OutlineHelper} to support the current
     * SDK.
     *
     * @return the correct version of the {@link OutlineHelper} to support the current
     * SDK.
     */
    public static OutlineHelper getOutlineHelper() {
        return(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) ?
              new OutlineHelper() :
              new OutlineHelperCompat();
    }
}
