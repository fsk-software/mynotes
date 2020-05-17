package fsk.com.mynotes.ui.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fsk.com.mynotes.R
import fsk.com.mynotes.data.database.note.Note
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * The adapter to display a card.
 */
class NotesAdapter : ListAdapter<Note, NotesAdapter.CardViewHolder>(NoteDiffCallback()) {

    private val noteClickedSubject: PublishSubject<Note> = PublishSubject.create()
    val noteClickedObservable: Observable<Note> get() = noteClickedSubject

    companion object {
        private class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {

            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id &&
                        oldItem.color == newItem.color &&
                        oldItem.text == newItem.text &&
                        oldItem.title == newItem.title
            }
        }
    }

    /**
     * The view holder to hold the card UI components.
     */
    inner class CardViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        lateinit var data: Note

        /**
         * The text view to display the note text.
         */
        val textView: TextView = itemView.findViewById(R.id.recycler_item_card_text)


        /**
         * The card view that will change color to reflect the note color.
         */
        val cardView: CardView = itemView.findViewById(R.id.recycler_item_card_view)

        init {
            cardView.setOnClickListener{noteClickedSubject.onNext(data)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): CardViewHolder =
        CardViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_note, parent, false)
        )


    override fun onBindViewHolder(holder: CardViewHolder,
                                  position: Int) {
        val note = getItem(position)

        //We can't change a cards color in the normal way, but google did supply a special method
        //to do it. Directly, calling setBackgroundResource() will work on the newest lollipop
        //builds (v2) but crashes everywhere else.
//
//        holder.cardView.setCardBackgroundColor(
//                note.color.getColorArgb(holder.cardView.context))
//        holder.cardView.tag = note
//        holder.textView.setText(note.text)
//        ViewCompat.setTransitionName(holder.cardView, note.id.toString())
    }
}