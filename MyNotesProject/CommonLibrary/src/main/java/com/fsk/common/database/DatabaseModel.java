package com.fsk.common.database;


import android.database.sqlite.SQLiteDatabase;

/**
 * Defines the common database management behaviors.
 */
public interface DatabaseModel {

    /**
     * Called when the database is created for the first time.
     *
     * @param db
     *         The database
     */
    public void onCreate(final SQLiteDatabase db);

    /**
     * Called when the database is upgraded.
     *
     * @param db
     *         The database
     * @param oldVersion
     *         the old version of the database.
     * @param newVersion
     *         the new version of the database.
     */
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion);

    /**
     * Called when the database is downgraded.
     *
     * @param db
     *         The database
     * @param oldVersion
     *         the old version of the database.
     * @param newVersion
     *         the new version of the database.
     */
    public void onDowngrade(final SQLiteDatabase db, final int oldVersion, final int newVersion);

    /**
     * Get the database name.
     *
     * @return the database name.
     */
    public String getName();

    /**
     * Get the database version.
     *
     * @return the database version.
     */
    public int getVersion();


    /**
     * Get the {@link android.database.sqlite.SQLiteDatabase.CursorFactory} for the database.
     *
     * @return the {@link android.database.sqlite.SQLiteDatabase.CursorFactory}. This can be null.
     */
    public SQLiteDatabase.CursorFactory getCursorFactory();
}