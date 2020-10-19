package fsk.com.koin.mynotes.data

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import fsk.com.koin.mynotes.R

/**
 * The available note colors
 *
 * @property colorResId the resource Id for the note color.
 * @property nameResId the resource Id for the displayable note name.
 */
enum class NoteColor(
    @ColorRes val colorResId: Int,
    @StringRes val nameResId: Int
) {
    //I waffled on whether or not to have the resource ids in here versus using lookup table.
    // Ultimately, this is just cleaner to use so I opted for this approach.

    YELLOW(R.color.note_yellow, R.string.yellow),
    BLUE(R.color.note_blue, R.string.blue),
    GREEN(R.color.note_green, R.string.green),
    PINK(R.color.note_pink, R.string.pink),
    GREY(R.color.note_grey, R.string.grey),
    PURPLE(R.color.note_purple, R.string.purple);
}