package fsk.com.koin.mynotes.ui.activities.main.di

import fsk.com.koin.mynotes.ui.fragments.colorfilter.di.colorFilterModule
import fsk.com.koin.mynotes.ui.fragments.note.di.editNoteModule
import fsk.com.koin.mynotes.ui.fragments.notes.di.notesModule

/**
 * Module to support the main activity injections
 */
val mainActivityModule = colorFilterModule + editNoteModule + notesModule
