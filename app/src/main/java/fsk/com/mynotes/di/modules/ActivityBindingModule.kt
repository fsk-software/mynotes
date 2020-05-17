package fsk.com.mynotes.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import fsk.com.mynotes.di.scopes.ActivityScope
import fsk.com.mynotes.ui.main.MainActivity


@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [ActivityResourceModule::class, FragmentBindingModule::class])
    @ActivityScope
    abstract fun bindMainActivity(): MainActivity

}