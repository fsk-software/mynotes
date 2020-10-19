package fsk.com.koin.mynotes

import android.app.Application
import fsk.com.koin.mynotes.di.applicationModule
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

import timber.log.Timber


/**
 * Custom Application
 */
class CustomApplication : Application() {

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

        startKoin {
            androidContext(this@CustomApplication)
            modules(applicationModule)
        }
    }
}