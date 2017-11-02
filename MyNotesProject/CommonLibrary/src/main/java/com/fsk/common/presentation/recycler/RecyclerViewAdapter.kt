package com.fsk.common.presentation.recycler

import android.os.Handler
import android.support.v7.widget.RecyclerView

/**
 * A version of the {@link android.support.v7.widget.RecyclerView.Adapter} that provides methods to
 * perform the notify methods asynchronously.  This prevents cashes when the notify methods are
 * called while the {@link RecyclerView} is laying out or scrolling.
 */
abstract class RecyclerViewAdapter<VH : RecyclerView.ViewHolder>() : RecyclerView.Adapter<VH>() {

    /**
     * Asynchronously post [.notifyDataSetChanged] if the handler exists.

     * @param handler
     * *         the handler to use for posting the requested notify method.
     */
    fun postNotifyDataSetChanged(handler: Handler?) {
        handler?.post { notifyDataSetChanged() }
    }


    /**
     * Asynchronously post [.notifyItemChanged] if the handler exists.

     * @param handler
     * *         the handler to use for posting the requested notify method.
     */
    fun postNotifyItemChanged(handler: Handler?,
                              position: Int) {
        handler?.post { notifyItemChanged(position) }
    }


    /**
     * Asynchronously post [.notifyItemRangeChanged].

     * @param handler
     * *         the handler to use for posting the requested notify method.
     */
    fun postNotifyItemRangeChanged(handler: Handler?,
                                   positionStart: Int,
                                   itemCount: Int) {

        handler?.post { notifyItemRangeChanged(positionStart, itemCount) }
    }


    /**
     * Asynchronously post [.notifyItemInserted].

     * @param handler
     * *         the handler to use for posting the requested notify method.
     */
    fun postNotifyItemInserted(handler: Handler?,
                               position: Int) {
        handler?.post { notifyItemInserted(position) }
    }


    /**
     * Asynchronously post [.notifyItemMoved].

     * @param handler
     * *         the handler to use for posting the requested notify method.
     */
    fun postNotifyItemMoved(handler: Handler?,
                            fromPosition: Int,
                            toPosition: Int) {
        handler?.post { notifyItemMoved(fromPosition, toPosition) }
    }


    /**
     * Asynchronously post [.notifyItemRangeInserted].

     * @param handler
     * *         the handler to use for posting the requested notify method.
     */
    fun postNotifyItemRangeInserted(handler: Handler?,
                                    positionStart: Int,
                                    itemCount: Int) {

        handler?.post { notifyItemRangeInserted(positionStart, itemCount) }
    }


    /**
     * Asynchronously post [.notifyItemRemoved]

     * @param handler
     * *         the handler to use for posting the requested notify method.
     */
    fun postNotifyItemRemoved(handler: Handler?,
                              position: Int) {

        handler?.post { notifyItemRemoved(position) }
    }


    /**
     * Asynchronously post [.notifyItemRangeRemoved].

     * @param handler
     * *         the handler to use for posting the requested notify method.
     */
    fun postNotifyItemRangeRemoved(handler: Handler?,
                                   positionStart: Int,
                                   itemCount: Int) {
        handler?.post { notifyItemRangeRemoved(positionStart, itemCount) }
    }
}