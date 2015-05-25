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

    public void testValueOf() {
        //test null case
        try {
            assertNull(NoteColor.valueOf(null));
            assert true;
        }
        catch (NullPointerException e) {}

        //test empty/unknown case
        try {
            assertNull(NoteColor.valueOf(""));
            assert true;
        }
        catch (IllegalArgumentException e) {}

        //test sunny day
        for (NoteColor color : NoteColor.values()) {
            assertEquals(color, NoteColor.valueOf(color.name()));
        }
    }
}