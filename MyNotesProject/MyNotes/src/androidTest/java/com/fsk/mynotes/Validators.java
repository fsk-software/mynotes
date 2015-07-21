package com.fsk.mynotes;


import com.fsk.mynotes.data.Note;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


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
        assertThat(expected == null, is(actual == null));
        if (expected == null) {
            return;
        }

        assertThat(expected.size(), is(actual.size()));
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
        assertThat(expected.getId(), is(actual.getId()));
        assertThat(expected.getText(), is(actual.getText()));
        assertThat(expected.getColor(), is(actual.getColor()));
    }

}
