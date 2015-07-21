package com.fsk.common.presentation.components;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;

import com.fsk.common.R;
import com.fsk.common.presentation.utils.checkable_helper.OnCheckedChangeListener;
import com.fsk.common.presentation.utils.outline_provider.OutlineShape;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test {@link CheckableFloatingFrameLayout}
 */
@RunWith(AndroidJUnit4.class)
public class CheckableFloatingFrameLayoutTest {

    private Context mContext;

    @Before
    public void prepareForTest() {
        mContext = InstrumentationRegistry.getInstrumentation().getContext();
    }

    @Test
    public void testConstructor() {
        CheckableFloatingFrameLayout button = new CheckableFloatingFrameLayout(mContext);
        assertThat(button.isChecked(), is(false));

        button = new CheckableFloatingFrameLayout(mContext, null);
        assertThat(button.isChecked(), is(false));

        button = new CheckableFloatingFrameLayout(mContext, null, 0);
        assertThat(button.isChecked(), is(false));
    }


    @Test
    public void testInflatedShapes() {
        View rootView = LayoutInflater.from(mContext)
                                      .inflate(R.layout.test_checkable_floating_button,
                                               null);
        CheckableFloatingFrameLayout defaultButton =
                (CheckableFloatingFrameLayout) rootView.findViewById(R.id.test_checkable_floating_default);
        assertThat(OutlineShape.OVAL, is(defaultButton.mShape));
        assertThat(defaultButton.mRoundedRectangleRadius, is(0));
        assertThat(defaultButton.mCheckableViewHelper, is(notNullValue()));

        CheckableFloatingFrameLayout ovalButton =
                (CheckableFloatingFrameLayout) rootView.findViewById(R.id.test_checkable_floating_oval);
        assertThat(OutlineShape.OVAL, is(ovalButton.mShape));
        assertThat(ovalButton.mRoundedRectangleRadius, is(0));
        assertThat(ovalButton.mCheckableViewHelper, is(notNullValue()));

        CheckableFloatingFrameLayout rectButton =
                (CheckableFloatingFrameLayout) rootView.findViewById(R.id.test_checkable_floating_rect);
        assertThat(OutlineShape.RECTANGLE, is(rectButton.mShape));
        assertThat(rectButton.mRoundedRectangleRadius, is(0));
        assertThat(rectButton.mCheckableViewHelper, is(notNullValue()));

        CheckableFloatingFrameLayout defaultRoundedRectButton = (CheckableFloatingFrameLayout) rootView
                .findViewById(R.id.test_checkable_floating_rounded_rect_default);
        assertThat(OutlineShape.ROUNDED_RECTANGLE, is(defaultRoundedRectButton.mShape));
        assertThat(defaultRoundedRectButton.mRoundedRectangleRadius, is(0));
        assertThat(defaultRoundedRectButton.mCheckableViewHelper, is(notNullValue()));

        CheckableFloatingFrameLayout roundedRectButton = (CheckableFloatingFrameLayout) rootView
                .findViewById(R.id.test_checkable_floating_rounded_rect);
        assertThat(OutlineShape.ROUNDED_RECTANGLE, is(roundedRectButton.mShape));
        assertThat(roundedRectButton.mCheckableViewHelper, is(notNullValue()));

        float expectedPixels =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25,
                                          mContext.getResources().getDisplayMetrics());
        assertThat((int) expectedPixels, is(roundedRectButton.mRoundedRectangleRadius));
    }


    @Test
    public void testCheckChanged() {
        CheckableFloatingFrameLayout button = new CheckableFloatingFrameLayout(mContext);
        assertThat(button.isChecked(), is(false));

        button.setOnCheckedChangeListener(new LocalOnCheckedChangeListener(true));
        button.setChecked(true);

        button.setOnCheckedChangeListener(new LocalOnCheckedChangeListener(false));
        button.setChecked(false);
    }


    @Test
    public void testToggle() {
        CheckableFloatingFrameLayout button = new CheckableFloatingFrameLayout(mContext);
        assertThat(button.isChecked(), is(false));

        button.setOnCheckedChangeListener(new LocalOnCheckedChangeListener(true));
        button.toggle();

        button.setOnCheckedChangeListener(new LocalOnCheckedChangeListener(false));
        button.toggle();
    }


    @Test
    public void testPerformClick() {
        CheckableFloatingFrameLayout button = new CheckableFloatingFrameLayout(mContext);
        assertThat(button.isChecked(), is(false));

        button.setOnCheckedChangeListener(new LocalOnCheckedChangeListener(true));
        button.performClick();

        button.setOnCheckedChangeListener(new LocalOnCheckedChangeListener(false));
        button.performClick();
    }


    /**
     * Local listener to validate the checked status.
     */
    private static class LocalOnCheckedChangeListener implements OnCheckedChangeListener {

        private final boolean mExpectedChecked;


        LocalOnCheckedChangeListener(boolean expected) {
            mExpectedChecked = expected;
        }


        @Override
        public void onCheckedChanged(final Checkable checkable) {
            assertThat(mExpectedChecked, is(checkable.isChecked()));
        }
    }
}
