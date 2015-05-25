package com.fsk.common.presentation.recycler;


import android.os.Handler;
import android.support.v7.widget.RecyclerView;


/**
 * A version of the {@link android.support.v7.widget.RecyclerView.Adapter} that provides methods to
 * perform the notify methods asynchronously.  This prevents cashes when the notify methods are
 * called while the {@link RecyclerView} is laying out or scrolling.
 */
public abstract class RecyclerViewAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {


    /**
     * Asynchronously post {@link #notifyDataSetChanged()} if the handler exists.
     *
     * @param handler
     *         the handler to use for posting the requested notify method.
     */
    public void postNotifyDataSetChanged(Handler handler) {
        if (handler != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
        }
    }


    /**
     * Asynchronously post {@link #notifyItemChanged(int)} if the handler exists.
     *
     * @param handler
     *         the handler to use for posting the requested notify method.
     */
    public final void postNotifyItemChanged(Handler handler, final int position) {
        if (handler != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    notifyItemChanged(position);
                }
            });
        }
    }


    /**
     * Asynchronously post {@link #notifyItemRangeChanged(int, int)}.
     *
     * @param handler
     *         the handler to use for posting the requested notify method.
     */
    public final void postNotifyItemRangeChanged(Handler handler, final int positionStart,
                                                 final int itemCount) {

        if (handler != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    notifyItemRangeChanged(positionStart, itemCount);
                }
            });
        }
    }


    /**
     * Asynchronously post {@link #notifyItemInserted(int)}.
     *
     * @param handler
     *         the handler to use for posting the requested notify method.
     */
    public final void postNotifyItemInserted(Handler handler, final int position) {
        if (handler != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    notifyItemInserted(position);
                }
            });
        }
    }


    /**
     * Asynchronously post {@link #notifyItemMoved(int, int)}.
     *
     * @param handler
     *         the handler to use for posting the requested notify method.
     */
    public final void postNotifyItemMoved(Handler handler, final int fromPosition,
                                          final int toPosition) {
        if (handler != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    notifyItemMoved(fromPosition, toPosition);
                }
            });
        }
    }


    /**
     * Asynchronously post {@link #notifyItemRangeInserted(int, int)}.
     *
     * @param handler
     *         the handler to use for posting the requested notify method.
     */
    public final void postNotifyItemRangeInserted(Handler handler, final int positionStart,
                                                  final int itemCount) {

        if (handler != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    notifyItemRangeInserted(positionStart, itemCount);
                }
            });
        }
    }


    /**
     * Asynchronously post {@link #notifyItemRemoved(int)}
     *
     * @param handler
     *         the handler to use for posting the requested notify method.
     */
    public final void postNotifyItemRemoved(Handler handler, final int position) {

        if (handler != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    notifyItemRemoved(position);
                }
            });
        }
    }


    /**
     * Asynchronously post {@link #notifyItemRangeRemoved(int, int)}.
     *
     * @param handler
     *         the handler to use for posting the requested notify method.
     */
    public final void postNotifyItemRangeRemoved(Handler handler, final int positionStart,
                                                 final int itemCount) {
        if (handler != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    notifyItemRangeRemoved(positionStart, itemCount);
                }
            });
        }
    }
}

