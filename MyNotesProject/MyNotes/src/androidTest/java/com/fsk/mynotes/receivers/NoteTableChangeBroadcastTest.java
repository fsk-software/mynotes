package com.fsk.mynotes.receivers;


import android.content.Intent;
import android.content.IntentFilter;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test the {@link com.fsk.mynotes.receivers.NoteTableChangeBroadcast}.
 */
@RunWith(AndroidJUnit4.class)
public class NoteTableChangeBroadcastTest {


    @Test
    public void testCreateIntentFilter() {
        IntentFilter intentFilter = NoteTableChangeBroadcast.createIntentFilter();
        assertThat(NoteTableChangeBroadcast.ACTION, is(intentFilter.getAction(0)));
    }



    @Test
    public void testCreateIntent() {
        Intent intent = NoteTableChangeBroadcast.createIntent();
        assertThat(NoteTableChangeBroadcast.ACTION, is(intent.getAction()));
    }
}