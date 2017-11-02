package com.fsk.common.presentation.checkable

import android.widget.Checkable


/**
 * Interface definition for a {@link Checkable} that supports a listener that notifies when
 * the checked state changes.
 */
interface CheckableListenerSupport : Checkable {


    /**
     * Register a callback to be invoked when the checked state changes.

     * @param listener
     * *         the callback to call on checked state change
     */
    fun setOnCheckedChangeListener(listener: OnCheckedChangeListener)
}