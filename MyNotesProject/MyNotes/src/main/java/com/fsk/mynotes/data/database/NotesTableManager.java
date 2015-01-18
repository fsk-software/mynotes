/*
 * Copyright (C) 2010,2012 FSK Consulting, Inc.
 * 
 * Licensed under the LGPL License, Version 3 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.gnu.org/licenses/lgpl.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.fsk.mynotes.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

import com.fsk.common.database.DatabaseHelper;
import com.fsk.mynotes.constants.NoteColors;
import com.fsk.mynotes.data.Note;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

@Deprecated
public class NotesTableManager {
    
    private static final String[] keys =
            new String[] { MyNotesDatabaseModel.Columns.NOTE_ID,
                    MyNotesDatabaseModel.Columns.NOTE_TEXT,
                    MyNotesDatabaseModel.Columns.NOTE_COLOR};

    private final static NotesTableManager sSingleton = new NotesTableManager();

    public static NotesTableManager getSingleton() {
        return sSingleton;
    }

    private static Note createData(Cursor cursor)
    {
        long row = (long)cursor.getInt(cursor.getColumnIndex(MyNotesDatabaseModel.Columns.NOTE_ID));
        String name = cursor.getString(cursor.getColumnIndex(MyNotesDatabaseModel.Columns.NOTE_TEXT));
        long color = (long)cursor.getInt(cursor.getColumnIndex(MyNotesDatabaseModel.Columns.NOTE_COLOR));
        return (new Note(row, name, color, sSingleton));
    }

    public static long createNote(String name, long color) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(MyNotesDatabaseModel.Columns.NOTE_TEXT, name);
        initialValues.put(MyNotesDatabaseModel.Columns.NOTE_COLOR, color);

        return DatabaseHelper.getDatabase().insert(MyNotesDatabaseModel.Tables.NOTES, null, initialValues);
    }

    public static boolean deleteNote (long rowId) {
        return DatabaseHelper.getDatabase().delete(MyNotesDatabaseModel.Tables.NOTES,
                                MyNotesDatabaseModel.Columns.NOTE_ID + "=" + rowId, null) > 0;
    }

    public static boolean editNote (long rowId, String text, long color) {
        ContentValues args = new ContentValues();
        args.put(MyNotesDatabaseModel.Columns.NOTE_TEXT, text);
        args.put(MyNotesDatabaseModel.Columns.NOTE_COLOR, color);

        return DatabaseHelper.getDatabase().update(
                MyNotesDatabaseModel.Tables.NOTES, args,
                MyNotesDatabaseModel.Columns.NOTE_ID + "=" + rowId, null) > 0;
    }

    public static ArrayList<Note> fetchAllNotes() {
        Cursor cursor =
                DatabaseHelper.getDatabase().query(MyNotesDatabaseModel.Tables.NOTES,
                                keys,
                                null, null, null, null, null);

        ArrayList<Note> returnValue = new ArrayList<Note>();
        cursor.moveToFirst();
        for (int i=0; i<cursor.getCount(); ++i) {
            returnValue.add(createData(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return returnValue;
    }

    public static Note fetchNote(long rowId) throws SQLException {

        Cursor cursor =
                DatabaseHelper.getDatabase().query(MyNotesDatabaseModel.Tables.NOTES,
                                keys,
                                MyNotesDatabaseModel.Columns.NOTE_ID + "=" + rowId,
                                null, null, null, null, null);
        cursor.moveToFirst();
        Note returnValue = createData(cursor);
        cursor.close();
        return returnValue;
    }

    public static ArrayList<Note> fetchNotes(Map<NoteColors,Boolean> colors) {
        Iterator<NoteColors> iter = NoteColors.getIterator();
        String query = "";
        while (iter.hasNext()) {
            NoteColors color = iter.next();
            if (colors.get(color)) {
                if (query.length() > 0) {
                    query = query + " OR ";
                }
                query = query +
                        MyNotesDatabaseModel.Columns.NOTE_COLOR + "=" + color.ordinal();
            }
        }

        Cursor cursor =
                DatabaseHelper.getDatabase().query(MyNotesDatabaseModel.Tables.NOTES,
                                keys,
                                query,
                                null, null, null, null);

        ArrayList<Note> returnValue = new ArrayList<Note>();
        cursor.moveToFirst();
        for (int i=0; i<cursor.getCount(); ++i) {
            returnValue.add(createData(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return returnValue;
    }

}