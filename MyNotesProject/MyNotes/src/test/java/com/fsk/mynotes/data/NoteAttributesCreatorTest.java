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
 * Test the {@link NoteAttributes class}.
 */
@RunWith(PowerMockRunner.class)
public class NoteAttributesCreatorTest {

    @Mock
    private Parcel mMockParcel;


    /**
     * Test the basic constructor and default values.
     */
    @Test
    public void testCreateFromParcel() {
        when(mMockParcel.readLong()).thenReturn(1L);
        when(mMockParcel.readInt()).thenReturn(2);
        when(mMockParcel.readString()).thenReturn("hello");

        NoteAttributes noteAttributes =
                NoteAttributes.CREATOR.createFromParcel(mMockParcel);

        assertThat(noteAttributes.getId(), is(1L));
        assertThat(NoteColor.getColor(2), is(noteAttributes.getColor()));
        assertThat("hello", is(noteAttributes.getText()));
    }


    /**
     * Test the basic constructor and default values.
     */
    @Test
    public void testCreateArray() {
        NoteAttributes noteAttributes[] = NoteAttributes.CREATOR.newArray(2);

        assertThat(noteAttributes.length, is(2));
    }
}