package fsk.com.koin.mynotes.ui.activities.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fsk.com.koin.mynotes.R

/**
 * The primary activity.  It provides a list of notes and the tools to manage them.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}