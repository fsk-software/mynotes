package com.fsk.mynotes.presentation.fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fsk.common.presentation.recycler.DividerItemDecoration;
import com.fsk.mynotes.R;
import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.data.cache.NoteFilterCache;
import com.fsk.mynotes.presentation.adapters.FilterColorFilterAdapter;
import com.fsk.mynotes.presentation.layout_managers.ColorFilterLayoutManager;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A fragment to manage selecting/unselecting multiple colors.
 */
public class ColorFilterFragment extends Fragment {

    /**
     * Create a new instance of the {@link ColorFilterFragment}
     *
     * @return a new instance of the {@link ColorFilterFragment}
     */
    public static ColorFilterFragment newInstance() {
        return new ColorFilterFragment();
    }


    /**
     * UI element that allows the user to filter notes by color.
     */
    @InjectView(R.id.fragment_color_filter_recycler)
    RecyclerView mFilterRecyclerView;


    /**
     * The Cache for the selected colors.
     */
    NoteFilterCache mNoteFilterCache;


    /**
     * The listener to react to changes to the color filter selection.
     */
    final FilterColorFilterAdapter.OnColorChangeListener mOnColorSelectChangeListener =
            new FilterColorFilterAdapter.OnColorChangeListener() {
                @Override
                public void onColorSelected(final NoteColor color, final boolean enabled) {
                    mNoteFilterCache.enableColor(color, enabled);
                }
            };


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_color_selector, container, false);
        ButterKnife.inject(this, rootView);

        Context context = inflater.getContext();
        mNoteFilterCache = new NoteFilterCache(context);

        Resources resources = context.getResources();
        mFilterRecyclerView.setAdapter(createAdapter(context));
        mFilterRecyclerView.addItemDecoration(createDividerItemDecoration(resources));
        mFilterRecyclerView.setLayoutManager(createLayoutManager(context, resources));

        return rootView;
    }


    /**
     * Create the adapter.
     *
     * @param context
     *         The context to use for creating the adapter.
     *
     * @return The adapter for managing {@link #mFilterRecyclerView}.
     */
    private FilterColorFilterAdapter createAdapter(@NonNull Context context) {
        FilterColorFilterAdapter adapter = new FilterColorFilterAdapter(context);
        adapter.setOnColorChangeListener(mOnColorSelectChangeListener);
        adapter.setSelectedColors(mNoteFilterCache.getEnabledColors());

        return adapter;
    }


    /**
     * Create a {@link DividerItemDecoration} for the RecyclerView.
     *
     * @param resources
     *         The resources to read for divider and orientation data.
     *
     * @return a new {@link DividerItemDecoration}.
     */
    private DividerItemDecoration createDividerItemDecoration(Resources resources) {
        int dividerHorizontal =
                (int) resources.getDimension(R.dimen.color_filter_item_divider_horizontal);
        int dividerVertical =
                (int) resources.getDimension(R.dimen.color_filter_item_divider_vertical);

        return new DividerItemDecoration(dividerVertical, dividerHorizontal);
    }


    /**
     * Create a {@link ColorFilterLayoutManager} for the RecyclerView.
     *
     * @param context
     *         The context to assign to the layout manager.
     * @param resources
     *         The resources to read for divider and orientation data.
     *
     * @return a new {@link ColorFilterLayoutManager}.
     */
    private RecyclerView.LayoutManager createLayoutManager(Context context, Resources resources) {
        boolean vertical = resources.getBoolean(R.bool.color_filter_toolbar_vertical);
        int orientation = vertical ? LinearLayout.VERTICAL : LinearLayout.HORIZONTAL;
        return new ColorFilterLayoutManager(context, orientation);
    }
}
