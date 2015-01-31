package com.fsk.common.database;


import android.os.Message;

import com.fsk.common.threads.ThreadException;

/**
 * Unit Tests for the {@link com.fsk.common.database.DatabaseUtilities#bulkDelete
 * (DatabaseStorable[],
 * android.database.sqlite.SQLiteDatabase)}
 */
public class DatabaseUtilitiesBulkDeleteArrayTest extends DatabaseUtilitiesBaseTest {

    /**
     * Test the method with null parameters.
     */
    public void testNullParameters() {
        //Test for the null item.
        try {
            DatabaseUtilities.bulkDelete((DatabaseStorable[]) null, DatabaseHelper.getDatabase());
            assert true;
        }
        catch (NullPointerException e) {
        }

        //Test for the null database.
        try {
            Item[] items = new Item[] { new Item() };
            DatabaseUtilities.bulkDelete(items, null);
            assert true;
        }
        catch (NullPointerException e) {
        }

    }


    /**
     * Test the method with empty items.
     */
    public void testEmptyItems() {
        try {
            Item[] items = new Item[0];
            DatabaseUtilities.bulkDelete(items, DatabaseHelper.getDatabase());
            assert true;
        }
        catch (IllegalArgumentException e) {
        }
    }


    /**
     * Test the method with valid parameters.
     */
    public void testSunnyDay() {
        //create and validate test data.
        Item item1 = new Item();
        Item item2 = new Item();
        Item[] items = { item1, item2 };

        assertTrue(item1.mKey == -1);
        assertTrue(item2.mKey == -1);

        //delete items that are not saved yet.
        DatabaseUtilities.bulkDelete(items, DatabaseHelper.getDatabase());
        assertTrue(item1.mKey == -1);
        assertTrue(item2.mKey == -1);

        //Save the data
        DatabaseUtilities.bulkSave(items, DatabaseHelper.getDatabase());
        assertTrue(item1.mKey >= 0);
        assertTrue(item2.mKey >= 0);
        assertTrue(item2.mKey != item1.mKey);

        //Delete the saved data.
        DatabaseUtilities.bulkDelete(items, DatabaseHelper.getDatabase());
        assertTrue(item1.mKey == -1);
        assertTrue(item2.mKey == -1);
    }


    @Override
    public void testCallOnUIThread() {
        new UIThreadHandler() {
            @Override
            public void handleMessage(final Message msg) {
                super.handleMessage(msg);

                try {
                    DatabaseUtilities
                            .bulkDelete((DatabaseStorable[]) null, DatabaseHelper.getDatabase());
                    assert true;
                }
                catch (ThreadException e) {
                }
            }
        }.sendMessage(new Message());
    }
}
