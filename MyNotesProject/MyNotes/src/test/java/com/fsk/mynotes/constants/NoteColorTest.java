package com.fsk.mynotes.constants;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Test the {@link NoteColor}.
 */
@RunWith(PowerMockRunner.class)
public class NoteColorTest {

    /**
     * Test method {@link NoteColor#getColor(long)}
     */
    @Test
    public void testGetColor() {
        //test a negative index
        NoteColor negativeColor = NoteColor.getColor(-1);
        assertThat(negativeColor, is(nullValue()));

        //test an excessive index
        NoteColor excessiveColor = NoteColor.getColor(Integer.MAX_VALUE);
        assertThat(excessiveColor, is(nullValue()));

        for (NoteColor color : NoteColor.values()) {
            assertThat(NoteColor.getColor(color.ordinal()), is(color));
        }
    }

    @Test
    public void testValueOf() {
        //test null case
        try {
            assertThat(NoteColor.valueOf(null), is(nullValue()));
            assert false;
        }
        catch (NullPointerException e) {}

        //test empty/unknown case
        try {
            assertThat(NoteColor.valueOf(""), is(nullValue()));
            assert false;
        }
        catch (IllegalArgumentException e) {}

        //test sunny day
        for (NoteColor color : NoteColor.values()) {
            assertThat(NoteColor.valueOf(color.name()), is(color));
        }
    }
}