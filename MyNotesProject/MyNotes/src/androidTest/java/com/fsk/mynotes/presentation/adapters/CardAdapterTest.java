package com.fsk.mynotes.presentation.adapters;


import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.fsk.mynotes.R;
import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.data.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Me on 3/20/2015.
 */
public class CardAdapterTest extends AndroidTestCase {

    public void testConstructionWithNullList() {
        CardAdapter adapter = new CardAdapter(null);
        assertNotNull(adapter.mNotes);
        assertTrue(adapter.mNotes.isEmpty());
        assertEquals(0, adapter.getItemCount());
    }

    public void testConstructionWithEmptyList() {
        CardAdapter adapter = new CardAdapter(new ArrayList<Note>());
        assertNotNull(adapter.mNotes);
        assertTrue(adapter.mNotes.isEmpty());
        assertEquals(0, adapter.getItemCount());
    }


    public void testConstructionSunnyDay() {
        List<Note> expected = new ArrayList<>();
        for (int i=0; i<10; ++i) {
            Note note = new Note();
            note.setId(i);
            expected.add(note);
        }

        CardAdapter adapter = new CardAdapter(expected);
        assertNotNull(adapter.mNotes);
        assertEquals(expected.size(), adapter.mNotes.size());
        assertEquals(expected.size(), adapter.getItemCount());

        for (int i=0; i<10; ++i) {
            assertEquals(expected.get(i), adapter.mNotes.get(i));
        }
    }


    public void testViewHolderCreation() {
        CardAdapter adapter = new CardAdapter(null);
        assertNotNull(adapter.onCreateViewHolder(new LinearLayout(getContext()), 0));
    }


    public void testViewBinding() {

        View root = LayoutInflater.from(getContext()).inflate(R.layout.recycler_item_card, null);
        CardAdapter.CardViewHolder holder = new CardAdapter.CardViewHolder(root);

        List<Note> expected = new ArrayList<>();
        for (NoteColor color : NoteColor.values()) {
            Note note = new Note();
            note.setId(color.ordinal());
            note.setColor(color);
            note.setText(color.name());
            expected.add(note);
        }

        CardAdapter adapter = new CardAdapter(expected);
        for (NoteColor color : NoteColor.values()) {
            adapter.onBindViewHolder(holder, color.ordinal());

            assertEquals(color.name(), holder.mTextView.getText());
        }
    }
}
