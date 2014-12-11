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
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.fsk.mynotes.ColorSelector.OnSelectListener;

public class Main extends Activity {
	enum SortOptions {
		None(R.drawable.sort_off_button),
		AtoZ(R.drawable.sort_atoz_button),
		ZtoA(R.drawable.sort_ztoa_button);
		
		private int mResource;
		
		private SortOptions(int resource) {
			mResource = resource;
		}
	
		public static SortOptions toSortOption(int index) {
			SortOptions[] options = SortOptions.values();
			
			for (int i = 0; i < options.length; ++i) {
				if (index == options[i].ordinal()) {
					return options[i];
				}
			}	
			return null;
		}
		
		public SortOptions increment() {
			if (this == ZtoA) {
				return None;
			}
			else {
				return toSortOption(this.ordinal()+1);
			}
		}
	}
	
	static class NoteComparator implements Comparator<Note> {
		
		private SortOptions mSortOption;
		private boolean mGroupByColor;
		
		public NoteComparator(SortOptions sortOption, boolean groupByColor) {
			mSortOption = sortOption;
			mGroupByColor = groupByColor;
		}

		public int compare(Note arg0, Note arg1) {
			if (mGroupByColor) {
				return compareColors(arg0, arg1);
			}
			else {
				return compareAlpha(arg0, arg1);
			}
		}

		private int compareColors(Note arg0, Note arg1) {
			int returnValue = 0;
			if (arg0.getColor() == arg1.getColor()) {
				returnValue = compareAlpha(arg0, arg1);
			}
			else {
				returnValue = (arg0.getColor() < arg1.getColor()) ? -1 : 1;
			}
			return returnValue;
		}
		
		private int compareAlpha(Note arg0, Note arg1) {
			int returnValue = 0;
			
			if (mSortOption != SortOptions.None) {
				int factor = (mSortOption == SortOptions.ZtoA) ? -1 : 1;
				returnValue = 
					arg0.getText().compareToIgnoreCase(arg1.getText())*factor;
			}			
			
			return returnValue;
		}
	}

	private SharedPreferences mAppPreferences;
    private SharedPreferences.Editor mPreferenceEditor;

	private static final String sPrefLeftElide="left_elide_open";
	private static final String sPrefSort="sort";
	private static final String sPrefGroup="group_by_color";
	private static final String sPrefColor="show_colors";
	
	private static final int ACTIVITY_CREATE_NOTE=1;
	private static final int ACTIVITY_EDIT_NOTE=2;
    
    private GridView mNotesView;
    
    private NotesTableManager mNotesTableManager;
    
    private ToggleButton mLeftElideButton;
    private ViewGroup mColorFilterGroup;
    private ViewGroup mSortGroup;
    
    private ToggleButton mGroupByColorToggleButton;
    private Button mSortButton;
    private MultiColorSelector mColorFilter;
    
    private SortOptions mSortOption = SortOptions.None;
    
