package com.fsk.mynotes.data;


import android.os.Parcel;

import com.fsk.mynotes.constants.NoteColor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test the {@link NoteAttributes class}.
 */
@RunWith(PowerMockRunner.class)
public class NoteAttributesTest {

    @Mock
    private Parcel mMockParcel;


    /**
     * Test the basic constructor and default values.
     */
    @Test
    public void testConstruction() {
        NoteAttributes noteAttributes = new NoteAttributes();
        assertThat(noteAttributes.getId(), is((long) NoteAttributes.UNKNOWN));
        assertThat(NoteColor.YELLOW, is(noteAttributes.getColor()));
        assertThat("", is(noteAttributes.getText()));
    }


    /**
     * Test the basic constructor and default values.
     */
    @Test
    public void testMockedParcelConstruction() {
        when(mMockParcel.readLong()).thenReturn(1L);
        when(mMockParcel.readInt()).thenReturn(2);
        when(mMockParcel.readString()).thenReturn("hello");

        NoteAttributes noteAttributes = new NoteAttributes(mMockParcel);
        assertThat(noteAttributes.getId(), is(1L));
        assertThat(NoteColor.getColor(2), is(noteAttributes.getColor()));
        assertThat("hello", is(noteAttributes.getText()));
    }

    @Test
    public void testWriteToParcelMocked() {
        NoteAttributes noteAttributes = new NoteAttributes();
        doNothing().when(mMockParcel).writeLong(NoteAttributes.UNKNOWN);
        doNothing().when(mMockParcel).writeString("");
        doNothing().when(mMockParcel).writeInt(NoteColor.YELLOW.ordinal());

        noteAttributes.writeToParcel(mMockParcel, 0);

        verify(mMockParcel).writeLong(NoteAttributes.UNKNOWN);
        verify(mMockParcel).writeString("");
        verify(mMockParcel).writeInt(NoteColor.YELLOW.ordinal());

    }

    /**
     * Test change the noteAttributes id to a valid value
     */
    @Test
    public void testChangingNoteIdSunnyDay() {
        NoteAttributes noteAttributes = new NoteAttributes();
        noteAttributes.setId(1);
        assertThat(noteAttributes.getId(), is(1L));

        //Set it to the not stored case
        noteAttributes.setId(NoteAttributes.UNKNOWN);
        assertThat((long) NoteAttributes.UNKNOWN, is(noteAttributes.getId()));
    }


    /**
     * Test change the noteAttributes id to invalid values.
     */
    @Test
    public void testChangingNoteIdRainyDay() {
        NoteAttributes noteAttributes = new NoteAttributes();
        try {
            noteAttributes.setId(-2);
            assert false;
        }
        catch (IllegalArgumentException e) {}
    }

    /**
     * Test {@link NoteAttributes#setColor(NoteColor)} to each color.
     */
    @Test
    public void testChangingNoteColorToEachValidColor() {
        NoteAttributes noteAttributes = new NoteAttributes();
        for (NoteColor noteColor : NoteColor.values()) {
            noteAttributes.setColor(noteColor);
            assertThat(noteColor, is(noteAttributes.getColor()));
        }
    }


    /**
     * Test {@link NoteAttributes#setColor(NoteColor)} to null.
     */
    @Test
    public void testChangeNoteColorToNull() {
        NoteAttributes noteAttributes = new NoteAttributes();
        try {
            noteAttributes.setColor(null);
            assert false;
        }
        catch (NullPointerException npe) {
        }
    }


    /**
     * Change the noteAttributes string to a valid value.
     */
    @Test
    public void testChangeNoteTextToValidString() {
        NoteAttributes noteAttributes = new NoteAttributes();

        //Set the non-null and not empty string.
        String testString = "AA";
        noteAttributes.setText(testString);
        assertThat(testString, is(noteAttributes.getText()));
    }


    /**
     * Change the noteAttributes text to null.
     */
    @Test
    public void testNoteTextToNull() {
        NoteAttributes noteAttributes = new NoteAttributes();

        //First set the string to a valid string.
        noteAttributes.setText("Hello");

        //Now set the null string and verify that the empty string is set.
        noteAttributes.setText(null);
        assertThat("", is(noteAttributes.getText()));
    }

    /**
     * Change the noteAttributes text to empty.
     */
    @Test
    public void testNoteTextToEmptyString() {
        NoteAttributes noteAttributes = new NoteAttributes();

        //First set the string to a valid string.
        noteAttributes.setText("Hello");

        //Now set the empty string and verify that the empty string is set.
        noteAttributes.setText("");
        assertThat("", is(noteAttributes.getText()));
    }


    /**
     * test {@link NoteAttributes#describeContents()}
     */
    @Test
    public void testDescribeContents() {
        assertThat(new NoteAttributes().describeContents(), is(0));
    }


    @Test
    public void testEqualityToNull() {
        NoteAttributes unitUnderTest = new NoteAttributes();
        assertThat(unitUnderTest.equals(null), is(false));
    }

    @Test
    public void testEqualityToObjectOfWrongType() {
        NoteAttributes unitUnderTest = new NoteAttributes();
        assertThat(unitUnderTest.equals(new Object()), is(false));
    }


    @Test
    public void testEqualityToSelf() {
        NoteAttributes unitUnderTest = new NoteAttributes();
        assertThat(unitUnderTest.equals(unitUnderTest), is(true));
    }

    @Test
    public void testEqualityToOtherWithSameValue() {
        NoteAttributes unitUnderTest = new NoteAttributes();
        assertThat(unitUnderTest.equals(new NoteAttributes()), is(true));
    }


    @Test
    public void testCloneUnmodified() throws Exception {
        NoteAttributes unitUnderTest = new NoteAttributes();
        NoteAttributes cloned = unitUnderTest.clone();
        assertThat(unitUnderTest.equals(cloned), is(true));
    }

    @Test
    public void testCloneModified() throws Exception {
        NoteAttributes unitUnderTest = new NoteAttributes();
        unitUnderTest.setId(2L);
        unitUnderTest.setColor(NoteColor.GREEN);
        unitUnderTest.setText("hello");

        NoteAttributes cloned = unitUnderTest.clone();
        assertThat(unitUnderTest.equals(cloned), is(true));
    }
}