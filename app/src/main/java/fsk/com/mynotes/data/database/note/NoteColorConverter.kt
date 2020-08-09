package fsk.com.mynotes.data.database.note

import androidx.room.TypeConverter
import fsk.com.mynotes.data.NoteColor

/**
 * Converter to assist in storing the note color field
 */
class NoteColorConverter {

    private val colorIdMap = hashMapOf(
        Pair(NoteColor.YELLOW, "yellow"),
        Pair(NoteColor.BLUE, "blue"),
        Pair(NoteColor.GREEN, "green"),
        Pair(NoteColor.PINK, "pink"),
        Pair(NoteColor.GREY, "grey"),
        Pair(NoteColor.PURPLE, "purple")
    )

    @TypeConverter
    fun toNoteColor(colorId: String): NoteColor {
        val returnValue = NoteColor.values().filter { colorIdMap[it].equals(colorId, true) }
        return if (returnValue.isNotEmpty()) {
            returnValue.first()
        } else {
            throw IllegalArgumentException("The color Id is unknown")
        }
    }

    @TypeConverter
    fun fromNoteColor(color: NoteColor): String = colorIdMap[color]!!
}