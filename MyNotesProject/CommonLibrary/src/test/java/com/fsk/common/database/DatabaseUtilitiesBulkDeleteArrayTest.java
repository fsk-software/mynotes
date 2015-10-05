package com.fsk.common.database;


import com.fsk.common.utils.threads.ThreadException;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Unit Tests for the {@link DatabaseUtilities#bulkDelete (DatabaseStorable[],
 * android.database.sqlite.SQLiteDatabase)}
 */

public class DatabaseUtilitiesBulkDeleteArrayTest extends DatabaseUtilitiesBaseTest {

    /**
     * Test the method with null parameters.
     */
    @Test
    public void testNullParameters() {
        //Test for the null item.
        try {
            DatabaseUtilities.bulkDelete((DatabaseStorable[]) null, DatabaseHelper.getDatabase());
            assert false;
        }
        catch (NullPointerException e) {
        }

        //Test for the null database.
        try {
            Item[] items = new Item[] { new Item() };
            DatabaseUtilities.bulkDelete(items, null);
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
            DatabaseUtilities.bulkDelete(items, DatabaseHelper.getDatabase());
            assert false;
        }
        catch (IllegalArgumentException e) {
        }
    }


    /**
     * Test the method with valid parameters.
     */
    @Test
    public void testSunnyDay() {
        //create and validate test data.
        Item item1 = new Item();
        Item item2 = new Item();
        Item[] items = { item1, item2 };

        assertThat(item1.mKey, is(-1L));
        assertThat(item2.mKey, is(-1L));

        //delete items that are not saved yet.
        DatabaseUtilities.bulkDelete(items, DatabaseHelper.getDatabase());
        assertThat(item1.mKey, is(-1L));
        assertThat(item2.mKey, is(-1L));

        //Save the data
        DatabaseUtilities.bulkSave(items, DatabaseHelper.getDatabase());
        assertThat(item1.mKey >= 0, is(true));
        assertThat(item2.mKey >= 0, is(true));
        assertThat(item2.mKey, not(item1.mKey));

        //Delete the saved data.
        DatabaseUtilities.bulkDelete(items, DatabaseHelper.getDatabase());
        assertThat(item1.mKey, is(-1L));
        assertThat(item2.mKey, is(-1L));
    }


    @Override
    public void testCallOnUIThread() {
        try {
            configureThreadCheckMock(true);
            DatabaseUtilities.bulkDelete((DatabaseStorable[]) null, DatabaseHelper.getDatabase());
            assert false;
        }
        catch (ThreadException e) {
        }
    }
}
