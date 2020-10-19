package fsk.com.koin.mynotes.ui.fragments.notes.di

import fsk.com.koin.mynotes.ui.fragments.notes.NotesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Module to create and define the notes subcomponent.
 */
val notesModule = module {
    viewModel { NotesViewModel(get(), get()) }
}