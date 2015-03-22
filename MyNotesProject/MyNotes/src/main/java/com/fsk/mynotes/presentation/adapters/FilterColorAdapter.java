package com.fsk.mynotes.presentation.adapters;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.CheckedTextView;

import com.fsk.mynotes.R;
import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.data.cache.NoteFilterCache;
import com.google.common.base.Preconditions;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * The adapter that provides a togglable view for each value in {@link NoteColor}.  It will persist
 * the toggle state via {@link NoteFilterCache}.
 */
public class FilterColorAdapter extends RecyclerView.Adapter<FilterColorAdapter.ViewHolder> {

    /**
     * The ViewHolder for the adapter.
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * The checkable text view that presents the color selection state to the user.
         */
        @InjectView(R.id.recycler_item_color_filter_text)
        CheckedTextView mCheckedTextView;


        /**
         * Constructor.
         *
         * @param v
         *         the root view that will be used to find the ViewHolder views.
         * @param listener
         *         The click listener to assign to {@link #mCheckedTextView}.
         */
        public ViewHolder(View v, View.OnClickListener listener) {
            super(v);
            ButterKnife.inject(this, v);
            mCheckedTextView.setOnClickListener(listener);
        }
    }


    /**
     * The cache to use for reading and persisting the note filter selection.
     */
    final NoteFilterCache mNoteFilterCache;


    /**
     * The basic template to use for the filter selection text.
     */
    final String mTemplate;


    /**
     * The text to use to reflect a selected filter option.
     */
    final String mOn;


    /**
     * The text to use to reflect an unselected filter option.
     */
    final String mOff;


    /**
     * The click listener for the {@link com.fsk.mynotes.presentation.adapters.FilterColorAdapter
     * .ViewHolder#mCheckedTextView}.
     * Clicking the view will toggle the checked status and persist that selection.
     */
    final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            Checkable checkable = (Checkable) v;
            NoteColor noteColor = (NoteColor) v.getTag();
            checkable.toggle();

            mNoteFilterCache.enableColor(noteColor, checkable.isChecked());
            notifyItemChanged(noteColor.ordinal());
        }
    };


    /**
     * Constructor.
     *
     * @param context
     *         The context to use for accessing the application resources. This is not stored in the
     *         adapter.
     */
    public FilterColorAdapter(@NonNull Context context) {
        Preconditions.checkNotNull(context);

        mNoteFilterCache = new NoteFilterCache(context);

        mTemplate = context.getString(R.string.note_filter_text);
        mOff = context.getString(R.string.note_filter_off);
        mOn = context.getString(R.string.note_filter_on);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View view = LayoutInflater.from(context)
                                  .inflate(R.layout.recycler_item_color_filter, parent, false);

        return new ViewHolder(view, mOnClickListener);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        NoteColor noteColor = NoteColor.getColor(position);
        String colorText = holder.mCheckedTextView.getContext().getString(noteColor.nameResourceId);

        boolean checked = holder.mCheckedTextView.isChecked();
        int textColor = (checked) ? R.color.primaryTextColor : R.color.secondaryTextColor;

        holder.mCheckedTextView.setBackgroundResource(noteColor.selectorResourceId);
        holder.mCheckedTextView.setTag(noteColor);
        holder.mCheckedTextView
                .setTextColor(holder.mCheckedTextView.getResources().getColor(textColor));
        holder.mCheckedTextView.setAlpha(checked ? 1f : 0.5f);
        holder.mCheckedTextView.setText(String.format(mTemplate, colorText, checked ? mOn : mOff));
        holder.mCheckedTextView.setTypeface(Typeface.DEFAULT_BOLD);

        updateElevation(holder, checked);
    }


    @Override
    public int getItemCount() {
        return NoteColor.values().length;
    }


    /**
     * Update the elevation for the view holder.  Nothing occurs on SDKs prior to Lollipop.
     *
     * @param holder
     *         the holder containing the views that will change elevation.
     * @param checked
     *         true to raise the elevation, false to lower it to the default elevation.
     */
    private void updateElevation(ViewHolder holder, boolean checked) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Resources resources = holder.mCheckedTextView.getContext().getResources();
            int elevationDimension = (checked) ? R.dimen.note_filter_selected_elevation :
                                     R.dimen.note_filter_unselected_elevation;
            holder.mCheckedTextView.setElevation(resources.getDimension(elevationDimension));
        }
    }
}