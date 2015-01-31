package com.fsk.common.database;


import android.os.Message;

import com.fsk.common.threads.ThreadException;

/**
 * Unit Tests for the {@link com.fsk.common.database.DatabaseUtilities#save(com.fsk.common.database.DatabaseStorable, android.database.sqlite.SQLiteDatabase)}.
 */
public class DatabaseUtilitiesSingleItemSaveTest extends DatabaseUtilitiesBaseTest {

    /**
     * Test the method with null parameters.
     */
    public void testNullParameters() {
        //Test for the null item.
        try {
            DatabaseUtilities.save(null, DatabaseHelper.getDatabase());
            assert true;
        }
        catch (NullPointerException e) {
        }

        //Test for the null database.
        try {
            DatabaseUtilities.save(new Item(), null);
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

        //Save the data
        DatabaseUtilities.save(item1, DatabaseHelper.getDatabase());
        assertTrue(item1.mKey >= 0);
    }


    @Override
    public void testCallOnUIThread() {
        new UIThreadHandler() {
            @Override
            public void handleMessage(final Message msg) {
                super.handleMessage(msg);

                try {
                    DatabaseUtilities.save(null, null);
                    assert true;
                }
                catch (ThreadException e) {
                }
            }
        }.sendMessage(new Message());
    }
}
