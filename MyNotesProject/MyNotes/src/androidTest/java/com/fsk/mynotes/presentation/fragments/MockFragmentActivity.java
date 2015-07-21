package com.fsk.mynotes.presentation.fragments;


import android.app.Activity;
import android.os.Bundle;

import com.fsk.mynotes.R;
import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.data.Note;
import com.fsk.mynotes.utils.NoteEditor;

/**
 * Created by Me on 3/15/2015.
 */
public class MockFragmentActivity extends Activity implements NoteEditor {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_mock_fragment_activity);
    }


    @Override
    public Note getNote() {
        return new Note();
    }


    @Override
    public void changeNoteColor(final NoteColor color) {

    }


    @Override
    public void saveNote() {

    }


    @Override
    public void deleteNote() {

    }


    @Override
    public void showColorPicker() {

    }


    @Override
    public void showEditOptions() {

    }
}
