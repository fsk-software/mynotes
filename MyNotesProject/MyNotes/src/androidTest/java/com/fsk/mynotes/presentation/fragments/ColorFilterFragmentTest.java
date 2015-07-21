package com.fsk.mynotes.presentation.fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.fsk.mynotes.R;

import org.junit.Test;

/**
 * Unit Test {@link ColorFilterFragment}
 */
public class ColorFilterFragmentTest extends
                                     ActivityInstrumentationTestCase2<MockFragmentActivity> {

    private static final String TAG = "tag";
    private MockFragmentActivity mActivity;
    private FragmentManager mFragmentManager;


    public ColorFilterFragmentTest() {
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
        ColorFilterFragment fragment = startFragment();

        assertNotNull(fragment.mFilterRecyclerView);
        assertEquals(View.VISIBLE, fragment.mFilterRecyclerView.getVisibility());

        assertNotNull(fragment.mNoteFilterCache);
        assertNotNull(fragment.mOnColorSelectChangeListener);

    }


    private ColorFilterFragment startFragment() {

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.test_mock_fragment_activity_container, new ColorFilterFragment(), TAG);
        transaction.commit();
        getInstrumentation().waitForIdleSync();

        ColorFilterFragment returnValue =
                (ColorFilterFragment) mFragmentManager.findFragmentByTag(TAG);
        assertNotNull(mFragmentManager.findFragmentByTag(TAG));

        return returnValue;
    }

}