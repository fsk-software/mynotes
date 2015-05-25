package com.fsk.mynotes.presentation.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.fsk.common.presentation.recycler.RecyclerViewAdapter;
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
public class FilterColorAdapter extends RecyclerViewAdapter<FilterColorAdapter.ViewHolder> {

    /**
     * The ViewHolder for the adapter.
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * The checkable view that presents the color selection state to the user.
         */
        @InjectView(R.id.recycler_item_color_filter_check_view)
        ToggleButton mToggle;


        /**
         * Constructor.
         *
         * @param v
         *         the root view that will be used to find the ViewHolder views.
         * @param checkedChangeListener
         *         The checked changes listener to assign to {@link #mToggle}.
         * @param longClickListener
         *         The long click listener to assign to {@link #mToggle}.
         */
        public ViewHolder(View v, CompoundButton.OnCheckedChangeListener checkedChangeListener,
                          View.OnLongClickListener longClickListener) {
            super(v);
            ButterKnife.inject(this, v);
            mToggle.setOnCheckedChangeListener(checkedChangeListener);
            mToggle.setOnLongClickListener(longClickListener);
        }
    }


    /**
     * The cache to use for reading and persisting the note filter selection.
     */
    final NoteFilterCache mNoteFilterCache;


    /**
     * The content description template to use when the color filter is enabled.
     */
    final String mFilterOnTemplate;


    /**
     * The content description template to use when the color filter is disabled.
     */
    final String mFilterOffTemplate;


    /**
     * The click listener for the {@link com.fsk.mynotes.presentation.adapters.FilterColorAdapter
     * .ViewHolder#mToggle}. Clicking the view will toggle the checked status in {@link
     * #mNoteFilterCache} and request an UI update.
     */
    final CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener =
            new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(final CompoundButton buttonView,
                                             final boolean isChecked) {
                    final NoteColor noteColor = (NoteColor) buttonView.getTag();

                    if (noteColor != null) {
                        mNoteFilterCache.enableColor(noteColor, isChecked);
                    }
                }
            };


    /**
     * Listener to a long click that will show a Toast with the toggles content description.
     */
    final View.OnLongClickListener mShowHintListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(final View v) {
            Toast.makeText(v.getContext(), v.getContentDescription(), Toast.LENGTH_LONG).show();
            return true;
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
        super();
        Preconditions.checkNotNull(context);

        mFilterOffTemplate = context.getString(R.string.accessibility_filter_off_template);
        mFilterOnTemplate = context.getString(R.string.accessibility_filter_on_template);
        mNoteFilterCache = new NoteFilterCache(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View view = LayoutInflater.from(context)
                                  .inflate(R.layout.recycler_item_color_filter, parent, false);

        return new ViewHolder(view, mOnCheckedChangeListener, mShowHintListener);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        NoteColor noteColor = NoteColor.getColor(position);
        if (noteColor == null) {
            throw new IllegalStateException("The note color should never be null here");
        }

        String colorText = holder.mToggle.getContext().getString(noteColor.nameResourceId);
        boolean colorEnabled = mNoteFilterCache.isColorEnabled(noteColor);
        String filterText = colorEnabled ? mFilterOnTemplate : mFilterOffTemplate;

        holder.mToggle.setBackgroundResource(noteColor.colorFilterBackgroundResourceId);
        holder.mToggle.setChecked(colorEnabled);
        holder.mToggle.setTag(noteColor);
        holder.mToggle.setContentDescription(String.format(filterText, colorText));
    }


    @Override
    public int getItemCount() {
        return NoteColor.values().length;
    }
}