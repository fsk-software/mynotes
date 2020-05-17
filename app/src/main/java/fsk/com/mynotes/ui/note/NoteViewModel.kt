package fsk.com.mynotes.ui.note

import android.content.Context
import androidx.lifecycle.ViewModel
import fsk.com.mynotes.data.NoteColor
import fsk.com.mynotes.data.database.note.Note
import fsk.com.mynotes.data.database.note.NoteDao
import fsk.com.mynotes.extensions.getNoteArgb
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class NoteViewModel @Inject constructor(
    val context: Context,
    @NoteId
    val initialNoteId: Long,
    val noteDao: NoteDao
) : ViewModel() {

    private lateinit var note: Note

    fun getNoteArgb(): Int = context.getNoteArgb(note.color)

    fun initialize(): Single<Note> {
       return noteDao.getNoteById(initialNoteId)
            .flatMap {
                note = it
                Single.just(note)
            }
            .onErrorReturn {
                note = Note()
                note
            }
    }

    fun changeNoteColor(color: NoteColor): Completable {
        note.color = color
        return saveNote()
    }

    fun editNoteText(text: String): Completable {
        note.text = text
        return saveNote()
    }

    fun editNoteTitle(title: String): Completable {
        note.title = title
        return saveNote()
    }

    private fun saveNote(): Completable {
        return if (note.persisted()) {
            noteDao.insert(note).flatMapCompletable {
                note.id = it
                Completable.complete()
            }
        }
        else {
            noteDao.update(note)
        }
    }

    fun deleteNote(): Completable = noteDao.delete(note)
}