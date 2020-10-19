package fsk.com.koin.mynotes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fsk.com.koin.mynotes.data.database.note.Note
import fsk.com.koin.mynotes.data.database.note.NoteColorConverter
import fsk.com.koin.mynotes.data.database.note.NoteDao

/**
 * The notes database definition.
 */
@Database(entities = [Note::class], version = 1)
@TypeConverters(NoteColorConverter::class)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        /**
         * Database file name
         */
        const val FILENAME = "notes.db"
    }
}
