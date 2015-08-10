package com.fsk.mynotes.presentation.fragments;


import android.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Unit Test {@link ColorFilterFragment}
 */
public class NoteEditOptionsFragmentTest extends
                                     ActivityInstrumentationTestCase2<MockFragmentActivity> {

    private static final String TAG = "tag";
    private MockFragmentActivity mActivity;
    private FragmentManager mFragmentManager;


    public NoteEditOptionsFragmentTest() {
        super(MockFragmentActivity.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
        mFragmentManager = mActivity.getFragmentManager();
    }
//
//
//    @Test
//    public void testFragment() {
//        NoteEditOptionsFragment fragment = startFragment();
//    }
//
//    @Test
//    public NoteEditOptionsFragment checkNewInstanceCreation() {
//        NoteEditOptionsFragment actual = NoteEditOptionsFragment.newInstance();
//        assertTrue(actual instanceof NoteEditOptionsFragment);
//        Bundle arguments = actual.getArguments();
//        assertNull(arguments);
//        return actual;
//    }
//
//
//    private NoteEditOptionsFragment startFragment() {
//
//        FragmentTransaction transaction = mFragmentManager.beginTransaction();
//        transaction.add(R.id.test_mock_fragment_activity_container, checkNewInstanceCreation(), TAG);
//        transaction.commit();
//        getInstrumentation().waitForIdleSync();
//
//        NoteEditOptionsFragment returnValue =
//                (NoteEditOptionsFragment) mFragmentManager.findFragmentByTag(TAG);
//        assertNotNull(mFragmentManager.findFragmentByTag(TAG));
//
//        return returnValue;
//    }

}