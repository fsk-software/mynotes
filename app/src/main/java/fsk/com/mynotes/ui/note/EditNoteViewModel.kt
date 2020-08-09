package fsk.com.mynotes.ui.note

import android.content.Context
import androidx.lifecycle.ViewModel
import fsk.com.mynotes.data.NoteColor
import fsk.com.mynotes.data.database.note.Note
import fsk.com.mynotes.data.database.note.NoteDao
import fsk.com.mynotes.di.qualifiers.ApplicationContext
import fsk.com.mynotes.extensions.getNoteArgb
import fsk.com.mynotes.ui.note.di.NoteId
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * View model to manage the [EditNoteFragment]
 */
class EditNoteViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    @NoteId private val initialNoteId: Long,
    private val noteDao: NoteDao
) : ViewModel() {

    private lateinit var note: Note

    /**
     * Get the aRGB value for the note.
     *
     * @return the aRGB value for the note.
     */
    internal fun getNoteArgb(): Int = context.getNoteArgb(note.color)

    /**
     * Asynchronously, load the note from storage
     *
     * @return a Single containing the loaded note.
     */
    internal fun loadNote(): Single<Note> {
        return noteDao.getNoteById(initialNoteId)
            .flatMap {
                note = it
                Single.just(note)
            }
            .onErrorReturn {
                it.printStackTrace()
                note = Note()
                note
            }
    }

    /**
     * Asynchronously, delete the note from storage.
     *
     * @return a completable to monitor the operation.
     */
    internal fun deleteNote(): Completable = noteDao.delete(note)

    /**
     * Asynchronously, update the note color.  This persists the note to storage.
     *
     * @return a completable to monitor the operation.
     */
    internal fun changeNoteColor(color: NoteColor): Completable {
        note.color = color
        return saveNote()
    }

    /**
     * Asynchronously, update the note text.  This persists the note to storage.
     *
     * @return a completable to monitor the operation.
     */
    internal fun updateText(text: String): Completable {
        note.text = text
        return saveNote()
    }

    /**
     * Asynchronously, update the note title.  This persists the note to storage.
     *
     * @return a completable to monitor the operation.
     */
    internal fun updateTitle(title: String): Completable {
        note.title = title
        return saveNote()
    }

    private fun saveNote(): Completable =
        note.id?.let {
            noteDao.update(note)
        } ?: noteDao.insert(note).flatMapCompletable {
            note.id = it
            Completable.complete()
        }
}