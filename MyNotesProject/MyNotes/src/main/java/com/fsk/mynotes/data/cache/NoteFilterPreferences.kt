package com.fsk.mynotes.data.cache;


import android.content.Context;
import android.content.SharedPreferences;

import com.fsk.mynotes.constants.NoteColor;


/**
 * The location of the persistent shared preference cache for the note filter.
 */
val CACHE_NAME = "com.fsk.mynotes.note_filter_cache";

/**
 * A class to manager the saving and retrieval of the note color filter criteria to persistent
 * memory.
 * @param context The shared preference object to allow access the shared preference cache.
 */
class NoteFilterPreferences(context: Context) {


    /**
     * The shared preference object to allow access the shared preference cache.
     */
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(CACHE_NAME, Context.MODE_PRIVATE);


    /**
     * Purge all of the stored filter values.  This will restore the system back to the default
     * filter values.
     */
    fun clear() {
        sharedPreferences.edit().clear().apply();
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
    fun enableColor(color: NoteColor,
                    enable: Boolean) {
        sharedPreferences.edit().putBoolean(color.name, enable).apply();
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
    fun isColorEnabled(color: NoteColor): Boolean {
        return sharedPreferences.getBoolean(color.name, true);
    }


    /**
     * Get all of the enabled colors.
     *
     * @return A {@link Set} of the enabled colors.  This may be empty, but will not be null.
     */
    fun getEnabledColors(): Set<NoteColor> {
        val returnValue: MutableSet<NoteColor> = mutableSetOf<NoteColor>();
        NoteColor.values().forEach {
            if (isColorEnabled(it)) {
                returnValue.add(it);
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
    fun registerListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }


    /**
     * Unregisters a previous callback.
     *
     * @param listener
     *         the listener to remove.
     */
    fun unregisterListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }
}
