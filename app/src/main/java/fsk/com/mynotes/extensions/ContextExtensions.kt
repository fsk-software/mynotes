package fsk.com.mynotes.extensions

import android.content.Context
import androidx.core.content.ContextCompat
import fsk.com.mynotes.R
import fsk.com.mynotes.data.NoteColor


/**
 * Get the String name for the note color.
 *
 * @param noteColor
 *         The note color to use for getting the displayable color name.
 *
 * @return The argb value for the note color.
 */
fun Context.getNoteColorName(noteColor: NoteColor): String =
    getString(noteColor.nameResId)

/**
 * Get the argb value for the color.
 *
 * @param noteColor
 *         The note color to use for getting the aRGB value.
 *
 * @return The argb value for the color.
 */
fun Context.getNoteArgb(noteColor: NoteColor): Int =
    when (noteColor) {
        NoteColor.YELLOW -> ContextCompat.getColor(this, R.color.note_yellow)
        NoteColor.GREEN -> ContextCompat.getColor(this, R.color.note_green)
        NoteColor.PINK -> ContextCompat.getColor(this, R.color.note_pink)
        NoteColor.GREY -> ContextCompat.getColor(this, R.color.note_grey)
        NoteColor.PURPLE -> ContextCompat.getColor(this, R.color.note_purple)
        NoteColor.BLUE -> ContextCompat.getColor(this, R.color.note_blue)
    }
