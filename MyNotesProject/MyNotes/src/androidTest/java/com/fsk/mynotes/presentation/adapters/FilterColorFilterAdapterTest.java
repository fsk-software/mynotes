package com.fsk.mynotes.presentation.adapters;


import android.content.Context;
import android.content.res.Resources;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.fsk.mynotes.R;
import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.data.cache.NoteFilterCache;

/**
 * Created by Me on 3/20/2015.
 */
public class FilterColorFilterAdapterTest extends AndroidTestCase {

    public void testConstructionWithNullContext() {
        try {
            new FilterColorFilterAdapter(null);
            assert false;
        }
        catch(NullPointerException e) {
            assert true;
        }
    }

    public void testConstructionSunnyDay() {

        Context context = getContext();
        String expectedOff = context.getString(R.string.accessibility_filter_off_template);
        String expectedOn = context.getString(R.string.accessibility_filter_on_template);

        FilterColorFilterAdapter adapter = new FilterColorFilterAdapter(context);
        assertEquals(expectedOff, adapter.mFilterOffTemplate);
        assertEquals(expectedOn, adapter.mFilterOnTemplate);
    }


    public void testViewHolderCreation() {
        FilterColorFilterAdapter adapter = new FilterColorFilterAdapter(getContext());
        assertNotNull(adapter.onCreateViewHolder(new LinearLayout(getContext()), 0));
    }

    public void testViewBindingWithNoColor() {

        Context context = getContext();
        View root = LayoutInflater.from(context).inflate(R.layout.recycler_item_color_filter, null);
        FilterColorFilterAdapter.ViewHolder holder = new FilterColorFilterAdapter.ViewHolder(root, null, null);

        FilterColorFilterAdapter adapter = new FilterColorFilterAdapter(getContext());
        try {
            adapter.onBindViewHolder(holder, -1);
            assert true;
        }
        catch (IllegalStateException e) {}
    }


    public void testViewBindingChecked() {

        Context context = getContext();
        View root = LayoutInflater.from(context).inflate(R.layout.recycler_item_color_filter, null);
        FilterColorFilterAdapter.ViewHolder holder = new FilterColorFilterAdapter.ViewHolder(root, null, null);
        holder.mToggle.setChecked(true);

        NoteFilterCache noteFilterCache = new NoteFilterCache(context);
        FilterColorFilterAdapter adapter = new FilterColorFilterAdapter(getContext());
        Resources resources = context.getResources();
        for (NoteColor color : NoteColor.values()) {
            noteFilterCache.enableColor(color, true);
            String colorText = resources.getString(color.nameResourceId);

            String expectedText = String.format(adapter.mFilterOnTemplate, colorText);
            adapter.onBindViewHolder(holder, color.ordinal());

            assertEquals(expectedText, holder.mToggle.getContentDescription());
            assertEquals(color, holder.mToggle.getTag());
        }
    }

    public void testViewBindingUnchecked() {

        Context context = getContext();
        View root = LayoutInflater.from(context).inflate(R.layout.recycler_item_color_filter, null);
        FilterColorFilterAdapter.ViewHolder holder = new FilterColorFilterAdapter.ViewHolder(root, null, null);

        NoteFilterCache noteFilterCache = new NoteFilterCache(context);
        FilterColorFilterAdapter adapter = new FilterColorFilterAdapter(getContext());
        Resources resources = context.getResources();
        for (NoteColor color : NoteColor.values()) {
            noteFilterCache.enableColor(color, false);
            String colorText = resources.getString(color.nameResourceId);

            String expectedText = String.format(adapter.mFilterOffTemplate, colorText);
            adapter.onBindViewHolder(holder, color.ordinal());

            assertEquals(expectedText, holder.mToggle.getContentDescription());
            assertEquals(color, holder.mToggle.getTag());
        }
    }

    public void testOnCheckedChangeListener() {
        Context context = getContext();
        FilterColorFilterAdapter adapter = new FilterColorFilterAdapter(context);
        ToggleButton button = new ToggleButton(context);

        NoteFilterCache noteFilterCache = new NoteFilterCache(context);
        //test the null tag case
        adapter.mOnCheckedChangeListener.onCheckedChanged(button, true);

        button.setTag(NoteColor.YELLOW);

        //test the Yellow Color tag (unchecked);
        noteFilterCache.enableColor(NoteColor.YELLOW, true);
        adapter.mOnCheckedChangeListener.onCheckedChanged(button, false);
        assertFalse(noteFilterCache.isColorEnabled(NoteColor.YELLOW));

        //test the Yellow Color tag (checked);
        adapter.mOnCheckedChangeListener.onCheckedChanged(button, true);
        assertTrue(noteFilterCache.isColorEnabled(NoteColor.YELLOW));
    }


    public void testOnLongPressListener() {
        Context context = getContext();
        FilterColorFilterAdapter adapter = new FilterColorFilterAdapter(context);
        ToggleButton button = new ToggleButton(context);

        button.setContentDescription("");
        assertTrue(adapter.mShowHintListener.onLongClick(button));
    }
}
