package com.fsk.mynotes.receivers;


import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;

import com.fsk.mynotes.constants.NoteColor;
import com.google.common.base.Preconditions;

/**
 * The schema and helper for Broadcasts about changes to the note filter values.
 */
public class NoteFilterBroadcast {

    /**
     * The action for the broadcast.
     */
    public static final String ACTION = "NoteFilterChangeBroadcast";


    /**
     * The extra data to include in each broadcast.
     */
    public static class Extras {
        /**
         * The key to the integer ordinal value that represents a value in {@link
         * com.fsk.mynotes.constants.NoteColor}.
         */
        public static final String COLOR = "NoteFilterColor";


        /**
         * The key to a boolean value that indicates if the note color is enabled.
         */
        public static final String ENABLED = "NoteFilterColorEnabled";
    }


    /**
     * Create the broadcast intent for the specified note color.
     *
     * @param color
     *         The note color for the broadcast.
     * @param enable
     *         true if the note color is enabled in the filter.
     *
     * @return the Intent for the broadcast.
     */
    public static Intent createIntent(@NonNull NoteColor color, boolean enable) {
        Preconditions.checkNotNull(color);

        final Intent returnValue = new Intent(ACTION);
        returnValue.putExtra(Extras.COLOR, color.ordinal());
        returnValue.putExtra(Extras.ENABLED, enable);

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
