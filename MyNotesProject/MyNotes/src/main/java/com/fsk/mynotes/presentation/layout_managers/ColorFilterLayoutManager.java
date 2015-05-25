package com.fsk.mynotes.presentation.layout_managers;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;

/**
 * A {@link LinearLayoutManager} that tries to make all of the children have the same height or
 * width and fit on the screen without requiring scrolling.
 */
public class ColorFilterLayoutManager extends LinearLayoutManager {

    /**
     * The height/width for each child.
     */
    private int mChildDimension;


    /**
     * Constructor.
     *
     * @param context
     *         The context to use for accessing application resources.
     */
    public ColorFilterLayoutManager(final Context context) {
        super(context);
    }


    /**
     * Constructor.
     *
     * @param context
     *         The context to use for accessing application resources.
     * @param orientation
     *         The orientation of the Views. The only valid values are {@link
     *         LinearLayoutManager#VERTICAL} or {@link LinearLayoutManager#HORIZONTAL}.
     */
    public ColorFilterLayoutManager(final Context context, int orientation) {
        super(context, orientation, false);
    }


    @Override
    public void onMeasure(final RecyclerView.Recycler recycler, final RecyclerView.State state,
                          final int widthSpec, final int heightSpec) {
        super.onMeasure(recycler, state, widthSpec, heightSpec);

        int itemCount = getItemCount();
        if ((itemCount == 0) && (getChildCount() == 0)) {
            return;
        }

        //Determine the expected child dimension for the correct axis.
        switch (getOrientation()) {
            case VERTICAL:
                mChildDimension = (getHeight() - getPaddingBottom() + getPaddingTop()) / itemCount;
                break;
            case HORIZONTAL:
                mChildDimension = (getWidth() - getPaddingLeft() - getPaddingRight()) / itemCount;
                break;
        }
    }


    @Override
    public void measureChildWithMargins(final View child, final int widthUsed,
                                        final int heightUsed) {
        super.measureChildWithMargins(child, widthUsed, heightUsed);

        //Determine the child height/width.
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        int width = lp.width;
        int height = lp.height;
        switch (getOrientation()) {
            case VERTICAL:
                height = mChildDimension - getBottomDecorationHeight(child) -
                         getTopDecorationHeight(child);
                break;
            case HORIZONTAL:
                width = mChildDimension - getLeftDecorationWidth(child) -
                        getRightDecorationWidth(child);
                break;
        }

        //Determine the height and width specs and re-measure the child.
        final int widthSpec = getChildMeasureSpec(getWidth(),
                                                  getPaddingLeft() + getPaddingRight() +
                                                  lp.leftMargin + lp.rightMargin + widthUsed,
                                                  width,
                                                  canScrollHorizontally());
        final int heightSpec =
                getChildMeasureSpec(getHeight(),
                                    getPaddingTop() + getPaddingBottom() +
                                    lp.topMargin + lp.bottomMargin + heightUsed,
                                    height,
                                    canScrollVertically());
        child.measure(widthSpec, heightSpec);
    }
}
