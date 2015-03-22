package com.fsk.common.presentation.components;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.fsk.common.R;
import com.fsk.common.presentation.utils.outline_provider.OutlineShape;
import com.fsk.common.presentation.utils.outline_provider.OutlineHelper;
import com.fsk.common.presentation.utils.outline_provider.OutlineHelperCompat;
import com.fsk.common.versions.Versions;

/**
 * A Floating Action Button is a view distinguished by a circled icon floating above the UI, with
 * special motion behaviors.
 * <p/>
 * This class is based on the FloatingActionButton found in the Android samples.
 */
public class FloatingFrameLayout extends FrameLayout {

    /**
     * The tag to use for log statements.
     */
    private static final String TAG = FloatingFrameLayout.class.getName();


    /**
     * The radius to use for the rounded rectangle shape.
     */
    protected int mRoundedRectangleRadius = 0;


    /**
     * The shape of the button.  This cannot be null.
     */
    protected OutlineShape mShape = OutlineShape.OVAL;


    /**
     * The outline helper.
     */
    protected final OutlineHelper mOutlineHelper;


    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context
     *         The Context to associate with the view
     */
    public FloatingFrameLayout(Context context) {
        this(context, null, 0);
    }


    /**
     * Constructor that is called when inflating a view from XML.
     *
     * @param context
     *         The Context to associate with the view.
     * @param attrs
     *         the Attributes to customize the view.
     */
    public FloatingFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
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
     */
    public FloatingFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttributes(context, attrs);
        setClickable(true);

        mOutlineHelper =
                (Versions.isAtLeastLollipop()) ? new OutlineHelper() : new OutlineHelperCompat();
        mOutlineHelper.setTarget(this).setOutlineShape(mShape)
                      .setRoundRectRadius(mRoundedRectangleRadius).setup();
    }


    /**
     * Read the attributes from the xml file.
     *
     * @param context
     *         The Context to associated with the view.
     * @param attrs
     *         The attributes from the xml file.
     */
    private void readAttributes(@NonNull Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Shape);

        for (int i = 0; i < typedArray.getIndexCount(); ++i) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.Shape_shape) {
                mShape = OutlineShape.getShape(typedArray.getInt(attr, 0));
            }
            else if (attr == R.styleable.Shape_roundedRectRadius) {
                mRoundedRectangleRadius = (int) typedArray.getDimension(attr, 0);
            }
        }

        typedArray.recycle();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mOutlineHelper.invalidateOutline();
    }
}
