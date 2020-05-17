package fsk.com.mynotes.ui.notes

import android.content.Context
import androidx.lifecycle.ViewModel
import fsk.com.mynotes.R
import fsk.com.mynotes.data.NoteColor
import fsk.com.mynotes.data.database.note.Note
import fsk.com.mynotes.data.database.note.NoteDao
import fsk.com.mynotes.data.preferences.AppPreferences
import fsk.com.mynotes.extensions.getNoteColorName
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class NotesViewModel @Inject constructor(
    val context: Context,
    val noteDao: NoteDao,
    val appPreferences: AppPreferences
) : ViewModel() {

    val selectedColorsObservable: Observable<Set<NoteColor>> get() = appPreferences.selectedColorsObservable

    private val editNoteSubject: PublishSubject<Long> = PublishSubject.create()
    val editNoteObservable: Observable<Long> get() = editNoteSubject

    val notesFlowable: Flowable<List<Note>> = noteDao.getNotesForColors(appPreferences.getSelectedColors())

    fun getSelectedColorsToolTip(): String {
        val selectedColors = appPreferences.getSelectedColors()

        val colorString = if (selectedColors.size == NoteColor.values().size) {
            context.getString(R.string.all_colors_selected)
        } else {
            val builder = StringBuilder()
            selectedColors.forEach { builder.appendln(context.getNoteColorName(it)) }
            builder.toString()
        }

        return context.getString(R.string.color_filter_tooltip, colorString)
    }

    fun updateNote(note:Note?) {
        editNoteSubject.onNext(note?.id?: -1)
    }
}