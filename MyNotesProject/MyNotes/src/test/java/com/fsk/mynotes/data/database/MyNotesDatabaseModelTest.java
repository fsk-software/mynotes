package com.fsk.mynotes.data.database;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;

import com.fsk.common.database.CommonTerms;
import com.fsk.common.threads.ThreadUtils;
import com.fsk.mynotes.MyNotesApplication;
import com.fsk.mynotes.data.cache.NoteFilterPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test the {@link NoteFilterPreferences}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({LocalBroadcastManager.class, ThreadUtils.class, MyNotesApplication.class, Intent.class})
public class MyNotesDatabaseModelTest {

    @Mock
    private SQLiteDatabase mMockDatabase;


    @Before
    public void prepareForTest() throws Exception {
        doNothing().when(mMockDatabase).beginTransaction();
        doNothing().when(mMockDatabase).execSQL(anyString());
        doNothing().when(mMockDatabase).setTransactionSuccessful();
        doNothing().when(mMockDatabase).endTransaction();
    }


    @Test
    public void testOnCreate() {
        MyNotesDatabaseModel unitUnderTest = new MyNotesDatabaseModel();
        unitUnderTest.onCreate(mMockDatabase);
        verify(mMockDatabase).beginTransaction();

        for (String cmd : MyNotesDatabaseModel.CREATE_COMMANDS) {
            verify(mMockDatabase).execSQL(cmd);
        }

        verify(mMockDatabase).setTransactionSuccessful();
        verify(mMockDatabase).endTransaction();
    }

    @Test
    public void testOnUpgrade() {
        MyNotesDatabaseModel unitUnderTest = new MyNotesDatabaseModel();
        unitUnderTest.onUpgrade(mMockDatabase, 0, 1);

        verify(mMockDatabase, times(2)).beginTransaction();

        for (String cmd : MyNotesDatabaseModel.CREATE_COMMANDS) {
            verify(mMockDatabase).execSQL(cmd);
        }

        for (String table : MyNotesDatabaseModel.TABLES) {
            verify(mMockDatabase).execSQL(String.format(CommonTerms.DROP_TABLE_IF_EXISTS, table));
        }

        verify(mMockDatabase, times(2)).setTransactionSuccessful();
        verify(mMockDatabase, times(2)).endTransaction();
    }


    @Test
    public void testOnDowngrade() {
        MyNotesDatabaseModel unitUnderTest = new MyNotesDatabaseModel();
        unitUnderTest.onDowngrade(mMockDatabase, 0, 1);

        verify(mMockDatabase, times(2)).beginTransaction();

        for (String cmd : MyNotesDatabaseModel.CREATE_COMMANDS) {
            verify(mMockDatabase).execSQL(cmd);
        }

        for (String table : MyNotesDatabaseModel.TABLES) {
            verify(mMockDatabase).execSQL(String.format(CommonTerms.DROP_TABLE_IF_EXISTS, table));
        }

        verify(mMockDatabase, times(2)).setTransactionSuccessful();
        verify(mMockDatabase, times(2)).endTransaction();
    }



    @Test
    public void testGetters() {
        MyNotesDatabaseModel unitUnderTest = new MyNotesDatabaseModel();
        assertThat(unitUnderTest.getName(), is(MyNotesDatabaseModel.DATABASE_NAME));
        assertThat(unitUnderTest.getVersion(), is(MyNotesDatabaseModel.DATABASE_VERSION));
        assertThat(unitUnderTest.getCursorFactory(), is(nullValue()));
    }
}