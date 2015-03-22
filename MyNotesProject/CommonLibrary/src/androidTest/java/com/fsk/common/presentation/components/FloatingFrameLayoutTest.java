package com.fsk.common.presentation.components;


import android.content.Context;
import android.test.AndroidTestCase;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import com.fsk.common.R;
import com.fsk.common.presentation.utils.outline_provider.OutlineShape;

/**
 * Tests {@link FloatingFrameLayout}
 */
public class FloatingFrameLayoutTest extends AndroidTestCase {


    public void testConstructors() {
        Context context = getContext();
        FloatingFrameLayout button = new FloatingFrameLayout(context);

        button = new FloatingFrameLayout(context, null);

        button = new FloatingFrameLayout(context, null, 0);
    }


    public void testInflatedShapes() {
        Context context = getContext();

        View rootView = LayoutInflater.from(context)
                                      .inflate(R.layout.test_floating_button,
                                               null);
        FloatingFrameLayout defaultButton =
                (FloatingFrameLayout) rootView.findViewById(R.id.test_floating_default);
        assertEquals(OutlineShape.OVAL, defaultButton.mShape);
        assertEquals(0, defaultButton.mRoundedRectangleRadius);

        FloatingFrameLayout ovalButton =
                (FloatingFrameLayout) rootView.findViewById(R.id.test_floating_oval);
        assertEquals(OutlineShape.OVAL, ovalButton.mShape);
        assertEquals(0, ovalButton.mRoundedRectangleRadius);

        FloatingFrameLayout rectButton =
                (FloatingFrameLayout) rootView.findViewById(R.id.test_floating_rect);
        assertEquals(OutlineShape.RECTANGLE, rectButton.mShape);
        assertEquals(0, rectButton.mRoundedRectangleRadius);

        FloatingFrameLayout defaultRoundedRectButton = (FloatingFrameLayout) rootView
                .findViewById(R.id.test_floating_rounded_rect_default);
        assertEquals(OutlineShape.ROUNDED_RECTANGLE, defaultRoundedRectButton.mShape);
        assertEquals(0, defaultRoundedRectButton.mRoundedRectangleRadius);

        FloatingFrameLayout roundedRectButton = (FloatingFrameLayout) rootView
                .findViewById(R.id.test_floating_rounded_rect);
        assertEquals(OutlineShape.ROUNDED_RECTANGLE, roundedRectButton.mShape);

        float expectedPixels =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25,
                                          rootView.getResources().getDisplayMetrics());
        assertEquals((int) expectedPixels, roundedRectButton.mRoundedRectangleRadius);
    }
}