package com.fsk.common.database

import android.database.sqlite.SQLiteQueryBuilder
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper

/**
 * Created by me on 10/17/2017.
 */

/**
 * Build a comma-separated-list of question marks.

 * @param count
 * *         the number of questions marks to include in the return value.
 * *
 * *
 * @return a comma-separated-list of question marks.
 */
fun buildQueryQuestionMarkString(count: Int): String {
    val queryBuilder = StringBuilder()
    var i = 0
    val lastItem = count - 1
    while (i < count) {
        queryBuilder.append("?")
        if (i < lastItem) {
            queryBuilder.append(", ")
        }
        ++i;
    }

    return queryBuilder.toString()
}