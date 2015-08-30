package com.fsk.mynotes.services;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.fsk.common.database.DatabaseHelper;
import com.fsk.mynotes.data.Note;

/**
 * A service that deletes a note from the persistent storage
 */
public class DeleteNoteService extends BaseNoteService {

    public static Intent createIntent(@NonNull Context context, @NonNull Note note) {
        Intent intent = new Intent(context, DeleteNoteService.class);
        updateIntent(note, intent);

        return intent;
    }


    @Override
    protected void onHandleIntent(final Intent intent) {
        super.onHandleIntent(intent);

        Note note = getNote();
        note.save(DatabaseHelper.getDatabase());
        notifyOnComplete(note.getId() != Note.NOT_STORED);
    }

}
