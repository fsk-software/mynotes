package com.fsk.mynotes.services;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.fsk.common.database.DatabaseHelper;
import com.fsk.common.utils.Preconditions;
import com.fsk.mynotes.constants.NoteExtraKeys;
import com.fsk.mynotes.data.Note;


/**
 * A service that saves a note to persistent storage.
 */
public class SaveNoteService extends IntentService {

    /**
     * Start the service.
     *
     * @param context The context to use for starting the service.
     * @param note The note to save.
     */
    public static void startService(@NonNull Context context, @NonNull Note note) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(note);

        Intent intent = new Intent(context, SaveNoteService.class);
        intent.putExtra(NoteExtraKeys.NOTE_KEY, note);
        context.startService(intent);
    }


    /**
     * Default Constructor.
     */
    public SaveNoteService() {
        super(SaveNoteService.class.getName());
    }


    @Override
    protected void onHandleIntent(final Intent intent) {
        Note note = intent.getParcelableExtra(NoteExtraKeys.NOTE_KEY);
        note.save(DatabaseHelper.getDatabase());
    }
}
