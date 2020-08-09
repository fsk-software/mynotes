package fsk.com.mynotes

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import fsk.com.mynotes.di.components.DaggerAppComponent
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins

import timber.log.Timber


/**
 * Custom Application to assist in Dagger injections
 */
class CustomApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        //the global rxJava error handler.   It just logs all exceptions and prevents the crash.
        RxJavaPlugins.setErrorHandler { t ->
            if (t is UndeliverableException) {
                Timber.e(t.cause, "Undeliverable exception")
            } else {
                Timber.e(t)
            }
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.factory().create(this)
}