package com.fsk.common.database;


import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.fsk.common.utils.threads.ThreadUtils;
import com.fsk.common.utils.Preconditions;


import java.util.Arrays;
import java.util.List;

/**
 * Common Utilities for reading and writing to the database.
 */
public final class DatabaseUtilities {


    /**
     * Hide the constructor to prevent any instances.
     */
    private DatabaseUtilities() {
    }


    /**
     * Build a comma-separated-list of question marks.
     *
     * @param count
     *         the number of questions marks to include in the return value.
     *
     * @return a comma-separated-list of question marks.
     */
    public static String buildQueryQuestionMarkString(int count) {
        StringBuilder queryBuilder = new StringBuilder();
        for (int i = 0, lastItem = count - 1; i < count; ++i) {
            queryBuilder.append("?");
            if (i < lastItem) {
                queryBuilder.append(", ");
            }
        }

        return queryBuilder.toString();
    }


    /**
     * Save all of the specified items in a single transaction.
     *
     * @param items
     *         the items to save to the database.
     * @param db
     *         the database to receive the items.
     *
     * @throws java.lang.NullPointerException
     *         when either of the parameters in null.
     * @throws java.lang.IllegalArgumentException
     *         when the items is empty.
     * @throws com.fsk.common.utils.threads.ThreadException
     *         when called from the UI thread.
     */
    public static void bulkSave(@NonNull DatabaseStorable[] items, @NonNull SQLiteDatabase db) {
        new ThreadUtils().checkOffUIThread();

        Preconditions.checkNotNull(db);
        Preconditions.checkNotNull(items);
        Preconditions.checkArgument(items.length > 0);

        bulkSave(Arrays.asList(items), db);
    }


    /**
     * Delete all of the specified items in a single transaction.
     *
     * @param items
     *         the items to delete from the database.
     * @param db
     *         the database containing the items to delete.
     *
     * @throws java.lang.NullPointerException
     *         when either of the parameters in null.
     * @throws java.lang.IllegalArgumentException
     *         when the items is empty.
     * @throws com.fsk.common.utils.threads.ThreadException
     *         when called from the UI thread.
     */
    public static void bulkDelete(@NonNull DatabaseStorable[] items, @NonNull SQLiteDatabase db) {
        new ThreadUtils().checkOffUIThread();

        Preconditions.checkNotNull(db);
        Preconditions.checkNotNull(items);
        Preconditions.checkArgument(items.length > 0);

        bulkDelete(Arrays.asList(items), db);
    }


    /**
     * Save all of the specified items in a single transaction.
     *
     * @param items
     *         the items to save to the database.
     * @param db
     *         the database to receive the items.
     *
     * @throws java.lang.NullPointerException
     *         when either of the parameters in null.
     * @throws java.lang.IllegalArgumentException
     *         when the items is empty.
     * @throws com.fsk.common.utils.threads.ThreadException
     *         when called from the UI thread.
     */
    public static <T extends DatabaseStorable> void bulkSave(@NonNull List<T> items,
                                                             @NonNull SQLiteDatabase db) {
        new ThreadUtils().checkOffUIThread();

        Preconditions.checkNotNull(db);
        Preconditions.checkNotNull(items);
        Preconditions.checkArgument(!items.isEmpty());

        try {
            db.beginTransaction();
            for (DatabaseStorable item : items) {
                item.save(db);
            }
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }


    /**
     * Delete all of the specified items in a single transaction.
     *
     * @param items
     *         the items to delete from the database.
     * @param db
     *         the database containing the items to delete.
     *
     * @throws java.lang.NullPointerException
     *         when either of the parameters in null.
     * @throws java.lang.IllegalArgumentException
     *         when the items is empty.
     * @throws com.fsk.common.utils.threads.ThreadException
     *         when called from the UI thread.
     */
    public static <T extends DatabaseStorable> void bulkDelete(@NonNull List<T> items,
                                                               @NonNull SQLiteDatabase db) {
        new ThreadUtils().checkOffUIThread();

        Preconditions.checkNotNull(db);
        Preconditions.checkNotNull(items);
        Preconditions.checkArgument(!items.isEmpty());

        try {
            db.beginTransaction();
            for (DatabaseStorable item : items) {
                item.delete(db);
            }
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }


    /**
     * Save the item to the database.
     *
     * @param item
     *         the object to save to the database.
     * @param db
     *         the database to save the object to.
     *
     * @throws java.lang.NullPointerException
     *         when either of the parameters in null.
     * @throws com.fsk.common.utils.threads.ThreadException
     *         when called from the UI thread.
     */
    public static void save(@NonNull DatabaseStorable item, @NonNull SQLiteDatabase db) {
        new ThreadUtils().checkOffUIThread();

        Preconditions.checkNotNull(item);
        Preconditions.checkNotNull(db);

        db.beginTransaction();
        try {
            item.save(db);
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }


    /**
     * Delete the item data from the database.
     *
     * @param item
     *         the item to delete from the database.
     * @param db
     *         the database to delete the item from.
     *
     * @throws java.lang.NullPointerException
     *         when either of the parameters in null.
     * @throws com.fsk.common.utils.threads.ThreadException
     *         when called from the UI thread.
     */
    public static void delete(@NonNull DatabaseStorable item, @NonNull SQLiteDatabase db) {
        new ThreadUtils().checkOffUIThread();

        Preconditions.checkNotNull(item);
        Preconditions.checkNotNull(db);

        db.beginTransaction();
        try {
            item.delete(db);
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }
}
