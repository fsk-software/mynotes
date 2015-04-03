package com.fsk.common.presentation.components;


import android.content.Context;
import android.util.AttributeSet;

import com.fsk.common.presentation.utils.checkable_helper.CheckableListenerSupport;
import com.fsk.common.presentation.utils.checkable_helper.CheckableViewHelper;
import com.fsk.common.presentation.utils.checkable_helper.OnCheckedChangeListener;

/**
 * A Floating Action Button that also supports being checkable and notifying listeners of changes to
 * the checked state.
 */
public class CheckableFloatingFrameLayout extends FloatingFrameLayout
        implements CheckableListenerSupport {


    /**
     * The debug logging tag.
     */
    private static final String TAG = CheckableFloatingFrameLayout.class.getName();


    /**
     * The checkable view helper.
     */
    final CheckableViewHelper mCheckableViewHelper;


    /**
     * flag to indicate the checked state.
     */
    boolean mChecked;


    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context
     *         The Context to associate with the view
     */
    public CheckableFloatingFrameLayout(Context context) {
        this(context, null, 0);
    }


    /**
     * Constructor that is called when inflating a view from XML.
     *
     * @param context
     *         The Context to associate with the view.
     * @param attrs
     *         the Attributes to customize the view.
     */
    public CheckableFloatingFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    /**
     * Constructor. Perform inflation from XML and apply a class-specific base style
     *
     * @param context
     *         The Context to associate with the view.
     * @param attrs
     *         the Attributes to customize the view.
     * @param defStyleAttr
     *         The class-specific base style.
     */
    public CheckableFloatingFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mCheckableViewHelper = new CheckableViewHelper(context);
        mChecked = mCheckableViewHelper.readCheckableAttributes(context, attrs);

        setContentDescription(mCheckableViewHelper.getAccessibilityTextForCheckedState(mChecked));
    }


    @Override
    public void setChecked(boolean checked) {
        if (checked != mChecked) {
            mChecked = checked;
            refreshDrawableState();

            mCheckableViewHelper.sendChangedNotification(this);
            setContentDescription(
                    mCheckableViewHelper.getAccessibilityTextForCheckedState(mChecked));
        }
    }


    @Override
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mCheckableViewHelper.setOnCheckedChangeListener(listener);
    }


    @Override
    public boolean isChecked() {
        return mChecked;
    }


    @Override
    public void toggle() {
        setChecked(!mChecked);
    }


    /**
     * Override performClick() so that we can toggle the checked state when the view is clicked
     */
    @Override
    public boolean performClick() {
        toggle();
        return super.performClick();
    }


    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CheckableViewHelper.CHECKED_STATE_SET);
        }
        return drawableState;
    }

}
