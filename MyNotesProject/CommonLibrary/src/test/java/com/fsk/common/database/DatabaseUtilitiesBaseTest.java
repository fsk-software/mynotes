package com.fsk.common.database;


import android.database.sqlite.SQLiteDatabase;

import com.fsk.common.threads.ThreadUtils;
import com.fsk.common.threads.ThreadException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Unit Tests for the {@link com.fsk.common.database.DatabaseUtilitiesBaseTest}
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ThreadUtils.class, DatabaseHelper.class})
public abstract class DatabaseUtilitiesBaseTest {

    /**
     * A generator to increment a global key value on each save.
     */
    private int mGlobalSaveKey = 0;

    @Mock
    SQLiteDatabase mMockDatabase;

    @Before
    public void prepareMocks() {
        PowerMockito.mockStatic(ThreadUtils.class);
        PowerMockito.mockStatic(DatabaseHelper.class);
        when(DatabaseHelper.getDatabase()).thenReturn(mMockDatabase);
        when(DatabaseHelper.needsInitializing()).thenReturn(false);
    }

    /**
     * Test the {@link DatabaseUtilities#buildQueryQuestionMarkString(int)}
     * method.
     */
    @Test
    public void testBuildQueryQuestionMarkString() {
        //Test the case where the count is negative.
        String testString = DatabaseUtilities.buildQueryQuestionMarkString(-1);
        assertThat(testString.isEmpty(), is(true));

        //Test the case where the count is zero.
        testString = DatabaseUtilities.buildQueryQuestionMarkString(0);
        assertThat(testString.isEmpty(), is(true));

        //Test the case where the count is one.
        testString = DatabaseUtilities.buildQueryQuestionMarkString(1);
        assertThat(testString, is("?"));


        //Test the case where the count is five.
        testString = DatabaseUtilities.buildQueryQuestionMarkString(5);
        assertThat(testString, is("?, ?, ?, ?, ?"));
    }


    /**
     * Verify that the method response to running on the UI thread.
     */
    @Test
    public abstract void testCallOnUIThread();


    protected void configureThreadCheckMock(final boolean failCheck) {
        if (failCheck) {
            doThrow(new ThreadException()).when(ThreadUtils.class);
            new ThreadUtils().checkOffUIThread();
        }
        else {
            doNothing().when(ThreadUtils.class);
            new ThreadUtils().checkOffUIThread();
        }
    }


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
}
