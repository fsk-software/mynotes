package com.fsk.mynotes.data;


import android.annotation.SuppressLint
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import com.fsk.common.database.getInt
import com.fsk.common.database.getLong
import com.fsk.common.database.getString

import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.constants.getNoteColorAt
import com.fsk.mynotes.data.database.NotesSQLiteOpenHelper.Companion.NOTE_COLOR_COL
import com.fsk.mynotes.data.database.NotesSQLiteOpenHelper.Companion.NOTE_ID_COL
import com.fsk.mynotes.data.database.NotesSQLiteOpenHelper.Companion.NOTE_TEXT_COL
import com.fsk.mynotes.data.providers.NoteContentProvider;
import com.fsk.mynotes.data.providers.NoteContentProvider.Companion.NOTE_CONTENT_URI
import kotlinx.android.parcel.Parcelize
import org.jetbrains.anko.db.rowParser


/**
 * Convert the cursor into a non-null list of {@link com.fsk.mynotes.data.Note}s.
 *
 * @return the non-null list of {@link com.fsk.mynotes.data.Note}s from the cursor.
 */
fun Cursor.getNotes()  : MutableList<Note>{
    val returnValue = mutableListOf<Note>();
    moveToFirst();
    while (!isAfterLast) {

        val id = getLong(NOTE_ID_COL);
        val text = getString(NOTE_TEXT_COL)?: "";
        val color = getNoteColorAt(getInt(NOTE_COLOR_COL));
        returnValue.add(Note(id, text, color));
        moveToNext();
    }

    return returnValue;
}

/**
 * The Note data model.
 */
@SuppressLint("ParcelCreator")
@Parcelize class Note( var id : Long, var text : String, var color : NoteColor = NoteColor.YELLOW) : Parcelable {


    /**
     * Convert the object into a {@link android.content.ContentValues} object for storage in the
     * database.
     *
     * @return The {@link android.content.ContentValues} initialized with the values.  It will
     * contain the following entries:<p> key=>{@link com.fsk.mynotes.data.database
     * .NotesDatabaseSchema.Columns#NOTE_ID}<br> value=> long<br> description=> The note id.  this
     * only exists when {@link #getId()} is not {@link #NOT_STORED}.
     * <p/>
     * key=>{@link com.fsk.mynotes.data.database.NotesDatabaseSchema.Columns#NOTE_TEXT}<br>
     * value=>String<br> description=> The note's text.
     * <p/>
     * key=>{@link com.fsk.mynotes.data.database.NotesDatabaseSchema.Columns#NOTE_COLOR}<br>
     * value=>Integer<br> description=> The note colors ordinal.
     */
    private fun  createContentValues() : ContentValues {
        val returnValue = ContentValues();
        if (id != -1L) {
            returnValue.put(NOTE_ID_COL, id);
        }

        returnValue.put(NOTE_TEXT_COL, text);
        returnValue.put(NOTE_COLOR_COL, color.ordinal);

        return returnValue;
    }


    fun save(contentResolver : ContentResolver) {
        if (text?.isNotEmpty()) {
            val contentValues = createContentValues();

            if (id == -1L) {
                contentResolver.insert(NOTE_CONTENT_URI, contentValues);
            }
            else {
                contentResolver.update(NOTE_CONTENT_URI, contentValues, null, null);
            }
        }
    }


    fun delete(contentResolver : ContentResolver?) {
        if ((contentResolver != null) && (id != -1L)) {
            val uri = Uri.parse(NOTE_CONTENT_URI.toString() + "/" + id);
            contentResolver.delete(uri, null, null);
        }
    }
}
