package com.fsk.mynotes.data;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.fsk.common.database.DatabaseStorable;
import com.fsk.common.utils.threads.ThreadUtils;
import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.data.database.MyNotesDatabaseModel.Columns;
import com.fsk.mynotes.data.database.MyNotesDatabaseModel.Tables;


import java.util.Observable;


/**
 * The Note data model.
 */
public class Note extends Observable implements Parcelable, DatabaseStorable {

    /**
     * Standard Creator Pattern
     */
    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
        @Override
        public Note createFromParcel(final Parcel source) {
            return new Note(source);
        }


        @Override
        public Note[] newArray(final int size) {
            return new Note[size];
        }
    };


    /**
     * The id for notes that are not stored in the database.
     */
    public static final int NOT_STORED = NoteAttributes.UNKNOWN;


    /**
     * The current data for the note.
     */
    private NoteAttributes mCurrentData;


    /**
     * The last persisted data for the note
     */
    private NoteAttributes mOriginalData;


    /**
     * Constructor
     *
     * @param startingData
     *         the initial starting data for the note.
     *
     * @throws CloneNotSupportedException
     *         when the starting data cannot be cloned.
     */
    Note(@NonNull NoteAttributes startingData) throws CloneNotSupportedException {
        mOriginalData = startingData;
        mCurrentData = startingData.clone();
    }


    /**
     * Constructor.
     *
     * @param source
     *         the parcel containing the data for the note.
     */
    public Note(Parcel source) {
        mOriginalData = source.readParcelable(NoteAttributes.class.getClassLoader());
        mCurrentData = source.readParcelable(NoteAttributes.class.getClassLoader());
    }


    /**
     * Get the note color.
     *
     * @return The non-null note color.  By default, this returns {@link NoteColor#YELLOW}.
     */
    public NoteColor getColor() {
        return mCurrentData.getColor();
    }


    /**
     * Set the color for the note.
     *
     * @param color
     *         the color for the note.
     */
    public void setColor(@NonNull NoteColor color) {
        boolean modified = mCurrentData.setColor(color);
        notifyObserversOnChange(modified);
    }


    /**
     * Get the text for the note.
     *
     * @return the non-null text for the note.
     */
    public String getText() {
        return mCurrentData.getText();
    }


    /**
     * Set the note text.
     *
     * @param text
     *         the text for the note.  If null, then an empty string is set.
     */
    public void setText(String text) {
        boolean modified = mCurrentData.setText(text);
        notifyObserversOnChange(modified);
    }


    /**
     * Get the id for the note.
     *
     * @return the id for the note.
     */
    public long getId() {
        return mCurrentData.getId();
    }


    /**
     * Determine if the Note has been modified without the changes being persisted.
     *
     * @return true if the Note has been modified without the changes being persisted.
     */
    public boolean isDirty() {
        return !mCurrentData.equals(mOriginalData);
    }


    /**
     * Notify any {@link java.util.Observer}(s) of change to the data.
     *
     * @param changed
     *         true if the data was changed.
     */
    private void notifyObserversOnChange(boolean changed) {
        if (changed) {
            setChanged();
            notifyObservers();
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeParcelable(mOriginalData, flags);
        dest.writeParcelable(mCurrentData, flags);
    }


    /**
     * Convert the object into a {@link android.content.ContentValues} object for storage in the
     * database.
     *
     * @return The {@link android.content.ContentValues} initialized with the values.  It will
     * contain the following entries:<p> key=>{@link com.fsk.mynotes.data.database
     * .MyNotesDatabaseModel.Columns#NOTE_ID}<br> value=> long<br> description=> The note id.  this
     * only exists when {@link #getId()} is not {@link #NOT_STORED}.
     * <p/>
     * key=>{@link com.fsk.mynotes.data.database.MyNotesDatabaseModel.Columns#NOTE_TEXT}<br>
     * value=>String<br> description=> The note's text.
     * <p/>
     * key=>{@link com.fsk.mynotes.data.database.MyNotesDatabaseModel.Columns#NOTE_COLOR}<br>
     * value=>Integer<br> description=> The note colors ordinal.
     */
    ContentValues createContentValues() {
        ContentValues returnValue = new ContentValues();
        if (getId() != NOT_STORED) {
            returnValue.put(Columns.NOTE_ID, getId());
        }

        returnValue.put(Columns.NOTE_TEXT, getText());
        returnValue.put(Columns.NOTE_COLOR, getColor().ordinal());

        return returnValue;
    }


    /**
     * Save the note to the database.
     *
     * @throws com.fsk.common.utils.threads.ThreadException
     *         when call from the UI thread.
     */
    @Override
    public void save(final SQLiteDatabase db) {
        new ThreadUtils().checkOffUIThread();
        long row = db.insertWithOnConflict(Tables.NOTES, null, createContentValues(),
                                           SQLiteDatabase.CONFLICT_REPLACE);

        if (row != NOT_STORED) {
            onPersistenceUpdate(row);
        }
    }


    /**
     * Delete the note from the database.
     *
     * @throws com.fsk.common.utils.threads.ThreadException
     *         when call from the UI thread.
     */
    @Override
    public void delete(final SQLiteDatabase db) {

        new ThreadUtils().checkOffUIThread();

        if (getId() != NOT_STORED) {
            int deletedRows = db.delete(Tables.NOTES, Columns.NOTE_ID + " = ?",
                                        new String[] { Long.toString(getId()) });
            if (deletedRows > 0) {
                onPersistenceUpdate(NOT_STORED);
            }
        }
    }


    /**
     * Reset the {@link #mOriginalData} to the current and update the id to the specified id.
     *
     * @param newId
     *         the new Id for the note.
     */
    private void onPersistenceUpdate(long newId) {
        try {
            mCurrentData.setId(newId);
            boolean dirty = isDirty();
            mOriginalData = mCurrentData.clone();
            notifyObserversOnChange(dirty);
        }
        catch (CloneNotSupportedException e) {
            Log.i("MyNotes", "Something really bad happened");
        }
    }


    /**
     * The Builder that will create a new {@link Note}.
     */
    public static class Builder {
        /**
         * The starting data for the new {@link Note}.
         */
        private final NoteAttributes mNoteAttributes = new NoteAttributes();


        /**
         * Set the color for the note.
         *
         * @param color
         *         the color for the note.
         *
         * @return the builder to allow method chaining.
         */
        public Builder setColor(@NonNull NoteColor color) {
            mNoteAttributes.setColor(color);
            return this;
        }


        /**
         * Set the note text.
         *
         * @param text
         *         the text for the note.  If null, then an empty string is set.
         *
         * @return the builder to allow method chaining.
         */
        public Builder setText(String text) {
            mNoteAttributes.setText(text);
            return this;
        }


        /**
         * Set the note id.
         *
         * @param id
         *         the id for the note.  This must be {@link #NOT_STORED} or a natural number.
         *
         * @return the builder to allow method chaining.
         */
        public Builder setId(long id) {
            mNoteAttributes.setId(id);
            return this;
        }


        /**
         * Create a new {@link Note} with the specified data.
         *
         * @return a new {@link Note} with the specified data.
         *
         * @throws CloneNotSupportedException
         *         when the note data cannot be created.
         */
        public Note build() throws CloneNotSupportedException {
            return new Note(mNoteAttributes);
        }
    }
}
