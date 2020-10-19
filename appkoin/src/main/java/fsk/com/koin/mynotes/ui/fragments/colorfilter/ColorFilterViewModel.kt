package fsk.com.koin.mynotes.ui.fragments.colorfilter

import androidx.lifecycle.ViewModel
import fsk.com.koin.mynotes.data.NoteColor
import fsk.com.koin.mynotes.data.preferences.NoteFilterPreferences
import io.reactivex.Flowable

/**
 * View Model to manage the color filter interactions.
 */
class ColorFilterViewModel(
    private val noteFilterPreferences: NoteFilterPreferences
) : ViewModel() {

    /**
     * Use to monitor changes the selected colors.  It emits a set of updated colors.
     */
    internal val getSelectedColorUpdates: Flowable<Set<NoteColor>> =
        noteFilterPreferences.getSelectedColorUpdates

    /**
     * Update the selected colors.  Subscribe to [getSelectedColorUpdates] to receive changes.
     *
     * @param noteColorOrdinal the ordinal of the note color to update
     * @param selected true if the note color is selected.
     */
    internal fun updateSelectedColor(noteColorOrdinal: Int, selected: Boolean) {
        NoteColor.values()[noteColorOrdinal].also {
            if (selected) {
                noteFilterPreferences.addSelectedColor(it)
            } else {
                noteFilterPreferences.removeSelectedColor(it)
            }
        }
    }
}