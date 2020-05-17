package fsk.com.mynotes.ui.note

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import fsk.com.mynotes.di.ViewModelKey

@Module
abstract class NoteBindsModule {

    @Binds
    @IntoMap
    @ViewModelKey(NoteViewModel::class)
    abstract fun bindNoteViewModel(viewModel: NoteViewModel): ViewModel

}
