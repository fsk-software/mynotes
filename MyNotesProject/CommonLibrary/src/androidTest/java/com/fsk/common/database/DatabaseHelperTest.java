package com.fsk.common.database;


import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.test.AndroidTestCase;

/**
 * Unit Tests for the {@link com.fsk.common.database.DatabaseHelper}
 */
public class DatabaseHelperTest extends AndroidTestCase {


    private static final String UNINITIALIZED_MESSAGE = "Initialization of database required";


    private static final String DATABASE_NAME = "Database";


    private static final int DATABASE_VERSION = 1;


    private static final SQLiteDatabase.CursorFactory CURSOR_FACTORY =
            new SQLiteDatabase.CursorFactory() {

                @Override
                public Cursor newCursor(final SQLiteDatabase db,
                                        final SQLiteCursorDriver masterQuery,
                                        final String editTable, final SQLiteQuery query) {
                    return null;
                }
            };


    @Override
    public void setUp() throws Exception {
        super.setUp();
        DatabaseHelper.reset();
    }


    @Override
    public void tearDown() throws Exception {
        getContext().deleteDatabase(DATABASE_NAME);
        super.tearDown();
    }


    /**
     * Test the {@link com.fsk.common.database.DatabaseHelper#initialize(android.content.Context,
     * DatabaseModel)} exceptions.
     * <p/>
     * Also verifies that {@link DatabaseHelper#needsInitializing()} never returns true when the
     * initialize fails.
     */
    public void testInitializeFailures() {
        assertTrue(DatabaseHelper.needsInitializing());

        //Test both parameters are null.
        try {
            DatabaseHelper.initialize(null, null);
            assert true;
        }
        catch (NullPointerException e) {
            assertTrue(DatabaseHelper.needsInitializing());
        }

        //Test null context
        try {
            DatabaseHelper.initialize(null, new TestDatabaseModel());
            assert true;
        }
        catch (NullPointerException e) {
            assertTrue(DatabaseHelper.needsInitializing());
        }

        //Test null database
        try {
            DatabaseHelper.initialize(getContext(), null);
            assert true;
        }
        catch (NullPointerException e) {
            assertTrue(DatabaseHelper.needsInitializing());
        }
    }


    /**
     * Test {@link com.fsk.common.database.DatabaseHelper#initialize(android.content.Context,
     * DatabaseModel)} success.
     * <p/>
     * Also verifies that {@link DatabaseHelper#needsInitializing()} returns false until the
     * initialize succeeds.
     */
    public void testInitializeSuccess() {
        assertTrue(DatabaseHelper.needsInitializing());
        DatabaseHelper.initialize(getContext(), new TestDatabaseModel());
        assertFalse(DatabaseHelper.needsInitializing());
    }


    /**
     * Test {@link DatabaseHelper#getInstance()} in the sunny day case.
     */
    public void testGetInstanceSunnyDay() {
        //Test the success
        TestDatabaseModel expectedDatabase = new TestDatabaseModel();
        DatabaseHelper.initialize(getContext(), expectedDatabase);
        DatabaseHelper helper = DatabaseHelper.getInstance();
        assertNotNull(helper);

        SQLiteDatabase testDatabase = DatabaseHelper.getDatabase();
        assertEquals(expectedDatabase.getVersion(), testDatabase.getVersion());
    }


    /**
     * Test {@link DatabaseHelper#getInstance()} bore the helper is initialized.
     */
    public void testGetInstanceBeforeInitialization() {

        //Test the failure
        assertTrue(DatabaseHelper.needsInitializing());
        try {
            DatabaseHelper.getInstance();
            assert true;
        }
        catch (IllegalStateException e) {
            assertEquals(UNINITIALIZED_MESSAGE, e.getMessage());
        }
    }


    /**
     * Test {@link DatabaseHelper#getInstance()} in the sunny day case.
     */
    public void testGetDatabaseSunnyDay() {
        DatabaseModel databaseModel = new TestDatabaseModel();
        DatabaseHelper.initialize(getContext(), databaseModel);
        assertNotNull(DatabaseHelper.getDatabase());
    }


    /**
     * Verify that {@link DatabaseHelper#getInstance()}  returns false before the initialize
     * succeeds.
     */
    public void testGetDatabaseBeforeInitialization() {

        try {
            assertNull(DatabaseHelper.getDatabase());
        }
        catch (IllegalStateException e) {
            assertEquals(UNINITIALIZED_MESSAGE, e.getMessage());
        }
    }


    /**
     * Test {@link DatabaseHelper#onCreate(android.database.sqlite.SQLiteDatabase)} ()}.
     */
    public void testOnCreate() {
        TestDatabaseModel database = new TestDatabaseModel();
        DatabaseHelper.initialize(getContext(), database);
        DatabaseHelper helper = DatabaseHelper.getInstance();

        helper.onCreate(DatabaseHelper.getDatabase());
        assertEquals(true, database.mOnCreatedCalled);
    }


    /**
     * Test {@link DatabaseHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)} ()}.
     */
    public void testOnUpgrade() {
        TestDatabaseModel database = new TestDatabaseModel();
        DatabaseHelper.initialize(getContext(), database);
        DatabaseHelper helper = DatabaseHelper.getInstance();

        helper.onUpgrade(DatabaseHelper.getDatabase(), 0, 1);
        assertEquals(true, database.mOnUpgradeCalled);
        assertEquals(0, database.mOnUpgradeOldVersion);
        assertEquals(1, database.mOnUpgradeNewVersion);
    }


    /**
     * Test {@link DatabaseHelper#onDowngrade(android.database.sqlite.SQLiteDatabase, int, int)}
     * ()}.
     */
    public void testOnDowngrade() {
        TestDatabaseModel database = new TestDatabaseModel();
        DatabaseHelper.initialize(getContext(), database);
        DatabaseHelper helper = DatabaseHelper.getInstance();

        helper.onDowngrade(DatabaseHelper.getDatabase(), 1, 0);
        assertEquals(true, database.mOnDowngradeCalled);
        assertEquals(1, database.mOnDowngradeOldVersion);
        assertEquals(0, database.mOnDowngradeNewVersion);
    }


    /**
     * Simple mock database model to use for testing the {@link com.fsk.common.database
     * .DatabaseHelper}.
     */
    private static class TestDatabaseModel implements DatabaseModel {

        boolean mOnCreatedCalled;


        boolean mOnUpgradeCalled;


        int mOnUpgradeOldVersion;


        int mOnUpgradeNewVersion;


        boolean mOnDowngradeCalled;


        int mOnDowngradeNewVersion;


        int mOnDowngradeOldVersion;


        @Override
        public void onCreate(final SQLiteDatabase db) {
            mOnCreatedCalled = true;
        }


        @Override
        public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
            mOnUpgradeCalled = true;
            mOnUpgradeNewVersion = newVersion;
            mOnDowngradeOldVersion = oldVersion;
        }


        @Override
        public void onDowngrade(final SQLiteDatabase db, final int oldVersion,
                                final int newVersion) {

            mOnDowngradeCalled = true;
            mOnDowngradeNewVersion = newVersion;
            mOnDowngradeOldVersion = oldVersion;

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
            return CURSOR_FACTORY;
        }
    }
}
