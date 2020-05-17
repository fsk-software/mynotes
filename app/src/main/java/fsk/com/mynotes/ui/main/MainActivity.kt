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

//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, NotesFragment.newInstance())
//                .commitNow()
//        }
    }

//
//    /**
//     * Edit the specified note.
//     *
//     * @param view
//     *         The clicked note view.
//     * @param note
//     *         the note to edit.
//     */
//    private fun editNote(view: View,
//                         note: Note) {
//        val notePair = Pair.create(view, note.id.toString())
//        val options =
//            ActivityOptionsCompat.makeSceneTransitionAnimation(this, notePair)
//        startActivity(createEditNoteActivityIntent(note), options.toBundle())
//    }
//

}