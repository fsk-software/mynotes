package com.fsk.mynotes.presentation.activity;


import android.app.Fragment;
import android.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;

import com.fsk.mynotes.presentation.fragments.NoteEditMainToolbarFragment;

/**
 * Test {@link MainActivity}
 */
public class EditNoteActivityTest extends ActivityInstrumentationTestCase2<EditNoteActivity> {

    public EditNoteActivityTest() {
        super(EditNoteActivity.class);
    }

    public void testDefaultSetup() {
        EditNoteActivity activity = getActivity();

        assertNotNull(activity.mBorderView);
        assertNotNull(activity.mEditText);
        assertEquals(activity.mColorShiftDuration, 400);
        assertNotNull(activity.mNote);

        FragmentManager fragmentManager = activity.getFragmentManager();
        Fragment toolbarFragment = fragmentManager.findFragmentByTag(
                EditNoteActivity.FragmentTags.MAIN_TOOLBAR_TAG);
        assertNotNull(toolbarFragment);
        assertTrue(toolbarFragment instanceof NoteEditMainToolbarFragment);
    }
}
