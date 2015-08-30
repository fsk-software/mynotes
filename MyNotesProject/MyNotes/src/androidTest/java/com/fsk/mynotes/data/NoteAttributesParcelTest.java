package com.fsk.mynotes.data;


import android.os.Bundle;
import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;

import com.fsk.mynotes.constants.NoteColor;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test the {@link NoteAttributes class}.
 */
@RunWith(AndroidJUnit4.class)
public class NoteAttributesParcelTest {

    /**
     * The Parcel Tag.
     */
    private static final String PARCEL_TAG = "PARCEL_TAG";


    /**
     * Test parceling of a modified note.
     */
    @Test
    public void testNoteParceling() throws Exception{
        NoteAttributes expected = new NoteAttributes();
        expected.setId(5);
        expected.setColor(NoteColor.GREEN);
        expected.setText("AAAA");

        //Create Bundle
        Bundle saveBundle = new Bundle();
        saveBundle.putParcelable(PARCEL_TAG, expected);

        //Create Parcel and Save Bundle in it.
        Parcel parcel = Parcel.obtain();
        saveBundle.writeToParcel(parcel, 0);

        //Extract Bundle from the Parcel
        parcel.setDataPosition(0);
        Bundle extractBundle = parcel.readBundle();
        extractBundle.setClassLoader(NoteAttributes.class.getClassLoader());

        NoteAttributes actual = extractBundle.getParcelable(PARCEL_TAG);
        validate(expected, actual);
    }


    /**
     * Test the parceling of the default note.
     */
    @Test
    public void testParcelingOfDefaultNote() throws Exception {
        NoteAttributes expected = new NoteAttributes();

        //Create Bundle
        Bundle saveBundle = new Bundle();
        saveBundle.putParcelable(PARCEL_TAG, expected);

        //Create Parcel and Save Bundle in it.
        Parcel parcel = Parcel.obtain();
        saveBundle.writeToParcel(parcel, 0);

        //Extract Bundle from the Parcel
        parcel.setDataPosition(0);
        Bundle extractBundle = parcel.readBundle();
        extractBundle.setClassLoader(NoteAttributes.class.getClassLoader());

        NoteAttributes actual = extractBundle.getParcelable(PARCEL_TAG);
        validate(expected, actual);
    }

    /**
     * test {@link NoteAttributes#describeContents()}
     */
    @Test
    public void testDescribeContents() throws Exception{
        assertThat(new NoteAttributes().describeContents(), is(0));
    }

    /**
     * test {@link android.os.Parcelable.Creator#newArray(int)}
     */
    @Test
    public void testCreatorNewArray() {
        NoteAttributes notes[] = NoteAttributes.CREATOR.newArray(5);
        assertThat(notes.length, is(5));
    }

    /**
     * Validate the expected Note Attributes values match the actual values.
     * @param expected the expected note attributes.
     * @param actual the actual note attributes.
     */
    private void validate(NoteAttributes expected, NoteAttributes actual) {
        assertThat(expected.getId(), is(actual.getId()));
        assertThat(expected.getText(), is(actual.getText()));
        assertThat(expected.getColor(), is(actual.getColor()));
    }

}
