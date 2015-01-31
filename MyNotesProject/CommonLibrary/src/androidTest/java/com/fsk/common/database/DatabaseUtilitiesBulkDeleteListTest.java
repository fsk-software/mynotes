package com.fsk.common.database;


import android.os.Message;

import com.fsk.common.threads.ThreadException;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit Tests for the {@link com.fsk.common.database.DatabaseUtilities#bulkDelete(java.util.List, android.database.sqlite.SQLiteDatabase)}.
 */
public class DatabaseUtilitiesBulkDeleteListTest extends DatabaseUtilitiesBaseTest {

    /**
     * Test the method with null parameters.
     */
    public void testNullParameters() {
        //Test for the null item.
        try {
            DatabaseUtilities.bulkDelete((List) null, DatabaseHelper.getDatabase());
            assert true;
        }
        catch (NullPointerException e) {
        }

        //Test for the null database.
        try {
            DatabaseUtilities.bulkDelete(new ArrayList<Item>(), null);
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
            DatabaseUtilities.bulkDelete(new ArrayList<Item>(), DatabaseHelper.getDatabase());
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
        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

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
                    DatabaseUtilities.bulkDelete((List) null, null);
                    assert true;
                }
                catch (ThreadException e) {
                }
            }
        }.sendMessage(new Message());
    }
}
