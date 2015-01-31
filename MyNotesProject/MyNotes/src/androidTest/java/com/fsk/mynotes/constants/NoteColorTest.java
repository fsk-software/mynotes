package com.fsk.mynotes.constants;


import android.test.AndroidTestCase;

/**
 * Test the {@link com.fsk.mynotes.constants.NoteColor}.
 */
public class NoteColorTest extends AndroidTestCase {

    /**
     * Test method {@link com.fsk.mynotes.constants.NoteColor#getColor(long)}
     */
    public void testGetColor() {
        //test a negative index
        NoteColor negativeColor = NoteColor.getColor(-1);
        assertNull(negativeColor);

        //test an excessive index
        NoteColor excessiveColor = NoteColor.getColor(Integer.MAX_VALUE);
        assertNull(excessiveColor);

        for (NoteColor color : NoteColor.values()) {
            assertEquals(color, NoteColor.getColor(color.ordinal()));
        }
    }
}