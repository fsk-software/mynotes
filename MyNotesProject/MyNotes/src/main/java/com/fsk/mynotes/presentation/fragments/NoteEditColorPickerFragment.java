package com.fsk.mynotes.presentation.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fsk.common.presentation.utils.animations.BackgroundAnimatorHelper;
import com.fsk.mynotes.R;
import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.utils.NoteEditor;

import java.util.HashSet;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fragment that displays all of the available color options and allows the user to pick one.
 */
public class NoteEditColorPickerFragment extends BaseNoteEditToolbarFragment {

    /**
     * The keys to the argument bundle.
     */
    private static class ArgumentExtras {
        /**
         * A key that references a integer value that represents the elapsed time in milliseconds to
         * shift the background color.
         */
        private static String COLOR_SHIFT_DURATION_KEY = "COLOR_SHIFT_DURATION_KEY";
    }


    /**
     * Create a new instance of {@link NoteEditColorPickerFragment} with the arguments primed with
     * the specified data.
     *
     * @param colorShiftDuration
     *         the elapsed time in milliseconds to shift the background color.
     *
     * @return a new instance of {@link NoteEditColorPickerFragment} with the arguments primed with
     * the specified data.
     */
    public static NoteEditColorPickerFragment newInstance(int colorShiftDuration) {

        Bundle arguments = new Bundle();
        arguments.putInt(ArgumentExtras.COLOR_SHIFT_DURATION_KEY, colorShiftDuration);

        NoteEditColorPickerFragment returnValue = new NoteEditColorPickerFragment();
        returnValue.setArguments(arguments);

        return returnValue;
    }


    /**
     * UI element to allow the user to select a single color.
     */
    RadioGroup mRadioGroup;


    @OnClick(R.id.fragment_note_edit_color_picker_done)
    public void doneButtonClicked(View view) {

        NoteEditor editor = getNoteEditor();
        if (editor != null) {
            editor.showEditOptions();
        }
    }


    /**
     * The animation duration in milliseconds for cross-blending the background color.
     */
    int mColorShiftDuration;


    /**
     * The set of the radio buttons is {@link #mRadioGroup}
     */
    final Set<RadioButton> mRadioButtons = new HashSet<>();


    /**
     * A listener to change the note color in response to a user selecting a new color.
     */
    final RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener =
            new RadioGroup.OnCheckedChangeListener() {


                @Override
                public void onCheckedChanged(final RadioGroup group, final int checkedId) {
                    updateSelectedColor(getNoteColorForRadioButtonId(checkedId));
                }
            };


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mColorShiftDuration = getArguments().getInt(ArgumentExtras.COLOR_SHIFT_DURATION_KEY);
    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.fragment_note_edit_color_toolbar, container, false);
        ButterKnife.inject(this, rootView);

        mRadioGroup =
                (RadioGroup) rootView.findViewById(R.id.fragment_note_edit_color_picker_group);
        mRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);

        initializeRadioButtons(rootView);
        return rootView;
    }


    /**
     * Initialize the radio buttons for the root view.
     *
     * @param rootView
     *         the root view to use for finding the radio buttons.
     */
    private void initializeRadioButtons(@NonNull View rootView) {
        for (NoteColor noteColor : NoteColor.values()) {
            int resourceId = 0;
            switch (noteColor) {
                case BLUE:
                    resourceId = R.id.color_picker_blue_button;
                    break;
                case GREEN:
                    resourceId = R.id.color_picker_green_button;
                    break;
                case GREY:
                    resourceId = R.id.color_picker_gray_button;
                    break;
                case PINK:
                    resourceId = R.id.color_picker_pink_button;
                    break;
                case PURPLE:
                    resourceId = R.id.color_picker_purple_button;
                    break;
                case YELLOW:
                    resourceId = R.id.color_picker_yellow_button;
                    break;
            }

            RadioButton radioButton = (RadioButton) rootView.findViewById(resourceId);
            if (radioButton == null) {
                throw new IllegalStateException("This should never be null");
            }

            mRadioButtons.add(radioButton);
            radioButton.setTag(noteColor);
        }
    }


    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateSelectedColor(getNoteEditor().getNote().getColor());
    }


    /**
     * Update the selected color.  This will notify the {@link #getNoteEditor()} of the color change
     * and animate the background color change.
     *
     * @param color
     *         The new color.
     */
    private void updateSelectedColor(@NonNull NoteColor color) {
        NoteEditor editor = getNoteEditor();
        View view = getView();
        if ((editor != null) && (view != null)) {
            BackgroundAnimatorHelper
                    .crossBlendColorResource(view, editor.getNote().getColor().darkColorResourceId,
                                             color.darkColorResourceId, mColorShiftDuration, null);

            mRadioGroup.check(getRadioButtonForColor(color).getId());
            editor.changeNoteColor(color);
        }
    }


    /**
     * Get the {@link RadioButton} for the specified color.
     *
     * @param noteColor
     *         The note color to use for finding the radio button.
     *
     * @return the radio button associated with the note color.
     */
    RadioButton getRadioButtonForColor(@NonNull NoteColor noteColor) {
        for (RadioButton candidate : mRadioButtons) {
            if (candidate.getTag().equals(noteColor)) {
                return candidate;
            }
        }
        return null;
    }


    /**
     * Get the {@link NoteColor} for the button Id.
     *
     * @param buttonId
     *         the resource id of the radio button.
     *
     * @return the {@link NoteColor} associated withe radio button with the specifed id.
     */
    NoteColor getNoteColorForRadioButtonId(int buttonId) {
        for (RadioButton candidate : mRadioButtons) {
            if (candidate.getId() == buttonId) {
                return (NoteColor) candidate.getTag();
            }
        }
        return null;
    }
}
