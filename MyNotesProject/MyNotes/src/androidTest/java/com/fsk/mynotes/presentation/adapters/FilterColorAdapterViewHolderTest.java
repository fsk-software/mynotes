package com.fsk.mynotes.presentation.adapters;


import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;

import com.fsk.mynotes.R;

/**
 * Created by Me on 3/20/2015.
 */
public class FilterColorAdapterViewHolderTest extends AndroidTestCase {

    public void testCreationWithNullClickListener() {

        View root = LayoutInflater.from(getContext()).inflate(R.layout.recycler_item_color_filter, null);
        FilterColorAdapter.ViewHolder holder = new FilterColorAdapter.ViewHolder(root, null);
        assertNotNull(holder.mCheckedTextView);
        assertTrue(!holder.mCheckedTextView.isChecked());
    }


    public void testCreationWithClickListener() {
        LocalOnClickListener listener = new LocalOnClickListener();
        View root = LayoutInflater.from(getContext()).inflate(R.layout.recycler_item_color_filter, null);
        FilterColorAdapter.ViewHolder holder = new FilterColorAdapter.ViewHolder(root, listener);
        assertNotNull(holder.mCheckedTextView);
        assertTrue(!holder.mCheckedTextView.isChecked());
        holder.mCheckedTextView.performClick();
        assertTrue(listener.mClicked);
    }

    private class LocalOnClickListener implements View.OnClickListener {
        private boolean mClicked = false;


        @Override
        public void onClick(final View v) {
            mClicked = true;
        }
    }
}
