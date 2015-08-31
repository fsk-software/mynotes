package com.fsk.mynotes.presentation.components;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.fsk.common.versions.Versions;
import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.data.Note;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


@RunWith(AndroidJUnit4.class)
public class NoteEditToolBarTest {

    private Context mContext;


    @Before
    public void prepareForTest() {
        mContext = InstrumentationRegistry.getTargetContext();
    }


    @Test
    public void testConstructors() {

        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                validateConstruction(new NoteEditToolbar(mContext));
                validateConstruction(new NoteEditToolbar(mContext, null));
                validateConstruction(new NoteEditToolbar(mContext, null, 0));

                if (Versions.isAtLeastLollipop()) {
                    validateConstruction(new NoteEditToolbar(mContext, null, 0, 0));
                }
            }
        });
}


            @Test
            public void testUpdateNote() throws Exception {

                InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
                    @Override
                    public void run(){
                        try {
                            final NoteEditToolbar unitUnderTest = new NoteEditToolbar(mContext);
                            Note note = new Note.Builder().setId(1).setText("hi").build();
                            note.setColor(NoteColor.GREEN);
                            unitUnderTest.updateNote(note);

                            assertThat(unitUnderTest.mNoteEditOptionsBar.mSaveImageSwitcher
                                               .isClickable(), is(true));
                            note.setText("");

                            unitUnderTest.updateNote(note);
                            assertThat(unitUnderTest.mNoteEditOptionsBar.mSaveImageSwitcher
                                               .isClickable(), is(false));
                        }
                        catch (Exception e) {
                            assert false;
                        }
                    }
                });
            }


            @Test
            public void testSetListeners() throws Exception {

                InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
                    @Override
                    public void run() {
                        final NoteEditToolbar unitUnderTest = new NoteEditToolbar(mContext);
                        NoteEditOptionsBar.OnPersistenceClickListener persistenceClickListener = new LocalOnPersistenceClickedListener();
                        unitUnderTest.setOnPersistenceClickListener(persistenceClickListener);

                        NoteEditColorPalette.OnColorSelectedListener colorSelectedListener = new LocalOnColorSelectedListener();
                        unitUnderTest.setOnColorSelectedListener(colorSelectedListener);

                        assertThat(unitUnderTest.mNoteEditOptionsBar.mOnPersistenceClickListener,
                                   is(persistenceClickListener));
                        assertThat(unitUnderTest.mNoteEditColorPalette.mOnColorSelectedListener,
                                   is(colorSelectedListener));

                    }
                });
            }


            @Test
            public void testModeSwitch() throws Exception {

                InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
                    @Override
                    public void run() {
                        final NoteEditToolbar unitUnderTest = new NoteEditToolbar(mContext);
                        assertThat(unitUnderTest.mViewFlipper.getDisplayedChild(),
                                   is(NoteEditToolbar.Mode.OPTIONS_BAR.ordinal()));

                        unitUnderTest.mOnColorPaletteClickListener.onPaletteClicked();

                        assertThat(unitUnderTest.mViewFlipper.getDisplayedChild(),
                                   is(NoteEditToolbar.Mode.PALETTE_BAR.ordinal()));

                        unitUnderTest.mOnColorPaletteDoneClickListener.onDoneClicked();

                        assertThat(unitUnderTest.mViewFlipper.getDisplayedChild(),
                                   is(NoteEditToolbar.Mode.OPTIONS_BAR.ordinal()));
                    }
                });
            }


            private void validateConstruction(NoteEditToolbar unitUnderTest) {
                assertThat(unitUnderTest.mNoteEditColorPalette, is(notNullValue()));
                assertThat(unitUnderTest.mNoteEditOptionsBar, is(notNullValue()));
                assertThat(unitUnderTest.mOnColorPaletteClickListener, is(notNullValue()));
                assertThat(unitUnderTest.mOnColorPaletteDoneClickListener, is(notNullValue()));
                assertThat(unitUnderTest.mViewFlipper.getDisplayedChild(),
                           is(NoteEditToolbar.Mode.OPTIONS_BAR.ordinal()));
            }


            private class LocalOnColorSelectedListener
                    implements NoteEditColorPalette.OnColorSelectedListener {


                @Override
                public void onColorSelected(@NonNull final NoteColor color) {
                }
            }


            private class LocalOnPersistenceClickedListener
                    implements NoteEditOptionsBar.OnPersistenceClickListener {


                @Override
                public void onSaveClicked() {

                }


                @Override
                public void onDeleteClicked() {

                }
            }
        }