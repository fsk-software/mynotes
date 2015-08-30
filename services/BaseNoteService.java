package com.fsk.mynotes.services;


import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;

import com.fsk.mynotes.constants.NoteExtraKeys;
import com.fsk.mynotes.data.Note;

/**
 * A service that deletes a note from the persistent storage
 */
public class BaseNoteService extends IntentService {

    /**
     * The callback listener to notify when the behavior is complete.
     */
    public interface OnCompleteListener {
        void onComplete(Note note, boolean success);
    }


    /**
     * Update the intent for the specified note.
     *
     * @param note
     *         The note to manipulate.
     * @param intent
     *         The intent for the service.
     */
    protected static Intent updateIntent(@NonNull Note note,
                                         @NonNull Intent intent) {
        intent.putExtra(NoteExtraKeys.NOTE_KEY, note);
        return intent;
    }


    /**
     * Class used for the client Binder.  Because we know this service always runs in the same
     * process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public BaseNoteService getService() {
            return BaseNoteService.this;
        }
    }


    /**
     * Binder given to clients
     */
    private final IBinder mBinder = new LocalBinder();


    /**
     * The note to manipulate.
     */
    private Note mNote;


    /**
     * The listener to notify when the service behavior is complete.
     */
    private OnCompleteListener mOnCompleteListener;


    /**
     * Default Constructor.
     */
    public BaseNoteService() {
        super(BaseNoteService.class.getName());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    @Override
    protected void onHandleIntent(final Intent intent) {
       mNote = intent.getParcelableExtra(NoteExtraKeys.NOTE_KEY);
    }

    /**
     * Set the {@link com.fsk.mynotes.services.BaseNoteService.OnCompleteListener} to notify when
     * the service is done.
     * @param listener the listener to set.  This will replace the current listener.
     */
    public void setOnCompleteListener(OnCompleteListener listener) {
        mOnCompleteListener = listener;
    }


    /**
     * Get the Note.
     *
     * @return the Note
     */
    protected Note getNote() {
        return mNote;
    }

    protected void notifyOnComplete(boolean successful) {
        if (mOnCompleteListener != null) {
            mOnCompleteListener.onComplete(getNote(), successful);
        }
    }
}
