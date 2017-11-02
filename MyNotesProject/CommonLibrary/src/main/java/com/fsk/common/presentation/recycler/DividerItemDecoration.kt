package com.fsk.common.presentation.recycler

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * @param rect
 *  the rectangle that defines the item divider. The units are pixels.
 */
class DividerItemDecoration : RecyclerView.ItemDecoration {


    /**
     * Constructor.
     *
     * @param rect
     * *        the rectangle that defines the item divider. The units are pixels.
     */
    constructor(rect: Rect) {
        dividerRect = rect;
    }

    /**
     * Constructor.

     * @param horizontal
     * *         the divider horizontal in pixels.  This cannot be negative.
     * *         This value is used for both the top and bottom edge.
     * *
     * @param vertical
     * *         the vertical divider in pixels. This cannot be negative.
     * *         This value is used for both the left and right edge.
     */
    constructor(vertical: Int,
                horizontal: Int) : this(Rect(horizontal, vertical, horizontal, vertical));

    /**
     * Constructor.

     * @param pixels
     * *         a single value to use for each side of the rectangular divider.
     * *         This cannot be negative.
     */
    constructor(pixels: Int) : this(Rect(pixels, pixels, pixels, pixels)) {
    }


    /**
     * The rectangular divider.
     */
    protected val dividerRect: Rect;


    override fun getItemOffsets(outRect: Rect,
                                view: View,
                                parent: RecyclerView,
                                state: RecyclerView.State?) {
        outRect.top = dividerRect.top;
        outRect.right = dividerRect.right;
        outRect.bottom = dividerRect.bottom;
        outRect.left = dividerRect.left;
    }
}