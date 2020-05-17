package fsk.com.mynotes.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import fsk.com.mynotes.data.database.NotesDatabase
import fsk.com.mynotes.data.database.note.NoteDao
import fsk.com.mynotes.di.scopes.ApplicationScope


@Module
class DatabaseModule {

    @Provides
    @ApplicationScope
    fun providesDatabase(context: Context): NotesDatabase =
        Room.databaseBuilder(
            context,
            NotesDatabase::class.java,
            NotesDatabase.FILENAME)
            .build()

    @Provides
    @ApplicationScope
    fun providesNoteDao(database: NotesDatabase): NoteDao = database.noteDao()

}