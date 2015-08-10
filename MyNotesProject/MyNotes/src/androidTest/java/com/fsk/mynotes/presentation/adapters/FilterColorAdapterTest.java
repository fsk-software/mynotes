package com.fsk.mynotes.presentation.adapters;


import android.content.res.Resources;
import android.os.Looper;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.fsk.mynotes.R;
import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.data.cache.NoteFilterPreferences;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Me on 3/20/2015.
 */
public class FilterColorAdapterTest extends AndroidTestCase {

    @Test
    public void testConstructionWithNullContext() {
        try {
            new FilterColorAdapter(null);
            assert false;
        }
        catch(NullPointerException e) {
        }
    }

    @Test
    public void testConstructionSunnyDay() {

        String expectedOff = getContext().getString(R.string.accessibility_filter_off_template);
        String expectedOn = getContext().getString(R.string.accessibility_filter_on_template);

        FilterColorAdapter adapter = new FilterColorAdapter(getContext());
        assertThat(expectedOff, is(adapter.mFilterOffTemplate));
        assertThat(expectedOn, is(adapter.mFilterOnTemplate));
    }


    @Test
    public void testViewHolderCreation() {
        FilterColorAdapter adapter = new FilterColorAdapter(getContext());
        assertThat(adapter.onCreateViewHolder(new LinearLayout(getContext()), 0), is(notNullValue()));
    }

    @Test
    public void testViewBindingWithNoColor() {

        View root = LayoutInflater.from(getContext()).inflate(R.layout.recycler_item_color_filter, null);
        FilterColorAdapter.ViewHolder holder = new FilterColorAdapter.ViewHolder(root, null, null);

        FilterColorAdapter adapter = new FilterColorAdapter(getContext());
        try {
            adapter.onBindViewHolder(holder, -1);
            assert false;
        }
        catch (IllegalStateException e) {}
    }


    @Test
    public void testViewBindingChecked() {

        View root = LayoutInflater.from(getContext()).inflate(R.layout.recycler_item_color_filter, null);
        FilterColorAdapter.ViewHolder holder = new FilterColorAdapter.ViewHolder(root, null, null);
        holder.mToggle.setChecked(true);

        Set<NoteColor> selectedColors = new HashSet<>();

        FilterColorAdapter adapter = new FilterColorAdapter(getContext());
        Resources resources = getContext().getResources();
        for (NoteColor color : NoteColor.values()) {
            selectedColors.add(color);
            adapter.setSelectedColors(selectedColors);
            String colorText = resources.getString(color.nameResourceId);

            String expectedText = String.format(adapter.mFilterOnTemplate, colorText);
            adapter.onBindViewHolder(holder, color.ordinal());

            assertThat(expectedText, is(holder.mToggle.getContentDescription()));
            assertThat(color, is(holder.mToggle.getTag()));
        }
    }

    @Test
    public void testViewBindingUnchecked() {

        View root = LayoutInflater.from(getContext()).inflate(R.layout.recycler_item_color_filter, null);
        FilterColorAdapter.ViewHolder holder = new FilterColorAdapter.ViewHolder(root, null, null);

        NoteFilterPreferences noteFilterPreferences = new NoteFilterPreferences(getContext());
        FilterColorAdapter adapter = new FilterColorAdapter(getContext());
        Resources resources = getContext().getResources();
        for (NoteColor color : NoteColor.values()) {
            noteFilterPreferences.enableColor(color, false);
            String colorText = resources.getString(color.nameResourceId);

            String expectedText = String.format(adapter.mFilterOffTemplate, colorText);
            adapter.onBindViewHolder(holder, color.ordinal());

            assertThat(expectedText, is(holder.mToggle.getContentDescription()));
            assertThat(color, is(holder.mToggle.getTag()));
        }
    }

    @Test
    public void testOnCheckedChangeListener() {
        FilterColorAdapter adapter = new FilterColorAdapter(getContext());
        ToggleButton button = new ToggleButton(getContext());
        //test the null tag case
        adapter.mOnCheckedChangeListener.onCheckedChanged(button, true);

        button.setTag(NoteColor.YELLOW);

        TestListener listener = new TestListener();
        listener.mExpectedColor = NoteColor.YELLOW;
        adapter.setOnColorChangeListener(listener);

        //test the Yellow Color tag (unchecked);
        listener.mExpectValue = false;
        adapter.mOnCheckedChangeListener.onCheckedChanged(button, false);

        //test the Yellow Color tag (checked);
        listener.mExpectValue = true;
        adapter.mOnCheckedChangeListener.onCheckedChanged(button, true);
    }


    @Test
    public void testOnLongPressListener() {
        Looper.prepare();
        FilterColorAdapter adapter = new FilterColorAdapter(getContext());
        ToggleButton button = new ToggleButton(getContext());

        button.setContentDescription("");
        assertThat(adapter.mShowHintListener.onLongClick(button), is(true));
    }

    private class TestListener implements FilterColorAdapter.OnColorChangeListener {

        NoteColor mExpectedColor;
        boolean mExpectValue;
        @Override
        public void onColorSelected(final NoteColor color, final boolean enabled) {
            assertThat(mExpectedColor, is(color));
            assertThat(mExpectValue, is(enabled));

        }
    }
}
