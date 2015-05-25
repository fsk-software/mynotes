package com.fsk.mynotes.presentation.activity;


import android.support.v7.widget.GridLayoutManager;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Test {@link com.fsk.mynotes.presentation.activity.MainActivity}
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void testDefaultSetup() {
        MainActivity mainActivity = getActivity();
        assertNotNull(mainActivity.mCardAdapter);
        assertNotNull(mainActivity.mCardsRecyclerView);
        assertNotNull(mainActivity.mFilterRecyclerView);

        assertEquals(mainActivity.mCardAdapter, mainActivity.mCardsRecyclerView.getAdapter());
        assertTrue(mainActivity.mCardsRecyclerView.getLayoutManager() instanceof GridLayoutManager);
    }
}
