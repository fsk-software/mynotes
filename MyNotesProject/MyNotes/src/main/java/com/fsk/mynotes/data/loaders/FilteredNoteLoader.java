package com.fsk.mynotes.data.loaders;


import android.content.AsyncTaskLoader;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.fsk.common.database.DatabaseHelper;
import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.data.Note;
import com.fsk.mynotes.data.NotesManager;
import com.fsk.mynotes.data.cache.NoteFilterCache;
import com.fsk.mynotes.receivers.NoteFilterBroadcast;
import com.fsk.mynotes.receivers.NoteTableChangeBroadcast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The loader to retrieve the filtered note list and listen for any changes to it.
 */
public class FilteredNoteLoader extends AsyncTaskLoader<List<Note>> {

    /**
     * The filtered notes retrieved from persistent storage.
     */
    private List<Note> mNotes;


    /**
     * The notes manager that retrieves data from persistent storage.
     */
    private final NotesManager mNotesManager;


    /**
     * The filter manager that retrieves data from persistent storage.
     */
    private final NoteFilterCache mNoteFilterCache;


    /**
     * A local broadcast manager to listen for broadcasts upon any changes to the notes or note
     * filter.
     */
    private final LocalBroadcastManager mBroadcastManager;


    /**
     * A flag that indicates that the loader is monitoring changes to the notes or note filter.
     */
    private boolean mObserverRegistered;


    /**
     * The broadcast receiver to handle any changes in the note or note filter.
     */
    private final BroadcastReceiver mOnChangeBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            onContentChanged();
        }
    };


    /**
     * Constructor.
     *
     * @param context
     *         The context to associate with this loader.
     */
    public FilteredNoteLoader(@NonNull Context context) {
        super(context);

        mNotesManager = new NotesManager(DatabaseHelper.getDatabase());
        mBroadcastManager = LocalBroadcastManager.getInstance(context);
        mNoteFilterCache = new NoteFilterCache(context);
    }


    @Override
    public List<Note> loadInBackground() {
        Set<NoteColor> enabledFilters = mNoteFilterCache.getEnabledColors();
        return mNotesManager.getNotesWithColors(new ArrayList<>(enabledFilters));
    }


    @Override
    public void deliverResult(List<Note> data) {
        if (isReset()) {
            return;
        }

        mNotes = data;

        if (isStarted()) {
            super.deliverResult(data);
        }
    }


    @Override
    protected void onStartLoading() {
        if (mNotes != null) {
            deliverResult(mNotes);
        }

        registerReceivers();

        if (takeContentChanged() || mNotes == null) {
            forceLoad();
        }
    }


    @Override
    protected void onStopLoading() {
        cancelLoad();
    }


    @Override
    protected void onReset() {
        onStopLoading();

        if (mNotes != null) {
            mNotes = null;
        }

        unregisterReceivers();
    }


    /**
     * Register the observer to monitor changes to the note or note filter.
     */
    private void registerReceivers() {
        if (!mObserverRegistered) {
            mBroadcastManager.registerReceiver(mOnChangeBroadcastReceiver,
                                               NoteFilterBroadcast.createIntentFilter());

            mBroadcastManager.registerReceiver(mOnChangeBroadcastReceiver,
                                               NoteTableChangeBroadcast.createIntentFilter());

            mObserverRegistered = true;
        }
    }


    /**
     * Stop monitoring changes to the note and note filter.
     */
    private void unregisterReceivers() {
        mObserverRegistered = false;

        mBroadcastManager.unregisterReceiver(mOnChangeBroadcastReceiver);
    }
}