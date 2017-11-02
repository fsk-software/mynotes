package com.fsk.mynotes.services;


import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.fsk.mynotes.constants.NOTE_KEY_EXTRA
import com.fsk.mynotes.data.Note

/**
 * Start the service for the specified note.
 *
 * @param note
 *         The note to delete.
 */
fun Context.startDeleteNoteService(note: Note) {

    val intent = Intent(this, DeleteNoteService::class.java).apply {
        putExtra(NOTE_KEY_EXTRA, note)
    }
    startService(intent);
}

/**
 * A service that deletes a note from the persistent storage
 */
class DeleteNoteService() : IntentService(DeleteNoteService::class.java.simpleName) {


    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val note: Note = intent.getParcelableExtra(NOTE_KEY_EXTRA);
            note.delete(contentResolver);
        }
    }
}
