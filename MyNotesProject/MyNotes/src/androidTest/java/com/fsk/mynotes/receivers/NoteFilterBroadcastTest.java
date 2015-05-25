package com.fsk.mynotes.receivers;


import android.content.Intent;
import android.content.IntentFilter;
import android.test.AndroidTestCase;

import com.fsk.mynotes.constants.NoteColor;

/**
 * Test the {@link com.fsk.mynotes.receivers.NoteFilterBroadcast}.
 */
public class NoteFilterBroadcastTest extends AndroidTestCase {


    public void testCreateIntentFilter() {
        IntentFilter intentFilter = NoteFilterBroadcast.createIntentFilter();
        assertEquals(NoteFilterBroadcast.ACTION, intentFilter.getAction(0));
    }


    public void testCreateIntentForEachNoteColorAndEnable() {
        for (NoteColor noteColor : NoteColor.values()) {
            Intent enabledIntent = NoteFilterBroadcast.createIntent(noteColor, true);
            assertEquals(NoteFilterBroadcast.ACTION, enabledIntent.getAction());
            assertEquals(noteColor.ordinal(), enabledIntent.getIntExtra(NoteFilterBroadcast.Extras.COLOR, -1));
            assertTrue(enabledIntent.getBooleanExtra(NoteFilterBroadcast.Extras.ENABLED, false));

            Intent disabledIntent = NoteFilterBroadcast.createIntent(noteColor, false);
            assertEquals(NoteFilterBroadcast.ACTION, disabledIntent.getAction());
            assertEquals(noteColor.ordinal(), disabledIntent.getIntExtra(NoteFilterBroadcast.Extras.COLOR, -1));
            assertFalse(disabledIntent.getBooleanExtra(NoteFilterBroadcast.Extras.ENABLED, true));
        }
    }

    public void testCreateIntentForNullColor() {
        try {
            NoteFilterBroadcast.createIntent(null, true);
            assert false;
        }
        catch (NullPointerException e) {
            assert true;
        }
    }
}