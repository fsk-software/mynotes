package com.fsk.mynotes.presentation.adapters;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fsk.common.presentation.recycler.RecyclerViewAdapter;
import com.fsk.mynotes.R;
import com.fsk.mynotes.data.Note;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * The adapter to display a card.
 */
public class CardAdapter extends RecyclerViewAdapter<CardAdapter.CardViewHolder> {

    /**
     * The view holder to hold the card UI components.
     */
    static class CardViewHolder extends RecyclerView.ViewHolder {

        /**
         * The text view to display the note text.
         */
        @InjectView(R.id.recycler_item_card_text)
        TextView mTextView;


        /**
         * The card view that will change color to reflect the note color.
         */
        @InjectView(R.id.recycler_item_card_view)
        CardView mCardView;


        /**
         * Constructor.
         *
         * @param v
         *         the root view that will be used to find the ViewHolder views.
         */
        public CardViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);
        }
    }

    /**
     * The data to display as Cards.
     */
    final List<Note> mNotes = new ArrayList<>();


    /**
     * Update the notes in the adapter.  This will remove the existing notes and replace it with the
     * specified notes.
     *
     * @param notes
     *         the new notes to display.  This can be null to clear the adapter.
     */
    public void setNotes(List<Note> notes) {
        mNotes.clear();

        if (notes != null) {
            mNotes.addAll(notes);
        }
    }


    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.recycler_item_note, parent, false);

        return new CardViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Note note = mNotes.get(position);

        //We can't change a cards color in the normal way, but google did supply a special method
        //to do it. Directly, calling setBackgroundResource() will work on the newest lollipop
        //builds (v2) but crashes everywhere else.
        int color = holder.mCardView.getResources().getColor(note.getColor().colorResourceId);
        holder.mCardView.setCardBackgroundColor(color);

        holder.mTextView.setText(note.getText());
    }


    @Override
    public int getItemCount() {
        return mNotes.size();
    }
}