package com.fsk.common.presentation.components;


import android.content.Context;
import android.test.AndroidTestCase;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;

import com.fsk.common.R;
import com.fsk.common.presentation.utils.checkable_helper.OnCheckedChangeListener;
import com.fsk.common.presentation.utils.outline_provider.OutlineShape;

/**
 * Test {@link CheckableFloatingFrameLayout}
 */
public class CheckableFloatingFrameLayoutTest extends AndroidTestCase {


    public void testConstructor() {
        Context context = getContext();
        CheckableFloatingFrameLayout button = new CheckableFloatingFrameLayout(context);
        assertTrue(!button.isChecked());

        button = new CheckableFloatingFrameLayout(context, null);
        assertTrue(!button.isChecked());

        button = new CheckableFloatingFrameLayout(context, null, 0);
        assertTrue(!button.isChecked());
    }


    public void testInflatedShapes() {

        Context context = getContext();

        View rootView = LayoutInflater.from(context)
                                      .inflate(R.layout.test_checkable_floating_button,
                                               null);
        CheckableFloatingFrameLayout defaultButton =
                (CheckableFloatingFrameLayout) rootView.findViewById(R.id.test_checkable_floating_default);
        assertEquals(OutlineShape.OVAL, defaultButton.mShape);
        assertEquals(0, defaultButton.mRoundedRectangleRadius);
        assertNotNull(defaultButton.mCheckableViewHelper);

        CheckableFloatingFrameLayout ovalButton =
                (CheckableFloatingFrameLayout) rootView.findViewById(R.id.test_checkable_floating_oval);
        assertEquals(OutlineShape.OVAL, ovalButton.mShape);
        assertEquals(0, ovalButton.mRoundedRectangleRadius);
        assertNotNull(ovalButton.mCheckableViewHelper);

        CheckableFloatingFrameLayout rectButton =
                (CheckableFloatingFrameLayout) rootView.findViewById(R.id.test_checkable_floating_rect);
        assertEquals(OutlineShape.RECTANGLE, rectButton.mShape);
        assertEquals(0, rectButton.mRoundedRectangleRadius);
        assertNotNull(rectButton.mCheckableViewHelper);

        CheckableFloatingFrameLayout defaultRoundedRectButton = (CheckableFloatingFrameLayout) rootView
                .findViewById(R.id.test_checkable_floating_rounded_rect_default);
        assertEquals(OutlineShape.ROUNDED_RECTANGLE, defaultRoundedRectButton.mShape);
        assertEquals(0, defaultRoundedRectButton.mRoundedRectangleRadius);
        assertNotNull(defaultRoundedRectButton.mCheckableViewHelper);

        CheckableFloatingFrameLayout roundedRectButton = (CheckableFloatingFrameLayout) rootView
                .findViewById(R.id.test_checkable_floating_rounded_rect);
        assertEquals(OutlineShape.ROUNDED_RECTANGLE, roundedRectButton.mShape);
        assertNotNull(roundedRectButton.mCheckableViewHelper);

        float expectedPixels =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25,
                                          context.getResources().getDisplayMetrics());
        assertEquals((int) expectedPixels, roundedRectButton.mRoundedRectangleRadius);
    }


    public void testCheckChanged() {
        CheckableFloatingFrameLayout button = new CheckableFloatingFrameLayout(getContext());
        assertTrue(!button.isChecked());

        button.setOnCheckedChangeListener(new LocalOnCheckedChangeListener(true));
        button.setChecked(true);

        button.setOnCheckedChangeListener(new LocalOnCheckedChangeListener(false));
        button.setChecked(false);
    }


    public void testToggle() {
        CheckableFloatingFrameLayout button = new CheckableFloatingFrameLayout(getContext());
        assertTrue(!button.isChecked());

        button.setOnCheckedChangeListener(new LocalOnCheckedChangeListener(true));
        button.toggle();

        button.setOnCheckedChangeListener(new LocalOnCheckedChangeListener(false));
        button.toggle();
    }


    public void testPerformClick() {
        CheckableFloatingFrameLayout button = new CheckableFloatingFrameLayout(getContext());
        assertTrue(!button.isChecked());

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
            assertEquals(mExpectedChecked, checkable.isChecked());
        }
    }
}
