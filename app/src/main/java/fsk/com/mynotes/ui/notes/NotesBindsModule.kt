package fsk.com.mynotes.ui.notes

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import fsk.com.mynotes.di.ViewModelKey

@Module
abstract class NotesBindsModule {

    @Binds
    @IntoMap
    @ViewModelKey(NotesViewModel::class)
    abstract fun bindNotesViewModel(viewModel: NotesViewModel): ViewModel

}
