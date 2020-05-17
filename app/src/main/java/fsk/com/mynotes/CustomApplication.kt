package fsk.com.mynotes

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import fsk.com.mynotes.di.components.DaggerAppComponent

import timber.log.Timber


/*
 * Custom Application for injecting via dagger.
 * */
class CustomApplication: DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.factory().create(this)
}