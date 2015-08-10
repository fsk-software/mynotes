package com.fsk.mynotes.presentation.components;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fsk.mynotes.R;
import com.fsk.mynotes.constants.NoteColor;

import java.util.HashSet;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * UI compound view that displays a {@link RadioGroup} that allows the user to select a single color
 * for a note.
 */
public class NoteEditColorPalette extends FrameLayout {

    /**
     * Callback listener to notify when the user clicks the done icon.
     */
    public interface OnDoneClickListener {
        /**
         * The callback method that will listener to notify when the user clicks the done icon.
         */
        void onDoneClicked();
    }

    /**
     * Callback listener to notify when the user selects a new note color.
     */
    public interface OnColorSelectedListener {

        /**
         * The callback method that will listener to notify when the user selects a new note color.
         *
         * @param color
         *         the selected note color.
         */
        void onColorSelected(@NonNull NoteColor color);
    }


    /**
     * The click listener to the done icon.  This will daisy chain the call to {@link
     * #mOnDoneClickListener}.
     *
     * @param view
     *         the clicked view.
     */
    @OnClick(R.id.component_note_edit_color_palette_done)
    public void doneButtonClicked(View view) {
        if (mOnDoneClickListener != null) {
            mOnDoneClickListener.onDoneClicked();
        }
    }


    /**
     * The listener to notify when a color is selected.
     */
    OnColorSelectedListener mOnColorSelectedListener;


    /**
     * The listener to notify when the user is done selecting colors.
     */
    OnDoneClickListener mOnDoneClickListener;


    /**
     * UI element to allow the user to select a single color.
     */
    RadioGroup mRadioGroup;


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
                    onColorSelected(getNoteColorForRadioButtonId(checkedId));
                }
            };


    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context
     *         The Context to associate with the view
     */
    public NoteEditColorPalette(final Context context) {
        this(context, null);
    }


    /**
     * Constructor that is called when inflating a view from XML.
     *
     * @param context
     *         The Context to associate with the view.
     * @param attrs
     *         the Attributes to customize the view.
     */
    public NoteEditColorPalette(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }


    /**
     * Constructor. Perform inflation from XML and apply a class-specific base style
     *
     * @param context
     *         The Context to associate with the view.
     * @param attrs
     *         the Attributes to customize the view.
     * @param defStyleAttr
     *         The class-specific base style.
     */
    public NoteEditColorPalette(final Context context, final AttributeSet attrs,
                                final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }


    /**
     * Constructor. Perform inflation from XML and apply a class-specific base style
     *
     * @param context
     *         The Context to associate with the view.
     * @param attrs
     *         the Attributes to customize the view.
     * @param defStyleAttr
     *         The class-specific base style.
     * @param defStyleRes
     *         I have no idea right now.
     */
    public NoteEditColorPalette(final Context context, final AttributeSet attrs,
                                final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context);
    }


    /**
     * Inflate the UI and set the component views.
     *
     * @param context
     *         the context to use for the inflation.
     */
    private void initialize(@NonNull Context context) {
        View rootView = LayoutInflater.from(context)
                                      .inflate(R.layout.component_note_edit_palette, this, true);
        ButterKnife.inject(this, rootView);

        mRadioGroup =
                (RadioGroup) rootView.findViewById(R.id.component_note_edit_color_palette_group);
        mRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);

        initializeRadioButtons(rootView);

    }


    /**
     * Update the selected color.  This will notify the {@link OnColorSelectedListener} of the color
     * change and animate the background color change.
     *
     * @param color
     *         The new color.
     */
    private void onColorSelected(@NonNull NoteColor color) {
        if (mOnColorSelectedListener != null) {
            mOnColorSelectedListener.onColorSelected(color);
        }
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


    /**
     * Set the listener to receive notifications of a note color change.
     *
     * @param onColorSelectedListener
     *         The listener to set.  This will replace the existing listener.
     */
    public void setOnColorSelectedListener(final OnColorSelectedListener onColorSelectedListener) {
        mOnColorSelectedListener = onColorSelectedListener;
    }


    /**
     * Set the listener to receive notifications of the user clicking the done icon.
     *
     * @param onDoneClickListener
     *         The listener to set.  This will replace the existing listener.
     */
    public void setOnDoneClickListener(final OnDoneClickListener onDoneClickListener) {
        mOnDoneClickListener = onDoneClickListener;
    }


    /**
     * Update the selected color to the specified color.
     *
     * @param color
     *         the color to use as the selection.
     */
    public void updateSelectedColor(@NonNull NoteColor color) {
        mRadioGroup.check(getRadioButtonForColor(color).getId());
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
