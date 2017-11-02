package com.fsk.mynotes.constants;


import android.content.Context
import android.support.v4.content.ContextCompat
import com.fsk.mynotes.R;


/**
 * Get the {@link NoteColor} associated with the supplied ordinal.
 *
 * @param index
 *         the ordinal of the {@link NoteColor} to retrieve.
 *
 * @return the {@link NoteColor} associated with the ordinal or null.
 */
fun getNoteColorAt(index: Int): NoteColor {
    val colors: Array<NoteColor> = NoteColor.values();
    require(index in 0..(colors.size - 1))

    return colors[index];
}

/**
 * An enumerator containing all of the valid note colors. Each color corresponds to three resource
 * ids: <ul> <li>The resource id for the printable color name.</li> <li>The resource id for notes
 * rgb color.</li> <li>The resource id for notes rgb dark color.</li>
 *
 * @param nameResId
 * *             The id of the string resource containing the printable name for the color.
 *
 * @param colorResourceId
 * *             The id of the color resource containing the ARGB color for the note color.
 *
 * @param darkColorResourceId
 * *             The id of the color resource containing the ARGB dark color for the note color.
 */
enum class NoteColor(val colorNameId: Int,
                     val colorId: Int,
                     val darkColorId: Int) {

    YELLOW(R.string.yellow, R.color.note_yellow, R.color.note_yellow_dark),

    BLUE(R.string.blue, R.color.note_blue, R.color.note_blue_dark),

    GREEN(R.string.green, R.color.note_green, R.color.note_green_dark),

    PINK(R.string.pink, R.color.note_pink, R.color.note_pink_dark),

    GREY(R.string.gray, R.color.note_gray, R.color.note_gray_dark),

    PURPLE(R.string.purple, R.color.note_purple, R.color.note_purple_dark);

    /**
     * Get the String name for the color.
     *
     * @param context
     *         The context to use for reading the string from the resources.
     *
     * @return The argb value for the color.
     */
    fun getColorName(context: Context): String {
        return context.getString(colorNameId)
    }


    /**
     * Get the argb value for the color.
     *
     * @param context
     *         The context to use for reading argb from the resources.
     *
     * @return The argb value for the color.
     */
    fun getColorArgb(context: Context): Int {
        return ContextCompat.getColor(context, colorId)
    }

    /**
     * Get the argb value for the dark color.
     *
     * @param context
     *         The context to use for reading argb from the resources.
     *
     * @return The argb value for the dark color.
     */
    fun getDarkColorArgb(context: Context): Int {
        return ContextCompat.getColor(context, darkColorId)
    }

}