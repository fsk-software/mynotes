package com.fsk.mynotes.data;


import android.os.Bundle;
import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;

import com.fsk.mynotes.Validators;
import com.fsk.mynotes.constants.NoteColor;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test the {@link Note class}.
 */
@RunWith(AndroidJUnit4.class)
public class NoteContentValuesTest {

    /**
     * The Parcel Tag.
     */
    private static final String PARCEL_TAG = "PARCEL_TAG";


    /**
     * Test parceling of a modified note.
     */
    @Test
    public void testNoteParceling() throws Exception {
        Note expectedNote = new Note.Builder()
                                    .setId(5)
                                    .setColor(NoteColor.GREEN)
                                    .setText("AAAA").build();

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
    @Test
    public void testParcelingOfDefaultNote() throws Exception {
        Note expectedNote = new Note.Builder().build();

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
     * test {@link Note#describeContents()}
     */
    @Test
    public void testDescribeContents() throws Exception{
        assertThat(new Note.Builder().build().describeContents(), is(0));
    }





    /**
     * test {@link android.os.Parcelable.Creator#newArray(int)}
     */
    @Test
    public void testCreatorNewArray() {
        Note notes[] = Note.CREATOR.newArray(5);
        assertThat(notes.length, is(5));
    }
}
