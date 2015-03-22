package com.fsk.common.presentation.utils.checkable_helper;


import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;

import com.fsk.common.R;
import com.fsk.common.presentation.components.CheckableImageView;
import com.fsk.common.presentation.components.CheckableFloatingFrameLayout;

/**
 * Tests {@link CheckableImageView}
 */
public class CheckableViewHelperTest extends AndroidTestCase {


    public void testConstructorWithNullContext() {
        try {
            new CheckableViewHelper(getContext());
            assert false;
        }
        catch (NullPointerException e) {
            assert true;
        }
    }


    public void testConstructorSunnyDay() {
        CheckableViewHelper helper = new CheckableViewHelper(getContext());
    }

    public void testReadAttributesWithNullAttrs() {
        CheckableViewHelper helper = new CheckableViewHelper(getContext());
        assertFalse(helper.readCheckableAttributes(getContext(), null));
    }

    public void testReadAttributesWithNullContext() {
        CheckableViewHelper helper = new CheckableViewHelper(getContext());
        helper.readCheckableAttributes(null, null);
        assertFalse(helper.readCheckableAttributes(getContext(), null));
    }

    public void testSetCheckedWithDefaultAttributes() {

        View rootView = LayoutInflater.from(getContext())
                                      .inflate(R.layout.test_checkable_view_helper, null);
        String defaultOffText = getContext().getString(
                R.string.accessibility_description_not_checked);

        String defaultOnText = getContext().getString(R.string.accessibility_description_checked);
        CheckableFloatingFrameLayout button =
                (CheckableFloatingFrameLayout) rootView.findViewById(R.id.test_checkable_view_helper_no_attributes);

        assertFalse(button.isChecked());
        assertEquals(defaultOffText, button.getContentDescription());

        button.setChecked(true);
        assertTrue(button.isChecked());
        assertEquals(defaultOnText, button.getContentDescription());

        button.setChecked(false);
        assertFalse(button.isChecked());
        assertEquals(defaultOffText, button.getContentDescription());
    }


    public void testCheckedInAttributes() {

        View rootView = LayoutInflater.from(getContext())
                                      .inflate(R.layout.test_checkable_view_helper, null);
        String defaultOnText = getContext().getString(
                R.string.accessibility_description_checked);

        CheckableFloatingFrameLayout button =
                (CheckableFloatingFrameLayout) rootView.findViewById(R.id.test_checkable_view_helper_initially_checked);

        assertTrue(button.isChecked());
        assertEquals(defaultOnText, button.getContentDescription());
    }


    public void testSetCheckedWithCustomOnTextAttribute() {

        View rootView = LayoutInflater.from(getContext())
                                      .inflate(R.layout.test_checkable_view_helper, null);
        String defaultOffText = getContext().getString(
                R.string.accessibility_description_not_checked);

        CheckableFloatingFrameLayout button =
                (CheckableFloatingFrameLayout) rootView.findViewById(R.id.test_checkable_view_helper_on_text);

        assertFalse(button.isChecked());
        assertEquals(defaultOffText, button.getContentDescription());

        button.setChecked(true);
        assertTrue(button.isChecked());
        assertEquals("TestOn", button.getContentDescription());

        button.setChecked(false);
        assertFalse(button.isChecked());
        assertEquals(defaultOffText, button.getContentDescription());
    }



    public void testSetCheckedWithCustomOffTextAttribute() {

        View rootView = LayoutInflater.from(getContext())
                                      .inflate(R.layout.test_checkable_view_helper, null);
        String defaultOnText = getContext().getString(R.string.accessibility_description_checked);

        CheckableFloatingFrameLayout button =
                (CheckableFloatingFrameLayout) rootView.findViewById(R.id.test_checkable_view_helper_off_text);

        assertFalse(button.isChecked());
        assertEquals("TestOff", button.getContentDescription());

        button.setChecked(true);
        assertTrue(button.isChecked());
        assertEquals(defaultOnText, button.getContentDescription());

        button.setChecked(false);
        assertFalse(button.isChecked());
        assertEquals("TestOff", button.getContentDescription());
    }


    public void testSetCheckedWithCustomOnOffTextAttribute() {

        View rootView = LayoutInflater.from(getContext())
                                      .inflate(R.layout.test_checkable_view_helper, null);

        CheckableFloatingFrameLayout button =
                (CheckableFloatingFrameLayout) rootView.findViewById(R.id.test_checkable_view_helper_on_off_text);

        assertFalse(button.isChecked());
        assertEquals("TestOff", button.getContentDescription());

        button.setChecked(true);
        assertTrue(button.isChecked());
        assertEquals("TestOn", button.getContentDescription());

        button.setChecked(false);
        assertFalse(button.isChecked());
        assertEquals("TestOff", button.getContentDescription());
    }



    public void testNotifyListener() {
        MockCheckable mockCheckable =new MockCheckable();
        mockCheckable.mChecked = true;

        LocalOnCheckedChangeListener listener = new LocalOnCheckedChangeListener(mockCheckable);
        CheckableViewHelper helper = new CheckableViewHelper(getContext());
        helper.setOnCheckedChangeListener(listener);
        helper.sendChangedNotification(mockCheckable);
        assertTrue(listener.mReceivedNotification);
    }


    public void testNotifyListenerWithNullCheckable() {

        LocalOnCheckedChangeListener listener = new LocalOnCheckedChangeListener(null);
        CheckableViewHelper helper = new CheckableViewHelper(getContext());
        helper.setOnCheckedChangeListener(listener);
        helper.sendChangedNotification(null);
        assertFalse(listener.mReceivedNotification);
    }

    /**
     * Local listener to validate the checked status.
     */
    private static class LocalOnCheckedChangeListener implements OnCheckedChangeListener {

        private final Checkable mExpectedCheckable;

        boolean mReceivedNotification;

        LocalOnCheckedChangeListener(Checkable expected) {
            mExpectedCheckable = expected;
        }


        @Override
        public void onCheckedChanged(final Checkable checkable) {
            assertEquals(mExpectedCheckable, checkable);
            mReceivedNotification = true;
        }
    }

    private class MockCheckable implements Checkable {

        boolean mChecked = false;
        @Override
        public void setChecked(final boolean checked) {

        }


        @Override
        public boolean isChecked() {
            return mChecked;
        }


        @Override
        public void toggle() {

        }
    }
}
