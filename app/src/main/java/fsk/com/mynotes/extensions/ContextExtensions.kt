package fsk.com.mynotes.extensions

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
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
    getString(getNoteColorNameId(noteColor))

/**
 * Get the String name for the note color.
 *
 * @param noteColor
 *         The note color to use for getting the displayable color name.
 *
 * @return The argb value for the note color.
 */
@StringRes
fun Context.getNoteColorNameId(noteColor: NoteColor): Int =
    when (noteColor) {
        NoteColor.YELLOW -> R.string.yellow
        NoteColor.GREEN -> R.string.green
        NoteColor.PINK -> R.string.pink
        NoteColor.GREY -> R.string.gray
        NoteColor.PURPLE -> R.string.purple
        NoteColor.BLUE -> R.string.blue
    }


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
        NoteColor.GREY -> ContextCompat.getColor(this, R.color.note_gray)
        NoteColor.PURPLE -> ContextCompat.getColor(this, R.color.note_purple)
        NoteColor.BLUE -> ContextCompat.getColor(this, R.color.note_blue)
    }

@ColorRes
fun Context.getNoteColorResId(noteColor: NoteColor): Int =
    when (noteColor) {
        NoteColor.YELLOW -> R.color.note_yellow
        NoteColor.GREEN -> R.color.note_green
        NoteColor.PINK -> R.color.note_pink
        NoteColor.GREY -> R.color.note_gray
        NoteColor.PURPLE -> R.color.note_purple
        NoteColor.BLUE -> R.color.note_blue
    }

fun Context.tintBackgroundForNoteColor(noteColor: NoteColor, drawableId: Int): Drawable {
    val drawable = DrawableCompat.wrap(resources.getDrawable(drawableId))
    drawable.colorFilter = PorterDuffColorFilter(getNoteArgb(noteColor), PorterDuff.Mode.SRC_IN)

    return drawable
}