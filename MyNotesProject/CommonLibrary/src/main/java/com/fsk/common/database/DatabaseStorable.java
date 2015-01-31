package com.fsk.common.database;


import android.database.sqlite.SQLiteDatabase;

/**
 * An interface that defines the storable values.
 */
public interface DatabaseStorable {

    /**
     * Store the value in the supplied database.
     *
     * @param db
     *         the database to receive the value.
     */
    public void save(SQLiteDatabase db);

    /**
     * Remove the value from the database.
     *
     * @param db
     *         the database to delete the value from.
     */
    public void delete(SQLiteDatabase db);
}
