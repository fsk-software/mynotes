package com.fsk.mynotes.presentation.fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;

import com.fsk.mynotes.R;
import com.fsk.mynotes.constants.NoteColor;

import org.junit.Test;

/**
 * Unit Test {@link ColorFilterFragment}
 */
public class NoteEditColorPickerFragmentTest extends
                                     ActivityInstrumentationTestCase2<MockFragmentActivity> {

    private static final String TAG = "tag";
    private MockFragmentActivity mActivity;
    private FragmentManager mFragmentManager;


    public NoteEditColorPickerFragmentTest() {
        super(MockFragmentActivity.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
        mFragmentManager = mActivity.getFragmentManager();
    }


    @Test
    public void testFragment() {
        NoteEditColorPickerFragment fragment = startFragment();

        assertEquals(fragment.mColorShiftDuration, 1);
        assertNotNull(fragment.mRadioButtons);
        assertEquals(fragment.mRadioButtons.size(), NoteColor.values().length);
        assertNotNull(fragment.mRadioGroup);
        assertNotNull(fragment.mOnCheckedChangeListener);
    }

    @Test
    public NoteEditColorPickerFragment checkNewInstanceCreation() {
        NoteEditColorPickerFragment actual = NoteEditColorPickerFragment.newInstance(1);
        assertTrue(actual instanceof NoteEditColorPickerFragment);
        Bundle arguments = actual.getArguments();
        assertNotNull(arguments);
        assertEquals(arguments.getInt(
                NoteEditColorPickerFragment.ArgumentExtras.COLOR_SHIFT_DURATION_KEY, 0), 1);
        return actual;
    }


    private NoteEditColorPickerFragment startFragment() {

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.test_mock_fragment_activity_container, checkNewInstanceCreation(), TAG);
        transaction.commit();
        getInstrumentation().waitForIdleSync();

        NoteEditColorPickerFragment returnValue =
                (NoteEditColorPickerFragment) mFragmentManager.findFragmentByTag(TAG);
        assertNotNull(mFragmentManager.findFragmentByTag(TAG));

        return returnValue;
    }

}