package com.fsk.common.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

/**
 * A Helper class to manage the database access and setup.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * The context to use for creating the helper instance.
     */
    private static Context sContext = null;


    /**
     * The database managed by this helper.
     */
    private static Database sDatabase;


    /**
     * The Singleton Instance of this class.
     */
    private static DatabaseHelper sInstance = null;


    /**
     * Get the singleton instance of the helper.
     *
     * @return the singleton instance of the helper.
     */
    public static DatabaseHelper getInstance() {
        if (needsInitializing()) {
            throw new IllegalStateException("Initialization of database required");
        }

        if (sInstance == null) {
            sInstance = new DatabaseHelper(sContext, sDatabase);
        }

        return sInstance;
    }


    /**
     * Return an instance of the Database to use for modifying the tables.
     *
     * @return an instance of the Database to use for modifying the tables.
     */
    public static SQLiteDatabase getDatabase() {
        return getInstance().getWritableDatabase();
    }


    /**
     * Initialize the helper to create the databases.
     *
     * @param context
     *         The context to use for creating the instance of this class.
     * @param database
     *         The database to manage with the
     *
     * @throws IllegalArgumentException
     *         when the context is null.
     */
    public static synchronized void initialize(@NonNull final Context context,
                                               @NonNull final Database database) {
        if (context == null) {
            throw new IllegalArgumentException("The context cannot be null");
        }
        else if (database == null) {
            throw new IllegalArgumentException("The database cannot be null");
        }

        sContext = context;
        sDatabase = database;
    }


    /**
     * Determines if the helper is initialized correctly.
     *
     * @return true if a call must be made to {@link #initialize(android.content.Context,
     * Database)}.
     */
    public static synchronized boolean needsInitializing() {
        return (sContext == null) || (sDatabase == null);
    }


    private DatabaseHelper(@NonNull final Context context, @NonNull final Database database) {
        super(context, database.getName(), database.getCursorFactory(), database.getVersion());
    }


    @Override
    public void onCreate(final SQLiteDatabase db) {
        sDatabase.onCreate(db);
    }


    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        if (oldVersion < newVersion) {
            sDatabase.onUpgrade(db, oldVersion, newVersion);
        }
    }


    @Override
    public void onDowngrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        if (newVersion < oldVersion) {
            sDatabase.onDowngrade(db, oldVersion, newVersion);
        }
    }
}
