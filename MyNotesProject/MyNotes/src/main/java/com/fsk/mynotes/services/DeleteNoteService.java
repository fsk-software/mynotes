package com.fsk.mynotes.services;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.fsk.common.database.DatabaseHelper;
import com.fsk.mynotes.constants.NoteExtraKeys;
import com.fsk.mynotes.data.Note;

/**
 * A service that deletes a note from the persistent storage
 */
public class DeleteNoteService extends IntentService {

    /**
     * Start the service for the specified note.
     *
     * @param context
     *         The context to use for starting the service.
     * @param note
     *         The note to delete.
     */
    public static void startService(@NonNull Context context, @NonNull Note note) {
        Intent intent = new Intent(context, DeleteNoteService.class);
        intent.putExtra(NoteExtraKeys.NOTE_KEY, note);
        context.startService(intent);
    }


    /**
     * Default Constructor.
     */
    public DeleteNoteService() {
        super(DeleteNoteService.class.getName());
    }


    @Override
    protected void onHandleIntent(final Intent intent) {
        Note note = intent.getParcelableExtra(NoteExtraKeys.NOTE_KEY);
        note.delete(DatabaseHelper.getDatabase());
    }
}
