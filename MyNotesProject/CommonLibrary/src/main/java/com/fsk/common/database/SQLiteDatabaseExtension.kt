package com.fsk.common.database

import android.database.sqlite.SQLiteDatabase
import com.fsk.common.threads.requireNotMainThread

/**
 * Save all of the specified items in a single transaction.

 * @param items
 * *         the items to save to the database.
 * *
 * @throws java.lang.NullPointerException
 * *         when either of the parameters in null.
 * *
 * @throws java.lang.IllegalArgumentException
 * *         when the items is empty.
 * *
 * @throws com.fsk.common.utils.threads.ThreadException
 * *         when called from the UI thread.
 */
fun <T : DatabaseStorable> SQLiteDatabase.bulkSave(items: List<T>) {
    require(items.isNotEmpty());
    val reference = this;

    performAtomicTransaction(
            object : TransactionActor {
                override fun perform() {
                    for (item in items) {
                        item.save(reference);
                    }
                }
            }
                            )
}


/**
 * Delete all of the specified items in a single transaction.

 * @param items
 * *         the items to delete from the database.
 * *
 * @throws java.lang.NullPointerException
 * *         when either of the parameters in null.
 * *
 * @throws java.lang.IllegalArgumentException
 * *         when the items is empty.
 * *
 * @throws com.fsk.common.utils.threads.ThreadException
 * *         when called from the UI thread.
 */
fun <T : DatabaseStorable> SQLiteDatabase.bulkDelete(items: List<T>) {
    require(items.isNotEmpty());

    val reference = this;

    performAtomicTransaction(
            object : TransactionActor {
                override fun perform() {
                    for (item in items) {
                        item.delete(reference);
                    }
                }
            }
                            )
}


/**
 * Save the item to the database.

 * @param item
 * *         the object to save to the database.
 * *
 * @throws java.lang.NullPointerException
 * *         when either of the parameters in null.
 * *
 * @throws com.fsk.common.utils.threads.ThreadException
 * *         when called from the UI thread.
 */
fun SQLiteDatabase.save(item: DatabaseStorable) {
    val reference = this;

    performAtomicTransaction(
            object : TransactionActor {
                override fun perform() {
                    item.save(reference);
                }
            }
                            )
}


/**
 * Delete the item data from the database.

 * @param item
 * *         the item to delete from the database.
 * *
 * @throws java.lang.NullPointerException
 * *         when either of the parameters in null.
 * *
 * @throws com.fsk.common.utils.threads.ThreadException
 * *         when called from the UI thread.
 */
fun SQLiteDatabase.delete(item: DatabaseStorable) {

    val reference = this;
    performAtomicTransaction(
            object : TransactionActor {
                override fun perform() {
                    item.delete(reference);
                }
            }
                            )
}

fun <T : TransactionActor> SQLiteDatabase.performAtomicTransaction(actor: T) {
    requireNotMainThread();

    try {
        beginTransaction();
        actor.perform();
        setTransactionSuccessful();
    }
    finally {
        endTransaction();
    }
}
