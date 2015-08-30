package com.fsk.mynotes.presentation.adapters;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.fsk.mynotes.R;
import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.data.Note;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Me on 3/20/2015.
 */
@RunWith(AndroidJUnit4.class)
public class CardAdapterTest {

    private Context mContext;

    @Before
    public void prepareForTest() {
        mContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testConstruction() {
        CardAdapter adapter = new CardAdapter();
        assertThat(adapter.mNotes.isEmpty(), is(true));
        assertThat(adapter.getItemCount(), is(0));
    }

    @Test
    public void testSetNotesWithNullList() {
        CardAdapter adapter = new CardAdapter();
        adapter.setNotes(null);
        assertThat(adapter.mNotes, is(notNullValue()));
        assertThat(adapter.mNotes.isEmpty(), is(true));
        assertThat(adapter.getItemCount(), is(0));
    }

    @Test
    public void testSetNotesWithEmptyList() {
        CardAdapter adapter = new CardAdapter();
        adapter.setNotes(new ArrayList<Note>());
        assertThat(adapter.mNotes, is(notNullValue()));
        assertThat(adapter.mNotes.isEmpty(), is(true));
        assertThat(adapter.getItemCount(), is(0));
    }


    @Test
    public void testSetNotesWithNonEmptyList() throws Exception {
        List<Note> expected = new ArrayList<>();
        for (int i=0; i<10; ++i) {
            Note note = new Note.Builder().setId(i).build();
            expected.add(note);
        }

        CardAdapter adapter = new CardAdapter();
        adapter.setNotes(expected);
        assertThat(adapter.mNotes, is(notNullValue()));
        assertThat(expected.size(), is(adapter.mNotes.size()));
        assertThat(expected.size(), is(adapter.getItemCount()));

        for (int i=0; i<10; ++i) {
            assertThat(expected.get(i), is(adapter.mNotes.get(i)));
        }
    }


    @Test
    public void testViewHolderCreation() {
        CardAdapter adapter = new CardAdapter();
        assertThat(adapter.onCreateViewHolder(new LinearLayout(mContext), 0),is(notNullValue()));
    }


    @Test
    public void testViewBinding() throws Exception{

        View root = LayoutInflater.from(mContext).inflate(R.layout.recycler_item_note, null);
        CardAdapter.CardViewHolder holder = new CardAdapter.CardViewHolder(root, null);

        List<Note> expected = new ArrayList<>();
        for (NoteColor color : NoteColor.values()) {
            Note note = new Note.Builder()
                                .setId(color.ordinal())
                                .setColor(color)
                                .setText(color.name())
                                .build();
            expected.add(note);
        }

        CardAdapter adapter = new CardAdapter();
        adapter.setNotes(expected);
        for (NoteColor color : NoteColor.values()) {
            adapter.onBindViewHolder(holder, color.ordinal());

            assertThat(color.name(), is(holder.mTextView.getText()));
        }
    }
}
