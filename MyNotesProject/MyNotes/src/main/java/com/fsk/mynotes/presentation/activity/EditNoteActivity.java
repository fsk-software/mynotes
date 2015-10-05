package com.fsk.mynotes.presentation.activity;


import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fsk.common.presentation.SimpleTextWatcher;
import com.fsk.common.presentation.utils.animations.BackgroundAnimatorHelper;
import com.fsk.common.presentation.utils.animations.SimpleAnimatorListener;
import com.fsk.common.versions.Versions;
import com.fsk.common.utils.Preconditions;
import com.fsk.mynotes.R;
import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.constants.NoteExtraKeys;
import com.fsk.mynotes.data.Note;
import com.fsk.mynotes.presentation.components.NoteEditColorPalette;
import com.fsk.mynotes.presentation.components.NoteEditOptionsBar;
import com.fsk.mynotes.presentation.components.NoteEditToolbar;
import com.fsk.mynotes.services.DeleteNoteService;
import com.fsk.mynotes.services.SaveNoteService;


import java.util.Observable;
import java.util.Observer;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * This activity is responsible for providing the UI to the user that allows them to modify or
 * create a new {@link Note}.
 */
public class EditNoteActivity extends AppCompatActivity implements Observer {

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
    public static Intent createIntentForExistingNote(@NonNull Context context, @NonNull Note note) {
        Preconditions.checkNotNull(note);

        Intent intent = new Intent(context, EditNoteActivity.class);
        intent.putExtra(NoteExtraKeys.NOTE_KEY, note);
        return intent;
    }


    /**
     * Create an intent for this activity that will create a new note..
     *
     * @param context
     *         The context to use for creating the intent.
     *
     * @return An intent that will start this activity.
     */
    public static Intent createIntentForNewNote(@NonNull Context context) {
        Preconditions.checkNotNull(context);
        return new Intent(context, EditNoteActivity.class);
    }


    @InjectView(R.id.activity_single_note_toolbar)
    NoteEditToolbar mToolbar;


    @InjectView(R.id.activity_single_note_edit_text)
    EditText mEditText;


    @InjectView(R.id.activity_single_note_edit_container)
    View mNoteContainerView;


    /**
     * The duration in milliseconds to cross-blend the note colors.
     */
    int mColorShiftDuration;


    /**
     * The note being edited.
     */
    Note mNote;


