package com.fsk.mynotes.data;


import android.os.Bundle;
import android.os.Parcel;
import android.test.AndroidTestCase;

import com.fsk.common.database.DatabaseHelper;
import com.fsk.mynotes.Validators;
import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.data.database.MyNotesDatabaseModel;

/**
 * Test the {@link com.fsk.mynotes.data.Note class}.
 */
public class NoteTest extends AndroidTestCase {


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
    }


    @Override
    public void tearDown() throws Exception {
        DatabaseHelper.getDatabase().delete(MyNotesDatabaseModel.Tables.NOTES, null, null);
        super.tearDown();
    }



    /**
     * Test the basic constructor and default values.
     */
    public void testConstruction() {
        Note note = new Note();
        assertEquals(Note.NOT_STORED, note.getId());
        assertEquals(NoteColor.YELLOW, note.getColor());
        assertEquals("", note.getText());
    }


    /**
     * Test {@link com.fsk.mynotes.data.Note#getId()} and {@link com.fsk.mynotes.data.Note#setId
     * (long)}.
     */
    public void testNoteIdGetterSetter() {
        Note note = new Note();
        assertEquals(Note.NOT_STORED, note.getId());

        note.setId(1);
        assertEquals(1, note.getId());
    }


    /**
     * Test {@link Note#getColor()} and {@link Note#setColor(com.fsk.mynotes.constants.NoteColor)}
     */
    public void testNoteColorGetterSetter() {
        Note note = new Note();
        assertEquals(NoteColor.YELLOW, note.getColor());

        try {
            note.setColor(null);
            assert true;
        }
        catch (NullPointerException npe) {
        }

        for (NoteColor noteColor : NoteColor.values()) {
            note.setColor(noteColor);
            assertEquals(noteColor, note.getColor());
        }
    }


    /**
     * Test {@link com.fsk.mynotes.data.Note#getText()} and {@link com.fsk.mynotes.data
     * .Note#setText(String)}
     */
    public void testNoteTextGetterSetter() {
        Note note = new Note();
        assertEquals("", note.getText());

        //Set the non-null and not empty string.
        String testString = "AA";
        note.setText(testString);
        assertEquals(testString, note.getText());

        //Set the null string
        note.setText(null);
        assertEquals("", note.getText());

        //Set the non-null and not empty string.
        note.setText(testString);
        assertEquals(testString, note.getText());

        //Set the empty string.
        note.setText("");
        assertEquals("", note.getText());
    }


    /**
     * Test the Note parceling
     */
    public void testNoteParceling() {
        Note defaultNote = new Note();
        Note note1 = new Note();
        note1.setId(5);
        note1.setColor(NoteColor.GREEN);
        note1.setText("AAAA");

        //Create Bundle
        Bundle saveBundle = new Bundle();
        saveBundle.putParcelable(DEFAULT_TAG, defaultNote);
        saveBundle.putParcelable(NOTE1_TAG, note1);

        //Create Parcel and Save Bundle in it.
        Parcel parcel = Parcel.obtain();
        saveBundle.writeToParcel(parcel, 0);

        //Extract Bundle from the Parcel
        parcel.setDataPosition(0);
        Bundle extractBundle = parcel.readBundle();
        extractBundle.setClassLoader(Note.class.getClassLoader());

        Note extractedDefaultNote = extractBundle.getParcelable(DEFAULT_TAG);
        Note extractedNote1 = extractBundle.getParcelable(NOTE1_TAG);

        //Checks Notes match
        assertEquals(note1.getId(), extractedNote1.getId());
        assertEquals(note1.getColor(), extractedNote1.getColor());
        assertEquals(note1.getText(), extractedNote1.getText());

        assertEquals(defaultNote.getId(), extractedDefaultNote.getId());
        assertEquals(defaultNote.getColor(), extractedDefaultNote.getColor());
        assertEquals(defaultNote.getText(), extractedDefaultNote.getText());
    }


    /**
     * Test {@link com.fsk.mynotes.data.Note#save(android.database.sqlite.SQLiteDatabase)}
     */
    public void testStore() {
        NotesManager notesManager = new NotesManager(DatabaseHelper.getDatabase());

        //Test the note storage succeeds
        Note note = new Note();
        assertEquals(Note.NOT_STORED, note.getId());
        note.save(DatabaseHelper.getDatabase());

        assertTrue(note.getId() >= 0);
        Note storedNote = notesManager.getNote(note.getId());
        Validators.validateNote(note, storedNote);

        //Update the note and verify the changes can be stored.
        note.setText("HELLO");
        note.setColor(NoteColor.BLUE);
        note.save(DatabaseHelper.getDatabase());

        storedNote = notesManager.getNote(note.getId());
        Validators.validateNote(note, storedNote);
    }


    /**
     * Test {@link com.fsk.mynotes.data.Note#save(android.database.sqlite.SQLiteDatabase)}
     */
    public void testRemove() {
        NotesManager notesManager = new NotesManager(DatabaseHelper.getDatabase());

        Note note = new Note();
        assertEquals(Note.NOT_STORED, note.getId());
        note.save(DatabaseHelper.getDatabase());

        assertTrue(note.getId() >= 0);
        Note storedNote = notesManager.getNote(note.getId());
        Validators.validateNote(note, storedNote);

        //Remove the note
        note.delete(DatabaseHelper.getDatabase());
        assertEquals(Note.NOT_STORED, note.getId());

        Note removedNote = notesManager.getNote(note.getId());
        assertNull(removedNote);
    }
}