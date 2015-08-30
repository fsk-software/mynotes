package com.fsk.mynotes.presentation.activity;


import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;

import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.constants.NoteExtraKeys;
import com.fsk.mynotes.data.Note;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;


/**
 * Test {@link MainActivity}
 */
public class EditNoteActivityTest extends ActivityInstrumentationTestCase2<EditNoteActivity> {

    public EditNoteActivityTest() {
        super(EditNoteActivity.class);
    }


    public void testNewNoteIntentCreation() {
        Intent actual = EditNoteActivity.createIntentForNewNote(getActivity());
        assertThat(actual, is(notNullValue()));
        assertThat(actual.getExtras(), is(nullValue()));
    }


    public void testNewNoteIntentCreationForNullContext() {
        try {
            EditNoteActivity.createIntentForNewNote(null);
            assert false;
        }
        catch (NullPointerException e) {
        }
    }


    public void testExistingNoteIntentCreationForNullNote() {
        try {
            EditNoteActivity.createIntentForExistingNote(getActivity(), null);
            assert false;
        }
        catch (NullPointerException e) {
        }
    }


    public void testExistingNoteIntentCreationForNullContext() throws Exception {
        try {
            EditNoteActivity.createIntentForExistingNote(null, new Note.Builder().build());
            assert false;
        }
        catch (NullPointerException e) {
        }
    }


    public void testDefaultSetup() {
        EditNoteActivity unitUnderTest = getActivity();
        assertThat(unitUnderTest.mEditText, is(notNullValue()));
        assertThat(unitUnderTest.mNote, is(notNullValue()));
        assertThat(unitUnderTest.mNoteContainerView, is(notNullValue()));
        assertThat(unitUnderTest.mToolbar, is(notNullValue()));
        assertThat(unitUnderTest.mUpdateToolbarRunnable, is(notNullValue()));
        assertThat(unitUnderTest.mTextWatcher, is(notNullValue()));
        assertThat(unitUnderTest.mOnColorSelectedListener, is(notNullValue()));
        assertThat(unitUnderTest.mOnPersistenceClickListener, is(notNullValue()));

        assertThat(unitUnderTest.mColorShiftDuration, is(unitUnderTest.getResources().getInteger(
                android.R.integer.config_mediumAnimTime)));

        assertThat(unitUnderTest.mNote.getText(), is(""));
        assertThat(unitUnderTest.mNote.getColor(), is(NoteColor.YELLOW));
        assertThat(unitUnderTest.mNote.getId(), is(-1L));
    }


    public void testOnSaveInstance() throws Exception {
        final Note note= new Note.Builder().setText("test").setId(2).setColor(NoteColor.GREEN)
                                     .build();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                final EditNoteActivity unitUnderTest = getActivity();

                unitUnderTest.mNote = note;
                Bundle testBundle = new Bundle();
                unitUnderTest.onSaveInstanceState(testBundle);

                assertThat(testBundle.containsKey(NoteExtraKeys.NOTE_KEY), is(true));
                Note actual = testBundle.getParcelable(NoteExtraKeys.NOTE_KEY);
                assertThat(unitUnderTest.mNote, is(actual));
            }
        });
    }


    public void testUpdate() throws Exception {
        EditNoteActivity unitUnderTest = getActivity();
        unitUnderTest.update(unitUnderTest.mNote, unitUnderTest.mNote);
        //I really don't have anything to validate here.
    }


    public void testInitializeNoteForNullBundle() throws Exception {
        EditNoteActivity unitUnderTest = getActivity();
        unitUnderTest.initializeNote(null);

        assertThat(unitUnderTest.mNote.getText(), is(""));
        assertThat(unitUnderTest.mNote.getColor(), is(NoteColor.YELLOW));
        assertThat(unitUnderTest.mNote.getId(), is(-1L));
    }


    public void testInitializeNoteForEmptyBundle() throws Exception {
        EditNoteActivity unitUnderTest = getActivity();
        unitUnderTest.initializeNote(new Bundle());

        assertThat(unitUnderTest.mNote.getText(), is(""));
        assertThat(unitUnderTest.mNote.getColor(), is(NoteColor.YELLOW));
        assertThat(unitUnderTest.mNote.getId(), is(-1L));
    }


    public void testInitializeNoteForNoteBundle() throws Exception {
        EditNoteActivity unitUnderTest = getActivity();
        Note expected =
                new Note.Builder().setId(1).setText("test").setColor(NoteColor.GREEN).build();
        Bundle bundle = new Bundle();
        bundle.putParcelable(NoteExtraKeys.NOTE_KEY, expected);
        unitUnderTest.initializeNote(bundle);

        assertThat(unitUnderTest.mNote.equals(expected), is(true));
    }


    public void testTextWatcher() throws Exception {
        final EditNoteActivity unitUnderTest = getActivity();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                unitUnderTest.mEditText.setText("test");
                assertThat(unitUnderTest.mNote.getText(), is("test"));
            }
        });
    }

            public void testChangeColor() throws Exception {
                EditNoteActivity unitUnderTest = getActivity();
                for (NoteColor color : NoteColor.values()) {
                    unitUnderTest.changeColor(color);
                    assertThat(color, is(unitUnderTest.mNote.getColor()));
                }
            }


            public void testColorSelectListener() throws Exception {
                EditNoteActivity unitUnderTest = getActivity();
                for (NoteColor color : NoteColor.values()) {
                    unitUnderTest.mOnColorSelectedListener.onColorSelected(color);
                    assertThat(color, is(unitUnderTest.mNote.getColor()));
                }
            }


            public void testSaveNoteListener() throws Exception {
                EditNoteActivity unitUnderTest = getActivity();
                unitUnderTest.mOnPersistenceClickListener.onSaveClicked();
                assertThat(unitUnderTest.isFinishing(), is(true));
            }


            public void testSaveNote() throws Exception {
                EditNoteActivity unitUnderTest = getActivity();
                unitUnderTest.saveNote();
                assertThat(unitUnderTest.isFinishing(), is(true));
            }


            public void testDeleteNoteListener() throws Exception {

                final EditNoteActivity unitUnderTest = getActivity();
                unitUnderTest.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        unitUnderTest.mOnPersistenceClickListener.onDeleteClicked();
                    }
                });

                try {
                    Thread.sleep(3000);
                }
                catch (InterruptedException e) {
                }

                assertThat(unitUnderTest.isFinishing(), is(true));
            }


            public void testDeleteNote() throws Exception {

                final EditNoteActivity unitUnderTest = getActivity();
                unitUnderTest.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        unitUnderTest.deleteNote();
                    }
                });
                        try {
                            Thread.sleep(3000);
                        }
                        catch (InterruptedException e) {
                        }

                        assertThat(unitUnderTest.isFinishing(), is(true));
                    }
                }
