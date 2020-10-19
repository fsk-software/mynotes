package fsk.com.koin.mynotes.di

import fsk.com.koin.mynotes.ui.activities.main.di.mainActivityModule

/**
 * Modules global to support the application.
 */
private val globalModule = databaseModule + serializationModule + sharedPreferenceModule
private val uiModule = mainActivityModule

val applicationModule = globalModule + uiModule


