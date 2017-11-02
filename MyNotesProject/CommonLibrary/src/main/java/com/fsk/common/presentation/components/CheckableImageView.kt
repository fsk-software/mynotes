package com.fsk.common.presentation.components

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.fsk.common.presentation.checkable.CHECKED_STATE_SET
import com.fsk.common.presentation.checkable.CheckableListenerSupport
import com.fsk.common.presentation.checkable.CheckableViewHelper
import com.fsk.common.presentation.checkable.OnCheckedChangeListener

/**
 * An Image View that also supports being checkable and notifying listeners of changes to the
 * checked state.
 */
class CheckableImageView : ImageView, CheckableListenerSupport {

    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context
     *         The Context to associate with the view
     */
    constructor(context: Context) : this(context, null);

    /**
     * Constructor that is called when inflating a view from XML.
     *
     * @param context
     *         The Context to associate with the view.
     * @param attrs
     *         the Attributes to customize the view.
     */
    constructor(context: Context,
                attrs: AttributeSet?) : this(context, attrs, 0);


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
    constructor(context: Context,
                attrs: AttributeSet?,
                defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize(attrs);
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
     * @param defStyleRes
     *         The style resource.
     */
    constructor(context: Context,
                attrs: AttributeSet,
                defStyleAttr: Int,
                defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        initialize(attrs);
    }


    /**
     * The debug logging tag.
     */
    private val TAG = CheckableImageView::class.java.name;


    /**
     * The checkable view helper.
     */
    private var mCheckableViewHelper: CheckableViewHelper = CheckableViewHelper(context);


    /**
     * flag to indicate the checked state.
     */
    internal var isChecked: Boolean = false;

    /**
     * Initialize the
     */
    private fun initialize(attrs: AttributeSet?) {

        isClickable = true

        if (attrs != null) {
            isChecked = mCheckableViewHelper.readCheckableAttributes(context, attrs);
            contentDescription = mCheckableViewHelper.getAccessibilityTextForCheckedState(
                    isChecked);
        }
    }

    override fun setChecked(checked: Boolean) {
        if (checked != isChecked) {
            isChecked = checked
            refreshDrawableState()

            mCheckableViewHelper.sendChangedNotification(this)
            contentDescription = mCheckableViewHelper.getAccessibilityTextForCheckedState(isChecked)
        }
    }


    override fun setOnCheckedChangeListener(listener: OnCheckedChangeListener) {
        mCheckableViewHelper.setOnCheckedChangeListener(listener)
    }


    override fun isChecked(): Boolean {
        return isChecked
    }


    override fun toggle() {
        isChecked = !isChecked
    }


    /**
     * Override performClick() so that we can toggle the checked state when the view is clicked
     */
    override fun performClick(): Boolean {
        toggle()
        return super.performClick()
    }


    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        }
        return drawableState
    }
}