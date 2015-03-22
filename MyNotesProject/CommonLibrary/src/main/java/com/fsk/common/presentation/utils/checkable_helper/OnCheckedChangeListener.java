package com.fsk.common.presentation.utils.checkable_helper;


import android.widget.Checkable;

/**
 * Interface definition for a callback to be invoked when the checked state of a compound button
 * changes.
 */
public interface OnCheckedChangeListener {

    /**
     * Called when the checked state changes.
     *
     * @param checkable
     *         The object whose state has changed.
     */
    void onCheckedChanged(Checkable checkable);
}
