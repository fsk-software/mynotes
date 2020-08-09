package fsk.com.mynotes.di.components

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import fsk.com.mynotes.CustomApplication
import fsk.com.mynotes.di.modules.ApplicationModule
import fsk.com.mynotes.di.modules.DatabaseModule
import fsk.com.mynotes.di.modules.SerializationModule
import fsk.com.mynotes.di.modules.SharedPreferenceModule
import fsk.com.mynotes.di.modules.ViewModelBindingModule
import fsk.com.mynotes.di.scopes.ApplicationScope
import fsk.com.mynotes.ui.main.di.ActivityBindingModule

/**
 * The main application component.  All other components in the app are sub-components to this component.
 */
@ApplicationScope
@Component(
    modules = [
        ApplicationModule::class,
        DatabaseModule::class,
        SharedPreferenceModule::class,
        SerializationModule::class,
        ActivityBindingModule::class,
        ViewModelBindingModule::class,
        AndroidSupportInjectionModule::class
    ]
)
interface AppComponent : AndroidInjector<CustomApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: CustomApplication): AppComponent
    }
}