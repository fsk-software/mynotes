package com.fsk.common.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.google.common.base.Preconditions;

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
    private static DatabaseModel sDatabaseModel;


    /**
     * The Singleton Instance of this class.
     */
    private static DatabaseHelper sInstance = null;


    /**
     * Get the singleton instance of the helper.
     *
     * @return the singleton instance of the helper.
     *
     * @throws java.lang.IllegalStateException
     *         when {@link #needsInitializing()} is true.
     */
    public static DatabaseHelper getInstance() {
        if (needsInitializing()) {
            throw new IllegalStateException("Initialization of database required");
        }

        if (sInstance == null) {
            sInstance = new DatabaseHelper(sContext, sDatabaseModel);
        }

        return sInstance;
    }


    /**
     * Return an instance of the Database to use for modifying the tables.
     *
     * @return an instance of the Database to use for modifying the tables.
     *
     * @throws java.lang.IllegalStateException
     *         when {@link #needsInitializing()} is true.
     */
    public static SQLiteDatabase getDatabase() {
        return getInstance().getWritableDatabase();
    }


    /**
     * Initialize the helper to create the databases.
     *
     * @param context
     *         The context to use for creating the instance of this class.
     * @param databaseModel
     *         The database to manage with the
     *
     * @throws IllegalArgumentException
     *         when the context is null.
     */
    public static synchronized void initialize(@NonNull final Context context,
                                               @NonNull final DatabaseModel databaseModel) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(databaseModel);

        sContext = context;
        sDatabaseModel = databaseModel;
    }


    /**
     * Determines if the helper is initialized correctly.
     *
     * @return true if a call must be made to {@link #initialize(android.content.Context,
     * DatabaseModel)}.
     */
    public static synchronized boolean needsInitializing() {
        return (sContext == null) || (sDatabaseModel == null);
    }


    private DatabaseHelper(@NonNull final Context context,
                           @NonNull final DatabaseModel databaseModel) {
        super(context,
              databaseModel.getName(),
              databaseModel.getCursorFactory(),
              databaseModel.getVersion());
    }


    @Override
    public void onCreate(final SQLiteDatabase db) {
        sDatabaseModel.onCreate(db);
    }


    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        if (oldVersion < newVersion) {
            sDatabaseModel.onUpgrade(db, oldVersion, newVersion);
        }
    }


    @Override
    public void onDowngrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        if (newVersion < oldVersion) {
            sDatabaseModel.onDowngrade(db, oldVersion, newVersion);
        }
    }


    /**
     * reset the static attributes to null.
     * <p/>
     * Do not call any of the following methods in production code.
     */
    static void reset() {
        //todo: ideally this will throw an exception when called outside of the unit test
        //environment, but I haven't found a reliable way to make that work yet.

        sContext = null;
        sDatabaseModel = null;
        sInstance = null;
    }
}
