package com.fsk.common.database;


import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit Tests for the {@link DatabaseUtilitiesTest}
 */
public class DatabaseUtilitiesTest extends AndroidTestCase {

    /**
     * A generator to increment a global key value on each save.
     */
    private int mGlobalSaveKey = 0;


    @Override
    public void setUp() throws Exception {
        super.setUp();
        DatabaseHelper.initialize(getContext(), new MockDatabaseModel());
    }


    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }


    /**
     * Test the {@link com.fsk.common.database.DatabaseUtilities#buildQueryQuestionMarkString(int)}
     * method.
     */
    public void testBuildQueryQuestionMarkString() {
        //Test the case where the count is negative.
        String testString = DatabaseUtilities.buildQueryQuestionMarkString(-1);
        assertEquals("", testString);

        //Test the case where the count is zero.
        testString = DatabaseUtilities.buildQueryQuestionMarkString(0);
        assertEquals("", testString);

        //Test the case where the count is one.
        testString = DatabaseUtilities.buildQueryQuestionMarkString(1);
        assertEquals("?", testString);


        //Test the case where the count is five.
        testString = DatabaseUtilities.buildQueryQuestionMarkString(5);
        assertEquals("?, ?, ?, ?, ?", testString);
    }


    /**
     * Test the {@link com.fsk.common.database.DatabaseUtilities#save(DatabaseStorable,
     * android.database.sqlite.SQLiteDatabase)} method.
     */
    public void testSave() {
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


        //Test for the normal case
        Item item = new Item();
        assertTrue(item.mKey == -1);
        DatabaseUtilities.save(item, DatabaseHelper.getDatabase());
        assertTrue(item.mKey >= 0);
    }


    /**
     * Test the {@link com.fsk.common.database.DatabaseUtilities#bulkSave(DatabaseStorable[],
     * android.database.sqlite.SQLiteDatabase)} method.
     */
    public void testBulkArraySave() {
        //Test for the null item.
        try {
            DatabaseUtilities.bulkSave((DatabaseStorable[]) null, DatabaseHelper.getDatabase());
            assert true;
        }
        catch (NullPointerException e) {
        }

        //Test for the empty items.
        try {
            Item[] items = new Item[0];
            DatabaseUtilities.bulkSave(items, DatabaseHelper.getDatabase());
            assert true;
        }
        catch (IllegalArgumentException e) {
        }

        //Test for the null database.
        try {
            Item[] items = new Item[] { new Item() };
            DatabaseUtilities.bulkSave(items, null);
            assert true;
        }
        catch (NullPointerException e) {
        }


        //Test for the normal case
        Item item1 = new Item();
        Item item2 = new Item();
        Item[] items = { item1, item2 };

        assertTrue(item1.mKey == -1);
        assertTrue(item2.mKey == -1);

        DatabaseUtilities.bulkSave(items, DatabaseHelper.getDatabase());
        assertTrue(item1.mKey >= 0);
        assertTrue(item2.mKey >= 0);
        assertTrue(item2.mKey != item1.mKey);
    }


    /**
     * Test the {@link com.fsk.common.database.DatabaseUtilities#bulkSave(java.util.List,
     * android.database.sqlite.SQLiteDatabase)} method.
     */
    public void testBulkListSave() {
        //Test for the null item.
        try {
            DatabaseUtilities.bulkSave((List<DatabaseStorable>) null, DatabaseHelper.getDatabase());
            assert true;
        }
        catch (NullPointerException e) {
        }

        //Test for the empty items.
        try {
            List<Item> items = new ArrayList<>();
            DatabaseUtilities.bulkSave(items, DatabaseHelper.getDatabase());
            assert true;
        }
        catch (IllegalArgumentException e) {
        }

        //Test for the null database.
        try {
            List<Item> items = new ArrayList<>();
            items.add(new Item());
            DatabaseUtilities.bulkSave(items, null);
            assert true;
        }
        catch (NullPointerException e) {
        }


        //Test for the normal case
        Item item1 = new Item();
        Item item2 = new Item();
        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        assertTrue(item1.mKey == -1);
        assertTrue(item2.mKey == -1);

        DatabaseUtilities.bulkSave(items, DatabaseHelper.getDatabase());
        assertTrue(item1.mKey >= 0);
        assertTrue(item2.mKey >= 0);
        assertTrue(item2.mKey != item1.mKey);
    }


    /**
     * Test the {@link com.fsk.common.database.DatabaseUtilities#delete(DatabaseStorable,
     * android.database.sqlite.SQLiteDatabase)} method.
     */
    public void testDelete() {
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


        //Test for the normal case :store an item and then delete it.
        Item item = new Item();
        assertTrue(item.mKey == -1);
        DatabaseUtilities.save(item, DatabaseHelper.getDatabase());
        assertTrue(item.mKey >= 0);

        DatabaseUtilities.delete(item, DatabaseHelper.getDatabase());
        assertTrue(item.mKey == -1);
    }


    /**
     * Test the {@link com.fsk.common.database.DatabaseUtilities#bulkDelete (DatabaseStorable[],
     * android.database.sqlite.SQLiteDatabase)} method.
     */
    public void testBulkArrayDelete() {
        //Test for the null item.
        try {
            DatabaseUtilities.bulkDelete((DatabaseStorable[]) null, DatabaseHelper.getDatabase());
            assert true;
        }
        catch (NullPointerException e) {
        }

        //Test for the empty items.
        try {
            Item[] items = new Item[0];
            DatabaseUtilities.bulkDelete(items, DatabaseHelper.getDatabase());
            assert true;
        }
        catch (IllegalArgumentException e) {
        }

        //Test for the null database.
        try {
            Item[] items = new Item[] { new Item() };
            DatabaseUtilities.bulkDelete(items, null);
            assert true;
        }
        catch (NullPointerException e) {
        }


        //Test for the normal case : first save the data and then delete it.
        Item item1 = new Item();
        Item item2 = new Item();
        Item[] items = { item1, item2 };

        assertTrue(item1.mKey == -1);
        assertTrue(item2.mKey == -1);

        DatabaseUtilities.bulkSave(items, DatabaseHelper.getDatabase());
        assertTrue(item1.mKey >= 0);
        assertTrue(item2.mKey >= 0);
        assertTrue(item2.mKey != item1.mKey);

        //delete it
        DatabaseUtilities.bulkDelete(items, DatabaseHelper.getDatabase());
        assertTrue(item1.mKey == -1);
        assertTrue(item2.mKey == -1);
    }


    /**
     * Test the {@link com.fsk.common.database.DatabaseUtilities#bulkDelete(java.util.List,
     * android.database.sqlite.SQLiteDatabase)} method.
     */
    public void testBulkListDelete() {
        //Test for the null item.
        try {
            DatabaseUtilities
                    .bulkDelete((List<DatabaseStorable>) null, DatabaseHelper.getDatabase());
            assert true;
        }
        catch (NullPointerException e) {
        }

        //Test for the empty items.
        try {
            List<Item> items = new ArrayList<>();
            DatabaseUtilities.bulkDelete(items, DatabaseHelper.getDatabase());
            assert true;
        }
        catch (IllegalArgumentException e) {
        }

        //Test for the null database.
        try {
            List<Item> items = new ArrayList<>();
            items.add(new Item());
            DatabaseUtilities.bulkDelete(items, null);
            assert true;
        }
        catch (NullPointerException e) {
        }


        //Test for the normal case: save the items and then delete them.
        Item item1 = new Item();
        Item item2 = new Item();
        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        assertTrue(item1.mKey == -1);
        assertTrue(item2.mKey == -1);

        DatabaseUtilities.bulkSave(items, DatabaseHelper.getDatabase());
        assertTrue(item1.mKey >= 0);
        assertTrue(item2.mKey >= 0);
        assertTrue(item2.mKey != item1.mKey);

        //now delete them
        DatabaseUtilities.bulkDelete(items, DatabaseHelper.getDatabase());
        assertTrue(item1.mKey == -1);
        assertTrue(item2.mKey == -1);
    }


    /**
     * An item to test the save/delete methods of {@link com.fsk.common.database.DatabaseUtilities}.
     */
    private class Item implements DatabaseStorable {
        long mKey = -1;


        @Override
        public void save(final SQLiteDatabase db) {
            mKey = mGlobalSaveKey++;
        }


        @Override
        public void delete(final SQLiteDatabase db) {
            mKey = -1;
        }
    }

    /**
     * A small test database for testing the {@link com.fsk.common.database.DatabaseUtilities}
     */
    public class MockDatabaseModel implements DatabaseModel {

        /**
         * The database schema version.
         */
        public static final int DATABASE_VERSION = 1;


        /**
         * The name of the database.
         */
        public static final String DATABASE_NAME = "unit_test";


        @Override
        public void onCreate(SQLiteDatabase db) {
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        }


        @Override
        public void onDowngrade(SQLiteDatabase db, int arg1, int arg2) {
        }


        @Override
        public String getName() {
            return DATABASE_NAME;
        }


        @Override
        public int getVersion() {
            return DATABASE_VERSION;
        }


        @Override
        public SQLiteDatabase.CursorFactory getCursorFactory() {
            return null;
        }
    }
}
