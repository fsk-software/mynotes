package com.fsk.common.database;


import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.test.AndroidTestCase;

/**
 * Unit Tests for the {@link com.fsk.common.database.DatabaseUtilitiesBaseTest}
 */
public abstract class DatabaseUtilitiesBaseTest extends AndroidTestCase {

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
     * Test the {@link DatabaseUtilities#buildQueryQuestionMarkString(int)}
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
     * Verify that the method response to running on the UI thread.
     */
    public abstract void testCallOnUIThread();

    /**
     * An item to test the save/delete methods of {@link DatabaseUtilities}.
     */
    protected class Item implements DatabaseStorable {
        protected long mKey = -1;


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
     * A small test database for testing the {@link DatabaseUtilities}
     */
    protected class MockDatabaseModel implements DatabaseModel {

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

    /**
     * A handler class to test running the database methods on the UI thread.
     */
    protected class UIThreadHandler extends Handler {

        /**
         * Constructor.
         */
        public UIThreadHandler () {
            super(Looper.getMainLooper());
        }
    }
}
