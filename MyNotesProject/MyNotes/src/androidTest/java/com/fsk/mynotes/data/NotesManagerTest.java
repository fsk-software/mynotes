package com.fsk.mynotes.data;


import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

/**
 * Test the {@link NotesManager}.
 */
@RunWith(AndroidJUnit4.class)
public class NotesManagerTest {
//
//    /**
//     * The Empty Parcel Tag.
//     */
//    private static final String DEFAULT_TAG = "DEFAULT_TAG";
//
//
//    /**
//     * The Non-Empty Parcel Tag.
//     */
//    private static final String NOTE1_TAG = "NOTE1_TAG";
//
//
//    private Context mContext;
//
//
//    @Before
//    public void prepareForTest() {
//        mContext = new RenamingDelegatingContext(
//                InstrumentationRegistry.getInstrumentation().getTargetContext(), "test_");
//        DatabaseHelper.initialize(mContext, new MyNotesDatabaseModel());
//        clearNotesTable();
//    }
//
//
//    @After
//    public void tearDown() throws Exception {
//        clearNotesTable();
//    }
//
//
//    /**
//     * Test {@link com.fsk.mynotes.data.NotesManager} constructor with null database.
//     */
//    @Test
//    public void testConstructorFailure() {
//        try {
//            new NotesManager(null);
//            assert false;
//        }
//        catch (NullPointerException e) {
//        }
//    }
//
//
//    /**
//     * Test method {@link NotesManager#getAllNotes()} with multiple notes.
//     */
//    @Test
//    public void testGettingAllNotes() {
//        NotesManager notesManager = new NotesManager(DatabaseHelper.getDatabase());
//
//        //Test multiple entries
//        clearNotesTable();
//        List<Note> expectedMultipleNotes = createNoteListAndCommit(10);
//        List<Note> actualMultipleNotes = notesManager.getAllNotes();
//        Validators.validateNoteLists(expectedMultipleNotes, actualMultipleNotes);
//    }
//
//
//    /**
//     * Test method {@link NotesManager#getAllNotes()} with a single entry.
//     */
//    @Test
//    public void testGettingAllNotesWithSingleEntry() {
//        NotesManager notesManager = new NotesManager(DatabaseHelper.getDatabase());
//
//        //Test single entry
//        List<Note> singleEntryExpected = createNoteListAndCommit(1);
//        List<Note> singleEntryNodeActual = notesManager.getAllNotes();
//        Validators.validateNoteLists(singleEntryExpected, singleEntryNodeActual);
//    }
//
//
//    /**
//     * Test method {@link NotesManager#getAllNotes()} with an empty database.
//     */
//    @Test
//    public void testGettingAllNotesWithEmptyDatabase() {
//        NotesManager notesManager = new NotesManager(DatabaseHelper.getDatabase());
//
//        List<Note> emptyList = notesManager.getAllNotes();
//        assertThat(emptyList.isEmpty(), is(true));
//    }
//
//
//    /**
//     * Test {@link NotesManager#getNotesWithColors(java.util.List)} in the sunny day case.
//     */
//    @Test
//    public void testGettingNotesByColorSunnyDay() {
//        NotesManager notesManager = new NotesManager(DatabaseHelper.getDatabase());
//        int colorCount = 5;
//
//        //create a master list of expected notes so that each color has the same number of entries.
//        List<Note> masterNoteList = createNoteListAndCommit(NoteColor.values().length * colorCount);
//
//        List<NoteColor> colorFilter = new ArrayList<>();
//        List<Note> actualNotes = null;
//        for (int i = 0, expectedCount = colorCount; i < NoteColor.values().length;
//             ++i, expectedCount += colorCount) {
//            colorFilter.add(NoteColor.getColor(i));
//            actualNotes = notesManager.getNotesWithColors(colorFilter);
//            assertThat(expectedCount, is(actualNotes.size()));
//        }
//
//        //Verify the actual notes match the expected for all notes.
//        Validators.validateNoteLists(masterNoteList, actualNotes);
//    }
//
//
//    /**
//     * Test {@link NotesManager#getNotesWithColors(java.util.List)} with an empty color list.
//     */
//    @Test
//    public void testGetNotesByColorWithEmptyColors() {
//        NotesManager notesManager = new NotesManager(DatabaseHelper.getDatabase());
//
//        List<NoteColor> colorFilter = new ArrayList<>();
//        List<Note> actualNotes = notesManager.getNotesWithColors(colorFilter);
//        assertThat(actualNotes.isEmpty(), is(true));
//    }
//
//
//    /**
//     * Test {@link NotesManager#getNotesWithColors(java.util.List)} with a null color list.
//     */
//    @Test
//    public void testGetNotesByColorWithNullColors() {
//        NotesManager notesManager = new NotesManager(DatabaseHelper.getDatabase());
//        try {
//            notesManager.getNotesWithColors(null);
//            assert false;
//        }
//        catch (NullPointerException e) {
//        }
//    }
//
//
//    /**
//     * Test {@link NotesManager#getNotesWithColors(java.util.List)} with an empty database.
//     */
//    @Test
//    public void testGetNotesByColorWithEmptyDatabase() {
//        NotesManager notesManager = new NotesManager(DatabaseHelper.getDatabase());
//
//        //Test empty database.
//        List<NoteColor> greenColorList = Collections.singletonList(NoteColor.GREEN);
//        List<Note> allGreenNotes = notesManager.getNotesWithColors(greenColorList);
//        assertThat(allGreenNotes.isEmpty(), is(true));
//    }
//
//
//    /**
//     * Test method {@link NotesManager#getNote(long)} in the  sunny day case.
//     */
//    @Test
//    public void testGettingNoteWithValidId() {
//        NotesManager notesManager = new NotesManager(DatabaseHelper.getDatabase());
//
//        int masterListCount = 10;
//        List<Note> masterList = createNoteListAndCommit(masterListCount);
//
//        for (Note expectedNote : masterList) {
//            Note actualNote = notesManager.getNote(expectedNote.getId());
//            Validators.validateNote(expectedNote, actualNote);
//        }
//    }
//
//
//    /**
//     * Test method {@link NotesManager#getNote(long)} with invalid ids.
//     */
//    @Test
//    public void testGettingNoteWithInvalidIds() {
//        NotesManager notesManager = new NotesManager(DatabaseHelper.getDatabase());
//
//        //Test with a negative id.
//        Note note = notesManager.getNote(-1);
//        assertThat(note, is(nullValue()));
//
//        //Test with a positive invalid id
//        note = notesManager.getNote(Integer.MAX_VALUE);
//        assertThat(note, is(nullValue()));
//    }
//
//
//    /**
//     * Test method {@link NotesManager#getNote(long)} with an empty database.
//     */
//    @Test
//    public void testGettingNoteWithEmptyDatabase() {
//        NotesManager notesManager = new NotesManager(DatabaseHelper.getDatabase());
//
//        //Test the empty database.
//        Note note = notesManager.getNote(1);
//        assertThat(note, is(nullValue()));
//    }
//
//
//    /**
//     * Create a list of Stored notes.  Each note will contain the its creation index as the text and
//     * its color is determined by ordinal position relative its creation order. Each note is
//     * committed to the database.
//     *
//     * @param count
//     *         the number of notes to create.
//     *
//     * @return the list of created notes.
//     */
//    private List<Note> createNoteListAndCommit(int count) {
//        List<Note> returnValue = new ArrayList<>();
//        for (int i = 0; i < count; ++i) {
//            Note note = new Note();
//
//            note.setText(Integer.toString(i));
//            note.setColor(NoteColor.getColor(i % NoteColor.values().length));
//            note.save(DatabaseHelper.getDatabase());
//
//            returnValue.add(note);
//        }
//
//        return returnValue;
//    }
//
//
//    /**
//     * Clear all rows in the Notes database table.
//     */
//    private void clearNotesTable() {
//        DatabaseHelper.getDatabase().delete(MyNotesDatabaseModel.Tables.NOTES, null, null);
//    }
}