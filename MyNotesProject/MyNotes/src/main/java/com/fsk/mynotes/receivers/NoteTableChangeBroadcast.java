package com.fsk.mynotes.receivers;


import android.content.Intent;
import android.content.IntentFilter;

/**
 * The schema and helper for Broadcasts about changes to the notes.
 */
public class NoteTableChangeBroadcast {

    /**
     * The action for the broadcast.
     */
    public static final String ACTION = "NoteTableChangeBroadcast";


    /**
     * Create the broadcast intent that indicates a note change occurred. The broadcast does note
     * include any data about the modified note.
     *
     * @return the Intent for the broadcast.
     */
    public static Intent createIntent() {

        final Intent returnValue = new Intent(ACTION);

        return returnValue;
    }


    /**
     * Create the broadcast intent filter.
     * @return the {@link android.content.IntentFilter} for the broadcast.
     */
    public static IntentFilter createIntentFilter() {
        return new IntentFilter(ACTION);
    }
}
