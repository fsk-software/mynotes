package com.fsk.common.database;


import com.fsk.common.utils.threads.ThreadException;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit Tests for the {@link DatabaseUtilities#bulkSave(List, android.database.sqlite.SQLiteDatabase)}.
 */
public class DatabaseUtilitiesBulkSaveListTest extends DatabaseUtilitiesBaseTest {

    /**
     * Test the method with null parameters.
     */
    @Test
    public void testNullParameters() {
        //Test for the null item.
        try {
            DatabaseUtilities.bulkSave((List) null, DatabaseHelper.getDatabase());
            assert false;
        }
        catch (NullPointerException e) {
        }

        //Test for the null database.
        try {
            DatabaseUtilities.bulkSave(new ArrayList<Item>(), null);
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
            DatabaseUtilities.bulkSave(new ArrayList<Item>(), DatabaseHelper.getDatabase());
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
        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

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
            DatabaseUtilities.bulkSave((List) null, null);

            assert false;
        }
        catch (ThreadException e) {
        }
    }
}
