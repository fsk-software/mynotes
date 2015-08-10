package com.fsk.mynotes.presentation.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fsk.common.presentation.recycler.DividerItemDecoration;
import com.fsk.mynotes.R;
import com.fsk.mynotes.data.Note;
import com.fsk.mynotes.data.loaders.FilteredNoteLoader;
import com.fsk.mynotes.presentation.activity.EditNoteActivity;
import com.fsk.mynotes.presentation.adapters.CardAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A fragment to display all of the loaded notes as cards.
 */
public class NoteCardsFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<Note>> {

    /**
     * Create a new instance of the {@link NoteCardsFragment}
     *
     * @return a new instance of the {@link NoteCardsFragment}
     */
    public static NoteCardsFragment newInstance() {
        return new NoteCardsFragment();
    }


    /**
     * The identifier of the main loader.  This loads the color filtered note list.
     */
    static final int MAIN_LOADER_ID = 0;


    /**
     * UI element to display the note data.
     */
    @InjectView(R.id.fragment_note_cards_recycler_view)
    RecyclerView mCardsRecyclerView;


    /**
     * The adapter to manage the display of cards in {@link #mCardsRecyclerView}.
     */
    CardAdapter mCardAdapter;


    /**
     * The listener to a note being clicked.
     */
    final CardAdapter.OnItemClickListener mNoteClickListener =
            new CardAdapter.OnItemClickListener() {

                @Override
                public void onItemClick(final View view, final Note note) {
                    editNote(view, note);
                }
            };


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note_cards, container, false);
        ButterKnife.inject(this, rootView);

        Context context = inflater.getContext();
        mCardAdapter = new CardAdapter();
        mCardAdapter.setOnItemClickListener(mNoteClickListener);

        Resources resources = context.getResources();
        int columnCount = resources.getInteger(R.integer.card_column_count);
        float cardDividerDimen = resources.getDimension(R.dimen.note_divider_size);

        mCardsRecyclerView.addItemDecoration(new DividerItemDecoration((int) cardDividerDimen));
        mCardsRecyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
        mCardsRecyclerView.setAdapter(mCardAdapter);

        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(MAIN_LOADER_ID, null, this);
    }


    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().getLoader( MAIN_LOADER_ID ).forceLoad();
    }


    @Override
    public Loader<List<Note>> onCreateLoader(final int id, final Bundle args) {
        return new FilteredNoteLoader(getActivity());
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
     * Edit the specified note.
     *
     * @param view
     *         The clicked note view.
     * @param note
     *         the note to edit.
     */
    private void editNote(View view, Note note) {
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        Pair<View, String> notePair = Pair.create(view, Long.toString(note.getId()));
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity, notePair);
        startActivity(EditNoteActivity.createIntentForExistingNote(activity, note), options.toBundle());
    }
}
