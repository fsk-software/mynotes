package fsk.com.mynotes.di.modules

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import fsk.com.mynotes.data.NoteColor
import fsk.com.mynotes.di.scopes.ApplicationScope

/**
 * Module to provide the serialization objects
 */
@Module
class SerializationModule {

    @Provides
    @ApplicationScope
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @ApplicationScope
    fun providesNoteColorSetAdapter(moshi: Moshi): JsonAdapter<Set<NoteColor>> =
        moshi.adapter(
            Types.newParameterizedType(
                Set::class.java,
                NoteColor::class.java
            )
        )
}