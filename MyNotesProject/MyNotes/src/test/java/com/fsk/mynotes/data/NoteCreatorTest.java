package com.fsk.mynotes.data;


import android.os.Parcel;

import com.fsk.mynotes.constants.NoteColor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

/**
 * Test the {@link Note class}.
 */
@RunWith(PowerMockRunner.class)
public class NoteCreatorTest {

    @Mock
    private Parcel mMockParcel;

    @Mock
    private NoteAttributes mMockNoteAttributes;

    /**
     * Test the basic constructor and default values.
     */
    @Test
    public void testCreateFromParcel() {
        when(mMockNoteAttributes.getId()).thenReturn(1L);
        when(mMockNoteAttributes.getColor()).thenReturn(NoteColor.GREEN);
        when(mMockNoteAttributes.getText()).thenReturn("hello");

        when(mMockParcel.readParcelable(NoteAttributes.class.getClassLoader())).thenReturn(
                mMockNoteAttributes);

        Note note = Note.CREATOR.createFromParcel(mMockParcel);

        assertThat(note.getId(), is(1L));
        assertThat(NoteColor.GREEN, is(note.getColor()));
        assertThat("hello", is(note.getText()));
    }


    /**
     * Test the basic constructor and default values.
     */
    @Test
    public void testCreateArray() {
        Note note[] = Note.CREATOR.newArray(2);

        assertThat(note.length, is(2));
    }
}