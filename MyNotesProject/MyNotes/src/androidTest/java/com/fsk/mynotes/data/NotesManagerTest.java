package com.fsk.mynotes.data;

import android.test.AndroidTestCase;

import com.fsk.common.database.DatabaseHelper;
import com.fsk.mynotes.Validators;
import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.data.database.MyNotesDatabaseModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Test the {@link NotesManager}.
 */
public class NotesManagerTest extends AndroidTestCase {

    /**
     * The Empty Parcel Tag.
     */
    private static final String DEFAULT_TAG = "DEFAULT_TAG";


    /**
     * The Non-Empty Parcel Tag.
     */
    private static final String NOTE1_TAG = "NOTE1_TAG";


    @Override
    public void setUp() throws Exception {
        super.setUp();
        DatabaseHelper.initialize(getContext(), new MyNotesDatabaseModel());
        clearNotesTable();
    }


    @Override
    public void tearDown() throws Exception {
        clearNotesTable();
        super.tearDown();
    }


    /**
     * Test {@link com.fsk.mynotes.data.NotesManager} constructor with null database.
     */
    public void testConstructorFailure() {
        try {
            new NotesManager(null);
            assert true;
        }
        catch (NullPointerException e) {}
    }


    /**
     * Test method {@link NotesManager#getAllNotes()} with multiple notes.
     */
    public void testGettingAllNotes() {
        NotesManager notesManager = new NotesManager(DatabaseHelper.getDatabase());

        //Test multiple entries
        clearNotesTable();
        List<Note> expectedMultipleNotes = createNoteListAndCommit(10);
        List<Note> actualMultipleNotes = notesManager.getAllNotes();
        Validators.validateNoteLists(expectedMultipleNotes, actualMultipleNotes);
    }


    /**
     * Test method {@link NotesManager#getAllNotes()} with a single entry.
     */
    public void testGettingAllNotesWithSingleEntry() {
        NotesManager notesManager = new NotesManager(DatabaseHelper.getDatabase());

        //Test single entry
        List<Note> singleEntryExpected = createNoteListAndCommit(1);
        List<Note> singleEntryNodeActual = notesManager.getAllNotes();
        Validators.validateNoteLists(singleEntryExpected, singleEntryNodeActual);
    }


    /**
     * Test method {@link NotesManager#getAllNotes()} with an empty database.
     */
    public void testGettingAllNotesWithEmptyDatabase() {
        NotesManager notesManager = new NotesManager(DatabaseHelper.getDatabase());

        List<Note> emptyList = notesManager.getAllNotes();
        assertTrue(emptyList.isEmpty());
    }


    /**
     * Test {@link NotesManager#getNotesWithColors(java.util.List)} in the sunny day case.
     */
    public void testGettingNotesByColorSunnyDay() {
        NotesManager notesManager = new NotesManager(DatabaseHelper.getDatabase());
        int colorCount = 5;

        //create a master list of expected notes so that each color has the same number of entries.
        List<Note> masterNoteList = createNoteListAndCommit(NoteColor.values().length * colorCount);

        List<NoteColor> colorFilter = new ArrayList<>();
        List<Note> actualNotes = null;
        for (int i=0, expectedCount=colorCount; i<NoteColor.values().length; ++i, expectedCount+=colorCount) {
            colorFilter.add(NoteColor.getColor(i));
            actualNotes = notesManager.getNotesWithColors(colorFilter);
            assertEquals(expectedCount, actualNotes.size());
        }

        //Verify the actual notes match the expected for all notes.
        Validators.validateNoteLists(masterNoteList, actualNotes);
    }


    /**
     * Test {@link NotesManager#getNotesWithColors(java.util.List)} with an empty color list.
     */
    public void testGetNotesByColorWithEmptyColors() {
        NotesManager notesManager = new NotesManager(DatabaseHelper.getDatabase());

        List<NoteColor> colorFilter = new ArrayList<>();
        List<Note> actualNotes = notesManager.getNotesWithColors(colorFilter);
        assertTrue(actualNotes.isEmpty());
    }


    /**
     * Test {@link NotesManager#getNotesWithColors(java.util.List)} with a null color list.
     */
    public void testGetNotesByColorWithNullColors() {
        NotesManager notesManager = new NotesManager(DatabaseHelper.getDatabase());
        try {
            notesManager.getNotesWithColors(null);
            assert true;
        }
        catch (NullPointerException e) {}
    }

    /**
     * Test {@link NotesManager#getNotesWithColors(java.util.List)} with an empty database.
     */
    public void testGetNotesByColorWithEmptyDatabase() {
        NotesManager notesManager = new NotesManager(DatabaseHelper.getDatabase());

        //Test empty database.
        List<NoteColor> greenColorList = Collections.singletonList(NoteColor.GREEN);
        List<Note> allGreenNotes = notesManager.getNotesWithColors(greenColorList);
        assertTrue(allGreenNotes.isEmpty());
    }


    /**
     * Test method {@link NotesManager#getNote(long)} in the  sunny day case.
     */
    public void testGettingNoteWithValidId() {
        NotesManager notesManager = new NotesManager(DatabaseHelper.getDatabase());

        int masterListCount = 10;
        List<Note> masterList = createNoteListAndCommit(masterListCount);

        for (Note expectedNote : masterList) {
            Note actualNote = notesManager.getNote(expectedNote.getId());
            Validators.validateNote(expectedNote, actualNote);
        }
    }


    /**
     * Test method {@link NotesManager#getNote(long)} with invalid ids.
     */
    public void testGettingNoteWithInvalidIds() {
        NotesManager notesManager = new NotesManager(DatabaseHelper.getDatabase());

        //Test with a negative id.
        Note note = notesManager.getNote(-1);
        assertNull(note);

        //Test with a positive invalid id
        note = notesManager.getNote(Integer.MAX_VALUE);
        assertNull(note);
    }


    /**
     * Test method {@link NotesManager#getNote(long)} with an empty database.
     */
    public void testGettingNoteWithEmptyDatabase() {
        NotesManager notesManager = new NotesManager(DatabaseHelper.getDatabase());

        //Test the empty database.
        Note note = notesManager.getNote(1);
        assertNull(note);
    }


    /**
     * Create a list of Stored notes.  Each note will contain the its creation index as the text
     * and its color is determined by ordinal position relative its creation order.
     * Each note is committed to the database.
     *
     * @param count the number of notes to create.
     * @return the list of created notes.
     */
    private List<Note> createNoteListAndCommit(int count) {
        List<Note> returnValue = new ArrayList<>();
        for (int i=0; i<count; ++i) {
            Note note = new Note();

            note.setText(Integer.toString(i));
            note.setColor(NoteColor.getColor(i % NoteColor.values().length));
            note.save(DatabaseHelper.getDatabase());

            returnValue.add(note);
        }

        return returnValue;
    }


    /**
     * Clear all rows in the Notes database table.
     */
    private void clearNotesTable() {
        DatabaseHelper.getDatabase().delete(MyNotesDatabaseModel.Tables.NOTES, null, null);
    }
}