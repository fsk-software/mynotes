package com.fsk.common.database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * Unit Tests for the {@link DatabaseHelper}
 */

@RunWith(PowerMockRunner.class)
public class DatabaseKtHelperTest {


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


    @Mock
    Context mMockContent;

    /**
     * Test the {@link DatabaseHelper#initialize(android.content.Context,
     * DatabaseModel)} exceptions.
     * <p/>
     * Also verifies that {@link DatabaseHelper#needsInitializing()} never returns true when the
     * initialize fails.
     */
    @Test
    public void testInitializeFailures() {
        assertThat(DatabaseHelper.needsInitializing(), is(true));

        //Test both parameters are null.
        try {
            DatabaseHelper.initialize(null, null);
            assert false;
        }
        catch (NullPointerException e) {
            assertThat(DatabaseHelper.needsInitializing(), is(true));
        }

        //Test null context
        try {
            DatabaseHelper.initialize(null, new TestDatabaseModel());
            assert false;
        }
        catch (NullPointerException e) {
            assertThat(DatabaseHelper.needsInitializing(), is(true));
        }

        //Test null database
        try {
            DatabaseHelper.initialize(mMockContent, null);
            assert false;
        }
        catch (NullPointerException e) {
            assertThat(DatabaseHelper.needsInitializing(), is(true));
        }
    }


    /**
     * Test {@link DatabaseHelper#initialize(android.content.Context,
     * DatabaseModel)} success.
     * <p/>
     * Also verifies that {@link DatabaseHelper#needsInitializing()} returns false until the
     * initialize succeeds.
     */
    public void testInitializeSuccess() {
        assertThat(DatabaseHelper.needsInitializing(), is(true));
        DatabaseHelper.initialize(mMockContent, new TestDatabaseModel());
        assertThat(DatabaseHelper.needsInitializing(), is(false));
    }


    /**
     * Test {@link DatabaseHelper#getInstance()} in the sunny day case.
     */
    public void testGetInstanceSunnyDay() {
        //Test the success
        TestDatabaseModel expectedDatabase = new TestDatabaseModel();
        DatabaseHelper.initialize(mMockContent, expectedDatabase);
        DatabaseHelper helper = DatabaseHelper.getInstance();
        assertThat(helper, is(notNullValue()));

        SQLiteDatabase testDatabase = DatabaseHelper.getDatabase();
        assertThat(expectedDatabase.getVersion(), is(testDatabase.getVersion()));
    }


    /**
     * Test {@link DatabaseHelper#getInstance()} bore the helper is initialized.
     */
    public void testGetInstanceBeforeInitialization() {

        //Test the failure
        assertThat(DatabaseHelper.needsInitializing(), is(true));
        try {
            DatabaseHelper.getInstance();
            assert false;
        }
        catch (IllegalStateException e) {
            assertThat(UNINITIALIZED_MESSAGE, is(e.getMessage()));
        }
    }


    /**
     * Test {@link DatabaseHelper#getInstance()} in the sunny day case.
     */
    public void testGetDatabaseSunnyDay() {
        DatabaseModel databaseModel = new TestDatabaseModel();
        DatabaseHelper.initialize(mMockContent, databaseModel);
        assertThat(DatabaseHelper.getDatabase(), is(notNullValue()));
    }


    /**
     * Verify that {@link DatabaseHelper#getInstance()}  returns false before the initialize
     * succeeds.
     */
    public void testGetDatabaseBeforeInitialization() {

        try {
            assertThat(DatabaseHelper.getDatabase(), is(nullValue()));
        }
        catch (IllegalStateException e) {
            assertThat(UNINITIALIZED_MESSAGE, is(e.getMessage()));
        }
    }


    /**
     * Test {@link DatabaseHelper#onCreate(SQLiteDatabase)} ()}.
     */
    public void testOnCreate() {
        TestDatabaseModel database = new TestDatabaseModel();
        DatabaseHelper.initialize(mMockContent, database);
        DatabaseHelper helper = DatabaseHelper.getInstance();

        helper.onCreate(DatabaseHelper.getDatabase());
        assertThat(true, is(database.mOnCreatedCalled));
    }


    /**
     * Test {@link DatabaseHelper#onUpgrade(SQLiteDatabase, int, int)} ()}.
     */
    public void testOnUpgrade() {
        TestDatabaseModel database = new TestDatabaseModel();
        DatabaseHelper.initialize(mMockContent, database);
        DatabaseHelper helper = DatabaseHelper.getInstance();

        helper.onUpgrade(DatabaseHelper.getDatabase(), 0, 1);
        assertThat(database.mOnUpgradeCalled, is(true));
        assertThat(database.mOnUpgradeOldVersion, is(0));
        assertThat(database.mOnUpgradeNewVersion, is(1));
    }


    /**
     * Test {@link DatabaseHelper#onDowngrade(SQLiteDatabase, int, int)}
     * ()}.
     */
    public void testOnDowngrade() {
        TestDatabaseModel database = new TestDatabaseModel();
        DatabaseHelper.initialize(mMockContent, database);
        DatabaseHelper helper = DatabaseHelper.getInstance();

        helper.onDowngrade(DatabaseHelper.getDatabase(), 1, 0);
        assertThat(database.mOnUpgradeCalled, is(true));
        assertThat(database.mOnUpgradeOldVersion, is(1));
        assertThat(database.mOnUpgradeNewVersion, is(0));
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
