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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;

	public static final String DATABASE_NAME = "data";
	private static DatabaseManager sSingleton;
	
    protected DatabaseManager(Context context) {
    	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void openDatabase(Context context) {
    	if (sSingleton == null) {
    		sSingleton = new DatabaseManager(context);
    	    sSingleton.getWritableDatabase();
    	}
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	@Override
	public void onCreate(SQLiteDatabase db) {	
    	NotesTableManager.createTable(db);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {		
		NotesTableManager.openTable(db);
	}
}