    private NoteEventListener mNoteEventListener = new NoteEventListener(){

		@Override
		public void onNoteClick(NoteView view, long id) {
			edit(id);
		}

		@Override
		public void onNoteDeleteClick(NoteView view, long id) {
			final long noteId = id;  
			AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
			builder.setMessage("Delete the note?");

			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					mNotesTableManager.deleteNote(noteId);
	                fill();
					dialog.cancel();
				}
	          });
			builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
	          });
			builder.create().show();
		}
    };
    
    private void add() {
    	Intent i = new Intent(this, NoteEditActivity.class);
    	startActivityForResult(i, ACTIVITY_CREATE_NOTE);
    }
    
    private void edit(long id) {
        Intent i = new Intent(this, NoteEditActivity.class);
        i.putExtra(DatabaseKeys.NOTE_ID.getKeyName(), id);
        startActivityForResult(i, ACTIVITY_EDIT_NOTE);
    }
    
    private void fill() {
    	ArrayList<Note> notes = 
    		mNotesTableManager.fetchNotes(mColorFilter.getColorsSelected());
        
   	    Collections.sort(notes, new NoteComparator(mSortOption, 
   	    		                                   mGroupByColorToggleButton.isChecked()));

        NotesViewAdapter noteAdapter = 
            new NotesViewAdapter(this, notes, mNoteEventListener);
        mNotesView.setAdapter(noteAdapter); 

        if (notes.size() < 2) {
        	mNotesView.setNumColumns(1);
        }
        else {
        	mNotesView.setNumColumns(GridView.AUTO_FIT);
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
       super.onActivityResult(requestCode, resultCode, intent);
       fill();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	DatabaseManager.openDatabase(getApplicationContext());
    	mNotesTableManager = NotesTableManager.getSingleton();

    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

		mAppPreferences = 
			PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		mPreferenceEditor = mAppPreferences.edit();
		
        mNotesView = (GridView) findViewById(R.id.main_notes);
        mNotesView.setOnItemClickListener(new OnItemClickListener() {
	   	@Override
			public void onItemClick(AdapterView<?> adapterType, View view, 
					                int position, long id) {
				edit(id);
			}
        });
        
        mColorFilter = (MultiColorSelector) findViewById(R.id.main_color_filter);        
		Iterator<NoteColors> colorIter = NoteColors.getIterator();
		while (colorIter.hasNext()) {
			NoteColors color = colorIter.next();
			boolean enabled = mAppPreferences.getBoolean(sPrefColor+"_"+color.getName(), true);
			if (enabled) {
				mColorFilter.selectColor(color);
			}
		}
		
		mColorFilter.invalidate();
		mColorFilter.setOnSelectListner(new OnSelectListener() {
			@Override
			public void onColorSelectChange(ColorSelector view, NoteColors color) {
				if (view.getSelectedColor() == null) {
					view.selectColor(color);
				}
				
				mPreferenceEditor.putBoolean(
						sPrefColor+"_"+color.getName(), view.isColorSelected(color));
			    mPreferenceEditor.commit();
				
			    fill();
			}
        });
         
        ImageButton addButton = (ImageButton)this.findViewById(R.id.main_add_button);
        addButton.setOnClickListener(new OnClickListener() {
           	public void onClick (View v){ 
           		add();
           	}
        });
        
        mSortButton = (Button)this.findViewById(R.id.main_sort_button);
        mSortButton.setIncludeFontPadding(false);
        mSortButton.setOnClickListener(new OnClickListener() {
           	public void onClick (View v){ 
           		sort();
           	}
        });

		mSortOption = SortOptions.toSortOption(mAppPreferences.getInt(sPrefSort, 0));
  	  	mSortButton.setBackgroundResource(mSortOption.mResource);
  	  
  	  	mGroupByColorToggleButton = 
  	  		(ToggleButton) findViewById(R.id.main_group_button);
  	  	mGroupByColorToggleButton.setIncludeFontPadding(false);
  	  	mGroupByColorToggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener () {
			public void onCheckedChanged(CompoundButton view, boolean checked) {
				mPreferenceEditor.putBoolean(sPrefGroup, checked);
			    mPreferenceEditor.commit();
				fill();
			}
  	  	});
  	  	
  	  	mColorFilterGroup = (ViewGroup) findViewById(R.id.main_filter_wrapper);
  	  	mSortGroup = (ViewGroup) findViewById(R.id.main_sort_wrapper);
  	  	
  	  	mLeftElideButton = (ToggleButton) findViewById(R.id.main_eliding_button);
  	  	mLeftElideButton.setOnCheckedChangeListener(new OnCheckedChangeListener () {
			public void onCheckedChanged(CompoundButton view, boolean checked) {
				if (checked) {
					mColorFilterGroup.setVisibility(View.GONE);
					mSortGroup.setVisibility(View.GONE);
				}
				else {
					mColorFilterGroup.setVisibility(View.VISIBLE);
					mSortGroup.setVisibility(View.VISIBLE);
				}
				fill();
		        
		        mPreferenceEditor.putBoolean(sPrefLeftElide, checked);
		        mPreferenceEditor.commit();
			}
  	  	});
  	  	
  	  	mLeftElideButton.setChecked(mAppPreferences.getBoolean(sPrefLeftElide, true));
  	  	mGroupByColorToggleButton.setChecked(mAppPreferences.getBoolean(sPrefGroup, false));

  	  	fill();
     }
    
    private void sort() {
      mSortOption = mSortOption.increment();
	  mSortButton.setBackgroundResource(mSortOption.mResource);
    
	  mPreferenceEditor.putInt(sPrefSort, mSortOption.ordinal());
      mPreferenceEditor.commit();
      
	  fill();
    }
}