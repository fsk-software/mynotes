package com.fsk.mynotes.services;


import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.fsk.mynotes.constants.NOTE_KEY_EXTRA
import com.fsk.mynotes.data.Note


/**
 * Start the service.
 *
 * @param note The note to save.
 */
fun Context.startSaveNoteService(note: Note) {
    val intent = Intent(this, SaveNoteService::class.java).apply {
        putExtra(NOTE_KEY_EXTRA, note)
    }
    startService(intent);
}


/**
 * A service that saves a note to persistent storage.
 */
class SaveNoteService() : IntentService(SaveNoteService::class.java.simpleName) {


    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            val note: Note = intent.getParcelableExtra(NOTE_KEY_EXTRA);
            if (note.text?.isNotEmpty()) {
                note.save(contentResolver);
            }
            else {
                note.delete(contentResolver);
            }
        }
    }


}
