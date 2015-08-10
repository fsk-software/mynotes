package com.fsk.mynotes.presentation.components;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ViewFlipper;

import com.fsk.mynotes.R;
import com.fsk.mynotes.data.Note;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * UI compound view that provides the user with the options needed to edit the note.  These include
 * cancelling/deleting, saving, and changing the color.
 */
public class NoteEditToolbar extends FrameLayout {

    /**
     * The modes for the toolbar. This directly corresponds to the types of children loaded into the
     * {@link ViewFlipper}.
     */
    enum Mode {
        /**
         * The main options for editing the text.  This will display the save/delete icons.
         */
        OPTIONS_BAR,

        /**
         * The color palette to allow the user to change colors.
         */
        PALETTE_BAR
    }


    /**
     * UI component to flip between the toolbar modes.
     */
    @InjectView(R.id.component_note_edit_toolbar_flipper)
    ViewFlipper mViewFlipper;


    /**
     * UI component to allow the user to change the note color.
     */
    @InjectView(R.id.component_note_edit_toolbar_palette)
    NoteEditColorPalette mNoteEditColorPalette;


    /**
     * UI component to allow the user to save/delete the note.
     */
    @InjectView(R.id.component_note_edit_toolbar_options)
    NoteEditOptionsBar mNoteEditOptionsBar;


    /**
     * The listener to the user being done selecting note colors. This will change the mode to
     * {@link Mode#OPTIONS_BAR}
     */
    private final NoteEditColorPalette.OnDoneClickListener mOnColorPaletteDoneClickListener =
            new NoteEditColorPalette.OnDoneClickListener() {


                @Override
                public void onDoneClicked() {
                    mViewFlipper.setDisplayedChild(Mode.OPTIONS_BAR.ordinal());
                }
            };


    /**
     * The listener to the user requesting to change the note color. This will change the mode to
     * {@link Mode#PALETTE_BAR}
     */
    private final NoteEditOptionsBar.OnPaletteClickListener mOnColorPaletteClickListener =
            new NoteEditOptionsBar.OnPaletteClickListener() {
                @Override
                public void onPaletteClicked() {
                    mViewFlipper.setDisplayedChild(Mode.PALETTE_BAR.ordinal());
                }
            };


    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context
     *         The Context to associate with the view
     */
    public NoteEditToolbar(final Context context) {
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
    public NoteEditToolbar(final Context context, final AttributeSet attrs) {
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
    public NoteEditToolbar(final Context context, final AttributeSet attrs,
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
    public NoteEditToolbar(final Context context, final AttributeSet attrs, final int defStyleAttr,
                           final int defStyleRes) {
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
                                      .inflate(R.layout.component_note_edit_toolbar, this, true);
        ButterKnife.inject(this, rootView);
        mNoteEditOptionsBar.setOnPaletteClickListener(mOnColorPaletteClickListener);
        mNoteEditColorPalette.setOnDoneClickListener(mOnColorPaletteDoneClickListener);
    }


    /**
     * Update the UI based on the note data.
     *
     * @param note
     *         the note containing the data to use for the UI.
     */
    public void updateNote(@NonNull Note note) {
        mNoteEditColorPalette.updateSelectedColor(note.getColor());
        mNoteEditOptionsBar.updateUiForNote(note);
    }


    /**
     * Set the listener to receive notifications of a color selection
     *
     * @param onColorSelectedListener
     *         the listener to set.  This will replace the current listener.
     */
    public void setOnColorSelectedListener(
            final NoteEditColorPalette.OnColorSelectedListener onColorSelectedListener) {
        mNoteEditColorPalette.setOnColorSelectedListener(onColorSelectedListener);
    }


    /**
     * Set the listener to receive notifications of a save or delete request.
     *
     * @param onPersistenceClickListener
     *         the listener to set.  This will replace the current listener.
     */
    public void setOnPersistenceClickListener(
            final NoteEditOptionsBar.OnPersistenceClickListener onPersistenceClickListener) {
        mNoteEditOptionsBar.setOnPersistenceClickListener(onPersistenceClickListener);
    }

}
