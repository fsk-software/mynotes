package com.fsk.mynotes.presentation.adapters;


import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;

import com.fsk.mynotes.R;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Me on 3/20/2015.
 */
public class FilterColorAdapterViewHolderTest extends AndroidTestCase {

    @Test
    public void testCreationWithNullClickListener() {

        View root =
                LayoutInflater.from(getContext()).inflate(R.layout.recycler_item_color_filter, null);
        FilterColorAdapter.ViewHolder holder =
                new FilterColorAdapter.ViewHolder(root, null, null);
        assertThat(holder.mToggle, is(notNullValue()));
        assertThat(holder.mToggle.isChecked(), is(false));
    }


    @Test
    public void testCreationWithoutListeners() {

        View root =
                LayoutInflater.from(getContext()).inflate(R.layout.recycler_item_color_filter, null);
        FilterColorAdapter.ViewHolder holder =
                new FilterColorAdapter.ViewHolder(root, null, null);
    }
}
