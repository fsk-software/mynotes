package fsk.com.mynotes.di.modules

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import fsk.com.mynotes.di.viewmodels.AppViewModelFactory

@Module
abstract class ViewModelBindingModule {

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}
