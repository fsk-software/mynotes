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

package com.fsk.mynotes.data;


import com.fsk.mynotes.data.database.NotesTableManager;

public class Note {

	private long mRow;
	private String mText;
	private long mColor;
	private NotesTableManager mNotesTableManager;
		
	public Note (long row, String text, long color, NotesTableManager manager)
	{
		mRow = row;
		mText = text;
		mColor = color;
		mNotesTableManager = manager;
	}

	public long getColor() {
		return mColor;
	}
	
	public void setColor(long color) {
		mColor = color;
		mNotesTableManager.editNote(mRow, mText, color);
	}
	
	public String getText() {
		return mText;
	}
	
	public void setText(String text) {
		mText = text;
		mNotesTableManager.editNote(mRow, text, mColor);
	}

	public long getRow() {
		return mRow;
	}
}
