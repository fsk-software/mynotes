package fsk.com.mynotes.ui.notes.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import fsk.com.mynotes.di.scopes.FragmentScope
import fsk.com.mynotes.di.viewmodels.ViewModelKey
import fsk.com.mynotes.ui.notes.NotesFragment
import fsk.com.mynotes.ui.notes.NotesViewModel

/**
 * Module to create and define the edit note subcomponent.
 */
@Module
interface NotesBuilderModule {

    @FragmentScope
    @ContributesAndroidInjector(
        modules = [
            BindsModule::class
        ]
    )
    fun bindFragment(): NotesFragment

    @Module
    interface BindsModule {
        @Binds
        @IntoMap
        @ViewModelKey(NotesViewModel::class)
        fun bindNotesViewModel(viewModel: NotesViewModel): ViewModel

    }
}