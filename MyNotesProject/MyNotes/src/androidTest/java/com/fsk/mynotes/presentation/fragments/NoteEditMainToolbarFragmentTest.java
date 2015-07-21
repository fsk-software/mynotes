package com.fsk.mynotes.presentation.fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;

import com.fsk.mynotes.R;

import org.junit.Test;

/**
 * Unit Test {@link ColorFilterFragment}
 */
public class NoteEditMainToolbarFragmentTest extends
                                     ActivityInstrumentationTestCase2<MockFragmentActivity> {

    private static final String TAG = "tag";
    private MockFragmentActivity mActivity;
    private FragmentManager mFragmentManager;


    public NoteEditMainToolbarFragmentTest() {
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
        NoteEditMainToolbarFragment fragment = startFragment();
    }

    @Test
    public NoteEditMainToolbarFragment checkNewInstanceCreation() {
        NoteEditMainToolbarFragment actual = NoteEditMainToolbarFragment.newInstance();
        assertTrue(actual instanceof NoteEditMainToolbarFragment);
        Bundle arguments = actual.getArguments();
        assertNull(arguments);
        return actual;
    }


    private NoteEditMainToolbarFragment startFragment() {

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.test_mock_fragment_activity_container, checkNewInstanceCreation(), TAG);
        transaction.commit();
        getInstrumentation().waitForIdleSync();

        NoteEditMainToolbarFragment returnValue =
                (NoteEditMainToolbarFragment) mFragmentManager.findFragmentByTag(TAG);
        assertNotNull(mFragmentManager.findFragmentByTag(TAG));

        return returnValue;
    }

}