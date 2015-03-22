package com.fsk.common.presentation.utils.checkable_helper;


import android.widget.Checkable;

/**
 * Interface definition for a {@link Checkable} that supports a listener that notifies when
 * the checked state changes.
 */
public interface CheckableListenerSupport extends Checkable {


    /**
     * Register a callback to be invoked when the checked state changes.
     *
     * @param listener
     *         the callback to call on checked state change
     */
     void setOnCheckedChangeListener(OnCheckedChangeListener listener);
}
