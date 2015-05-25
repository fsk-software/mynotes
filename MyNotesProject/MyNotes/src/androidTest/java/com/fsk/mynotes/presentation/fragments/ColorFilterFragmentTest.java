package com.fsk.mynotes.presentation.fragments;


import android.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;

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
//
//
//    @Override
//    protected void setUp() throws Exception {
//        super.setUp();
//        mActivity = getActivity();
//        mFragmentManager = mActivity.getFragmentManager();
//    }
//
//
//    public void testFragment() {
//        ColorFilterFragment fragment = startFragment();
//
//        assertNotNull(fragment.mFilterRecyclerView);
//        assertEquals(View.GONE, fragment.mFilterRecyclerView.getVisibility());
//
//        assertNotNull(fragment.mShowFilterToggle);
//        assertEquals(View.VISIBLE, fragment.mShowFilterToggle.getVisibility());
//
//    }
//
//
//
//    public void testToggleFilterToggle() {
//        final ColorFilterFragment fragment = startFragment();
//
//        Runnable toggleRunnable = new Runnable() {
//            @Override
//            public void run() {
//                fragment.mShowFilterToggle.toggle();
//            }
//        };
//
//        assertFalse(fragment.mShowFilterToggle.isChecked());
//        getActivity().runOnUiThread(toggleRunnable);
//
//        try {
//            Thread.sleep(10000);
//        }
//        catch (Exception e){};
//        assertTrue(fragment.mShowFilterToggle.isChecked());
//        assertEquals(View.VISIBLE, fragment.mFilterRecyclerView.getVisibility());
//
//        getActivity().runOnUiThread(toggleRunnable);
//        try {
//            Thread.sleep(10000);
//        }
//        catch (Exception e){};
//        assertFalse(fragment.mShowFilterToggle.isChecked());
//        assertEquals(View.GONE, fragment.mFilterRecyclerView.getVisibility());
//    }
//
//    private ColorFilterFragment startFragment() {
//
//        FragmentTransaction transaction = mFragmentManager.beginTransaction();
//        transaction.add(R.id.test_mock_fragment_activity_container, new ColorFilterFragment(), TAG);
//        transaction.commit();
//        getInstrumentation().waitForIdleSync();
//
//        ColorFilterFragment returnValue =
//                (ColorFilterFragment) mFragmentManager.findFragmentByTag(TAG);
//        assertNotNull(mFragmentManager.findFragmentByTag(TAG));
//
//        return returnValue;
//    }

}