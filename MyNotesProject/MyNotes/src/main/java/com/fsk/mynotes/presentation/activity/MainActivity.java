package com.fsk.mynotes.presentation.activity;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fsk.mynotes.R;
import com.fsk.mynotes.presentation.fragments.ColorFilterFragment;
import com.fsk.mynotes.presentation.fragments.NoteCardsFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * The primary activity.  It provides a list of notes and the tools to manage them.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The fragment tags.
     */
    static class FragmentTags {
        /**
         * The tag for the note cards fragment.
         */
        public static final String NOTE_CARDS_FRAGMENT_TAG = "NOTE_CARDS_FRAGMENT_TAG";

        /**
         * The tag for the color filter fragment.
         */
        public static final String COLOR_FILTER_FRAGMENT_TAG = "COLOR_FILTER_FRAGMENT_TAG";
    }


    /**
     * UI element that allows the user to add a note.
     * @param view
     */
    @OnClick(R.id.activity_main_add_view)
    public void addViewClick(View view) {
        createNewNote();
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        // Set a toolbar to replace the action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);

        Fragment colorFilterSelectorFragment = ColorFilterFragment.newInstance();
        Fragment noteCardsFragment = NoteCardsFragment.newInstance();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_notes_container,
                            noteCardsFragment,
                            FragmentTags.NOTE_CARDS_FRAGMENT_TAG);

        transaction.replace(R.id.activity_main_color_filter_container,
                            colorFilterSelectorFragment,
                            FragmentTags.COLOR_FILTER_FRAGMENT_TAG);
        transaction.commit();
    }


    /**
     * Launch a UI that allows the user to create a new note.
     */
    private void createNewNote() {
        startActivity(EditNoteActivity.createIntentForNewNote(this));
    }
}