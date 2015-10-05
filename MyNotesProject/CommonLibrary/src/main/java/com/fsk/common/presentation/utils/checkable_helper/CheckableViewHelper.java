package com.fsk.common.presentation.utils.checkable_helper;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.Checkable;

import com.fsk.common.R;
import com.fsk.common.utils.Preconditions;


/*
 * A helper to simplify the common behaviors for a View tied to a checkable state.
 */
public class CheckableViewHelper {

    /**
     * An array of states for the view.
     */
    public static final int[] CHECKED_STATE_SET = { android.R.attr.state_checked };


    /**
     * The checked on accessibility text.
     */
    private String mCheckedOnAccessibilityText = "";


    /**
     * The not checked accessibility text.
     */
    private String mCheckedOffAccessibilityText = "";


    /**
     * The listener to notify when checked state changes.
     */
    private OnCheckedChangeListener mOnCheckedChangeListener;


    /**
     * Constructor.
     *
     * @param context
     *         the context to access the application resources.
     */
    public CheckableViewHelper(@NonNull Context context) {
        Preconditions.checkNotNull(context);

        mCheckedOffAccessibilityText =
                context.getString(R.string.accessibility_description_not_checked);
        mCheckedOnAccessibilityText = context.getString(R.string.accessibility_description_checked);
    }


    /**
     * Read the checkable attributes.
     *
     * @param context
     *         the context to use for obtaining the attributes.
     * @param attrs
     *         the attribute set.
     *
     * @return true if the initial checked state is true.
     */
    public boolean readCheckableAttributes(Context context, AttributeSet attrs) {
        if ((attrs == null) || (context == null)) {
            return false;
        }

        boolean returnValue = false;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Checkable);

        for (int i = 0; i < typedArray.getIndexCount(); ++i) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.Checkable_checkedOnText) {
                mCheckedOnAccessibilityText = typedArray.getString(attr);
            }
            else if (attr == R.styleable.Checkable_checkedOffText) {
                mCheckedOffAccessibilityText = typedArray.getString(attr);
            }
            else if (attr == R.styleable.Checkable_initiallyChecked) {
                returnValue = typedArray.getBoolean(attr, false);
            }
        }

        typedArray.recycle();

        return returnValue;
    }


    /**
     * Register a callback to be invoked when the checked state changes.
     *
     * @param listener
     *         the callback to call on checked state change
     */
    public void setOnCheckedChangeListener(final OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }


    /**
     * Send the {@link OnCheckedChangeListener} a notification with the checked status.
     *
     * @param checkable
     *         the checkable to include in the notification.
     */
    public void sendChangedNotification(Checkable checkable) {

        if ((checkable != null) && (mOnCheckedChangeListener != null)) {
            mOnCheckedChangeListener.onCheckedChanged(checkable);
        }
    }


    /**
     * Get the accessibility text for the checked state.
     *
     * @return the accessibility text for the checked state.
     */
    public String getAccessibilityTextForCheckedState(boolean checked) {
        return checked ? mCheckedOnAccessibilityText : mCheckedOffAccessibilityText;
    }
}
