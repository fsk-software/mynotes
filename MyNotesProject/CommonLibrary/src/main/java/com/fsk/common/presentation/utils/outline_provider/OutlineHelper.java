package com.fsk.common.presentation.utils.outline_provider;


import android.annotation.TargetApi;
import android.graphics.Outline;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.google.common.base.Preconditions;

/**
 * A builder to setup the outline for a target view.
 */
public class OutlineHelper {


    /**
     * The target view that will be setup to setup its outline.
     */
    protected View mTarget;


    /**
     * The shape of the outline.
     */
    protected OutlineShape mShape = OutlineShape.OVAL;


    /**
     * The radius for the rounded rectangle in device-dependent pixels.
     */
    protected int mRoundedRectangleRadius;


    /**
     * Set the target View.
     *
     * @param target
     *         the target view that will have its outline updated.
     *
     * @return This instance to support call chaining.
     */
    public OutlineHelper setTarget(@NonNull View target) {
        mTarget = target;
        return this;
    }


    /**
     * Set the outline shape.
     *
     * @param shape
     *         The shape for the outline.
     *
     * @return This instance to support call chaining.
     */
    public OutlineHelper setOutlineShape(@NonNull OutlineShape shape) {
        mShape = shape;
        return this;
    }


    /**
     * Set the rounded rectangle corner radius.
     *
     * @param radius
     *         The corner radius in device dependent pixels.  It must be a natural number.
     *
     * @return This instance to support call chaining.
     */
    public OutlineHelper setRoundRectRadius(int radius) {
        mRoundedRectangleRadius = radius;
        return this;
    }


    /**
     * Invalidate the outline of the target view.
     */
    @TargetApi(21)
    public void invalidateOutline() {
        Preconditions.checkNotNull(mTarget);
        mTarget.invalidateOutline();
    }


    /**
     * Sets the outline provider and ensure the target view clips to the outline.
     */
    @TargetApi(21)
    public void setup() {
        validate();

        mTarget.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(final View view, final Outline outline) {
                updateViewOutline(outline);
            }
        });

        mTarget.setClipToOutline(true);
    }


    /**
     * Validate that the builder values are valid.
     */
    protected void validate() {
        Preconditions.checkNotNull(mTarget);
        Preconditions.checkNotNull(mShape);
        Preconditions.checkArgument(mRoundedRectangleRadius >= 0);
    }


    /**
     * Update the view outline to the specified outline.
     *
     * @param outline
     *         the outline for the target view.
     */
    @TargetApi(21)
    protected void updateViewOutline(Outline outline) {
        int height = mTarget.getHeight();
        int width = mTarget.getWidth();

        switch (mShape) {
            case OVAL:
                outline.setOval(0, 0, width, height);
                break;
            case RECTANGLE:
                outline.setRect(0, 0, width, height);
                break;
            case ROUNDED_RECTANGLE:
                outline.setRoundRect(0, 0, width, height, mRoundedRectangleRadius);
                break;
        }
    }
}
