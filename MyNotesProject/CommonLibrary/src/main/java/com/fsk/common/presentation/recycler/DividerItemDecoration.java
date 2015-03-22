package com.fsk.common.presentation.recycler;


import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.common.base.Preconditions;

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
     * @param height
     *         the height of the divider in pixels.  This cannot be negative.
     * @param height
     *         the width of the divider in pixels. This cannot be negative.
     */
    public DividerItemDecoration(int height, int width) {
        Preconditions.checkArgument(height > 0);
        Preconditions.checkArgument(width > 0);

        mDividerRect = new Rect(height, width, height, width);
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