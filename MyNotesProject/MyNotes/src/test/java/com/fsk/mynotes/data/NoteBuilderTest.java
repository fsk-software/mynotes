package com.fsk.mynotes.data;


import com.fsk.mynotes.constants.NoteColor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Test the {@link NoteAttributes class}.
 */
@RunWith(PowerMockRunner.class)
public class NoteBuilderTest {


    @Test
    public void testBuildUnmodified() throws Exception {
        Note.Builder unitUnderTest = new Note.Builder();
        Note note = unitUnderTest.build();

        assertThat(note.getId(), is((long) Note.NOT_STORED));
        assertThat(note.getColor(), is(NoteColor.YELLOW));
        assertThat(note.getText(), is(""));
    }


    @Test
    public void testBuildModified() throws Exception {
        Note.Builder unitUnderTest = new Note.Builder();
        unitUnderTest.setId(1L);
        unitUnderTest.setText("hello");
        unitUnderTest.setColor(NoteColor.GREEN);

        Note note = unitUnderTest.build();

        assertThat(note.getId(), is(1L));
        assertThat(note.getColor(), is(NoteColor.GREEN));
        assertThat(note.getText(), is("hello"));
    }
}