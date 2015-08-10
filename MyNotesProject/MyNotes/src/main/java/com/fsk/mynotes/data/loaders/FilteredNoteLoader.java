package com.fsk.mynotes.data.loaders;


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.fsk.common.database.DatabaseHelper;
import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.data.Note;
import com.fsk.mynotes.data.NotesManager;
import com.fsk.mynotes.data.cache.NoteFilterPreferences;
import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

/**
 * The loader to retrieve the filtered note list and listen for any changes to it.
 */
public class FilteredNoteLoader extends AsyncTaskLoader<List<Note>> implements Observer {

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
    private final NoteFilterPreferences mNoteFilterPreferences;


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
     * The listener to changes in the filtered note colors.
     */
    private SharedPreferences.OnSharedPreferenceChangeListener mNoteFilterPreferenceListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences,
                                                      final String key) {
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
        mNoteFilterPreferences = new NoteFilterPreferences(context);
    }


    @Override
    public List<Note> loadInBackground() {
        Set<NoteColor> enabledFilters = mNoteFilterPreferences.getEnabledColors();
        List<Note> returnValue;
        try {
            returnValue = mNotesManager.getNotesWithColors(new ArrayList<>(enabledFilters));
        }
        catch (Exception e) {
            returnValue = null;
        }
        return returnValue;
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


    @Override
    public void update(final Observable observable, final Object data) {
        onContentChanged();
    }


    /**
     * Register the observer to monitor changes to the note or note filter.
     */
    private void registerReceivers() {
        if (!mObserverRegistered) {
            mNoteFilterPreferences.registerListener(mNoteFilterPreferenceListener);

            for (Note note : MoreObjects.firstNonNull(mNotes, new ArrayList<Note>())) {
                note.addObserver(this);
            }

            mObserverRegistered = true;
        }
    }


    /**
     * Stop monitoring changes to the note and note filter.
     */
    private void unregisterReceivers() {
        mObserverRegistered = false;

        for (Note note : MoreObjects.firstNonNull(mNotes, new ArrayList<Note>())) {
            note.deleteObserver(this);
        }

        mNoteFilterPreferences.unregisterListener(mNoteFilterPreferenceListener);
    }


}