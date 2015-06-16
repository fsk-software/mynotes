package com.fsk.mynotes.presentation.adapters;


import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;

import com.fsk.mynotes.R;

/**
 * Created by Me on 3/20/2015.
 */
public class FilterColorFilterAdapterViewHolderTest extends AndroidTestCase {

    public void testCreationWithNullClickListener() {

        View root = LayoutInflater.from(getContext()).inflate(R.layout.recycler_item_color_filter, null);
        FilterColorFilterAdapter.ViewHolder holder = new FilterColorFilterAdapter.ViewHolder(root, null, null);
        assertNotNull(holder.mToggle);
        assertTrue(!holder.mToggle.isChecked());
    }


//    public void testCreationWithClickListener() {
//        LocalOnClickListener listener = new LocalOnClickListener();
//        View root = LayoutInflater.from(getContext()).inflate(R.layout.recycler_item_color_filter, null);
//        FilterColorAdapter.ViewHolder holder = new FilterColorAdapter.ViewHolder(root, listener);
//        assertNotNull(holder.mToggle);
//        assertTrue(!holder.mToggle.isChecked());
//        holder.mToggle.performClick();
//        assertTrue(listener.mClicked);
//    }

    private class LocalOnClickListener implements View.OnClickListener {
        private boolean mClicked = false;


        @Override
        public void onClick(final View v) {
            mClicked = true;
        }
    }
}
