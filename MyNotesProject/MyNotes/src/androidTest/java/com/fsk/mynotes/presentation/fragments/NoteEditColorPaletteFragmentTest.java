package com.fsk.mynotes.presentation.fragments;


import android.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Unit Test {@link ColorFilterFragment}
 */
public class NoteEditColorPaletteFragmentTest extends
                                     ActivityInstrumentationTestCase2<MockFragmentActivity> {

    private static final String TAG = "tag";
    private MockFragmentActivity mActivity;
    private FragmentManager mFragmentManager;


    public NoteEditColorPaletteFragmentTest() {
        super(MockFragmentActivity.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
        mFragmentManager = mActivity.getFragmentManager();
    }

//
//    @Test
//    public void testFragment() {
//        NoteEditColorPaletteFragment fragment = startFragment();
//
//        assertEquals(fragment.mColorShiftDuration, 1);
//        assertNotNull(fragment.mRadioButtons);
//        assertEquals(fragment.mRadioButtons.size(), NoteColor.values().length);
//        assertNotNull(fragment.mRadioGroup);
//        assertNotNull(fragment.mOnCheckedChangeListener);
//    }
//
//    @Test
//    public NoteEditColorPaletteFragment checkNewInstanceCreation() {
//        NoteEditColorPaletteFragment actual = NoteEditColorPaletteFragment.newInstance(1);
//        assertTrue(actual instanceof NoteEditColorPaletteFragment);
//        Bundle arguments = actual.getArguments();
//        assertNotNull(arguments);
//        assertEquals(arguments.getInt(
//                NoteEditColorPaletteFragment.ArgumentExtras.COLOR_SHIFT_DURATION_KEY, 0), 1);
//        return actual;
//    }
//
//
//    private NoteEditColorPaletteFragment startFragment() {
//
//        FragmentTransaction transaction = mFragmentManager.beginTransaction();
//        transaction.add(R.id.test_mock_fragment_activity_container, checkNewInstanceCreation(), TAG);
//        transaction.commit();
//        getInstrumentation().waitForIdleSync();
//
//        NoteEditColorPaletteFragment returnValue =
//                (NoteEditColorPaletteFragment) mFragmentManager.findFragmentByTag(TAG);
//        assertNotNull(mFragmentManager.findFragmentByTag(TAG));
//
//        return returnValue;
//    }

}