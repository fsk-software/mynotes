package com.fsk.mynotes.data.loaders;


import android.test.LoaderTestCase;

import org.junit.Test;

/**
 * Test the {@link com.fsk.mynotes.data.loaders.FilteredNoteLoader}.
 */
public class FilteredNoteLoaderTest extends LoaderTestCase {
//
//
//    private NoteFilterCache mNoteFilterCache;
//
//    @Override
//    public void setUp() throws Exception {
//        super.setUp();
//        DatabaseHelper.initialize(getContext(), new NotesDatabaseSchema());
//
//        mNoteFilterCache = new NoteFilterCache(getContext());
//        clearPersistentData();
//    }
//
//
//    @Override
//    public void tearDown() throws Exception {
//        super.tearDown();
//        clearPersistentData();
//    }
//
//
//    /**
//     * Clear the database and Preference cache.
//     */
//    private void clearPersistentData() {
//        DatabaseHelper.getDatabase().delete(NotesDatabaseSchema.Tables.NOTES, null, null);
//        new NoteFilterCache(getContext()).clear();
//    }
//
//
//    /**
//     * Test the case where there are no notes in the database.
//     */
//    @Test
//    public void testNoNotesCase() {
//        clearPersistentData();
//        List<Note> result = getLoaderResultSynchronously(new FilteredNoteLoader(getContext()));
//        assertTrue(result.isEmpty());
//    }
//
//
//
//    /**
//     * Test the case where there are notes in the database for each color, but the all of the colors are disabled.
//     */
//    @Test
//    public void testEmptyFilterCase() {
//        for (NoteColor noteColor: NoteColor.values()) {
//            Note note = new Note();
//            note.setColor(noteColor);
//            note.setText(noteColor.name());
//            note.save(DatabaseHelper.getDatabase());
//
//            mNoteFilterCache.enableColor(noteColor, false);
//        }
//
//        List<Note> result = getLoaderResultSynchronously(new FilteredNoteLoader(getContext()));
//        assertTrue(result.isEmpty());
//    }
//
//    /**
//     * Test the case where there are notes in the database for each color and the all of the colors are enabled.
//     */
//    @Test
//    public void testLoadedFilterCase() {
//        for (NoteColor noteColor : NoteColor.values()) {
//            Note note = new Note();
//            note.setColor(noteColor);
//            note.setText(noteColor.name());
//            note.save(DatabaseHelper.getDatabase());
//
//            mNoteFilterCache.enableColor(noteColor, true);
//        }
//
//        List<Note> result = getLoaderResultSynchronously(new FilteredNoteLoader(getContext()));
//        assertEquals(NoteColor.values().length, result.size());
//    }
}