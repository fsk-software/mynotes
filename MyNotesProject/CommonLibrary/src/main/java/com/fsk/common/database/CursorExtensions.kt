package com.fsk.common.database

import android.database.Cursor

/**
 * Created by me on 10/30/2017.
 */

fun Cursor.getLong(columnName: String) : Long {
    return getLong(getColumnIndex(columnName));
}


fun Cursor.getString(columnName: String) : String? {
    return getString(getColumnIndex(columnName));
}


fun Cursor.getInt(columnName: String) : Int {
    return getInt(getColumnIndex(columnName));
}