package com.fsk.common.presentation.components

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * A layout that allows animators to use relative percentages for doing x and y translations.
 */
class FractionalFrameLayout : FrameLayout {

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
     * Constructor that is called when inflating a view from XML.
     *
     * @param context
     *         The Context to associate with the view.
     * @param attrs
     *         the Attributes to customize the view.
     */
    constructor(context: Context,
                attrs: AttributeSet?,
                defStyleAttr: Int) : super(context, attrs, defStyleAttr);

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
                defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes);

    /**
     * The last requested y-fraction.
     */
    private var yFraction = 0f;


    /**
     * The last requested x-fraction.
     */
    private var xFraction = 0f;


    override fun onMeasure(widthMeasureSpec: Int,
                           heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (height != 0) {
            setYFraction(yFraction)
        }

        if (width != 0) {
            setXFraction(xFraction)
        }
    }


    /**
     * Get the current horizontal percentage of the view that is visible.

     * @return the current horizontal percentage of the view that is visible.
     */
    fun getXFraction(): Float {
        val width = width
        return if (width == 0) 0f else (x / width.toFloat());
    }


    /**
     * Set the current horizontal percentage of the view that is visible.

     * @return the current horizontal percentage of the view that is visible.
     */
    fun setXFraction(xFraction: Float) {
        this.xFraction = xFraction
        val width = width
        if (width != 0) {
            x = xFraction * width
        }
    }


    fun getYFraction(): Float {
        val height = height
        return if (height == 0) 0f else (y / height.toFloat());
    }


    /**
     * Set the current vertical percentage of the view that is visible.

     * @return the current vertical percentage of the view that is visible.
     */
    fun setYFraction(yFraction: Float) {
        this.yFraction = yFraction
        val height = height

        if (height != 0) {
            y = yFraction * height
        }
    }

}