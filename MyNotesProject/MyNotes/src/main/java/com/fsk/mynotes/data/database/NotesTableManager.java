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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.fsk.mynotes.data.Note;
import com.fsk.mynotes.constants.NoteColors;

public class NotesTableManager {

	public static final String DATABASE_NOTES_TABLE = "notes";

	private static final String[] keys = 
		new String[] { DatabaseKeys.NOTE_ID.getKeyName(),
					  DatabaseKeys.NOTE_TEXT.getKeyName(),
					  DatabaseKeys.NOTE_COLOR.getKeyName()};
	
    /**
     * Database creation sql statement
     */
    private static final String NOTES_TABLE_CREATE =
        "create table " +
        DATABASE_NOTES_TABLE + 
        "(" +
        DatabaseKeys.NOTE_ID.getKeyName() + " integer primary key autoincrement not null, " +
        DatabaseKeys.NOTE_TEXT.getKeyName()  +  " text, " +
        DatabaseKeys.NOTE_COLOR.getKeyName()  + " integer" +
        ");";
  
    private static NotesTableManager sSingleton;
	private SQLiteDatabase mDatabase; 

    private NotesTableManager(SQLiteDatabase db) {
    	mDatabase = db;
    }
    
    public static void createTable(SQLiteDatabase db) {
    	if (sSingleton == null) {
    		db.execSQL(NOTES_TABLE_CREATE);
    		sSingleton = new NotesTableManager(db);
    	}
    }

    public static void openTable(SQLiteDatabase db) {
    	if (sSingleton == null) {
    		sSingleton = new NotesTableManager(db);
    	}
    }

    public final static NotesTableManager getSingleton() {
    	return sSingleton;	
    }

    private Note createData(Cursor cursor)
    {
    	long row = (long)cursor.getInt(cursor.getColumnIndex(DatabaseKeys.NOTE_ID.getKeyName()));
		String name = cursor.getString(cursor.getColumnIndex(DatabaseKeys.NOTE_TEXT.getKeyName()));
		long color = (long)cursor.getInt(cursor.getColumnIndex(DatabaseKeys.NOTE_COLOR.getKeyName()));
        return (new Note(row, name, color, this));
    }
    
    public long createNote(String name, long color) {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(DatabaseKeys.NOTE_TEXT.getKeyName(), name);
    	initialValues.put(DatabaseKeys.NOTE_COLOR.getKeyName(), color);

   		return mDatabase.insert(DATABASE_NOTES_TABLE, null, initialValues);
    }

    public boolean deleteNote (long rowId) {
        return mDatabase.delete(DATABASE_NOTES_TABLE, 
        		                DatabaseKeys.NOTE_ID.getKeyName() + "=" + rowId, null) > 0;
    }
    
    public boolean editNote (long rowId, String text, long color) {
    	ContentValues args = new ContentValues();
        args.put(DatabaseKeys.NOTE_TEXT.getKeyName(), text);
        args.put(DatabaseKeys.NOTE_COLOR.getKeyName(), color);

        return mDatabase.update(
        		DATABASE_NOTES_TABLE, args,
        		DatabaseKeys.NOTE_ID.getKeyName() + "=" + rowId, null) > 0;
    }
    
    public ArrayList<Note> fetchAllNotes() {
    	Cursor cursor = 
    		mDatabase.query(DATABASE_NOTES_TABLE, 
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

    public Note fetchNote(long rowId) throws SQLException {

        Cursor cursor =
            mDatabase.query(DATABASE_NOTES_TABLE, 
            				keys, 
            	            DatabaseKeys.NOTE_ID.getKeyName() + "=" + rowId, 
            	            null, null, null, null, null);
        cursor.moveToFirst();
        Note returnValue = createData(cursor);
        cursor.close();
        return returnValue;
    }

    public ArrayList<Note> fetchNotes(Map<NoteColors,Boolean> colors) {
    	Iterator<NoteColors> iter = NoteColors.getIterator();
    	String query = "";
    	while (iter.hasNext()) {
    		NoteColors color = iter.next();
    		if (colors.get(color)) {
    			if (query.length() > 0) {
    				query = query + " OR "; 
    			}
    			query = query + 
    			        DatabaseKeys.NOTE_COLOR.getKeyName() + "=" + color.ordinal();
    		}
    	}
    	
    	Cursor cursor = 
    		mDatabase.query(DATABASE_NOTES_TABLE, 
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
