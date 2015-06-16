package com.fsk.mynotes.presentation.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.fsk.common.presentation.recycler.RecyclerViewAdapter;
import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.data.cache.NoteFilterCache;
import com.google.common.base.Preconditions;

/**
 * The adapter that provides a togglable view for each value in {@link NoteColor}.  It will persist
 * the toggle state via {@link NoteFilterCache}.
 */
public abstract class ColorFilterAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerViewAdapter<VH> {

    /**
     * The callback listener to call when a color changed.
     */
    public interface OnColorChangeListener {
        /**
         * Called when a color check changed.
         *
         * @param color
         *         The color that changed.
         * @param enabled
         *          true if the the color is enabled.
         */
        void onColorSelected(NoteColor color, boolean enabled);
    }


    /**
     * The callback listener to call when the Color changes.
     */
    OnColorChangeListener mOnColorChangeListener;


    /**
     * Constructor.
     *
     * @param context
     *         The context to use for accessing the application resources. This is not stored in the
     *         adapter.
     */
    public ColorFilterAdapter(@NonNull Context context) {
        super();
        Preconditions.checkNotNull(context);

    }

    /**
     * Set the {@link OnColorChangeListener} listener.
     *
     * @param listener
     *         the listener.
     */
    public void setOnColorChangeListener(OnColorChangeListener listener) {
        mOnColorChangeListener = listener;
    }

    @Override
    public int getItemCount() {
        return NoteColor.values().length;
    }


    /**
     * Notify the {@link OnColorChangeListener} about the change to the note color.
     *
     * @param noteColor
     *         The note color that changed.
     * @param checked
     *         true if the note color is checked.
     */
    protected void notifyListenerOfColorChange(@NonNull NoteColor noteColor, boolean checked) {
        if (mOnColorChangeListener != null) {

            mOnColorChangeListener.onColorSelected(noteColor, checked);
        }
    }
}