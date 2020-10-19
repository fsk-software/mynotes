package fsk.com.mynotes.ui.notes

import androidx.lifecycle.ViewModel
import fsk.com.mynotes.data.NoteColor
import fsk.com.mynotes.data.database.note.Note
import fsk.com.mynotes.data.database.note.NoteDao
import fsk.com.mynotes.data.preferences.NoteFilterPreferences
import io.reactivex.Flowable
import io.reactivex.rxkotlin.combineLatest
import javax.inject.Inject

/**
 * View model for [NotesFragment]
 * @param noteDao The Dao object to manage persistence of notes.
 * @param noteFilterPreferences Helper to manage the note filter preferences
 */
class NotesViewModel @Inject constructor(
    private val noteDao: NoteDao,
    private val noteFilterPreferences: NoteFilterPreferences
) : ViewModel() {

    /**
     * Monitor for changes to the selected colors. It emits a pair consisting of a set
     * of the selected note colors.
     */
    internal val getSelectedColorsUpdates: Flowable<Set<NoteColor>> =
        noteFilterPreferences.getSelectedColorUpdates

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
}