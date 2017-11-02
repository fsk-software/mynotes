package com.fsk.mynotes.data.providers;

import android.content.ContentProvider;
import android.content.ContentResolver
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.fsk.mynotes.data.database.*
import com.fsk.mynotes.data.database.NotesSQLiteOpenHelper.Companion.NOTES_TABLE
import com.fsk.mynotes.data.database.NotesSQLiteOpenHelper.Companion.NOTE_COLOR_COL
import com.fsk.mynotes.data.database.NotesSQLiteOpenHelper.Companion.NOTE_ID_COL
import com.fsk.mynotes.data.database.NotesSQLiteOpenHelper.Companion.NOTE_TEXT_COL

import java.util.Arrays;

/**
 * Created by me on 5/22/2016.
 */

class NoteContentProvider() : ContentProvider() {

    companion object {
        const val NOTE_CONTENT_BASE_PATH = "notes";

        const val NOTE_CONTENT_AUTHORITY = "com.fsk.mynotes.android.notes.contentprovider";

        val NOTE_CONTENT_URI = Uri.parse("content://" + NOTE_CONTENT_AUTHORITY + "/" + NOTE_CONTENT_BASE_PATH);

    }

    // used for the UriMacher
    private val notesUriCode = 1;
    private val singleNoteUriCode = 10;

    val contentType = ContentResolver.CURSOR_DIR_BASE_TYPE + "/notes";
    val contentItemType = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/notes";

    private val uriMatcher : UriMatcher = UriMatcher(UriMatcher.NO_MATCH);

    init{
        uriMatcher.addURI(NOTE_CONTENT_AUTHORITY, NOTE_CONTENT_BASE_PATH, notesUriCode);
        uriMatcher.addURI(NOTE_CONTENT_AUTHORITY, NOTE_CONTENT_BASE_PATH + "/#", singleNoteUriCode);
    }

    override fun onCreate() : Boolean{
            return false;
        }

    override fun  query(uri : Uri, projection: Array<String>?, selection : String?,
                        selectionArgs : Array<String>?, sortOrder : String?) : Cursor{
        val queryBuilder = SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exists
        validateColumns(projection);

        queryBuilder.tables = NOTES_TABLE;

        val uriType = uriMatcher.match(uri);
        if (uriType == singleNoteUriCode) {
            // adding the ID to the original query
            queryBuilder.appendWhere(NOTE_ID_COL + "=" + uri.lastPathSegment);
        }
        else if (uriType != notesUriCode) {
            throw IllegalArgumentException("Unknown URI: " + uri);
        }


        val cursor = queryBuilder.query(context.database.readableDatabase,
                                        projection,
                                        selection,
                                        selectionArgs,
                                        null,
                                        null, sortOrder);
        cursor.setNotificationUri(context.contentResolver, uri);

        return cursor;
    }

    override fun getType(uri : Uri) : String? {
        return null;
    }

    override fun insert(uri : Uri, values : ContentValues) : Uri {
        val uriType = uriMatcher.match(uri);
        require(uriType == notesUriCode);
        val id = context.database.writableDatabase.insert(NOTES_TABLE, null, values);

        context.contentResolver.notifyChange(uri, null);
        return Uri.parse(NOTE_CONTENT_BASE_PATH + "/" + id);
    }


    override fun delete(uri : Uri, selection : String?, selectionArgs : Array<String>) : Int {
        val database = context.database.writableDatabase;
        val uriType = uriMatcher.match(uri);
        var rowsDeleted : Int;
        when (uriType) {
             notesUriCode ->
                rowsDeleted = database?.delete(NOTES_TABLE, selection, selectionArgs) ?: 0;

            singleNoteUriCode -> {
                val id = uri.lastPathSegment;
                if (selection.isNullOrEmpty()) {
                    rowsDeleted = database?.delete(NOTES_TABLE,
                                                  NOTE_ID_COL + "=" + id,
                                                  null) ?: 0;
                }
                else {
                    rowsDeleted = database?.delete(NOTES_TABLE,
                                                  NOTE_ID_COL + "=" + id + " and " + selection,
                                                  selectionArgs) ?: 0;
                }
            }
            else ->
                throw IllegalArgumentException("Unknown URI: " + uri);
        }
        context.contentResolver.notifyChange(uri, null);
        return rowsDeleted;
    }

    override fun update(uri : Uri, values : ContentValues,
                        selection : String?,
                        selectionArgs : Array<String>?) : Int {

        val database = context.database.writableDatabase;
        val uriType = uriMatcher.match(uri);
        var rowsUpdated = 0;
        when (uriType) {
             notesUriCode ->
                rowsUpdated = database?.update(NOTES_TABLE,
                                              values,
                                              selection,
                                              selectionArgs) ?: 0;

            singleNoteUriCode-> {
                val id = uri.lastPathSegment;
                if (selection.isNullOrEmpty()) {
                    rowsUpdated = database?.update(NOTES_TABLE,
                                                  values,
                                               NOTE_ID_COL + "=" + id,
                                                  null) ?: 0;
                }
                else {
                    rowsUpdated = database?.update(NOTES_TABLE,
                                                   values,
                                               NOTE_ID_COL + "=" + id + " and " + selection,
                                                   selectionArgs) ?: 0;
                }
            }
            else ->
                throw IllegalArgumentException("Unknown URI: " + uri);
        }
        context.contentResolver.notifyChange(uri, null);
        return rowsUpdated;
    }


    private fun validateColumns(projection : Array<String>?) {
        val available = arrayOf (NOTE_ID_COL, NOTE_COLOR_COL, NOTE_TEXT_COL);

        if (projection != null) {
            val requestedColumns = Arrays.asList(projection);
           val availableColumns = Arrays.asList(available);

            if (!availableColumns.containsAll(requestedColumns)) {
                throw IllegalArgumentException("Unknown columns in projection");
            }
        }
    }

}
