package fsk.com.koin.mynotes.di

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import fsk.com.koin.mynotes.data.NoteColor
import org.koin.dsl.module

/**
 * Module to provide the serialization objects
 */
internal val serializationModule = module {
    single<Moshi> {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single<JsonAdapter<Set<NoteColor>>> {
        get<Moshi>().adapter(
            Types.newParameterizedType(
                Set::class.java,
                NoteColor::class.java
            )
        )
    }
}