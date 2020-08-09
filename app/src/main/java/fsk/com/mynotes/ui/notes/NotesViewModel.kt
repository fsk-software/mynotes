package fsk.com.mynotes.ui.notes

import android.content.Context
import androidx.lifecycle.ViewModel
import fsk.com.mynotes.R
import fsk.com.mynotes.data.NoteColor
import fsk.com.mynotes.data.database.note.Note
import fsk.com.mynotes.data.database.note.NoteDao
import fsk.com.mynotes.data.preferences.NoteFilterPreferences
import fsk.com.mynotes.di.qualifiers.ApplicationContext
import fsk.com.mynotes.extensions.getNoteColorName
import io.reactivex.Flowable
import io.reactivex.rxkotlin.combineLatest
import javax.inject.Inject

/**
 * View model for [NotesFragment]
 * @property context Context to access application resources
 * @param noteDao The Dao object to manage persistence of notes.
 * @param noteFilterPreferences Helper to manage the note filter preferences
 */
class NotesViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val noteDao: NoteDao,
    private val noteFilterPreferences: NoteFilterPreferences
) : ViewModel() {

    /**
     * Monitor for changes to the selected colors. It emits a pair consisting of a set
     * of the selected note colors.
     */
    internal val getSelectedColorsUpdates: Flowable<Set<NoteColor>> =
        noteFilterPreferences.getSelectedColorUpdates.doOnNext {
            selectedColorsToolTip = createToolTipForColors(it)
        }

    /**
     * Monitor for updates to the notes.
     * It emits an ordered and filtered list of the notes
     */
    internal val getNoteUpdates: Flowable<List<Note>> =
        noteDao.getAllNotes()
            .combineLatest(getSelectedColorsUpdates)
            .map {
                it.first.filter { note ->
                    it.second.contains(note.color)
                }
            }

    /**
     * The displayable tooltip text for the color palette menu item
     */
    internal var selectedColorsToolTip: String = ""
        private set

    private fun createToolTipForColors(selectedColors: Set<NoteColor>): String {
        val colorString = if (selectedColors.size == NoteColor.values().size) {
            context.getString(R.string.all_colors_selected)
        } else {
            val builder = StringBuilder()
            selectedColors.forEach { builder.appendln(context.getNoteColorName(it)) }
            builder.toString()
        }

        return context.getString(R.string.color_filter_tooltip, colorString)
    }

}