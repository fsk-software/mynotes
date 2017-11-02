package com.fsk.common.presentation.checkable

import android.widget.Checkable

/**
 * Interface definition for a callback to be invoked when the checked state of a compound button
 * changes.
 */
interface OnCheckedChangeListener {

    /**
     * Called when the checked state changes.

     * @param checkable
     * *         The object whose state has changed.
     */
    fun onCheckedChanged(checkable: Checkable)
}