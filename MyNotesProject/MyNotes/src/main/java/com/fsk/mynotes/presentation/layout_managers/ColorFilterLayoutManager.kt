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
class ColorFilterLayoutManager : LinearLayoutManager {

    constructor(context: Context) : super(context) {}

    constructor(context: Context,
                orientation: Int) : super(context, orientation, false) {
    }

    /**
     * The height/width for each child.
     */
    var childDimension: Int = 0;


    override fun onMeasure(recycler: RecyclerView.Recycler,
                           state: RecyclerView.State,
                           widthSpec: Int,
                           heightSpec: Int) {
        super.onMeasure(recycler, state, widthSpec, heightSpec);

        val itemCount = getItemCount();
        if ((itemCount == 0) && (childCount == 0)) {
            return;
        }

        //Determine the expected child dimension for the correct axis.
        when (orientation) {
            VERTICAL   ->
                childDimension = (height - paddingBottom + paddingTop) / itemCount;

            HORIZONTAL ->
                childDimension = (width - paddingLeft - paddingRight) / itemCount;
        }
    }


    override fun measureChildWithMargins(child: View,
                                         widthUsed: Int,
                                         heightUsed: Int) {
        super.measureChildWithMargins(child, widthUsed, heightUsed);

        //Determine the child height/width.
        val lp: MarginLayoutParams = child.layoutParams as MarginLayoutParams;
        var width = lp.width;
        var height = lp.height;
        when (orientation) {
            VERTICAL   ->
                height = childDimension - getBottomDecorationHeight(child) -
                         getTopDecorationHeight(child);
            HORIZONTAL ->
                width = childDimension - getLeftDecorationWidth(child) -
                        getRightDecorationWidth(child);
        }

        //Determine the height and width specs and re-measure the child.
        val widthSpec = getChildMeasureSpec(getWidth(),
                                            paddingLeft + paddingRight +
                                            lp.leftMargin + lp.rightMargin + widthUsed,
                                            width,
                                            canScrollHorizontally());
        val heightSpec =
                getChildMeasureSpec(getHeight(),
                                    paddingTop + paddingBottom +
                                    lp.topMargin + lp.bottomMargin + heightUsed,
                                    height,
                                    canScrollVertically());
        child.measure(widthSpec, heightSpec);
    }
}
