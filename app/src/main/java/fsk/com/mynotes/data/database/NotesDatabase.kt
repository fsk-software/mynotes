package fsk.com.mynotes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fsk.com.mynotes.data.database.note.Note
import fsk.com.mynotes.data.database.note.NoteColorConverter
import fsk.com.mynotes.data.database.note.NoteDao

@Database(entities = [Note::class], version = 1)
@TypeConverters(NoteColorConverter::class)

abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao


    companion object {
        const val FILENAME = "notes.db"
    }
}
