package com.fsk.common.presentation.components;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * A layout that allows animators to use relative percentages for doing x and y translations.
 */
public class FractionalFrameLayout extends FrameLayout {


    /**
     * The last requested y-fraction.
     */
    private float mRequestedYFraction;


    /**
     * The last requested x-fraction.
     */
    private float mRequestedXFraction;


    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context
     *         The Context to associate with the view
     */
    public FractionalFrameLayout(final Context context) {
        this(context, null);
    }


    /**
     * Constructor that is called when inflating a view from XML.
     *
     * @param context
     *         The Context to associate with the view.
     * @param attrs
     *         the Attributes to customize the view.
     */
    public FractionalFrameLayout(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }


    /**
     * Constructor that is called when inflating a view from XML.
     *
     * @param context
     *         The Context to associate with the view.
     * @param attrs
     *         the Attributes to customize the view.
     */
    public FractionalFrameLayout(final Context context, final AttributeSet attrs,
                                 final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
    public FractionalFrameLayout(final Context context, final AttributeSet attrs,
                                 final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getHeight() != 0) {
            setYFraction(mRequestedYFraction);
        }

        if (getWidth() != 0) {
            setXFraction(mRequestedXFraction);
        }
    }


    /**
     * Get the current horizontal percentage of the view that is visible.
     *
     * @return the current horizontal percentage of the view that is visible.
     */
    public float getXFraction() {
        int width = getWidth();
        return (width == 0) ? 0 : getX() / (float) width;
    }


    /**
     * Set the current horizontal percentage of the view that is visible.
     *
     * @return the current horizontal percentage of the view that is visible.
     */
    public void setXFraction(float xFraction) {
        mRequestedXFraction = xFraction;
        int width = getWidth();
        if (width != 0) {
            setX(xFraction * width);
        }
    }


    public float getYFraction() {
        int height = getHeight();
        return (height == 0) ? 0 : getY() / (float) height;
    }


    /**
     * Set the current vertical percentage of the view that is visible.
     *
     * @return the current vertical percentage of the view that is visible.
     */
    public void setYFraction(float yFraction) {
        mRequestedYFraction = yFraction;
        int height = getHeight();

        if (height != 0) {
            setY(yFraction * height);
        }
    }
}
