package fsk.com.mynotes.ui.colorfilter.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import fsk.com.mynotes.di.scopes.FragmentScope
import fsk.com.mynotes.di.viewmodels.ViewModelKey
import fsk.com.mynotes.ui.colorfilter.ColorFilterDialogFragment
import fsk.com.mynotes.ui.colorfilter.ColorFilterViewModel

/**
 * Module to create and define the color filter subcomponent.
 */
@Module
interface ColorFilterBuilderModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [BindsModule::class])
    fun bindColorFilterFragment(): ColorFilterDialogFragment

    @Module
    interface BindsModule {
        @Binds
        @IntoMap
        @ViewModelKey(ColorFilterViewModel::class)
        fun bindColorFilterViewModel(viewModel: ColorFilterViewModel): ViewModel
    }
}