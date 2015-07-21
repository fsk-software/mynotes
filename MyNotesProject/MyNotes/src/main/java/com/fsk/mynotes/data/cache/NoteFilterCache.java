package com.fsk.mynotes.data.cache;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.receivers.NoteFilterBroadcast;
import com.google.common.base.Preconditions;

import java.util.HashSet;
import java.util.Set;

/**
 * A class to manager the saving and retrieval of the note color filter criteria to persistent
 * memory. Each setup will send a broadcast based on the schema in {@link
 * com.fsk.mynotes.receivers.NoteFilterBroadcast}
 */
public class NoteFilterCache {

    /**
     * The location of the persistent shared preference cache for the note filter.
     */
    static final String CACHE_NAME = "com.fsk.mynotes.note_filter_cache";


    /**
     * The shared preference object to allow access the shared preference cache.
     */
    private final SharedPreferences mSharedPreferences;


    /**
     * A local broadcast manager to send broadcasts upon any changes to the note filter preferences.
     * The broadcast is used instead of the Shared Preference listener in order to hide the
     * implementation.
     */
    private final LocalBroadcastManager mBroadcastManager;


    /**
     * Constructor
     *
     * @param context
     *         The context to use for accessing the system services and generating broadcasts. The
     *         context is not stored.
     */
    public NoteFilterCache(@NonNull Context context) {
        Preconditions.checkNotNull(context);

        mSharedPreferences = context.getSharedPreferences(CACHE_NAME, Context.MODE_PRIVATE);

        mBroadcastManager = LocalBroadcastManager.getInstance(context);
    }


    /**
     * Purge all of the stored filter values.  This will restore the system back to the default
     * filter values.
     */
    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }


    /**
     * Enable/Disable the specified color.  The actual storing to the persistent memory happens
     * asynchronously.
     *
     * @param color
     *         the color to modify.
     * @param enable
     *         true to enable the filter.
     */
    public void enableColor(@NonNull NoteColor color, boolean enable) {
        Preconditions.checkNotNull(color);
        mSharedPreferences.edit().putBoolean(color.name(), enable).apply();

        sendBroadcast(color, enable);
    }


    /**
     * Retrieve the color filter enable status from persistent memory. This reads from the
     * persistent memory.  Please use caution when calling from the UI thread.
     *
     * @param color
     *         The color to check.
     *
     * @return true if the color is enabled.
     */
    public boolean isColorEnabled(@NonNull NoteColor color) {
        Preconditions.checkNotNull(color);
        return mSharedPreferences.getBoolean(color.name(), true);
    }


    /**
     * Get all of the enabled colors.
     *
     * @return A {@link Set} of the enabled colors.  This may be empty, but will not be null.
     */
    public Set<NoteColor> getEnabledColors() {
        Set<NoteColor> returnValue = new HashSet<>();
        for (NoteColor color : NoteColor.values()) {
            if (isColorEnabled(color)) {
                returnValue.add(color);
            }
        }
        return returnValue;
    }


    /**
     * Send a broadcast containing the changed note filter color and its enabled status.
     *
     * @param color
     *         The updated color.
     * @param enable
     *         true if the color is enabled in the filter.
     */
    private void sendBroadcast(@NonNull NoteColor color, boolean enable) {
        mBroadcastManager.sendBroadcast(NoteFilterBroadcast.createIntent(color, enable));
    }
}
