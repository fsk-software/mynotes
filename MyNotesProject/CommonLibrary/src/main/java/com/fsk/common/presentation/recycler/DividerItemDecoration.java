package com.fsk.common.presentation.recycler;


import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fsk.common.utils.Preconditions;


/**
 * An item decoration to mimic a divider between the items.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * The rectangular divider.
     */
    protected final Rect mDividerRect;


    /**
     * Constructor.
     *
     * @param rect
     *         the rectangle that defines the item divider. The units are pixels.
     */
    public DividerItemDecoration(@NonNull Rect rect) {
        Preconditions.checkNotNull(rect);
        mDividerRect = rect;
    }


    /**
     * Constructor.
     *
     * @param horizontal
     *         the divider horizontal in pixels.  This cannot be negative.
     *         This value is used for both the top and bottom edge.
     * @param vertical
     *         the vertical divider in pixels. This cannot be negative.
     *         This value is used for both the left and right edge.
     */
    public DividerItemDecoration(int vertical, int horizontal) {
        Preconditions.checkArgument(vertical >= 0);
        Preconditions.checkArgument(horizontal >= 0);

        mDividerRect = new Rect(horizontal, vertical, horizontal, vertical);
    }


    /**
     * Constructor.
     *
     * @param pixels
     *         a single value to use for each side of the rectangular divider.
     *         This cannot be negative.
     */
    public DividerItemDecoration(int pixels) {
        Preconditions.checkArgument(pixels > 0);

        mDividerRect = new Rect(pixels, pixels, pixels, pixels);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.top = mDividerRect.top;
        outRect.right = mDividerRect.right;
        outRect.bottom = mDividerRect.bottom;
        outRect.left = mDividerRect.left;
    }
}