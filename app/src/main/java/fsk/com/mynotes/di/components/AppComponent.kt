package fsk.com.mynotes.di.components

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import fsk.com.mynotes.CustomApplication
import fsk.com.mynotes.di.modules.ActivityBindingModule
import fsk.com.mynotes.di.modules.DatabaseModule
import fsk.com.mynotes.di.modules.SerializationModule
import fsk.com.mynotes.di.modules.SharedPreferenceModule
import fsk.com.mynotes.di.modules.ViewModelBindingModule
import fsk.com.mynotes.di.scopes.ApplicationScope


@ApplicationScope
@Component(modules = [
    DatabaseModule::class,
    SharedPreferenceModule::class,
    SerializationModule::class,
    ActivityBindingModule::class,
    ViewModelBindingModule::class,
    AndroidSupportInjectionModule::class
]
)
interface AppComponent: AndroidInjector<CustomApplication>  {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}