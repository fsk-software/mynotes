package fsk.com.mynotes.ui.colorfilter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import fsk.com.mynotes.R
import fsk.com.mynotes.data.NoteColor
import fsk.com.mynotes.extensions.getNoteColorNameId
import fsk.com.mynotes.extensions.tintBackgroundForNoteColor
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ColorFilterAdapter: RecyclerView.Adapter<ColorFilterAdapter.ViewHolder>() {

    private val colorCheckedSubject: PublishSubject<Pair<NoteColor, Boolean>> = PublishSubject.create()
    val colorCheckedObservable: Observable<Pair<NoteColor, Boolean>> get() = colorCheckedSubject

    inner class ViewHolder(
        itemView: View
     ) : RecyclerView.ViewHolder(itemView) {
        lateinit var color: NoteColor
        val checkBox: CheckBox = itemView.findViewById(R.id.colorFilterCheckbox)

        init {
            checkBox.setOnCheckedChangeListener { view, checked ->
                if (view.isPressed) {
                    colorCheckedSubject.onNext(Pair(color, checked))
                }
            }
        }
    }

    private val selectedColors = mutableSetOf(*NoteColor.values())

    fun setSelectedColors(colors: Set<NoteColor>) {
        val oldSelections = HashSet<NoteColor>(selectedColors)

        selectedColors.clear()
        selectedColors.addAll(colors)
        NoteColor.values()
            .filter {
                (colors.contains(it) && !oldSelections.contains(it)) ||
                        (!colors.contains(it) && oldSelections.contains(it))
            }
            .forEach {
                notifyItemChanged(it.ordinal)
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_color_filter, parent, false)
        )

    override fun getItemCount(): Int = NoteColor.values().size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val color = NoteColor.values()[position]
        holder.color = color
        holder.checkBox.apply {
            buttonDrawable =
                context.tintBackgroundForNoteColor(color, R.drawable.color_filter_button_background)
            isChecked = selectedColors.contains(color)
            setText(context.getNoteColorNameId(color))
        }
    }
}