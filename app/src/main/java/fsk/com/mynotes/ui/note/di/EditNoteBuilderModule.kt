package fsk.com.mynotes.ui.note.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import fsk.com.mynotes.di.scopes.FragmentScope
import fsk.com.mynotes.di.viewmodels.ViewModelKey
import fsk.com.mynotes.ui.note.EditNoteFragment
import fsk.com.mynotes.ui.note.EditNoteViewModel

/**
 * Module to create and define the edit note subcomponent.
 */
@Module
interface EditNoteBuilderModule {

    @FragmentScope
    @ContributesAndroidInjector(
        modules = [
            BindsModule::class,
            ProvidesModule::class
        ]
    )
    fun bindFragment(): EditNoteFragment

    @Module
    interface BindsModule {
        @Binds
        @IntoMap
        @ViewModelKey(EditNoteViewModel::class)
        fun bindNoteViewModel(viewModel: EditNoteViewModel): ViewModel
    }

    @Module
    class ProvidesModule {
        @NoteId
        @Provides
        fun providesNoteId(fragment: EditNoteFragment): Long = fragment.noteId
    }
}