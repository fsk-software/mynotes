package fsk.com.mynotes.di.modules

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import fsk.com.mynotes.di.scopes.ApplicationScope


@Module
class SerializationModule {

    @Provides
    @ApplicationScope
    fun providesGson(): Gson = Gson()
}