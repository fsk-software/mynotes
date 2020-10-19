package fsk.com.koin.mynotes.ui.fragments.note.di

import fsk.com.koin.mynotes.ui.fragments.note.EditNoteViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


/**
 * Module to create and define the edit note subcomponent.
 */
val editNoteModule = module {
    viewModel { (noteId: Long) -> EditNoteViewModel(noteId, get()) }
}