package com.fsk.mynotes;


import com.fsk.mynotes.data.Note;

import java.util.List;

import static junit.framework.Assert.assertEquals;


/**
 * Validation methods to simplify unit testing.
 */
public abstract class Validators {

    /**
     * Validate that the expected list matches the actual list.
     * @param expected the list of expected notes.
     * @param actual the list of actual notes.
     */
    public static void validateNoteLists(List<Note> expected, List<Note> actual) {
        assertEquals(expected==null, actual==null);
        if (expected == null) {
            return;
        }

        assertEquals(expected.size(), actual.size());
        for (int i=0; i<expected.size(); ++i) {
            validateNote(expected.get(i), actual.get(i));
        }
    }


    /**
     * Validate the expected Note values match the actual values.
     * @param expected the expected note.
     * @param actual the actual note.
     */
    public static void validateNote(Note expected, Note actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getText(), actual.getText());
        assertEquals(expected.getColor(), actual.getColor());
    }

}
