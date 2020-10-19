package fsk.com.koin.mynotes.di

import androidx.room.Room
import fsk.com.koin.mynotes.data.database.NotesDatabase
import org.koin.dsl.module


/**
 * Module to support injecting the database and its supporting objects.
 */
internal val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            NotesDatabase::class.java,
            NotesDatabase.FILENAME
        ).build()
    }

    single { get<NotesDatabase>().noteDao() }
}