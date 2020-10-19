package fsk.com.koin.mynotes.data.database.note

import androidx.room.Entity
import androidx.room.PrimaryKey
import fsk.com.koin.mynotes.data.NoteColor

/**
 * The persistable note
 */
@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey var id: Long? = null,
    var color: NoteColor = NoteColor.YELLOW,
    var text: String = "",
    var title: String = ""
)