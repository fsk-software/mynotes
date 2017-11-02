package com.fsk.mynotes.presentation.activity;


import android.app.LoaderManager
import android.content.Context
import android.content.CursorLoader
import android.content.Intent
import android.content.Loader
import android.database.Cursor
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.SparseArray
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import android.widget.ToggleButton
import com.fsk.common.presentation.recycler.DividerItemDecoration
import com.fsk.mynotes.R
import com.fsk.mynotes.constants.NoteColor
import com.fsk.mynotes.data.Note
import com.fsk.mynotes.data.cache.NoteFilterPreferences
import com.fsk.mynotes.data.getNotes
import com.fsk.mynotes.data.providers.NoteContentProvider.Companion.NOTE_CONTENT_URI
import com.fsk.mynotes.data.queryForNoteColors
import com.fsk.mynotes.presentation.adapters.CardAdapter
import kotterknife.bindView


/**
 * The primary activity.  It provides a list of notes and the tools to manage them.
 */
class MainActivity() : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * Create an intent for this activity that will allow an existing note to be modified.
     *
     * @param context
     *         The context to use for creating the intent.
     *
     * @return An intent that will start this activity.
     */
    fun Context.launchMainActivity(): Intent {
        return Intent(this, MainActivity::class.java);
    }

    /**
     * UI element to display the note data.
     */
    val cardsRecyclerView: RecyclerView by bindView(R.id.activity_main_notes_recycler_view)
    val addButton: FloatingActionButton by bindView(R.id.activity_main_add_view);
    val toolbar: Toolbar by bindView(R.id.activity_main_toolbar);


    /**
     * The identifier of the main loader.  This loads the color filtered note list.
     */
    private val MAIN_LOADER_ID = 0;


    /**
     * The Cache for the selected colors.
     */
    private lateinit var noteFilterPreferences : NoteFilterPreferences;


    /**
     * The color toggle buttons indexed by their resource ids.
     */
    private val toggleButtons = SparseArray<ToggleButton>();


    /**
     * The adapter to manage the display of cards in {@link #cardsRecyclerView}.
     */
    private val cardAdapter = CardAdapter();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteFilterPreferences = NoteFilterPreferences(this);

        addButton.setOnClickListener { createNewNote() };

        // Set a toolbar to replace the action bar.
        setSupportActionBar(toolbar);

        cardAdapter.onItemClickListener = object : CardAdapter.OnItemClickListener {

            override fun onItemClick(view: View,
                                     note: Note) {
                editNote(view, note);
            }
        };

        val resources = getResources();
        val columnCount = resources.getInteger(R.integer.card_column_count);
        val cardDividerDimen = resources.getDimension(R.dimen.note_divider_size);

        cardsRecyclerView.addItemDecoration(DividerItemDecoration(cardDividerDimen.toInt()));
        cardsRecyclerView.setLayoutManager(GridLayoutManager(this, columnCount));
        cardsRecyclerView.setAdapter(cardAdapter);

        NoteColor.values().forEach {
            val buttonId = getButtonIdForColor(it);
            val toggleButton = findViewById<ToggleButton>(buttonId);
            toggleButton.setTag(it);
            toggleButton.setChecked(noteFilterPreferences.isColorEnabled(it));
            toggleButton.setOnCheckedChangeListener(
                    object : CompoundButton.OnCheckedChangeListener {
                        override fun onCheckedChanged(buttonView: CompoundButton?,
                                                      isChecked: Boolean) {
                            val noteColor = buttonView?.tag as NoteColor;
                            noteFilterPreferences.enableColor(noteColor, isChecked);
                            getContentResolver().queryForNoteColors(
                                    noteFilterPreferences.getEnabledColors());
                        }
                    }
                                                   );
            toggleButton.setOnLongClickListener(object : View.OnLongClickListener {
                override fun onLongClick(view: View?): Boolean {
                    if (view != null) {
                        Toast.makeText(view.context, view.getContentDescription(),
                                       Toast.LENGTH_LONG).show()
                    }
                    return true;
                }
            });

            toggleButtons.put(buttonId, toggleButton);
        }

        loaderManager.initLoader(MAIN_LOADER_ID, Bundle(), this);
    }


    override fun onResume() {
        super.onResume();
        loaderManager.getLoader<Note>(MAIN_LOADER_ID).forceLoad();
    }


    override fun onCreateLoader(id: Int,
                                args: Bundle): Loader<Cursor> {
        return CursorLoader(this, NOTE_CONTENT_URI, null, null, null, null);
    }


    override fun onLoadFinished(loader: Loader<Cursor>,
                                data: Cursor) {
        cardAdapter.setNotes(data.getNotes().toList());
        cardAdapter.postNotifyDataSetChanged(cardsRecyclerView.getHandler());
    }


    override fun onLoaderReset(loader: Loader<Cursor>) {
        cardAdapter.setNotes(listOf<Note>());
    }


    private fun getButtonIdForColor(color: NoteColor): Int {
        when (color) {
            NoteColor.BLUE   ->
                return R.id.main_activity_color_selector_blue_button;
            NoteColor.GREEN  ->
                return R.id.main_activity_color_selector_green_button;
            NoteColor.GREY   ->
                return R.id.main_activity_color_selector_gray_button;
            NoteColor.PINK   ->
                return R.id.main_activity_color_selector_pink_button;
            NoteColor.PURPLE ->
                return R.id.main_activity_color_selector_purple_button;
            else             ->
                return R.id.main_activity_color_selector_yellow_button;
        }
    }


    /**
     * Edit the specified note.
     *
     * @param view
     *         The clicked note view.
     * @param note
     *         the note to edit.
     */
    private fun editNote(view: View,
                         note: Note) {
        val notePair = Pair.create(view, note.id.toString());
        val options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, notePair);
        startActivity(createEditNoteActivityIntent(note), options.toBundle());
    }


    /**
     * Launch a UI that allows the user to create a new note.
     */
    private fun createNewNote() {
        startActivity(createEditNoteActivityIntent());
    }
}