package com.fsk.mynotes.data.database;


import android.database.sqlite.SQLiteDatabase;

import com.fsk.common.database.CommonTerms;
import com.fsk.common.database.DatabaseModel;

/**
 * The schemas and database creation for the primary MyNotes database.
 */
public class MyNotesDatabaseModel implements DatabaseModel {

    /**
     * The database schema version.
     */
    public static final int DATABASE_VERSION = 1;


    /**
     * The name of the database.
     */
    public static final String DATABASE_NAME = "data";


    /**
     * Defines the table names for the database.
     */
    public abstract static class Tables {
        public static final String NOTES = "notes";
    }


    /**
     * Defines the columns in the database tables.
     */
    public abstract static class Columns {
        public static final String NOTE_ID = "_id";


        public static final String NOTE_TEXT = "note_text";


        public static final String NOTE_COLOR = "color";
    }


    /**
     * Creation schema for the notes table.
     */
    static final String NOTES_TABLE_CREATE =
            CommonTerms.CREATE_TABLE_IF_NOT_EXISTS + Tables.NOTES +
            " ( " + Columns.NOTE_ID + CommonTerms.INTEGER_PRIMARY_KEY_AUTO_INCREMENT +
            CommonTerms.COMMA + Columns.NOTE_TEXT + CommonTerms.TEXT + CommonTerms.COMMA +
            Columns.NOTE_COLOR + CommonTerms.INTEGER + ")" + CommonTerms.SEMI_COLON;


    /**
     * An array that contains the creation schemas for all of the tables.
     */
    final static String[] CREATE_COMMANDS = new String[] {
            // creating tables
            NOTES_TABLE_CREATE };


    /**
     * An array that contains all of the table names for the database.
     */
    final static String[] TABLES = new String[] { Tables.NOTES };


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.beginTransaction();

            for (String command : CREATE_COMMANDS) {
                db.execSQL(command);
            }

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        dropTables(db);
        onCreate(db);
    }


    @Override
    public void onDowngrade(SQLiteDatabase db, int arg1, int arg2) {
        dropTables(db);
        onCreate(db);
    }


    /**
     * Remove the tables in {@link #TABLES}
     *
     * @param db
     *         The database containing the tables to delete.
     */
    private void dropTables(SQLiteDatabase db) {
        try {
            db.beginTransaction();
            for (final String tableName : TABLES) {
                final String dropTable = String.format(CommonTerms.DROP_TABLE_IF_EXISTS, tableName);
                db.execSQL(dropTable);
            }

            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }


    @Override
    public String getName() {
        return DATABASE_NAME;
    }


    @Override
    public int getVersion() {
        return DATABASE_VERSION;
    }


    @Override
    public SQLiteDatabase.CursorFactory getCursorFactory() {
        return null;
    }
}
