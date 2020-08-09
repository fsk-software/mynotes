package fsk.com.mynotes.ui.main

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import fsk.com.mynotes.R

/**
 * The primary activity.  It provides a list of notes and the tools to manage them.
 */
class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}