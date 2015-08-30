package com.fsk.mynotes.services;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;

import com.fsk.common.database.DatabaseHelper;
import com.fsk.mynotes.constants.NoteExtraKeys;
import com.fsk.mynotes.data.Note;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ DatabaseHelper.class, SQLiteDatabase.class, Intent.class, DeleteNoteService.class})
public class DeleteNoteServiceTest {

    @Mock
    private Context mMockContext;

    @Mock
    private Intent mMockIntent;

    @Mock
    private Note mMockNote;

    @Mock
    private SQLiteDatabase mMockDatabase;


    @Before
    public void prepareMocks() throws Exception {
        PowerMockito.whenNew(Intent.class)
                    .withArguments(mMockContext, DeleteNoteService.class)
                    .thenReturn(mMockIntent);
        when(mMockIntent.putExtra(eq(NoteExtraKeys.NOTE_KEY), (Parcelable) anyObject())).thenReturn(mMockIntent);
        when(mMockContext.startService(mMockIntent)).thenReturn(new ComponentName("test", "test"));
        when(mMockIntent.getParcelableExtra(NoteExtraKeys.NOTE_KEY)).thenReturn(mMockNote);

        doNothing().when(mMockNote).delete(mMockDatabase);
        PowerMockito.mockStatic(DatabaseHelper.class);
        when(DatabaseHelper.getDatabase()).thenReturn(mMockDatabase);

    }

    @Test
    public void testStaticCreatorFailures() {
        try {
            DeleteNoteService.startService(null, mMockNote);
            assert false;
        }
        catch (NullPointerException e) {}

        try {
            DeleteNoteService.startService(mMockContext, null);
            assert false;
        }
        catch (NullPointerException e) {}

        try {
            DeleteNoteService.startService(mMockContext, null);
            assert false;
        }
        catch (NullPointerException e) {}

        try {
            DeleteNoteService.startService(null, null);
            assert false;
        }
        catch (NullPointerException e) {}
    }

    @Test
    public void testStaticCreatorSuccess() {
        DeleteNoteService.startService(mMockContext, mMockNote);
        verify(mMockContext).startService(mMockIntent);
        verify(mMockIntent).putExtra(NoteExtraKeys.NOTE_KEY, mMockNote);
    }


    @Test
    public void testHandleIntent() {
        DeleteNoteService unitUnderTest = new DeleteNoteService();
        unitUnderTest.onHandleIntent(mMockIntent);

        verify(mMockIntent).getParcelableExtra(NoteExtraKeys.NOTE_KEY);
        verify(mMockNote).delete(mMockDatabase);


    }
}