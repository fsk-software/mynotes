package com.fsk.common.presentation.recycler;


import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.test.AndroidTestCase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Tests {@link RecyclerViewAdapter}
 */
public class RecyclerViewAdapterTest extends AndroidTestCase {




    public void testMethodsWithNullHandler() {
        LocalRecyclerViewAdapter adapter = new LocalRecyclerViewAdapter();
        adapter.postNotifyDataSetChanged(null);
        adapter.postNotifyItemChanged(null, 0);
        adapter.postNotifyItemInserted(null, 0);
        adapter.postNotifyItemMoved(null, 0, 0);
        adapter.postNotifyItemRangeChanged(null, 0, 0);
        adapter.postNotifyItemRangeInserted(null, 0, 0);
        adapter.postNotifyItemRangeRemoved(null, 0, 0);
        adapter.postNotifyItemRemoved(null, 0);
    }


    public void testMethodsWithNonNullHandler() {
        Handler handler = new Handler();
        LocalRecyclerViewAdapter adapter = new LocalRecyclerViewAdapter();

        adapter.postNotifyDataSetChanged(handler);
        adapter.postNotifyItemChanged(handler, 0);
        adapter.postNotifyItemInserted(handler, 0);
        adapter.postNotifyItemMoved(handler, 0, 0);
        adapter.postNotifyItemRangeChanged(handler, 0, 0);
        adapter.postNotifyItemRangeInserted(handler, 0, 0);
        adapter.postNotifyItemRangeRemoved(handler, 0, 0);
        adapter.postNotifyItemRemoved(handler, 0);

        //Just make sure nothing crashes
    }


    private static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(final View itemView) {
            super(itemView);
        }
    }

    private class LocalRecyclerViewAdapter extends RecyclerViewAdapter<ViewHolder> {
        
        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            return new ViewHolder(new TextView(getContext()));
        }


        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
        }


        @Override
        public int getItemCount() {
            return 1;
        }
    }
}
