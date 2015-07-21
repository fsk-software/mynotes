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
public class NoteCardsFragmentTest extends
                                     ActivityInstrumentationTestCase2<MockFragmentActivity> {

    private static final String TAG = "tag";
    private MockFragmentActivity mActivity;
    private FragmentManager mFragmentManager;


    public NoteCardsFragmentTest() {
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
        NoteCardsFragment fragment = startFragment();

        assertNotNull(fragment.mCardAdapter);
        assertNotNull(fragment.mCardsRecyclerView);
        assertNotNull(fragment.mNoteClickListener);
    }

    @Test
    public NoteCardsFragment checkNewInstanceCreation() {
        NoteCardsFragment actual = NoteCardsFragment.newInstance();
        assertTrue(actual instanceof NoteCardsFragment);
        Bundle arguments = actual.getArguments();
        assertNull(arguments);;

        return actual;
    }


    private NoteCardsFragment startFragment() {

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.test_mock_fragment_activity_container, checkNewInstanceCreation(), TAG);
        transaction.commit();
        getInstrumentation().waitForIdleSync();

        NoteCardsFragment returnValue = (NoteCardsFragment) mFragmentManager.findFragmentByTag(TAG);
        assertNotNull(mFragmentManager.findFragmentByTag(TAG));

        return returnValue;
    }

}