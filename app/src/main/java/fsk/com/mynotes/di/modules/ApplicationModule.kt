package fsk.com.mynotes.di.modules

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import fsk.com.mynotes.CustomApplication
import fsk.com.mynotes.di.qualifiers.ApplicationContext
import fsk.com.mynotes.di.scopes.ApplicationScope


/**
 * Module to provide the application
 */
@Module(
    includes = [
        ApplicationModule.ContextModule::class
    ]
)
interface ApplicationModule {
    @Binds
    fun bindApplication(app: CustomApplication): Application

    @Module
    class ContextModule {
        @ApplicationScope
        @ApplicationContext
        @Provides
        fun provideContext(application: Application): Context =
            application.applicationContext
    }
}