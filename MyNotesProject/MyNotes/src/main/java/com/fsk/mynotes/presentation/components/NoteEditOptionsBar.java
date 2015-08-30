package com.fsk.mynotes.presentation.components;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.fsk.mynotes.R;
import com.fsk.mynotes.data.Note;
import com.google.common.base.Strings;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * UI compound view that displays the main options for editing a note.
 */
public class NoteEditOptionsBar extends FrameLayout {

    /**
     * Callback listener to notify when the user chooses to save or delete a note.
     */
    public interface OnPersistenceClickListener {

        /**
         * The callback method that will listener to notify when the user clicks the save icon.
         */
        void onSaveClicked();

        /**
         * The callback method that will listener to notify when the user clicks the purge icon.
         */
        void onDeleteClicked();
    }


    /**
     * Callback listener to notify when the user wants to modify the note color.
     */
    public interface OnPaletteClickListener {

        /**
         * The callback method that will listener to notify when the user wants to modify the note
         * color.
         */
        void onPaletteClicked();
    }


    /**
     * UI component to allow the user to save a note.
     */
    @InjectView(R.id.component_note_edit_options_bar_save)
    ImageSwitcher mSaveImageSwitcher;


    /**
     * UI component to allow the user to purge a note.
     */
    @InjectView(R.id.component_note_edit_options_bar_purge)
    ImageSwitcher mPurgeImageSwitcher;


    /**
     * The listener to clicks on the save icon. This will notify then {@link
     * #mOnPersistenceClickListener} of a click to the save icon.
     *
     * @param view
     *         the clicked view.
     */
    @OnClick(R.id.component_note_edit_options_bar_save)
    public void onSaveClicked(View view) {
        if (mOnPersistenceClickListener != null) {
            mOnPersistenceClickListener.onSaveClicked();
        }
    }


    /**
     * The listener to clicks on the purge icon. This will notify then {@link
     * #mOnPersistenceClickListener} of a click to the purge icon.
     *
     * @param view
     *         the clicked view.
     */
    @OnClick(R.id.component_note_edit_options_bar_purge)
    public void purgeButtonClicked(View view) {
        if (mOnPersistenceClickListener != null) {
            mOnPersistenceClickListener.onDeleteClicked();
        }
    }


    /**
     * The listner to the pallette button. This will notify then {@link #mOnPaletteClickListener} of
     * a click to the palette icon.
     *
     * @param view
     *         the clicked view.
     */
    @OnClick(R.id.component_note_edit_options_bar_color_palette)
    public void paletteButtonClicked(View view) {
        if (mOnPaletteClickListener != null) {
            mOnPaletteClickListener.onPaletteClicked();
        }
    }


    /**
     * The view factory to support the {@link ImageSwitcher}s. This will create a basic {@link
     * ImageView} when making the view.
     */
    final ViewSwitcher.ViewFactory mViewFactory = new ViewSwitcher.ViewFactory() {

        public View makeView() {
            // Create a new ImageView set it's properties
            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.MATCH_PARENT,
                                                                     LayoutParams.MATCH_PARENT));
            return imageView;
        }
    };


    /**
     * The callback listener to notify when the user wants to modify the note color.
     */
    OnPaletteClickListener mOnPaletteClickListener;


    /**
     * The callback listener to notify when the user wants to save/delete the note.
     */
    OnPersistenceClickListener mOnPersistenceClickListener;


    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context
     *         The Context to associate with the view
     */
    public NoteEditOptionsBar(final Context context) {
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
    public NoteEditOptionsBar(final Context context, final AttributeSet attrs) {
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
    public NoteEditOptionsBar(final Context context, final AttributeSet attrs,
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
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NoteEditOptionsBar(final Context context, final AttributeSet attrs,
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
    void initialize(Context context) {
        View rootView = LayoutInflater.from(context)
                                      .inflate(R.layout.component_note_edit_options_bar, this,
                                               true);
        ButterKnife.inject(this, rootView);
        mPurgeImageSwitcher.setFactory(mViewFactory);
        mSaveImageSwitcher.setFactory(mViewFactory);

    }


    /**
     * Update the UI based on the note data. This will change the imagery for the cancel/delete and
     * save options.
     *
     * @param note
     *         the note containing the data to use for the note.
     */
    public void updateUiForNote(@NonNull Note note) {
        updatePurgeButtonImagery(note);
        updateSaveButtonVisibility(note);
    }


    /**
     * Change the Save button imagery and clickability based on the note.  If the note has changes
     * and text then the save button will show the checkmark and allow the user to click it.
     * Otherwise, it is set to invisible and is rendered unclickable.
     *
     * @param note
     *         the note to use for determining the save button UI.
     */
    private void updateSaveButtonVisibility(@NonNull Note note) {
        String nonNullText = Strings.nullToEmpty(note.getText());
        if (note.isDirty() && !TextUtils.isEmpty(nonNullText)) {
            mSaveImageSwitcher.setImageResource(R.drawable.save_note_background);
            mSaveImageSwitcher.setClickable(true);
        }
        else {
            mSaveImageSwitcher.setImageResource(0);
            mSaveImageSwitcher.setClickable(false);
        }
    }


    /**
     * Change the Purge button imagery based on the note. If the note is currently persisted, then
     * show the delete image. Otherwise, show the cancel image.
     *
     * @param note
     *         the note to use for determining the purge button UI.
     */
    private void updatePurgeButtonImagery(@NonNull Note note) {
        if (note.getId() == Note.NOT_STORED) {
            mPurgeImageSwitcher.setImageResource(R.drawable.cancel_background);
        }
        else {
            mPurgeImageSwitcher.setImageResource(R.drawable.delete_note_background);
        }
    }


    /**
     * Set the {@link NoteEditOptionsBar.OnPersistenceClickListener}.  This will replace the current
     * listener.
     *
     * @param onPersistenceClickListener
     *         the listener to set.
     */
    public void setOnPersistenceClickListener(
            final OnPersistenceClickListener onPersistenceClickListener) {
        mOnPersistenceClickListener = onPersistenceClickListener;
    }


    /**
     * Set the {@link com.fsk.mynotes.presentation.components.NoteEditOptionsBar
     * .OnPaletteClickListener}.
     * This will replace the current listener.
     *
     * @param onPaletteClickListener
     *         the listener to set.
     */
    public void setOnPaletteClickListener(final OnPaletteClickListener onPaletteClickListener) {
        mOnPaletteClickListener = onPaletteClickListener;
    }
}
