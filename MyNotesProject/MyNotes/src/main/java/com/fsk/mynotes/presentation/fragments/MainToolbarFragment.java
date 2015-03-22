package com.fsk.mynotes.presentation.fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;

import com.fsk.common.presentation.components.CheckableImageView;
import com.fsk.common.presentation.recycler.DividerItemDecoration;
import com.fsk.common.presentation.utils.checkable_helper.OnCheckedChangeListener;
import com.fsk.mynotes.R;
import com.fsk.mynotes.presentation.adapters.FilterColorAdapter;
import com.fsk.mynotes.presentation.animations.filter_toolbar.ToolbarAnimatorBuilder;
import com.fsk.mynotes.presentation.animations.filter_toolbar.ToolbarAnimatorBuilderCompat;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * This fragment manages the primary toolbar for the notes.
 */
public class MainToolbarFragment extends Fragment {


    /**
     * UI element to show/hide the color filter sub-menu.
     */
    @InjectView(R.id.fragment_toolbar_filter_toggle)
    CheckableImageView mShowFilterToggle;


    /**
     * UI element that allows the user to filter notes by color.
     */
    @InjectView(R.id.fragment_toolbar_filter_view)
    RecyclerView mFilterRecyclerView;


    /**
     * The listener to react to requests to hide or note color filter.
     */
    private final OnCheckedChangeListener mFilterToggleCheckedChangeListener =
            new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(final Checkable checkable) {

                    enableFilterToolbar(checkable.isChecked());
                }
            };


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_toolbar, container, false);
        ButterKnife.inject(this, view);

        mShowFilterToggle.setOnCheckedChangeListener(mFilterToggleCheckedChangeListener);

        Context context = inflater.getContext();
        Resources resources = context.getResources();
        int columnCount = resources.getInteger(R.integer.color_filter_column_count);
        float dividerDimension = resources.getDimension(R.dimen.color_filter_divider);

        mFilterRecyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
        mFilterRecyclerView.addItemDecoration(new DividerItemDecoration((int) dividerDimension));
        mFilterRecyclerView.setAdapter(new FilterColorAdapter(context));

        return view;
    }


    /**
     * Show or hide the note filter options.
     *
     * @param enabled
     *         true to show the note filter.  false will hide it.
     */
    private void enableFilterToolbar(boolean enabled) {
        ToolbarAnimatorBuilder builder = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) ?
                                          new ToolbarAnimatorBuilder() :
                                          new ToolbarAnimatorBuilderCompat();

        try {
            int x = (mShowFilterToggle.getLeft() + mShowFilterToggle.getRight()) / 2;
            int y = (mShowFilterToggle.getTop() + mShowFilterToggle.getBottom()) / 2;
            builder.setTarget(mFilterRecyclerView)
                   .setFocus(x, y)
                   .setRadius(getView().getWidth())
                   .setReveal(enabled).build()
                   .start();
        }
        catch (NullPointerException npe) {
            //Don't do anything except prevent the crash propagation.
        }
    }
}
