package com.fsk.mynotes.data;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.fsk.common.database.DatabaseStorable;
import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.data.database.MyNotesDatabaseModel.Columns;
import com.fsk.mynotes.data.database.MyNotesDatabaseModel.Tables;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;


/**
 * The Note data model.
 */
public class Note implements Parcelable, DatabaseStorable {

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
            return new Note[0];
        }
    };


    /**
     * The id for notes that are not stored in the database.
     */
    public static final int NOT_STORED = -1;


    /**
     * The unique note id.
     */
    private long mId = NOT_STORED;


    /**
     * The text for the note.
     */
    private String mText = "";


    /**
     * The color of the note.
     */
    private NoteColor mColor = NoteColor.YELLOW;


    /**
     * Default Constructor.
     */
    public Note() {
    }


    /**
     * Constructor.
     *
     * @param source
     *         the parcel containing the data for the note.
     */
    public Note(Parcel source) {
        mId = source.readLong();
        mText = source.readString();
        mColor = NoteColor.getColor(source.readInt());
    }


    /**
     * Get the note color.
     *
     * @return The non-null note color.  By default, this returns {@link NoteColor#YELLOW}.
     */
    public NoteColor getColor() {
        return mColor;
    }


    /**
     * Set the color for the note.
     *
     * @param color
     *         the color for the note.
     */
    public void setColor(@NonNull NoteColor color) {
        Preconditions.checkNotNull(color);
        mColor = color;
    }


    /**
     * Get the text for the note.
     *
     * @return the non-null text for the note.
     */
    public String getText() {
        return mText;
    }


    /**
     * Set the note text.
     *
     * @param text
     *         the text for the note.  If null, then an empty string is set.
     */
    public void setText(String text) {
        mText = Strings.nullToEmpty(text);
    }


    /**
     * Get the id for the note.  If the note is note stored in the database, then {@link
     * #NOT_STORED} is returned.
     *
     * @return the id for the note.
     */
    public long getId() {
        return mId;
    }


    /**
     * Set the id for the note.
     *
     * @param id
     *         the id for the note. This must be either {@link #NOT_STORED} or a natural value.
     */
    public void setId(long id) {
        Preconditions.checkArgument((id == NOT_STORED) || (id >= 0));

        mId = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeLong(mId);
        dest.writeString(mText);
        dest.writeInt(mColor.ordinal());
    }


    /**
     * Convert the object into a {@link android.content.ContentValues} object for storage in the
     * database.
     *
     * @return The {@link android.content.ContentValues} intitialized with the values.  It will
     * contain the following entries:<p>
     *  key=>{@link com.fsk.mynotes.data.database.MyNotesDatabaseModel.Columns#NOTE_ID}<br>
     *  value=> long<br>
     *  description=> The note id.  this only exists when {@link #getId()} is not {@link #NOT_STORED}.
     *  <p>
     *  key=>{@link com.fsk.mynotes.data.database.MyNotesDatabaseModel.Columns#NOTE_TEXT}<br>
     *  value=>String<br>
     *  description=> The note's text.
     *  <p>
     *  key=>{@link com.fsk.mynotes.data.database.MyNotesDatabaseModel.Columns#NOTE_COLOR}<br>
     *  value=>Integer<br>
     *  description=> The note colors ordinal.
     */
    private ContentValues createContentValues() {
        ContentValues returnValue = new ContentValues();
        if (getId() != NOT_STORED) {
            returnValue.put(Columns.NOTE_ID, getId());
        }

        returnValue.put(Columns.NOTE_TEXT, getText());
        returnValue.put(Columns.NOTE_COLOR, getColor().ordinal());

        return returnValue;
    }


    @Override
    public void save(final SQLiteDatabase db) {
        long row =  db.insertWithOnConflict(Tables.NOTES,
                                            null,
                                            createContentValues(),
                                            SQLiteDatabase.CONFLICT_REPLACE);

        if (row != NOT_STORED) {
            mId = row;
        }
    }


    @Override
    public void delete(final SQLiteDatabase db) {
        if (getId() != NOT_STORED) {
            int deletedRows = db.delete(Tables.NOTES, Columns.NOTE_ID + " = ?",
                                        new String[] { Long.toString(mId) });
            if (deletedRows > 0) {
                setId(NOT_STORED);
            }
        }
    }
}
