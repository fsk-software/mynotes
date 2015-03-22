package com.fsk.mynotes.presentation.activity;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fsk.common.presentation.recycler.DividerItemDecoration;
import com.fsk.mynotes.R;
import com.fsk.mynotes.data.Note;
import com.fsk.mynotes.data.loaders.FilteredNoteLoader;
import com.fsk.mynotes.presentation.adapters.CardAdapter;
import com.fsk.mynotes.presentation.fragments.MainToolbarFragment;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * The primary activity.  It provides a list of notes and the tools to manage them.
 */
public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<List<Note>> {

    /**
     * The identifier of the main loader.  This loads the color filtered note list.
     */
    static final int MAIN_LOADER_ID = 0;


    /**
     * UI element that manages the primary toolbar.
     */
    MainToolbarFragment mMainToolbarFragment;


    /**
     * UI element to display the note data.
     */
    @InjectView(R.id.activity_main_notes_recycler_view)
    RecyclerView mCardsRecyclerView;


    /**
     * UI element to allow a user to create a new note.
     */
    @InjectView(R.id.activity_main_add_view)
    View mAddView;


    /**
     * The adapter to manage the display of cards in {@link #mCardsRecyclerView}.
     */
    CardAdapter mCardAdapter;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mCardAdapter = new CardAdapter(null);

        int columnCount = getResources().getInteger(R.integer.card_column_count);
        float cardDividerDimen = getResources().getDimension(R.dimen.note_divider_size);

        mCardsRecyclerView.addItemDecoration(new DividerItemDecoration((int) cardDividerDimen));
        mCardsRecyclerView.setLayoutManager(new GridLayoutManager(this, columnCount));
        mCardsRecyclerView.setAdapter(mCardAdapter);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        mMainToolbarFragment = new MainToolbarFragment();

        transaction.replace(R.id.activity_main_toolbar, mMainToolbarFragment);

        transaction.commit();
        getLoaderManager().initLoader(MAIN_LOADER_ID, null, this);
    }


    @Override
    public Loader<List<Note>> onCreateLoader(final int id, final Bundle args) {
        return new FilteredNoteLoader(this);
    }


    @Override
    public void onLoadFinished(final Loader<List<Note>> loader, final List<Note> data) {
        mCardAdapter.setNotes(data);
    }


    @Override
    public void onLoaderReset(final Loader<List<Note>> loader) {
        //nothing
    }
}