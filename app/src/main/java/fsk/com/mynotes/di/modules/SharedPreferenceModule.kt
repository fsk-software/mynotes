package fsk.com.mynotes.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.JsonAdapter
import dagger.Module
import dagger.Provides
import fsk.com.mynotes.data.NoteColor
import fsk.com.mynotes.data.preferences.NoteFilterPreferences
import fsk.com.mynotes.di.qualifiers.ApplicationContext
import fsk.com.mynotes.di.scopes.ApplicationScope

/**
 * Module to support the shared preferences
 */
@Module
class SharedPreferenceModule {

    @Provides
    @ApplicationScope
    fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            NoteFilterPreferences.SHARED_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )

    @Provides
    @ApplicationScope
    fun providesAppPreferences(
        sharedPreferences: SharedPreferences,
        noteColorSetAdapter: JsonAdapter<Set<NoteColor>>
    ): NoteFilterPreferences = NoteFilterPreferences(sharedPreferences, noteColorSetAdapter)

}