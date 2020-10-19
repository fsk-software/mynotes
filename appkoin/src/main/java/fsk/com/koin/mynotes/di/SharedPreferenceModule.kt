package fsk.com.koin.mynotes.di

import android.content.Context
import android.content.SharedPreferences
import fsk.com.koin.mynotes.data.preferences.NoteFilterPreferences
import org.koin.dsl.module

/**
 * Module to support the shared preferences
 */
internal val sharedPreferenceModule = module {
    single<SharedPreferences> {
        get<Context>()
            .getSharedPreferences(
                NoteFilterPreferences.SHARED_PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )
    }

    single {
        NoteFilterPreferences(get(), get())
    }
}