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

package com.fsk.mynotes.presentation.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fsk.mynotes.constants.NoteColors;
import com.fsk.mynotes.R;
import com.fsk.mynotes.data.database.MyNotesDatabaseModel;
import com.fsk.mynotes.presentation.components.ColorSelector;
import com.fsk.mynotes.presentation.components.ColorSelector.OnSelectListener;
import com.fsk.mynotes.data.Note;
import com.fsk.mynotes.data.database.NotesTableManager;

public class NoteEditActivity extends Activity {

    protected EditText mText;
    protected ViewGroup mHeader;
    protected Long mRowId;
    protected ColorSelector mColorSelector;
    protected NotesTableManager mNotesTableManager;
    protected Button mConfirmButton;

	private String mOrigText;
	private NoteColors mOrigColor;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.note_edit);
        
        mNotesTableManager = NotesTableManager.getSingleton();
       	Typeface face=Typeface.createFromAsset(getAssets(), "fonts/memo2self.ttf");

       	mHeader = (ViewGroup) findViewById(R.id.note_edit_header);
        mText = (EditText) findViewById(R.id.note_edit_text);
    	mText.setTypeface(face);
    	
        mColorSelector = 
        	(ColorSelector) findViewById(R.id.note_edit_colors);
        mColorSelector.setOrientation(LinearLayout.HORIZONTAL);
        mColorSelector.invalidate();
        mColorSelector.setOnSelectListner(new OnSelectListener() {
			@Override
			public void onColorSelectChange(ColorSelector view, NoteColors color) {
				if (view.getSelectedColor() == null) {
					view.selectColor(color);
					mHeader.setBackgroundResource(color.getHeaderResource());
				}
				else {
					mText.setBackgroundResource(color.getImageResource());
					mHeader.setBackgroundResource(color.getHeaderResource());
				}
			}
        });
        	
        Bundle extras = getIntent().getExtras();
		mRowId = extras != null ? extras.getLong(MyNotesDatabaseModel.Columns.NOTE_ID): null;
		populateFields();
    }

    private void populateFields() {
    	NoteColors color = NoteColors.YELLOW;
        if (mRowId != null) {
            Note note = mNotesTableManager.fetchNote(mRowId);
            mText.setText(note.getText());
            color = NoteColors.getColor((int)note.getColor());
        }

        mOrigText = mText.getText().toString();
        mOrigColor = color;
        setColor(color);
    }

    private void setColor(NoteColors selectedColor) {
    	mColorSelector.selectColor(selectedColor);
    }
    
    protected boolean commit() {
        String text = mText.getText().toString();
        boolean allowCommit = !text.equals("");        
        
        long color = (long) mColorSelector.getSelectedColor().ordinal();
        if (allowCommit) {
        	if (mRowId == null) {
        		long id = mNotesTableManager.createNote(text, color);
        		if (id > 0) {
        			mRowId = id;
        		}
        	} else {
        		mNotesTableManager.editNote(mRowId, text, color);
        	}
        	
        	mOrigText = text;
        	mOrigColor = mColorSelector.getSelectedColor();
    	}
        else {
        	Toast msg = Toast.makeText(this, "Note cannot be blank", Toast.LENGTH_LONG);
        	msg.show();
        }
        return allowCommit;
    }
    
    private void showCommitDialogBeforeExit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(NoteEditActivity.this);
		builder.setMessage("Save the changes?");

		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				if (commit()) {
					dialog.cancel();
					NoteEditActivity.this.finish();
				}
			}
          });
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				NoteEditActivity.this.finish();
			}
          });
		builder.create().show();
    }

    
    @Override
	public void onBackPressed() {
    	if (!containsUncommittedChanges()) {
    		showCommitDialogBeforeExit();
    	}
    	else {
        	super.onBackPressed();
    	}
    }

    private boolean containsUncommittedChanges() {
    	return ((!mOrigText.equals(mText.getText().toString())) ||
    		    (mOrigColor != mColorSelector.getSelectedColor()));
    }
   
}
