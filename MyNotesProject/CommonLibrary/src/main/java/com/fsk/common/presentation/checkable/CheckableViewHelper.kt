package com.fsk.common.presentation.checkable

import android.content.Context
import android.util.AttributeSet
import android.widget.Checkable
import com.fsk.common.R

/**
 * An array of states for the view.
 */
val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked);


/*
 * A helper to simplify the common behaviors for a View tied to a checkable state.
 * @param context
 * *         the context to access the application resources.
 */
class CheckableViewHelper(context: Context) {


    /**
     * The checked on accessibility text.
     */
    private var checkedOnAccessibilityText = ""


    /**
     * The not checked accessibility text.
     */
    private var checkedOffAccessibilityText = ""


    /**
     * The listener to notify when checked state changes.
     */
    private var onCheckedChangeListener: OnCheckedChangeListener? = null

    init {
        checkedOffAccessibilityText = context.getString(
                R.string.accessibility_description_not_checked)
        checkedOnAccessibilityText = context.getString(R.string.accessibility_description_checked)
    }

    /**
     * Read the checkable attributes.

     * @param context
     * *         the context to use for obtaining the attributes.
     * *
     * @param attrs
     * *         the attribute set.
     * *
     * *
     * @return true if the initial checked state is true.
     */
    fun readCheckableAttributes(context: Context?,
                                attrs: AttributeSet?): Boolean {
        if (attrs == null || context == null) {
            return false
        }

        var returnValue = false
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Checkable)

        for (i in 0..typedArray.indexCount - 1) {
            val attr = typedArray.getIndex(i)
            if (attr == R.styleable.Checkable_checkedOnText) {
                checkedOnAccessibilityText = typedArray.getString(attr);
            }
            else if (attr == R.styleable.Checkable_checkedOffText) {
                checkedOffAccessibilityText = typedArray.getString(attr);
            }
            else if (attr == R.styleable.Checkable_initiallyChecked) {
                returnValue = typedArray.getBoolean(attr, false);
            }
        }

        typedArray.recycle()

        return returnValue
    }


    /**
     * Register a callback to be invoked when the checked state changes.

     * @param listener
     * *         the callback to call on checked state change
     */
    fun setOnCheckedChangeListener(listener: OnCheckedChangeListener) {
        onCheckedChangeListener = listener
    }


    /**
     * Send the [OnCheckedChangeListener] a notification with the checked status.

     * @param checkable
     * *         the checkable to include in the notification.
     */
    fun sendChangedNotification(checkable: Checkable) {

        if (onCheckedChangeListener != null) {
            onCheckedChangeListener!!.onCheckedChanged(checkable)
        }
    }


    /**
     * Get the accessibility text for the checked state.

     * @return the accessibility text for the checked state.
     */
    fun getAccessibilityTextForCheckedState(checked: Boolean): String {
        return if (checked) checkedOnAccessibilityText
        else checkedOffAccessibilityText
    }
}