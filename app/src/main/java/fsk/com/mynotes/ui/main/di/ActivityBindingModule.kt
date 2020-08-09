package fsk.com.mynotes.ui.main.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import fsk.com.mynotes.di.scopes.ActivityScope
import fsk.com.mynotes.ui.colorfilter.di.ColorFilterBuilderModule
import fsk.com.mynotes.ui.main.MainActivity
import fsk.com.mynotes.ui.note.di.EditNoteBuilderModule
import fsk.com.mynotes.ui.notes.di.NotesBuilderModule

/**
 * Module to support the activity bindings
 */
@Module
interface ActivityBindingModule {

    @ContributesAndroidInjector(
        modules = [
            ColorFilterBuilderModule::class,
            EditNoteBuilderModule::class,
            NotesBuilderModule::class
        ]
    )

    @ActivityScope
    fun bindMainActivity(): MainActivity

}