    /**
     * The listener to any text being altered in {@link #mEditText}.
     */
    final TextWatcher mTextWatcher = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(final Editable s) {

            mNote.setText(s.toString());
        }
    };


    /**
     * A listener to modify the UI coloring when the User changes the note color.
     */
    final NoteEditColorPalette.OnColorSelectedListener mOnColorSelectedListener =
            new NoteEditColorPalette.OnColorSelectedListener() {

                @Override
                public void onColorSelected(@NonNull final NoteColor color) {
                    changeColor(color);
                }
            };


    /**
     * A listener to react to user requests to change the persistency of the note (saving or
     * deleting).
     */
    final NoteEditOptionsBar.OnPersistenceClickListener mOnPersistenceClickListener =
            new NoteEditOptionsBar.OnPersistenceClickListener() {

                @Override
                public void onSaveClicked() {
                    saveNote();
                }


                @Override
                public void onPurgeClicked() {
                    deleteNote();
                }
            };


    /**
     * A runnable that will update the toolbar.  This exists to allow the update to handle on the UI
     * thread.
     */
    final Runnable mUpdateToolbarRunnable = new Runnable() {
        @Override
        public void run() {
            mToolbar.updateNote(mNote);
        }
    };


    /**
     * An Animator listener that will finish the activity when the animator completes or is
     * cancelled.
     */
    private final SimpleAnimatorListener mFinishWhenAnimationCompleteListener =
            new SimpleAnimatorListener() {


                @Override
                public void onAnimationEnd(final Animator animation) {
                    super.onAnimationEnd(animation);
                    safelyFinish();
                }


                @Override
                public void onAnimationCancel(final Animator animation) {
                    super.onAnimationCancel(animation);
                    safelyFinish();
                }
            };


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_note);
        ButterKnife.inject(this);

        mColorShiftDuration = getResources().getInteger(android.R.integer.config_mediumAnimTime);

        initializeNote((savedInstanceState == null) ? getIntent().getExtras() : savedInstanceState);
        mNote.addObserver(this);

        mToolbar.setOnColorSelectedListener(mOnColorSelectedListener);
        mToolbar.setOnPersistenceClickListener(mOnPersistenceClickListener);
        mToolbar.updateNote(mNote);

        changeColor(mNote.getColor());
        mEditText.setText(mNote.getText());
        mEditText.addTextChangedListener(mTextWatcher);

        ViewCompat.setTransitionName(mEditText, Long.toString(mNote.getId()));
    }


    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(NoteExtraKeys.NOTE_KEY, mNote);
    }


    @Override
    public void update(final Observable observable, final Object data) {
        mToolbar.post(mUpdateToolbarRunnable);
    }


    /**
     * Initialize the note based on the bundle data.
     *
     * @param bundle
     *         the bundle containing the note data.
     */
    void initializeNote( Bundle bundle) {
        mNote = null;

        //read the note data if the bundle and key exists.
        if ((bundle != null) && bundle.containsKey(NoteExtraKeys.NOTE_KEY)) {
            mNote = bundle.getParcelable(NoteExtraKeys.NOTE_KEY);
        }

        /**
         * No note exists, so create a new one.  If anything fails, pack up your toys and go home.
         */
        if (mNote == null) {
            try {
                mNote = new Note.Builder().build();
            }
            catch (Exception e) {
                Toast.makeText(this, R.string.unrecoverable_edit_error, Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }


    /**
     * Animate the UI color change based on the note colors.
     *
     * @param newColor
     *         the new color for the note.
     */
    void changeColor(@NonNull NoteColor newColor) {
        NoteColor oldColor = mNote.getColor();
        mNote.setColor(newColor);

        //change the color of the edit text.
        BackgroundAnimatorHelper.crossBlendColorResource(mEditText, oldColor.colorResourceId,
                                                         newColor.colorResourceId, 0,
                                                         mColorShiftDuration, null);

        //change the border color around the edit text.
        BackgroundAnimatorHelper
                .crossBlendColorResource(mNoteContainerView, oldColor.darkColorResourceId,
                                         newColor.darkColorResourceId, 0, mColorShiftDuration,
                                         null);

        //change the toolbar color.
        BackgroundAnimatorHelper.crossBlendColorResource(mToolbar, oldColor.darkColorResourceId,
                                                         newColor.darkColorResourceId, 0,
                                                         mColorShiftDuration, null);
    }


    /**
     * Start the save note service and then finish the activity.
     */
    void saveNote() {
        SaveNoteService.startService(this, mNote);
        safelyFinish();
    }


    /**
     * Start the delete note service and animate the note deletion.  The activity will finish when
     * the animation completes.
     */
    void deleteNote() {

        NoteColor noteColor = mNote.getColor();
        BackgroundAnimatorHelper
                .crossBlendColorResource(mNoteContainerView, noteColor.darkColorResourceId,
                                         android.R.color.transparent, 0, mColorShiftDuration, null);
        mEditText.animate().alpha(0f).setStartDelay(mColorShiftDuration)
                 .setDuration(mColorShiftDuration).setListener(mFinishWhenAnimationCompleteListener)
                 .start();

        DeleteNoteService.startService(this, mNote);
    }


    /**
     * Safely finish this activity.  If the build is at least lollipop, then the transition is
     * allowed to occur before the finish.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void safelyFinish() {
        if (Versions.isAtLeastLollipop()) {
            finishAfterTransition();
        }
        else {
            finish();
        }
    }
}