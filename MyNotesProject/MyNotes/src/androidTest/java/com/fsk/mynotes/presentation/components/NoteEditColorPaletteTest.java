package com.fsk.mynotes.presentation.components;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.UiThreadTest;
import android.view.View;

import com.fsk.common.versions.Versions;
import com.fsk.mynotes.R;
import com.fsk.mynotes.constants.NoteColor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;


@RunWith(AndroidJUnit4.class)
public class NoteEditColorPaletteTest {

    private Context mContext;


    @Before
    public void prepareForTest() {
        mContext = InstrumentationRegistry.getTargetContext();
    }


    @Test
    public void testConstructors() {
        validateConstruction(new NoteEditColorPalette(mContext));
        validateConstruction(new NoteEditColorPalette(mContext, null));
        validateConstruction(new NoteEditColorPalette(mContext, null, 0));
        if (Versions.isAtLeastLollipop()) {
            validateConstruction(new NoteEditColorPalette(mContext, null, 0, 0));
        }
    }


    @Test
    public void testThatRightButtonIsReturnedForColor() {
        NoteEditColorPalette unitUnderTest = new NoteEditColorPalette(mContext);

        for (NoteColor noteColor : NoteColor.values()) {
            int expectedResourceId = 0;
            switch (noteColor) {
                case BLUE:
                    expectedResourceId = R.id.color_picker_blue_button;
                    break;
                case GREEN:
                    expectedResourceId = R.id.color_picker_green_button;
                    break;
                case GREY:
                    expectedResourceId = R.id.color_picker_gray_button;
                    break;
                case PINK:
                    expectedResourceId = R.id.color_picker_pink_button;
                    break;
                case PURPLE:
                    expectedResourceId = R.id.color_picker_purple_button;
                    break;
                case YELLOW:
                    expectedResourceId = R.id.color_picker_yellow_button;
                    break;
            }
            assertThat(unitUnderTest.mRadioButtons.get(noteColor).getId(),
                       is(expectedResourceId));
        }
    }


    @Test
    public void testDoneButtonClicks() {
        final NoteEditColorPalette unitUnderTest = new NoteEditColorPalette(mContext);
        View view = unitUnderTest.findViewById(R.id.component_note_edit_color_palette_done);
        unitUnderTest.doneButtonClicked(view);

        LocalOnDoneClickedListener listener = new LocalOnDoneClickedListener();
        unitUnderTest.setOnDoneClickListener(listener);
        unitUnderTest.doneButtonClicked(view);
    }


    @Test
    public void testUpdatingColorWithListener() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                final NoteEditColorPalette unitUnderTest = new NoteEditColorPalette(mContext);

                for (NoteColor color : NoteColor.values()) {
                    LocalOnColorSelectedListener listener = new LocalOnColorSelectedListener(color);
                    unitUnderTest.setOnColorSelectedListener(listener);

                    assertThat(unitUnderTest.mOnColorSelectedListener,
                               is((NoteEditColorPalette.OnColorSelectedListener) listener));

                    unitUnderTest.updateSelectedColor(color);
                    assertThat(listener.mCallbackReceived, is(true));
                    assertThat(unitUnderTest.mRadioButtons.get(color).isChecked(), is(true));
                }
            }
        });
    }


    @Test
    @UiThreadTest
    public void testUpdatingColorWithoutListener() {

        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                final NoteEditColorPalette unitUnderTest = new NoteEditColorPalette(mContext);
                for (NoteColor color : NoteColor.values()) {
                    unitUnderTest.updateSelectedColor(color);
                    assertThat(unitUnderTest.mRadioButtons.get(color).isChecked(), is(true));
                }
            }
        });

    }

    private void validateConstruction(NoteEditColorPalette unitUnderTest) {
        assertThat(unitUnderTest.mOnDoneClickListener, is(nullValue()));
        assertThat(unitUnderTest.mOnColorSelectedListener, is(nullValue()));
        assertThat(unitUnderTest.mRadioButtons, is(notNullValue()));
        assertThat(unitUnderTest.mOnCheckedChangeListener, is(notNullValue()));
        assertThat(unitUnderTest.mRadioButtons.size(), is(NoteColor.values().length));

        for (NoteColor color : NoteColor.values()) {
            int expectedResourceId = 0;
            switch (color) {
                case BLUE:
                    expectedResourceId = R.id.color_picker_blue_button;
                    break;
                case GREEN:
                    expectedResourceId = R.id.color_picker_green_button;
                    break;
                case GREY:
                    expectedResourceId = R.id.color_picker_gray_button;
                    break;
                case PINK:
                    expectedResourceId = R.id.color_picker_pink_button;
                    break;
                case PURPLE:
                    expectedResourceId = R.id.color_picker_purple_button;
                    break;
                case YELLOW:
                    expectedResourceId = R.id.color_picker_yellow_button;
                    break;
            }
            assertThat(unitUnderTest.mRadioButtons.get(color).getId(), is(expectedResourceId));
        }
    }


    private class LocalOnColorSelectedListener
            implements NoteEditColorPalette.OnColorSelectedListener {
        final NoteColor mExpectedColor;


        boolean mCallbackReceived = false;


        public LocalOnColorSelectedListener(final NoteColor expectedColor) {
            mExpectedColor = expectedColor;
        }


        @Override
        public void onColorSelected(@NonNull final NoteColor color) {
            assertThat(mExpectedColor, is(color));
            mCallbackReceived = true;
        }
    }

    private class LocalOnDoneClickedListener implements NoteEditColorPalette.OnDoneClickListener {
        boolean mCallbackReceived = false;


        @Override
        public void onDoneClicked() {
            mCallbackReceived = true;
        }
    }
}