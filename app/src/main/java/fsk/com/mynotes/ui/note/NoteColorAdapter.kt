package fsk.com.mynotes.ui.note

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.appcompat.widget.TooltipCompat
import androidx.recyclerview.widget.RecyclerView
import fsk.com.mynotes.R
import fsk.com.mynotes.data.NoteColor
import fsk.com.mynotes.extensions.getNoteColorName
import fsk.com.mynotes.extensions.tintBackgroundForNoteColor
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class NoteColorAdapter : RecyclerView.Adapter<NoteColorAdapter.ViewHolder>() {

    private val colorCheckedSubject: PublishSubject<Pair<NoteColor, Boolean>> =
        PublishSubject.create()
    val colorCheckedObservable: Observable<Pair<NoteColor, Boolean>> get() = colorCheckedSubject

    inner class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        lateinit var color: NoteColor
        lateinit var hintText: String
        val checkBox: CheckBox = itemView.findViewById(R.id.noteColorItem)

        init {
            checkBox.setOnCheckedChangeListener { view, checked ->
                if (view.isPressed) {
                    colorCheckedSubject.onNext(Pair(color, checked))
                }
            }
        }
    }

    var selectedColor = NoteColor.YELLOW
        set(value) {
            if (value != field) {
                val oldSelection = field
                field = value

                notifyItemChanged(value.ordinal)
                notifyItemChanged(oldSelection.ordinal)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_color_note, parent, false)
        )

    override fun getItemCount(): Int = NoteColor.values().size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val color = NoteColor.values()[position]
        holder.apply {
            this.color = color
            hintText = itemView.context.getNoteColorName(color)

            checkBox.apply {
                buttonDrawable =
                    context.tintBackgroundForNoteColor(color, R.drawable.pallette_color)
                isChecked = color == selectedColor
                TooltipCompat.setTooltipText(this, context.getNoteColorName(color))
            }
        }
    }
}