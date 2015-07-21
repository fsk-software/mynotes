package com.fsk.common.database;


import com.fsk.common.threads.ThreadException;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit Tests for the {@link DatabaseUtilities#save(DatabaseStorable, android.database.sqlite.SQLiteDatabase)}.
 */
public class DatabaseUtilitiesSingleItemSaveTest extends DatabaseUtilitiesBaseTest {

    /**
     * Test the method with null parameters.
     */
    @Test
    public void testNullParameters() {
        //Test for the null item.
        try {
            DatabaseUtilities.save(null, DatabaseHelper.getDatabase());
            assert false;
        }
        catch (NullPointerException e) {
        }

        //Test for the null database.
        try {
            DatabaseUtilities.save(new Item(), null);
            assert false;
        }
        catch (NullPointerException e) {
        }
    }


    /**
     * Test the method with valid parameters.
     */
    @Test
    public void testSunnyDay() {
        //create and validate test data.
        Item item1 = new Item();
        assertThat(item1.mKey, is(-1L));

        //Save the data
        DatabaseUtilities.save(item1, DatabaseHelper.getDatabase());
        assertThat(item1.mKey >= 0, is(true));
    }


    @Override
    public void testCallOnUIThread() {

        try {
            configureThreadCheckMock(true);
            DatabaseUtilities.save(null, null);

            assert false;
        }
        catch (ThreadException e) {
        }
    }
}
