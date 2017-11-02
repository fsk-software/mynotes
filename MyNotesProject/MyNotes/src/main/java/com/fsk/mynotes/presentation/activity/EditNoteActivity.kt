package com.fsk.mynotes.presentation.activity;


import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import com.fsk.common.presentation.SimpleTextWatcher
import com.fsk.common.presentation.animations.crossBlendColorResource
import com.fsk.mynotes.R
import com.fsk.mynotes.constants.NOTE_KEY_EXTRA
import com.fsk.mynotes.constants.NoteColor
import com.fsk.mynotes.constants.getNoteColorAt
import com.fsk.mynotes.data.Note
import com.fsk.mynotes.presentation.adapters.NotePaletteAdapter
import com.fsk.mynotes.services.startDeleteNoteService
import com.fsk.mynotes.services.startSaveNoteService
import kotterknife.bindView
import android.util.DisplayMetrics




/**
 * Create an intent for this activity that will allow a new note.
 *
 * @param context
 *         The context to use for creating the intent.
 *
 * @return An intent that will start this activity.
 */
fun Context.createEditNoteActivityIntent(): Intent {
    return createEditNoteActivityIntent(Note(-1, "", NoteColor.YELLOW))
}

/**
 * Create an intent for this activity that will allow an existing note to be modified.
 *
 * @param context
 *         The context to use for creating the intent.
 * @param note
 *         The note to edit.
 *
 * @return An intent that will start this activity.
 */
fun Context.createEditNoteActivityIntent(
        note: Note): Intent {
    return Intent(this, EditNoteActivity::class.java).apply {
        putExtra(NOTE_KEY_EXTRA, note);
    }
}

/**
 * This activity is responsible for providing the UI to the user that allows them to modify or
 * create a new {@link Note}.
 */
class EditNoteActivity() : AppCompatActivity() {


    private val deleteButton: ImageView by bindView(R.id.activity_single_note_delete)
    private val paletteSpinner: Spinner by bindView(R.id.activity_single_note_spinner)
    private val headerView: View by bindView(R.id.activity_note_edit_options_bar_header)
    private val editText: EditText by bindView(R.id.activity_single_note_edit_text)

    /**
     * The duration in milliseconds to cross-blend the note colors.
     */
    private var colorShiftDuration: Int = 0;


    /**
     * The note being edited.
     */
    private lateinit var note: Note;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_single_note);
        adjustWindowBounds();

        colorShiftDuration = resources.getInteger(android.R.integer.config_mediumAnimTime);

        val bundle = if (savedInstanceState == null) intent.extras
        else savedInstanceState;
        note = if (bundle.containsKey(NOTE_KEY_EXTRA)) bundle.getParcelable(NOTE_KEY_EXTRA)
        else Note(-1, "", NoteColor.YELLOW);

        deleteButton.setOnClickListener { handleDeleteNoteRequest() }

        paletteSpinner.adapter = NotePaletteAdapter(this);

        paletteSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?,
                                        p1: View?,
                                        p2: Int,
                                        p3: Long) {
                changeColor(getNoteColorAt(p2));
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        });


        editText.setText(note.text);
        editText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {

                note.text = s.toString();
            }
        })

        ViewCompat.setTransitionName(editText, note.id.toString());
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(NOTE_KEY_EXTRA, note);
    }


    override fun onDestroy() {
        saveNote();
        super.onDestroy();
    }

    private fun adjustWindowBounds() {
        val widthPercentage = resources.getFraction(R.fraction.edit_note_container_width, 1, 1);
        val heightPercentage = resources.getFraction(R.fraction.edit_note_container_height, 1, 1);

        val metrics = resources.displayMetrics;
        window.setLayout((metrics.widthPixels * widthPercentage).toInt(),
                         (metrics.heightPixels * heightPercentage).toInt());
    }

    /**
     * Animate the UI color change based on the note colors.
     *
     * @param newColor
     *         the new color for the note.
     */
    private fun changeColor(newColor: NoteColor) {

        val oldColor = note.color;
        note.color = newColor;

        //change the toolbar color.
        headerView.crossBlendColorResource(oldColor.darkColorId,
                                           newColor.darkColorId,
                                           0,
                                           colorShiftDuration,
                                           null);

        //change the note body color.
        editText.crossBlendColorResource(oldColor.colorId,
                                         newColor.colorId,
                                         0,
                                         colorShiftDuration,
                                         null);
        val adapter = paletteSpinner.adapter as NotePaletteAdapter;
        adapter.setSelectedColor(newColor);
    }


    /**
     * Start the save note service and then finish the activity.
     */
    private fun saveNote() {
        startSaveNoteService(note);
    }


    /**
     * Start the delete note service and animate the note deletion.  The activity will finish when
     * the animation completes.
     */
    private fun deleteNote() {
        startDeleteNoteService(note);
        finish();
    }

    /**
     * Safely finish this activity.  If the build is at least lollipop, then the transition is
     * allowed to occur before the finish.
     */
    private fun safelyFinish() {
        if (!isFinishing) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            }
            else {
                finish();
            }
        }
    }

    /**
     * Handle a note deletion request. This will delete the note and finish the activity.
     *
     * @param view
     *         the clicked view.
     */
    private fun handleDeleteNoteRequest() {
        deleteNote();
        safelyFinish();
    }
}