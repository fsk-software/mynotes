package com.fsk.mynotes.receivers;


import android.content.Intent;
import android.content.IntentFilter;
import android.support.test.runner.AndroidJUnit4;

import com.fsk.mynotes.constants.NoteColor;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test the {@link com.fsk.mynotes.receivers.NoteFilterBroadcast}.
 */
@RunWith(AndroidJUnit4.class)
public class NoteFilterBroadcastTest {


    @Test
    public void testCreateIntentFilter() {
        IntentFilter intentFilter = NoteFilterBroadcast.createIntentFilter();
        assertThat(NoteFilterBroadcast.ACTION, is(intentFilter.getAction(0)));
    }


    @Test
    public void testCreateIntentForEachNoteColorAndEnable() {
        for (NoteColor noteColor : NoteColor.values()) {
            Intent enabledIntent = NoteFilterBroadcast.createIntent(noteColor, true);
            assertThat(NoteFilterBroadcast.ACTION, is(enabledIntent.getAction()));
            assertThat(enabledIntent.getIntExtra(NoteFilterBroadcast.Extras.COLOR, -1), is(noteColor.ordinal()));
            assertThat(enabledIntent.getBooleanExtra(NoteFilterBroadcast.Extras.ENABLED, false),
                       is(true));

            Intent disabledIntent = NoteFilterBroadcast.createIntent(noteColor, false);
            assertThat(NoteFilterBroadcast.ACTION, is(disabledIntent.getAction()));
            assertThat(disabledIntent.getIntExtra(NoteFilterBroadcast.Extras.COLOR, -1), is(noteColor.ordinal()));
            assertThat(disabledIntent.getBooleanExtra(NoteFilterBroadcast.Extras.ENABLED, true), is(false));
        }
    }

    @Test
    public void testCreateIntentForNullColor() {
        try {
            NoteFilterBroadcast.createIntent(null, true);
            assert false;
        }
        catch (NullPointerException e) {
        }
    }
}