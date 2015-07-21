package com.fsk.mynotes.presentation.activity;


import android.app.Fragment;
import android.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;

import com.fsk.mynotes.presentation.fragments.ColorFilterFragment;
import com.fsk.mynotes.presentation.fragments.NoteCardsFragment;

/**
 * Test {@link com.fsk.mynotes.presentation.activity.MainActivity}
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void testDefaultSetup() {
        MainActivity mainActivity = getActivity();
        FragmentManager fragmentManager = mainActivity.getFragmentManager();

        Fragment colorFilterFragment = fragmentManager.findFragmentByTag(
                MainActivity.FragmentTags.COLOR_FILTER_FRAGMENT_TAG);
        assertNotNull(colorFilterFragment);
        assertTrue(colorFilterFragment instanceof ColorFilterFragment);

        Fragment cardsFragment = fragmentManager.findFragmentByTag(
                MainActivity.FragmentTags.NOTE_CARDS_FRAGMENT_TAG);
        assertNotNull(cardsFragment);
        assertTrue(cardsFragment instanceof NoteCardsFragment);
    }
}
