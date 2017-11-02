package com.fsk.mynotes.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.fsk.common.database.*
import org.jetbrains.anko.db.*

/**
 * Created by me on 10/30/2017.
 */
class NotesSQLiteOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, NAME) {

    companion object {

        /**
         * The schemas and database creation for the primary MyNotes database.
         */
        /**
         * The database schema version.
         */
        val VERSION = 1;


        /**
         * The name of the database.
         */
        val NAME = "notes";


        /**
         * Defines the table names for the database.
         */
        val NOTES_TABLE = "notes";


        /**
         * Defines the columns in the database tables.
         */
        val NOTE_ID_COL = "_id";


        val NOTE_TEXT_COL = "note_text";


        val NOTE_COLOR_COL = "color";

        private var instance: NotesSQLiteOpenHelper? = null

        @Synchronized
        fun getInstance(context: Context): NotesSQLiteOpenHelper {
            if (instance == null) {
                instance = NotesSQLiteOpenHelper(context.applicationContext);
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(NOTES_TABLE,
                       true,
                       NOTE_ID_COL to INTEGER + PRIMARY_KEY,
                       NOTE_TEXT_COL to TEXT,
                       NOTE_COLOR_COL to INTEGER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
}

/**
 * Access property for Context
  */
val Context.database: NotesSQLiteOpenHelper
    get() = NotesSQLiteOpenHelper.getInstance(applicationContext)