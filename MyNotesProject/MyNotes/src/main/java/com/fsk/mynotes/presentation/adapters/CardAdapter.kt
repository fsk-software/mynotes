package com.fsk.mynotes.presentation.adapters;


import android.support.test.espresso.core.internal.deps.guava.collect.ImmutableList
import android.support.v4.view.ViewCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fsk.common.presentation.recycler.RecyclerViewAdapter
import com.fsk.mynotes.R
import com.fsk.mynotes.data.Note
import kotterknife.bindView

/**
 * The adapter to display a card.
 */
class CardAdapter() : RecyclerViewAdapter<CardAdapter.CardViewHolder>() {

    /**
     * The callback listener to handle and item being clicked.
     */
    interface OnItemClickListener {
        /**
         * The callback to handle an item being clicked.
         *
         * @param view
         *         the clicked view.
         * @param note
         *         the note associate with the clicked view.
         */
        fun onItemClick(view: View,
                        note: Note);
    }

    /**
     * The view holder to hold the card UI components.
     */
    class CardViewHolder(val root: View,
                         val listener: View.OnClickListener) : RecyclerView.ViewHolder(root) {

        /**
         * The text view to display the note text.
         */
        val textView: TextView by bindView(id = R.id.recycler_item_card_text);


        /**
         * The card view that will change color to reflect the note color.
         */
        val cardView: CardView by bindView(R.id.recycler_item_card_view)

        init {
            cardView.setOnClickListener(listener);

        }
    }

    /**
     * The data to display as Cards.
     */
    val notes = mutableListOf<Note>()


    /**
     * The listener to notify of an item click.
     */
    var onItemClickListener: OnItemClickListener? = null;


    /**
     * Update the notes in the adapter.  This will remove the existing notes and replace it with the
     * specified notes.
     *
     * @param notes
     *         the new notes to display.  This can be null to clear the adapter.
     */
    fun setNotes(notesArg: List<Note>) {
        notes.clear();
        notes.addAll(notesArg);
    }


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_note, parent, false);

        val listener = object : View.OnClickListener {
            override fun onClick(v: View) {
                val note: Note = v.tag as Note;
                onItemClickListener?.onItemClick(v, note);
            }
        };
        return CardViewHolder(view, listener);
    }


    override fun onBindViewHolder(holder: CardViewHolder,
                                  position: Int) {
        val note = notes.get(position);

        //We can't change a cards color in the normal way, but google did supply a special method
        //to do it. Directly, calling setBackgroundResource() will work on the newest lollipop
        //builds (v2) but crashes everywhere else.

        holder.cardView.setCardBackgroundColor(
                note.color.getColorArgb(holder.cardView.context));
        holder.cardView.tag = note;
        holder.textView.setText(note.text);
        ViewCompat.setTransitionName(holder.cardView, note.id.toString());
    }


    override fun getItemCount(): Int {
        return notes.size;
    }
}