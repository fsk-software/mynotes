package com.fsk.mynotes.data;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.fsk.common.database.DatabaseUtilities;
import com.fsk.common.threads.ThreadUtils;
import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.data.database.MyNotesDatabaseModel.Columns;
import com.fsk.mynotes.data.database.MyNotesDatabaseModel.Tables;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;


/**
 * Handles the retrieval and bulk storage of Notes data to the database.
 */

public class NotesManager {

    /**
     * The database to access for the persistent note data.
     */
    final private SQLiteDatabase mDatabase;


    /**
     * Constructor.
     *
     * @param database
     *         The database to access.  This database must correspond to the {@link
     *         com.fsk.mynotes.data.database.MyNotesDatabaseModel} schema or all subsequent calls
     *         will not return data.
     */
    public NotesManager(@NonNull SQLiteDatabase database) {
        Preconditions.checkNotNull(database);
        mDatabase = database;
    }


    /**
     * Get all of the notes from the database.
     *
     * @return a non-null List of {@link com.fsk.mynotes.data.Note}(s).
     *
     * @throws com.fsk.common.threads.ThreadException
     *         when call from the UI thread.
     */
    public List<Note> getAllNotes() throws Exception{
        new ThreadUtils().checkOffUIThread();
        Cursor cursor = mDatabase.query(Tables.NOTES, null, null, null, null, null, null);

        final List<Note> returnValue;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            returnValue = getNotesFromCursor(cursor);
        }
        else {
            returnValue = new ArrayList<>();
        }

        return returnValue;
    }


    /**
     * Get all of the notes from the database that contain the specified note colors.
     *
     * @return a non-null List of {@link com.fsk.mynotes.data.Note}(s) that contain the specified
     * colors.
     *
     * @throws com.fsk.common.threads.ThreadException
     *         when call from the UI thread.
     */
    public List<Note> getNotesWithColors(@NonNull List<NoteColor> colors) throws Exception {
        new ThreadUtils().checkOffUIThread();
        Preconditions.checkNotNull(colors);

        List<Note> returnValue = new ArrayList<>();
        if (!colors.isEmpty()) {
            String queryQuestionMarks =
                    DatabaseUtilities.buildQueryQuestionMarkString(colors.size());

            String[] args = new String[colors.size()];
            for (int i = 0; i < colors.size(); ++i) {
                args[i] = Integer.toString(colors.get(i).ordinal());
            }

            Cursor cursor = mDatabase.query(Tables.NOTES, null,
                                            Columns.NOTE_COLOR + " in (" + queryQuestionMarks + ")",
                                            args, null, null, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                returnValue = getNotesFromCursor(cursor);

            }
        }

        return returnValue;
    }


    /**
     * Get the {@link com.fsk.mynotes.data.Note} with the specified id.
     *
     * @return the {@link com.fsk.mynotes.data.Note} with the specified id or null.
     *
     * @throws com.fsk.common.threads.ThreadException
     *         when call from the UI thread.
     */
    public Note getNote(long noteId) throws Exception{
        new ThreadUtils().checkOffUIThread();

        Cursor cursor = mDatabase.query(Tables.NOTES, null, Columns.NOTE_ID + " = ?",
                                        new String[] { Long.toString(noteId) }, null, null, null);

        Note returnValue = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            returnValue = createNoteFromCursor(cursor);
        }

        return returnValue;
    }


    /**
     * Convert the cursor into a non-null list of {@link com.fsk.mynotes.data.Note}s.
     *
     * @param cursor
     *         the cursor containing the data to convert to {@link com.fsk.mynotes.data.Note}s
     *
     * @return the non-null list of {@link com.fsk.mynotes.data.Note}s from the cursor.
     */
    private static List<Note> getNotesFromCursor(Cursor cursor) throws Exception {
        List<Note> returnValue = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            returnValue.add(createNoteFromCursor(cursor));
            cursor.moveToNext();
        }
        return returnValue;
    }


    /**
     * Convert the cursor row into a {@link com.fsk.mynotes.data.Note}.
     *
     * @param cursor
     *         the cursor containing the data to convert to a {@link com.fsk.mynotes.data.Note}
     *
     * @return the {@link com.fsk.mynotes.data.Note} generated from the next line in the cursor.
     */
    private static Note createNoteFromCursor(Cursor cursor) throws Exception {
        Note.Builder builder = new Note.Builder();
        builder.setId(cursor.getLong(cursor.getColumnIndex(Columns.NOTE_ID)));
        builder.setText(cursor.getString(cursor.getColumnIndex(Columns.NOTE_TEXT)));

        int ordinal = cursor.getInt(cursor.getColumnIndex(Columns.NOTE_COLOR));
        builder.setColor(NoteColor.getColor(ordinal));

        return builder.build();
    }
}
