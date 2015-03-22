package com.fsk.mynotes.presentation.adapters;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.fsk.mynotes.R;
import com.fsk.mynotes.constants.NoteColor;

/**
 * Created by Me on 3/20/2015.
 */
public class FilterColorAdapterTest extends AndroidTestCase {

    public void testConstructionWithNullContext() {
        try {
            new FilterColorAdapter(null);
            assert false;
        }
        catch(NullPointerException e) {
            assert true;
        }
    }

    public void testConstructionSunnyDay() {

        Context context = getContext();
        String expectedTemplate = context.getString(R.string.note_filter_text);
        String expectedOff = context.getString(R.string.note_filter_off);
        String expectedOn = context.getString(R.string.note_filter_on);

        FilterColorAdapter adapter = new FilterColorAdapter(context);
        assertNotNull(adapter.mNoteFilterCache);
        assertEquals(expectedTemplate, adapter.mTemplate);
        assertEquals(expectedOff, adapter.mOff);
        assertEquals(expectedOn, adapter.mOn);
    }


    public void testViewHolderCreation() {
        FilterColorAdapter adapter = new FilterColorAdapter(getContext());
        assertNotNull(adapter.onCreateViewHolder(new LinearLayout(getContext()), 0));
    }


    public void testViewBindingChecked() {

        Context context = getContext();
        View root = LayoutInflater.from(context).inflate(R.layout.recycler_item_color_filter, null);
        FilterColorAdapter.ViewHolder holder = new FilterColorAdapter.ViewHolder(root, null);
        holder.mCheckedTextView.setChecked(true);

        FilterColorAdapter adapter = new FilterColorAdapter(getContext());

        Resources resources = context.getResources();
        for (NoteColor color : NoteColor.values()) {
            int rgbColor = resources.getColor(R.color.primaryTextColor);
            String colorText = resources.getString(color.nameResourceId);

            String expectedText = String.format(adapter.mTemplate, colorText,adapter.mOn);
            adapter.onBindViewHolder(holder, color.ordinal());

            assertEquals(expectedText, holder.mCheckedTextView.getText());
            assertEquals(color, holder.mCheckedTextView.getTag());
            assertEquals(rgbColor, holder.mCheckedTextView.getCurrentTextColor());
            assertEquals(1.0f, holder.mCheckedTextView.getAlpha());
            assertEquals(Typeface.DEFAULT_BOLD, holder.mCheckedTextView.getTypeface());
        }
    }

    public void testViewBindingUnchecked() {

        Context context = getContext();
        View root = LayoutInflater.from(context).inflate(R.layout.recycler_item_color_filter, null);
        FilterColorAdapter.ViewHolder holder = new FilterColorAdapter.ViewHolder(root, null);
        FilterColorAdapter adapter = new FilterColorAdapter(getContext());


        Resources resources = context.getResources();
        for (NoteColor color : NoteColor.values()) {
            int rgbColor = resources.getColor(R.color.secondaryTextColor);
            String colorText = resources.getString(color.nameResourceId);

            String expectedText = String.format(adapter.mTemplate, colorText,adapter.mOff);
            adapter.onBindViewHolder(holder, color.ordinal());

            assertEquals(expectedText, holder.mCheckedTextView.getText());
            assertEquals(color, holder.mCheckedTextView.getTag());
            assertEquals(rgbColor, holder.mCheckedTextView.getCurrentTextColor());
            assertEquals(0.5f, holder.mCheckedTextView.getAlpha());
            assertEquals(Typeface.DEFAULT_BOLD, holder.mCheckedTextView.getTypeface());
        }
    }
}
