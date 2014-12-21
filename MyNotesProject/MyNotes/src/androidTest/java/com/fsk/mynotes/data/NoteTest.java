package com.fsk.mynotes.data;

import android.test.AndroidTestCase;

/**
 * Test the {@link com.fsk.mynotes.data.Note class}.
 */
public class NoteTest extends AndroidTestCase {

    public void testNote() throws Exception {
        Note note = new Note(-1, null, 0, null);
        assertEquals(0, note.getColor());
        assertEquals(-1, note.getRow());
        assertEquals(null, note.getText());
    }
}