package com.fsk.common.presentation.components;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import com.fsk.common.R;
import com.fsk.common.presentation.utils.outline_provider.OutlineShape;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests {@link FloatingFrameLayout}
 */
@RunWith(AndroidJUnit4.class)
public class FloatingFrameLayoutTest {


    private Context mContext;

    @Before
    public void prepareForTest() {
        mContext = InstrumentationRegistry.getInstrumentation().getContext();
    }


    @Test
    public void testConstructors() {
        new FloatingFrameLayout(mContext);
        new FloatingFrameLayout(mContext, null);
        new FloatingFrameLayout(mContext, null, 0);
    }


    @Test
    public void testInflatedShapes() {
        View rootView = LayoutInflater.from(mContext)
                                      .inflate(R.layout.test_floating_button,
                                               null);
        FloatingFrameLayout defaultButton =
                (FloatingFrameLayout) rootView.findViewById(R.id.test_floating_default);
        assertThat(OutlineShape.OVAL, is(defaultButton.mShape));
        assertThat(defaultButton.mRoundedRectangleRadius, is(0));

        FloatingFrameLayout ovalButton =
                (FloatingFrameLayout) rootView.findViewById(R.id.test_floating_oval);
        assertThat(OutlineShape.OVAL, is(ovalButton.mShape));
        assertThat(ovalButton.mRoundedRectangleRadius, is(0));

        FloatingFrameLayout rectButton =
                (FloatingFrameLayout) rootView.findViewById(R.id.test_floating_rect);
        assertThat(OutlineShape.RECTANGLE, is(rectButton.mShape));
        assertThat(rectButton.mRoundedRectangleRadius, is(0));

        FloatingFrameLayout defaultRoundedRectButton = (FloatingFrameLayout) rootView
                .findViewById(R.id.test_floating_rounded_rect_default);
        assertThat(OutlineShape.ROUNDED_RECTANGLE, is(defaultRoundedRectButton.mShape));
        assertThat(defaultRoundedRectButton.mRoundedRectangleRadius, is(0));

        FloatingFrameLayout roundedRectButton = (FloatingFrameLayout) rootView
                .findViewById(R.id.test_floating_rounded_rect);
        assertThat(OutlineShape.ROUNDED_RECTANGLE, is(roundedRectButton.mShape));

        float expectedPixels =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25,
                                          rootView.getResources().getDisplayMetrics());
        assertThat((int) expectedPixels, is(roundedRectButton.mRoundedRectangleRadius));
    }

}