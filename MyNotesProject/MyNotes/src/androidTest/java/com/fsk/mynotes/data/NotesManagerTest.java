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
     * Test {@link com.fsk.mynotes.data.NotesManager} constructor.
     */
    public void testConstructor() {
        //null case
        try {
            new NotesManager(null);
            assert true;
        }
        catch (NullPointerException e) {}

        //Now try the nominal case.
        new NotesManager(DatabaseHelper.getDatabase());
    }


    /**
     * Test method {@link NotesManager#getAllNotes()}.
     */
    public void testGetAllNotes() {
        NotesManager notesManager = new NotesManager(DatabaseHelper.getDatabase());
        
        //test the empty database first. The result should be an empty list.
        List<Note> emptyList = notesManager.getAllNotes();
        assertTrue(emptyList.isEmpty());

        //Test single entry
        List<Note> singleEntryExpected = createNoteListAndCommit(1);
        List<Note> singleEntryNodeActual = notesManager.getAllNotes();
        Validators.validateNoteLists(singleEntryExpected, singleEntryNodeActual);

        //Test multiple entries
        clearNotesTable();
        List<Note> expectedMultipleNotes = createNoteListAndCommit(10);
        List<Note> actualMultipleNotes = notesManager.getAllNotes();
        Validators.validateNoteLists(expectedMultipleNotes, actualMultipleNotes);
    }



    /**
     * Test {@link NotesManager#getNotesWithColors(java.util.List)}.
     */
    public void testGetNotesWithColors() {
        NotesManager notesManager = new NotesManager(DatabaseHelper.getDatabase());

        //Test empty database.
        List<NoteColor> greenColorList = Collections.singletonList(NoteColor.GREEN);
        List<Note> allGreenNotes = notesManager.getNotesWithColors(greenColorList);
        assertTrue(allGreenNotes.isEmpty());

        //create a master list of expected notes.  Ensure that each color has the same number
        //of notes.
        int colorCount = 5;
        List<Note> masterNoteList = createNoteListAndCommit(NoteColor.values().length * 5);

        //Test the null color list.  This should throw a null pointer exception.
        try {
            notesManager.getNotesWithColors(null);
            assert true;
        }
        catch (NullPointerException e) {}

        //Test the empty color filter
        List<NoteColor> colorFilter = new ArrayList<>();
        List<Note> actualNotes = notesManager.getNotesWithColors(colorFilter);
        assertTrue(actualNotes.isEmpty());

        //Test the addition of each color to the color filter.
        int i=0;
        for (NoteColor color : NoteColor.values()) {
            colorFilter.add(color);
            actualNotes = notesManager.getNotesWithColors(colorFilter);
            assertEquals(++i*colorCount, actualNotes.size());
        }

        //Verify the actual notes match the expected for all notes.
        Validators.validateNoteLists(masterNoteList, actualNotes);
    }


    /**
     * Test method {@link NotesManager#getNote(long)}
     */
    public void testGetNote() {
        NotesManager notesManager = new NotesManager(DatabaseHelper.getDatabase());

        //Test the empty database.
        Note note = notesManager.getNote(1);
        assertNull(note);

        int masterListCount = 10;
        List<Note> masterList = createNoteListAndCommit(masterListCount);

        //Test the Not Stored id
        note = notesManager.getNote(-1);
        assertNull(note);

        //Test a positive invalid id
        note = notesManager.getNote(Integer.MAX_VALUE);
        assertNull(note);

        for (Note expectedNote : masterList) {
            Note actualNote = notesManager.getNote(expectedNote.getId());
            Validators.validateNote(expectedNote, actualNote);
        }
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