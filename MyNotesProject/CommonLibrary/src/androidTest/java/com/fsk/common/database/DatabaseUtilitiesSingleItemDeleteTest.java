package com.fsk.common.database;


import android.os.Message;

import com.fsk.common.threads.ThreadException;

/**
 * Unit Tests for the {@link DatabaseUtilities#delete(DatabaseStorable, android.database.sqlite.SQLiteDatabase)}.
 */
public class DatabaseUtilitiesSingleItemDeleteTest extends DatabaseUtilitiesBaseTest {

    /**
     * Test the method with null parameters.
     */
    public void testNullParameters() {
        //Test for the null item.
        try {
            DatabaseUtilities.delete(null, DatabaseHelper.getDatabase());
            assert true;
        }
        catch (NullPointerException e) {
        }

        //Test for the null database.
        try {
            DatabaseUtilities.delete(new Item(), null);
            assert true;
        }
        catch (NullPointerException e) {
        }
    }


    /**
     * Test the method with valid parameters.
     */
    public void testSunnyDay() {
        //create and validate test data.
        Item item1 = new Item();
        assertTrue(item1.mKey == -1);

        //delete item before it saved
        DatabaseUtilities.delete(item1, DatabaseHelper.getDatabase());
        assertTrue(item1.mKey == -1);

        //Save the data
        DatabaseUtilities.save(item1, DatabaseHelper.getDatabase());
        assertTrue(item1.mKey >= 0);

        //Delete the saved data.
        DatabaseUtilities.delete(item1, DatabaseHelper.getDatabase());
        assertTrue(item1.mKey == -1);
    }


    @Override
    public void testCallOnUIThread() {
        new UIThreadHandler() {
            @Override
            public void handleMessage(final Message msg) {
                super.handleMessage(msg);

                try {
                    DatabaseUtilities.delete(null, null);
                    assert true;
                }
                catch (ThreadException e) {
                }
            }
        }.sendMessage(new Message());
    }
}
