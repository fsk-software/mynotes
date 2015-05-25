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
     * The Parcel Tag.
     */
    private static final String PARCEL_TAG = "PARCEL_TAG";


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
     * Test change the note id to a valid value
     */
    public void testChangingNoteIdSunnyDay() {
        Note note = new Note();
        note.setId(1);
        assertEquals(1, note.getId());

        //Set it to the not stored case
        note.setId(Note.NOT_STORED);
        assertEquals(Note.NOT_STORED, note.getId());
    }


    /**
     * Test change the note id to invalid values.
     */
    public void testChangingNoteIdRainyDay() {
        Note note = new Note();
        try {
            note.setId(-2);
            assert true;
        }
        catch (IllegalArgumentException e) {}
    }

    /**
     * Test {@link Note#setColor(com.fsk.mynotes.constants.NoteColor)} to each color.
     */
    public void testChangingNoteColorToEachValidColor() {
        Note note = new Note();
        for (NoteColor noteColor : NoteColor.values()) {
            note.setColor(noteColor);
            assertEquals(noteColor, note.getColor());
        }
    }


    /**
     * Test {@link Note#setColor(com.fsk.mynotes.constants.NoteColor)} to null.
     */
    public void testChangeNoteColorToNull() {
        Note note = new Note();
        try {
            note.setColor(null);
            assert true;
        }
        catch (NullPointerException npe) {
        }
    }


    /**
     * Change the note string to a valid value.
     */
    public void testChangeNoteTextToValidString() {
        Note note = new Note();

        //Set the non-null and not empty string.
        String testString = "AA";
        note.setText(testString);
        assertEquals(testString, note.getText());
    }


    /**
     * Change the note text to null.
     */
    public void testNoteTextToNull() {
        Note note = new Note();

        //First set the string to a valid string.
        note.setText("Hello");

        //Now set the null string and verify that the empty string is set.
        note.setText(null);
        assertEquals("", note.getText());
    }

    /**
     * Change the note text to empty.
     */
    public void testNoteTextToEmptyString() {
        Note note = new Note();

        //First set the string to a valid string.
        note.setText("Hello");

        //Now set the empty string and verify that the empty string is set.
        note.setText("");
        assertEquals("", note.getText());
    }


    /**
     * Test parceling of a modified note.
     */
    public void testNoteParceling() {
        Note expectedNote = new Note();
        expectedNote.setId(5);
        expectedNote.setColor(NoteColor.GREEN);
        expectedNote.setText("AAAA");

        //Create Bundle
        Bundle saveBundle = new Bundle();
        saveBundle.putParcelable(PARCEL_TAG, expectedNote);

        //Create Parcel and Save Bundle in it.
        Parcel parcel = Parcel.obtain();
        saveBundle.writeToParcel(parcel, 0);

        //Extract Bundle from the Parcel
        parcel.setDataPosition(0);
        Bundle extractBundle = parcel.readBundle();
        extractBundle.setClassLoader(Note.class.getClassLoader());

        Note actualNote = extractBundle.getParcelable(PARCEL_TAG);
        Validators.validateNote(expectedNote, actualNote);
    }


    /**
     * Test the parceling of the default note.
     */
    public void testParcelingOfDefaultNote() {
        Note expectedNote = new Note();

        //Create Bundle
        Bundle saveBundle = new Bundle();
        saveBundle.putParcelable(PARCEL_TAG, expectedNote);

        //Create Parcel and Save Bundle in it.
        Parcel parcel = Parcel.obtain();
        saveBundle.writeToParcel(parcel, 0);

        //Extract Bundle from the Parcel
        parcel.setDataPosition(0);
        Bundle extractBundle = parcel.readBundle();
        extractBundle.setClassLoader(Note.class.getClassLoader());

        Note actualNote = extractBundle.getParcelable(PARCEL_TAG);
        Validators.validateNote(expectedNote, actualNote);
    }

    /**
     * Test {@link com.fsk.mynotes.data.Note#save(android.database.sqlite.SQLiteDatabase)}
     */
    public void testSavingNote() {
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
     * Test {@link com.fsk.mynotes.data.Note#delete(android.database.sqlite.SQLiteDatabase)}
     */
    public void testDeletingNote() {
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


    /**
     * test {@link Note#describeContents()}
     */
    public void testDescribeContents() {
        assertEquals(0, new Note().describeContents());
    }

    /**
     * test {@link android.os.Parcelable.Creator#newArray(int)}
     */
    public void testCreatorNewArray() {
        Note notes[] = Note.CREATOR.newArray(5);
        assertEquals(5, notes.length);
    }
}