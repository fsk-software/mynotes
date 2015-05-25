package com.fsk.mynotes.data.loaders;


import android.test.LoaderTestCase;

import com.fsk.common.database.DatabaseHelper;
import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.data.Note;
import com.fsk.mynotes.data.cache.NoteFilterCache;
import com.fsk.mynotes.data.database.MyNotesDatabaseModel;

import java.util.List;

/**
 * Test the {@link com.fsk.mynotes.data.loaders.FilteredNoteLoader}.
 */
public class FilteredNoteLoaderTest extends LoaderTestCase {


    private NoteFilterCache mNoteFilterCache;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        DatabaseHelper.initialize(getContext(), new MyNotesDatabaseModel());

        mNoteFilterCache = new NoteFilterCache(getContext());
        clearPersistentData();
    }


    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        clearPersistentData();
    }


    /**
     * Clear the database and Preference cache.
     */
    private void clearPersistentData() {
        DatabaseHelper.getDatabase().delete(MyNotesDatabaseModel.Tables.NOTES, null, null);
        new NoteFilterCache(getContext()).clear();
    }


    /**
     * Test the case where there are no notes in the database.
     */
    public void testNoNotesCase() {
        clearPersistentData();
        List<Note> result = getLoaderResultSynchronously(new FilteredNoteLoader(getContext()));
        assertTrue(result.isEmpty());
    }



    /**
     * Test the case where there are notes in the database for each color, but the all of the colors are disabled.
     */
    public void testEmptyFilterCase() {
        for (NoteColor noteColor: NoteColor.values()) {
            Note note = new Note();
            note.setColor(noteColor);
            note.setText(noteColor.name());
            note.save(DatabaseHelper.getDatabase());

            mNoteFilterCache.enableColor(noteColor, false);
        }

        List<Note> result = getLoaderResultSynchronously(new FilteredNoteLoader(getContext()));
        assertTrue(result.isEmpty());
    }

    /**
     * Test the case where there are notes in the database for each color and the all of the colors are enabled.
     */
    public void testLoadedFilterCase() {
        for (NoteColor noteColor : NoteColor.values()) {
            Note note = new Note();
            note.setColor(noteColor);
            note.setText(noteColor.name());
            note.save(DatabaseHelper.getDatabase());

            mNoteFilterCache.enableColor(noteColor, true);
        }

        List<Note> result = getLoaderResultSynchronously(new FilteredNoteLoader(getContext()));
        assertEquals(NoteColor.values().length, result.size());
    }
}