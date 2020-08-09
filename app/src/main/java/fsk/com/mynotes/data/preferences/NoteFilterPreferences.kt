package fsk.com.mynotes.data.preferences

import android.content.SharedPreferences
import com.squareup.moshi.JsonAdapter
import fsk.com.mynotes.data.NoteColor
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject


/**
 * Manages the storage and retrieval of the selected colors from persistent storage.
 *
 * @property sharedPreferences the shared preferences to manager
 * @property noteColorSetAdapter adapter to perform serialization and deserialization of the note colors
 */
class NoteFilterPreferences(
    private val sharedPreferences: SharedPreferences,
    private val noteColorSetAdapter: JsonAdapter<Set<NoteColor>>
) {

    companion object {
        /**
         * Name of the shared preferences files.
         */
        const val SHARED_PREFERENCE_NAME = "note_filters_preferences.xml"

        private const val SELECTED_COLORS_KEY = "selected_colors_key"
    }

    private val selectColorUpdatePublisher: BehaviorSubject<Set<NoteColor>> =
        BehaviorSubject.createDefault(getSelectedColors())

    /**
     * Use to monitor for changes to the persisted selected colors
     */
    val getSelectedColorUpdates: Flowable<Set<NoteColor>> =
        selectColorUpdatePublisher.toFlowable(BackpressureStrategy.LATEST)

    private val sharedPreferenceListener: SharedPreferences.OnSharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            when (key) {
                SELECTED_COLORS_KEY -> selectColorUpdatePublisher.onNext(getSelectedColors())
            }
        }

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceListener)
    }

    private fun getSelectedColors(): Set<NoteColor> =
        sharedPreferences.getString(SELECTED_COLORS_KEY, null)?.let {
            autoFillEmptyColors(noteColorSetAdapter.fromJson(it)!!.toMutableSet())
        } ?: NoteColor.values().toSet()

    /**
     * Set the selected colors in the preferences
     *
     * @param colors the selected colors to set
     */
    private fun setSelectedColors(colors: Set<NoteColor>) {
        autoFillEmptyColors(colors.toMutableSet())
        sharedPreferences.edit().apply {
            putString(SELECTED_COLORS_KEY, noteColorSetAdapter.toJson(colors))
            apply()
        }
    }

    /**
     * Clear only the specified color in the preferences
     *
     * @param color the color to remove
     */
    fun removeSelectedColor(color: NoteColor) {
        val selectedColors = getSelectedColors().toMutableSet()
        if (selectedColors.remove(color)) {
            setSelectedColors(selectedColors)

        }
    }

    /**
     * Add only the specified color t0 the preferences
     *
     * @param color the color to add
     */
    fun addSelectedColor(color: NoteColor) {
        val selectedColors = getSelectedColors().toMutableSet()
        if (selectedColors.add(color)) {
            setSelectedColors(selectedColors)
        }
    }

    private fun autoFillEmptyColors(colors: MutableSet<NoteColor>): MutableSet<NoteColor> {
        if (colors.isEmpty()) {
            colors.add(NoteColor.YELLOW)
        }
        return colors
    }
}