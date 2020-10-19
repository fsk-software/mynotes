package fsk.com.koin.mynotes.ui.fragments.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fsk.com.koin.mynotes.R
import fsk.com.koin.mynotes.data.database.note.Note
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * The adapter to display a card.
 */
internal class NotesAdapter : ListAdapter<Note, NotesAdapter.CardViewHolder>(NoteDiffCallback()) {

    companion object {
        private class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {

            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }
        }
    }

    private val onNoteClickedPublisher: PublishSubject<Int> = PublishSubject.create()

    /**
     * Use to monitor for clicks on a note.
     * It emits the position within the adapter of the clicked note.
     */
    val getOnNoteClickedUpdates: Observable<Int> = onNoteClickedPublisher

    /**
     * The view holder to hold the card UI components.
     */
    inner class CardViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        /**
         * The text view to display the note text.
         */
        val titleView: TextView = itemView.findViewById(R.id.noteTitleText)

        /**
         * The text view to display the note text.
         */
        val textView: TextView = itemView.findViewById(R.id.noteContentText)

        /**
         * The card view that will change color to reflect the note color.
         */
        val cardView: CardView = itemView.findViewById(R.id.noteCardView)

        init {
            cardView.setOnClickListener { onNoteClickedPublisher.onNext(adapterPosition) }
        }
    }

    /**
     * Get the note at the specified adapter position
     *
     * @param position the position of the item to retrieve
     */
    fun getNote(position: Int): Note = getItem(position)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewHolder =
        CardViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_note, parent, false)
        )


    override fun onBindViewHolder(
        holder: CardViewHolder,
        position: Int
    ) {
        val note = getItem(position)

        holder.apply {
            cardView.setBackgroundResource(note.color.colorResId)
            textView.text = note.text
            titleView.text = note.title
        }
    }
}