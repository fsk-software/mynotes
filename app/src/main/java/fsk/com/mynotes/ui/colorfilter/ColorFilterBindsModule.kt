package fsk.com.mynotes.ui.colorfilter

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import fsk.com.mynotes.di.ViewModelKey

@Module
abstract class ColorFilterBindsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ColorFilterViewModel::class)
    abstract fun bindColorFilterViewModel(viewModel: ColorFilterViewModel): ViewModel

}
