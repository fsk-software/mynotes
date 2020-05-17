package fsk.com.mynotes.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import fsk.com.mynotes.data.preferences.AppPreferences
import fsk.com.mynotes.di.scopes.ApplicationScope


@Module
class SharedPreferenceModule {

    @Provides
    @ApplicationScope
    fun providesSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(AppPreferences.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)

    @Provides
    @ApplicationScope
    fun providesAppPreferences(
        sharedPreferences: SharedPreferences,
        gson: Gson
    ): AppPreferences = AppPreferences(sharedPreferences, gson)

}