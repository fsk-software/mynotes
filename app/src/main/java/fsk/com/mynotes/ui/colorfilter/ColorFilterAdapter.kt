package fsk.com.mynotes.ui.colorfilter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import fsk.com.mynotes.R
import fsk.com.mynotes.data.NoteColor
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Adapter to display each note color as a Checkbox.
 */
internal class ColorFilterAdapter : RecyclerView.Adapter<ColorFilterAdapter.ViewHolder>() {

    private val onColorSelectionPublisher: PublishSubject<Pair<Int, Boolean>> =
        PublishSubject.create()

    /**
     * Use to monitor for note colors being checked/unchecked.
     * It emits a pair containing the [NoteColor] ordinal and a boolean status indicating if the color is checked.
     */
    val getOnColorSelectionUpdates: Observable<Pair<Int, Boolean>> get() = onColorSelectionPublisher

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.colorFilterCheckbox)

        init {
            checkBox.setOnCheckedChangeListener { view, checked ->
                if (view.isPressed) {
                    onColorSelectionPublisher.onNext(adapterPosition to checked)
                }
            }
        }
    }

    private val selectedColors = mutableSetOf(*NoteColor.values())

    /**
     * Set the selected colors.
     *
     * @param colors set of colors to check
     */
    internal fun setSelectedColors(colors: Set<NoteColor>) {
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
        NoteColor.values()[position].let { color ->
            holder.checkBox.apply {
                buttonTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context, color.colorResId))
                isChecked = selectedColors.contains(color)
                setText(color.nameResId)
            }
        }
    }
}