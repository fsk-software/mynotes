package com.fsk.mynotes.presentation.animations.filter_toolbar;


import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.google.common.base.Preconditions;

/**
 * A builder to create the toolbar opening and closing animations.
 */
public class ToolbarAnimatorBuilder {

    /**
     * The focal x-coordinate for the animation.
     */
    protected int mX;


    /**
     * The focal y-coordinate for the animation.
     */
    protected int mY;


    /**
     * The widest radius for the animation.
     */
    protected int mRadius;


    /**
     * The target view for the animation.
     */
    protected View mTarget;


    /**
     * A flag to indicate if the target view is opening or closing.  true opens the toolbar.
     */
    protected boolean mReveal;


    /**
     * Set the focal point for the animation.
     *
     * @param x
     *         the coordinate along the x-axis.
     * @param y
     *         the coordinate along the y-axis.
     *
     * @return {@link ToolbarAnimatorBuilder} to allow for call chaining.
     */
    public ToolbarAnimatorBuilder setFocus(int x, int y) {
        mX = x;
        mY = y;

        return this;
    }


    /**
     * Set the widest radius for the animation.
     *
     * @param radius
     *         the widest radius for the animation.
     *
     * @return {@link ToolbarAnimatorBuilder} to allow for call chaining.
     */
    public ToolbarAnimatorBuilder setRadius(int radius) {
        mRadius = radius;

        return this;
    }


    /**
     * Set the target for the animation.
     *
     * @param target
     *         the {@link View} being revealed or hidden.
     *
     * @return {@link ToolbarAnimatorBuilder} to allow for call chaining.
     */
    public ToolbarAnimatorBuilder setTarget(View target) {
        mTarget = target;
        return this;
    }


    /**
     * Set a status indicating if the toolbar is being revealed or hidden.
     *
     * @param reveal
     *         true to reveal the toolbar.
     *
     * @return {@link ToolbarAnimatorBuilder} to allow for call chaining.
     */
    public ToolbarAnimatorBuilder setReveal(boolean reveal) {
        mReveal = reveal;
        return this;
    }


    /**
     * Build the {@link android.animation.Animator} based on the supplied configuration.
     *
     * @return the created animator.
     *
     * @throws java.lang.NullPointerException
     *         when the target view is null.
     * @throws java.lang.IllegalArgumentException
     *         when the radius is negative.
     */
    public Animator build() {
        validate();
        Animator returnValue = createAnimator();
        returnValue.setTarget(mTarget);
        returnValue.addListener(new ToolbarAnimatorListener(getEndVisibility(), mTarget));

        return returnValue;
    }


    /**
     * Validate the configuration is okay for building an {@link Animator}.
     *
     * @throws java.lang.NullPointerException
     *         when the target view is null.
     * @throws java.lang.IllegalArgumentException
     *         when the radius is negative.
     */
    protected void validate() {
        Preconditions.checkNotNull(mTarget);
        Preconditions.checkArgument(mRadius >= 0);
    }


    /**
     * Create the animator for the supplied configuration.
     *
     * @return an {@link android.animation.Animator} to hide or show the target.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected Animator createAnimator() {
        return ViewAnimationUtils
                .createCircularReveal(mTarget, mX, mY, getStartRadius(), getEndRadius());
    }


    /**
     * Get the visibility for the target view after the animation completes.
     *
     * @return {@link android.view.View#INVISIBLE}, {@link android.view.View#VISIBLE}, or {@link
     * android.view.View#GONE}
,.     */
    protected int getEndVisibility() {
        return (mReveal) ? View.VISIBLE : View.GONE;
    }


    /**
     * Get the starting radius for animation.
     *
     * @return the starting radius for the animation.
     */
    protected int getStartRadius() {
        return (mReveal) ? 0 : mRadius;
    }


    /**
     * Get the final radius for animation.
     *
     * @return the final radius for the animation.
     */
    protected int getEndRadius() {
        return (mReveal) ? mRadius : 0;
    }
}
