package com.fsk.mynotes.receivers;


import android.content.Intent;
import android.content.IntentFilter;
import android.test.AndroidTestCase;

/**
 * Test the {@link com.fsk.mynotes.receivers.NoteTableChangeBroadcast}.
 */
public class NoteTableChangeBroadcastTest extends AndroidTestCase {


    public void testCreateIntentFilter() {
        IntentFilter intentFilter = NoteTableChangeBroadcast.createIntentFilter();
        assertEquals(NoteTableChangeBroadcast.ACTION, intentFilter.getAction(0));
    }



    public void testCreateIntent() {
        Intent intent = NoteTableChangeBroadcast.createIntent();
        assertEquals(NoteTableChangeBroadcast.ACTION, intent.getAction());
    }
}