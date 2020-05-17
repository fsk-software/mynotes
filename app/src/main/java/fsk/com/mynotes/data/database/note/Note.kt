package fsk.com.mynotes.data.database.note

import androidx.room.Entity
import androidx.room.PrimaryKey
import fsk.com.mynotes.data.NoteColor

@Entity(tableName = "note_table")
class Note(
    @PrimaryKey var id: Long? = null,
    var color: NoteColor = NoteColor.YELLOW,
    var text: String="",
    var title: String=""
) {

    fun persisted(): Boolean = id != null
}