package com.fsk.common.database

import android.database.sqlite.SQLiteDatabase

/**
 * An interface that defines the Database storable values.
 */
interface DatabaseStorable {

    /**
     * Store the value in the supplied database.
     *
     * @param db
     *          the database to receive the value.
     */
    fun save(db: SQLiteDatabase);

    /**
     * Remove the value from the database.
     *
     * @param db
     *           the database to delete the value from.
     */
    fun delete(db: SQLiteDatabase);
}