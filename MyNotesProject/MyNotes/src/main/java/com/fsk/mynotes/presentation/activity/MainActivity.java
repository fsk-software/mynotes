package com.fsk.mynotes.presentation.activity;


import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.fsk.common.presentation.recycler.DividerItemDecoration;
import com.fsk.mynotes.R;
import com.fsk.mynotes.data.Note;
import com.fsk.mynotes.data.loaders.FilteredNoteLoader;
import com.fsk.mynotes.presentation.adapters.CardAdapter;
import com.fsk.mynotes.presentation.adapters.FilterColorAdapter;
import com.fsk.mynotes.presentation.layout_managers.ColorFilterLayoutManager;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * The primary activity.  It provides a list of notes and the tools to manage them.
 */
public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Note>> {

    /**
     * The identifier of the main loader.  This loads the color filtered note list.
     */
    static final int MAIN_LOADER_ID = 0;


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
     * UI element that allows the user to filter notes by color.
     */
    @InjectView(R.id.activity_main_color_filter_recycler)
    RecyclerView mFilterRecyclerView;


    @OnClick(R.id.activity_main_add_view)
    public void addViewClick(View view) {
        createNewNote();
    }


    /**
     * The adapter to manage the display of cards in {@link #mCardsRecyclerView}.
     */
    CardAdapter mCardAdapter;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        // Set a toolbar to replace the action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        initializeNoteCards();
        initializeColorFilterToolbar();

        getLoaderManager().initLoader(MAIN_LOADER_ID, null, this);
    }


    /**
     * Initialize the color filter toolbar
     */
    private void initializeColorFilterToolbar() {

        int dividerHorizontal =
                (int) getResources().getDimension(R.dimen.color_filter_item_divider_horizontal);
        int dividerVertical =
                (int) getResources().getDimension(R.dimen.color_filter_item_divider_vertical);

        mFilterRecyclerView
                .addItemDecoration(new DividerItemDecoration(dividerVertical, dividerHorizontal));

        boolean vertical = getResources().getBoolean(R.bool.color_filter_toolbar_vertical);
        int orientation = vertical ? LinearLayout.VERTICAL : LinearLayout.HORIZONTAL;

        mFilterRecyclerView.setLayoutManager(new ColorFilterLayoutManager(this, orientation));
        mFilterRecyclerView.setAdapter(new FilterColorAdapter(this));
    }


    /**
     * Initialize the note cards UI.
     */
    private void initializeNoteCards() {

        mCardAdapter = new CardAdapter();

        int columnCount = getResources().getInteger(R.integer.card_column_count);
        float cardDividerDimen = getResources().getDimension(R.dimen.note_divider_size);

        mCardsRecyclerView.addItemDecoration(new DividerItemDecoration((int) cardDividerDimen));
        mCardsRecyclerView.setLayoutManager(new GridLayoutManager(this, columnCount));
        mCardsRecyclerView.setAdapter(mCardAdapter);
    }


    @Override
    public Loader<List<Note>> onCreateLoader(final int id, final Bundle args) {
        return new FilteredNoteLoader(this);
    }


    @Override
    public void onLoadFinished(final Loader<List<Note>> loader, final List<Note> data) {
        mCardAdapter.setNotes(data);
        mCardAdapter.postNotifyDataSetChanged(mCardsRecyclerView.getHandler());
    }


    @Override
    public void onLoaderReset(final Loader<List<Note>> loader) {
        //nothing
    }


    /**
     * Launch a UI that allows the user to create a new note.
     */
    private void createNewNote() {
    }
}