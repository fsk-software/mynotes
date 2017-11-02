package com.fsk.mynotes.presentation.components;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.fsk.common.Versions;
import com.fsk.mynotes.R;
import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.data.Note;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;


@RunWith(AndroidJUnit4.class)
public class NoteEditOptionsBarTest {

    private Context mContext;


    @Before
    public void prepareForTest() {
        mContext = InstrumentationRegistry.getTargetContext();
    }


    @Test
    public void testConstructors() {
        validateConstruction(new NoteEditOptionsBar(mContext));
        validateConstruction(new NoteEditOptionsBar(mContext, null));
        validateConstruction(new NoteEditOptionsBar(mContext, null, 0));

        if (Versions.isAtLeastLollipop()) {
            validateConstruction(new NoteEditOptionsBar(mContext, null, 0, 0));
        }
    }


    @Test
    public void testUpdatingUiForPersistedCleanNoteWithNoText() throws Exception {
        final NoteEditOptionsBar unitUnderTest = new NoteEditOptionsBar(mContext);
        Note note = new Note.Builder().setId(1).build();
        unitUnderTest.updateUiForNote(note);

        assertThat(unitUnderTest.mSaveImageSwitcher.isClickable(), is(false));

    }

    @Test
    public void testUpdatingUiForPersistedDirtyNoteWithNoText() throws Exception {
        final NoteEditOptionsBar unitUnderTest = new NoteEditOptionsBar(mContext);
        Note note = new Note.Builder().setId(1).build();
        note.setColor(NoteColor.GREEN);
        unitUnderTest.updateUiForNote(note);

        assertThat(unitUnderTest.mSaveImageSwitcher.isClickable(), is(false));

    }



    @Test
    public void testUpdatingUiForPersistedDirtyNoteWithText() throws Exception {
        final NoteEditOptionsBar unitUnderTest = new NoteEditOptionsBar(mContext);
        Note note = new Note.Builder().setText("hello").setId(1).build();
        note.setColor(NoteColor.GREEN);
        unitUnderTest.updateUiForNote(note);

        assertThat(unitUnderTest.mSaveImageSwitcher.isClickable(), is(true));

    }


    @Test
    public void testUpdatingUiForUnpersistedCleanNoteWithNoText() throws Exception {
        final NoteEditOptionsBar unitUnderTest = new NoteEditOptionsBar(mContext);
        Note note = new Note.Builder().build();
        unitUnderTest.updateUiForNote(note);

        assertThat(unitUnderTest.mSaveImageSwitcher.isClickable(), is(false));

    }

    @Test
    public void testUpdatingUiForUnpersistedDirtyNoteWithNoText() throws Exception {
        final NoteEditOptionsBar unitUnderTest = new NoteEditOptionsBar(mContext);
        Note note = new Note.Builder().build();
        note.setColor(NoteColor.GREEN);
        unitUnderTest.updateUiForNote(note);

        assertThat(unitUnderTest.mSaveImageSwitcher.isClickable(), is(false));

    }



    @Test
    public void testUpdatingUiForUnpersistedDirtyNoteWithText() throws Exception {
        final NoteEditOptionsBar unitUnderTest = new NoteEditOptionsBar(mContext);
        Note note = new Note.Builder().setText("hello").build();
        note.setColor(NoteColor.GREEN);
        unitUnderTest.updateUiForNote(note);

        assertThat(unitUnderTest.mSaveImageSwitcher.isClickable(), is(true));

    }


    @Test
    public void testOnSaveClick() {
        final NoteEditOptionsBar unitUnderTest = new NoteEditOptionsBar(mContext);
        View view = unitUnderTest.findViewById(R.id.component_note_edit_options_bar_save);
        unitUnderTest.onSaveClicked(view);

        LocalOnPersistenceClickedListener listener = new LocalOnPersistenceClickedListener();
        unitUnderTest.setOnPersistenceClickListener(listener);
        unitUnderTest.onSaveClicked(view);

        assertThat(listener.mSaveCallbackReceived, is(true));
        assertThat(listener.mDeleteCallbackReceived, is(false));
    }




    @Test
    public void testOnDeleteClick() {
        final NoteEditOptionsBar unitUnderTest = new NoteEditOptionsBar(mContext);
        View view = unitUnderTest.findViewById(R.id.component_note_edit_options_bar_purge);
        unitUnderTest.purgeButtonClicked(view);

        LocalOnPersistenceClickedListener listener = new LocalOnPersistenceClickedListener();
        unitUnderTest.setOnPersistenceClickListener(listener);
        unitUnderTest.purgeButtonClicked(view);

        assertThat(listener.mSaveCallbackReceived, is(false));
        assertThat(listener.mDeleteCallbackReceived, is(true));
    }


    @Test
    public void testOnPaletteClick() {
        final NoteEditOptionsBar unitUnderTest = new NoteEditOptionsBar(mContext);
        View view = unitUnderTest.findViewById(R.id.activity_note_edit_options_bar_color_palette);
        unitUnderTest.paletteButtonClicked(view);

        LocalOnPaletteClickListener listener = new LocalOnPaletteClickListener();
        unitUnderTest.setOnPaletteClickListener(listener);
        unitUnderTest.paletteButtonClicked(view);

        assertThat(listener.mCallbackReceived, is(true));
    }


    private void validateConstruction(NoteEditOptionsBar unitUnderTest) {
        assertThat(unitUnderTest.mOnPaletteClickListener, is(nullValue()));
        assertThat(unitUnderTest.mOnPersistenceClickListener, is(nullValue()));
        assertThat(unitUnderTest.mPurgeImageSwitcher, is(notNullValue()));
        assertThat(unitUnderTest.mSaveImageSwitcher, is(notNullValue()));
        assertThat(unitUnderTest.mViewFactory, is(notNullValue()));
    }


    private class LocalOnPaletteClickListener
            implements NoteEditOptionsBar.OnPaletteClickListener {

        boolean mCallbackReceived = false;


        @Override
        public void onPaletteClicked() {
            mCallbackReceived = true;
        }
    }

    private class LocalOnPersistenceClickedListener implements NoteEditOptionsBar.OnPersistenceClickListener {
        boolean mSaveCallbackReceived = false;
        boolean mDeleteCallbackReceived = false;


        @Override
        public void onSaveClicked() {
            mSaveCallbackReceived = true;
        }


        @Override
        public void onPurgeClicked() {
            mDeleteCallbackReceived = true;
        }
    }
}