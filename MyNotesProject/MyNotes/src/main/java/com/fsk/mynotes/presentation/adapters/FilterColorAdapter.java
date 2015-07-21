package com.fsk.mynotes.presentation.adapters;


import android.content.Context;
import android.os.Handler;
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
import com.google.common.base.Preconditions;

import java.util.HashSet;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * The adapter that provides a togglable view for each value in {@link NoteColor}.
 */
public class FilterColorAdapter extends RecyclerViewAdapter<FilterColorAdapter.ViewHolder> {


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
     * The callback listener to call when the Color changes.
     */
    OnColorChangeListener mOnColorChangeListener;


    /**
     * The content description template to use when the color filter is enabled.
     */
    final String mFilterOnTemplate;


    /**
     * The content description template to use when the color filter is disabled.
     */
    final String mFilterOffTemplate;


    /**
     * The currently selected colors.
     */
    final Set<NoteColor> mSelectedColors = new HashSet<>();


    /**
     * The click listener for the {@link FilterColorAdapter .ViewHolder#mToggle}.
     */
    final CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener =
            new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(final CompoundButton buttonView,
                                             final boolean isChecked) {
                    final NoteColor noteColor = (NoteColor) buttonView.getTag();
                    if (noteColor != null) {
                        updateSelectedColors(noteColor, isChecked);
                        notifyListenerOfColorChange(noteColor, isChecked);
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
        Preconditions.checkNotNull(context);

        mFilterOffTemplate = context.getString(R.string.accessibility_filter_off_template);
        mFilterOnTemplate = context.getString(R.string.accessibility_filter_on_template);
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


    /**
     * Set the currently selected colors.
     *
     * @param handler
     *         the handler to use to call {@link #postNotifyDataSetChanged(Handler)}.
     * @param selectedColors
     *         the currently selected colors.
     */
    public void setSelectedColors(Handler handler, @NonNull Set<NoteColor> selectedColors) {
        setSelectedColors(selectedColors);
        postNotifyDataSetChanged(handler);
    }


    /**
     * Set the currently selected colors.
     *
     * @param selectedColors
     *         the currently selected colors.
     */
    public void setSelectedColors(@NonNull Set<NoteColor> selectedColors) {
        mSelectedColors.clear();
        mSelectedColors.addAll(selectedColors);
    }


    /**
     * Update the selected colors with the specified note color.
     *
     * @param noteColor
     *         The note color that changed.
     * @param checked
     *         true if the note color is checked.
     */
    private void updateSelectedColors(@NonNull NoteColor noteColor, boolean checked) {
        if (checked) {
            mSelectedColors.add(noteColor);
        }
        else {
            mSelectedColors.remove(noteColor);
        }
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


    @Override
    public int getItemCount() {
        return NoteColor.values().length;
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
        boolean colorEnabled = mSelectedColors.contains(noteColor);
        String filterText = colorEnabled ? mFilterOnTemplate : mFilterOffTemplate;

        holder.mToggle.setBackgroundResource(getBackgroundResource(noteColor));
        holder.mToggle.setChecked(colorEnabled);
        holder.mToggle.setTag(noteColor);
        holder.mToggle.setContentDescription(String.format(filterText, colorText));
    }


    /**
     * Get the background resource for the specified note color.
     *
     * @param color
     *         The color to use for retrieving the background resource.
     *
     * @return The resource id of the background resource associated with the note color.
     */
    final int getBackgroundResource(@NonNull NoteColor color) {
        final int returnValue;
        switch (color) {
            case BLUE:
                returnValue = R.drawable.blue_color_filter_background;
                break;
            case GREEN:
                returnValue = R.drawable.green_color_filter_background;
                break;
            case GREY:
                returnValue = R.drawable.gray_color_filter_background;
                break;
            case PINK:
                returnValue = R.drawable.pink_color_filter_background;
                break;
            case PURPLE:
                returnValue = R.drawable.purple_color_filter_background;
                break;
            case YELLOW:
            default:
                returnValue = R.drawable.yellow_color_filter_background;
                break;
        }
        return returnValue;
    }

}