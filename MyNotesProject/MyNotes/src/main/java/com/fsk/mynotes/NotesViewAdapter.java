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
package com.fsk.mynotes;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class NotesViewAdapter extends BaseAdapter {
	
	private ArrayList<Note> mNotes;
	private NoteEventListener mListener;
	
    public NotesViewAdapter(Activity activity, ArrayList<Note> notes, NoteEventListener listener) {		
    	mNotes = notes;
    	mListener = listener;
    }
        
    public View getView(int position, View convertView, ViewGroup parent) {
        Note note = mNotes.get(position);
    	ViewHolder viewHolder = null;
        
    	if (convertView == null) {
        	viewHolder = new ViewHolder();
        	viewHolder.mNoteView =
        		new NoteView(parent.getContext(), note, mListener);
        	convertView = viewHolder.mNoteView;
        	convertView.setTag(viewHolder);
        }
    	else {
    		viewHolder = (ViewHolder) convertView.getTag();
        	viewHolder.mNoteView.setNote(note);
    	}
    	return convertView;
    }
    
    @Override
    public Note getItem(int position) {
    	return mNotes.get(position);
    }
    
    @Override
    public long getItemId(int position) {
    	return mNotes.get(position).getRow();
    }
    
    protected final ArrayList<Note> getArrayList() {
    	return mNotes;
    }

	@Override
	public int getCount() {
		return mNotes.size();
	}
	
    static class ViewHolder {
        NoteView mNoteView;
    }
}
