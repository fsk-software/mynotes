package com.fsk.common.presentation;


import android.text.Editable;
import android.text.TextWatcher;

/**
 * A simple text watcher that allows the user to only implement the methods they need.
 */
open class SimpleTextWatcher : TextWatcher {


    override fun beforeTextChanged(s: CharSequence,
                                   start: Int,
                                   count: Int,
                                   after: Int) {
        //This space for rent.
    }


    override fun onTextChanged(s: CharSequence,
                               start: Int,
                               before: Int,
                               count: Int) {
        //This space for rent.
    }


    override fun afterTextChanged(s: Editable) {
        //This space for rent.
    }
}
