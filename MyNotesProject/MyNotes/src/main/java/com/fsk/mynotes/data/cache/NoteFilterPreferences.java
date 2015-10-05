package com.fsk.mynotes.data.cache;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.fsk.mynotes.constants.NoteColor;


import java.util.HashSet;
import java.util.Set;

/**
 * A class to manager the saving and retrieval of the note color filter criteria to persistent
 * memory.
 */
public class NoteFilterPreferences {

    /**
     * The location of the persistent shared preference cache for the note filter.
     */
    static final String CACHE_NAME = "com.fsk.mynotes.note_filter_cache";


    /**
     * The shared preference object to allow access the shared preference cache.
     */
    private final SharedPreferences mSharedPreferences;


    /**
     * Constructor
     *
     * @param context
     *         The context to use for accessing the system services and generating broadcasts. The
     *         context is not stored.
     */
    public NoteFilterPreferences(@NonNull Context context) {
        mSharedPreferences = context.getSharedPreferences(CACHE_NAME, Context.MODE_PRIVATE);
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
        mSharedPreferences.edit().putBoolean(color.name(), enable).apply();
    }


    /**
     * Retrieve the color filter enable status from persistent memory. Please use caution when
     * calling from the UI thread.
     *
     * @param color
     *         The color to check.
     *
     * @return true if the color is enabled.
     */
    public boolean isColorEnabled(@NonNull NoteColor color) {
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
     * Registers a callback to be invoked when a change happens to a preference.
     *
     * @param listener
     *         the listener to register.
     */
    public void registerListener(
            @NonNull SharedPreferences.OnSharedPreferenceChangeListener listener) {
        mSharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }


    /**
     * Unregisters a previous callback.
     *
     * @param listener
     *         the listener to remove.
     */
    public void unregisterListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }
}
