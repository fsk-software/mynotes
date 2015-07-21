package com.fsk.common.database;


import com.fsk.common.threads.ThreadException;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit Tests for the {@link DatabaseUtilities#bulkSave(DatabaseStorable[], android.database.sqlite.SQLiteDatabase)}
 */
public class DatabaseUtilitiesBulkSaveArrayTest extends DatabaseUtilitiesBaseTest {

    /**
     * Test the method with null parameters.
     */
    @Test
    public void testNullParameters() {
        //Test for the null item.
        try {
            DatabaseUtilities.bulkSave((DatabaseStorable[]) null, DatabaseHelper.getDatabase());
            assert false;
        }
        catch (NullPointerException e) {
        }

        //Test for the null database.
        try {
            Item[] items = new Item[] { new Item() };
            DatabaseUtilities.bulkSave(items, null);
            assert false;
        }
        catch (NullPointerException e) {
        }

    }


    /**
     * Test the method with empty items.
     */
    @Test
    public void testEmptyItems() {
        try {
            Item[] items = new Item[0];
            DatabaseUtilities.bulkSave(items, DatabaseHelper.getDatabase());
            assert false;
        }
        catch (IllegalArgumentException e) {
        }
    }


    /**
     * Test the with valid parameters.
     */
    @Test
    public void testSunnyDay() {
        //create and validate test data.
        Item item1 = new Item();
        Item item2 = new Item();
        Item[] items = { item1, item2 };

        assertThat(item1.mKey, is(-1L));
        assertThat(item2.mKey, is(-1L));

        //Save the data
        DatabaseUtilities.bulkSave(items, DatabaseHelper.getDatabase());
        assertThat(item1.mKey >= 0, is(true));
        assertThat(item2.mKey >= 0, is(true));
        assertThat(item2.mKey, not(item1.mKey));
    }


    @Override
    public void testCallOnUIThread() {

        try {
            configureThreadCheckMock(true);
            DatabaseUtilities.bulkSave((DatabaseStorable[]) null, DatabaseHelper.getDatabase());

            assert false;
        }
        catch (ThreadException e) {
        }
    }
}
