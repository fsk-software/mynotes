package com.fsk.mynotes.presentation.activity;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.fsk.common.presentation.utils.animations.BackgroundAnimatorHelper;
import com.fsk.common.versions.Versions;
import com.fsk.mynotes.R;
import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.constants.NoteExtraKeys;
import com.fsk.mynotes.data.Note;
import com.fsk.mynotes.presentation.fragments.NoteEditColorPickerFragment;
import com.fsk.mynotes.presentation.fragments.NoteEditMainToolbarFragment;
import com.fsk.mynotes.services.DeleteNoteService;
import com.fsk.mynotes.services.SaveNoteService;
import com.fsk.mynotes.utils.NoteEditor;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * The activity to edit a note.
 */
public class EditNoteActivity extends AppCompatActivity implements NoteEditor {


    /**
     * The tags for the availbel fragments for this activity.
     */
    private static class FragmentTags {
        /**
         * The tag for the main toolbar.
         */
        private static final String MAIN_TOOLBAR_TAG = "MAIN_TOOLBAR_TAG";


        /**
         * The tag for the color picker toolbar.
         */
        private static final String COLOR_PICKER_TOOLBAR_TAG = "COLOR_PICKER_TOOLBAR_TAG";

    }


    /**
     * Create an intent for this activity.
     *
     * @param context
     *         The context to use for creating the intent.
     * @param note
     *         The note to edit.
     *
     * @return An intent that will start this activity.
     */
    public static Intent createIntent(@NonNull Context context, @NonNull Note note) {

        Intent returnValue = new Intent(context, EditNoteActivity.class);
        returnValue.putExtra(NoteExtraKeys.NOTE_KEY, note);

        return returnValue;
    }


    @InjectView(R.id.activity_single_note_edit_text)
    EditText mEditText;


    @InjectView(R.id.activity_single_note_edit_container)
    View mBorderView;


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
    final View.OnKeyListener mOnKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(final View v, final int keyCode, final KeyEvent event) {

            return false;
        }
    };


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_note);
        ButterKnife.inject(this);

        mColorShiftDuration = getResources().getInteger(android.R.integer.config_mediumAnimTime);
        mNote = getIntent().getParcelableExtra(NoteExtraKeys.NOTE_KEY);
        if (mNote == null) {
            mNote = new Note();
        }

        changeNoteColor(mNote.getColor());
        mEditText.setText(mNote.getText());

        ViewCompat.setTransitionName(mEditText, Long.toString(mNote.getId()));
        if (savedInstanceState == null) {
            showEditOptions();
        }
    }


    @Override
    public Note getNote() {
        return mNote;
    }


    @Override
    public void changeNoteColor(final NoteColor color) {
        NoteColor oldColor = mNote.getColor();
        mNote.setColor(color);

        BackgroundAnimatorHelper
                .crossBlendColorResource(mEditText, oldColor.colorResourceId, color.colorResourceId,
                                         mColorShiftDuration, null);

        BackgroundAnimatorHelper.crossBlendColorResource(mBorderView, oldColor.darkColorResourceId,
                                                         color.darkColorResourceId,
                                                         mColorShiftDuration, null);
    }


    @Override
    public void saveNote() {
        mNote.setText(mEditText.getText().toString());
        SaveNoteService.startService(this, mNote);
    }


    @Override
    public void deleteNote() {
        DeleteNoteService.startService(this, mNote);
        safelyFinish();
    }


    @Override
    public void showColorPicker() {
        showFragment(NoteEditColorPickerFragment.newInstance(mColorShiftDuration),
                     FragmentTags.COLOR_PICKER_TOOLBAR_TAG);
    }


    @Override
    public void showEditOptions() {
        showFragment(NoteEditMainToolbarFragment.newInstance(), FragmentTags.MAIN_TOOLBAR_TAG);
    }


    /**
     * Show the specified fragment in the container with the {@link R
     * .id#activity_single_note_header_container}
     * id.
     *
     * @param fragment
     *         The fragment to show.
     * @param tag
     *         the tag for the fragment.
     */
    private void showFragment(@NonNull Fragment fragment, @NonNull String tag) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_down, R.animator.slide_up);
        transaction.replace(R.id.activity_single_note_header_container, fragment, tag);
        transaction.commit();
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