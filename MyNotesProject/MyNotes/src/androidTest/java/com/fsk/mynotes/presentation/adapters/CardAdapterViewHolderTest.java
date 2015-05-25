package com.fsk.mynotes.presentation.adapters;


import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;

import com.fsk.mynotes.R;

/**
 * Created by Me on 3/20/2015.
 */
public class CardAdapterViewHolderTest extends AndroidTestCase {

    public void testCreation() {

        View root = LayoutInflater.from(getContext()).inflate(R.layout.recycler_item_note, null);
        CardAdapter.CardViewHolder holder = new CardAdapter.CardViewHolder(root);
        assertNotNull(holder.mCardView);
        assertNotNull(holder.mTextView);
    }
}
