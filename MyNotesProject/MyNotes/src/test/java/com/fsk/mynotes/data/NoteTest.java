package com.fsk.mynotes.data;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import com.fsk.common.database.DatabaseHelper;
import com.fsk.common.utils.threads.ThreadUtils;
import com.fsk.mynotes.MyNotesApplication;
import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.data.database.MyNotesDatabaseModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalMatchers;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Observable;
import java.util.Observer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test the {@link NoteAttributes class}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ DatabaseHelper.class, SQLiteDatabase.class, ContentValues.class,
        ThreadUtils.class, MyNotesApplication.class, Note.class, NoteAttributes.class })
public class NoteTest {

    @Mock
    private Parcel mMockParcel;


    @Mock
    private SQLiteDatabase mMockDatabase;


    @Mock
    private ThreadUtils mMockThreadUtils;


    @Mock
    private ContentValues mMockContentValues;


    @Mock
    private Observer mMockObserver;


    @Before
    public void prepareForTest() throws Exception {
        doNothing().when(mMockObserver).update((Observable) anyObject(), anyObject());

        PowerMockito.whenNew(ThreadUtils.class).withNoArguments().thenReturn(mMockThreadUtils);
        doNothing().when(mMockThreadUtils).checkOffUIThread();

        PowerMockito.mockStatic(DatabaseHelper.class);
        when(DatabaseHelper.getDatabase()).thenReturn(mMockDatabase);

        when(mMockDatabase
                     .insertWithOnConflict(eq(MyNotesDatabaseModel.Tables.NOTES), eq((String) null),
                                           (ContentValues) anyObject(),
                                           eq(SQLiteDatabase.CONFLICT_REPLACE))).thenReturn(1L);

        PowerMockito.mockStatic(MyNotesApplication.class);
        PowerMockito.doNothing()
                    .when(MyNotesApplication.class, "sendLocalBroadcast", (Intent) anyObject());

        PowerMockito.whenNew(ContentValues.class).withNoArguments().thenReturn(mMockContentValues);
    }


    /**
     * Test the basic constructor and default values.
     */
    @Test
    public void testConstruction() throws Exception {
        NoteAttributes noteAttributes = new NoteAttributes();
        Note unitUnderTest = new Note(noteAttributes);

        assertThat(unitUnderTest.getId(), is(noteAttributes.getId()));
        assertThat(unitUnderTest.getColor(), is(noteAttributes.getColor()));
        assertThat(unitUnderTest.getText(), is(noteAttributes.getText()));
    }


    /**
     * Test the basic constructor and default values.
     */
    @Test
    public void testMockedParcelConstruction() {
        NoteAttributes noteAttributes = new NoteAttributes();
        when(mMockParcel.readParcelable(NoteAttributes.class.getClassLoader()))
                .thenReturn(noteAttributes);

        Note unitUnderTest = new Note(mMockParcel);

        assertThat(unitUnderTest.getId(), is(noteAttributes.getId()));
        assertThat(unitUnderTest.getColor(), is(noteAttributes.getColor()));
        assertThat(unitUnderTest.getText(), is(noteAttributes.getText()));
        verify(mMockParcel, times(2)).readParcelable(NoteAttributes.class.getClassLoader());
    }


    @Test
    public void testWriteToParcelMocked() throws Exception {
        NoteAttributes noteAttributes = new NoteAttributes();
        Note note = new Note(noteAttributes);
        doNothing().when(mMockParcel).writeParcelable((Parcelable) anyObject(), anyInt());

        note.writeToParcel(mMockParcel, 0);

        verify(mMockParcel, times(2)).writeParcelable((Parcelable) anyObject(), anyInt());
    }


    /**
     * Test {@link NoteAttributes#setColor(NoteColor)} to each color.
     */
    @Test
    public void testChangingNoteColorToEachValidColor() throws Exception {
        NoteAttributes noteAttributes = new NoteAttributes();
        noteAttributes.setColor(NoteColor.GREEN);

        Note note = new Note(noteAttributes);
        note.addObserver(mMockObserver);


        for (NoteColor noteColor : NoteColor.values()) {
            note.setColor(noteColor);
            assertThat(noteColor, is(note.getColor()));
        }
        verify(mMockObserver, times(NoteColor.values().length))
                .update((Observable) anyObject(), anyObject());

    }


    /**
     * Change the noteAttributes string to a valid value.
     */
    @Test
    public void testChangeNoteTextToValidString() throws Exception {
        NoteAttributes noteAttributes = new NoteAttributes();
        Note note = new Note(noteAttributes);
        note.addObserver(mMockObserver);

        //Set the non-null and not empty string.
        String testString = "AA";
        note.setText(testString);
        assertThat(testString, is(note.getText()));

        verify(mMockObserver).update((Observable) anyObject(), anyObject());
    }


    /**
     * Change the noteAttributes text to null.
     */
    @Test
    public void testNoteTextToNull() throws Exception {
        NoteAttributes noteAttributes = new NoteAttributes();
        Note note = new Note(noteAttributes);
        note.addObserver(mMockObserver);

        //First set the string to a valid string.
        note.setText("Hello");
        verify(mMockObserver).update((Observable) anyObject(), anyObject());

        //Now set the null string and verify that the empty string is set.
        note.setText(null);
        assertThat("", is(note.getText()));

        verify(mMockObserver, times(2)).update((Observable) anyObject(), anyObject());
    }


    /**
     * test {@link NoteAttributes#describeContents()}
     */
    @Test
    public void testDescribeContents() throws Exception {
        assertThat(new Note(new NoteAttributes()).describeContents(), is(0));
    }


    /**
     * Test {@link Note#save(android.database.sqlite.SQLiteDatabase)}
     */
    @Test
    public void testSavingDefaultNote() throws Exception {
        doNothing().when(mMockContentValues).put(MyNotesDatabaseModel.Columns.NOTE_TEXT, "");
        doNothing().when(mMockContentValues).put(MyNotesDatabaseModel.Columns.NOTE_COLOR, 0);

        Note note = new Note(new NoteAttributes());
        assertThat((long) Note.NOT_STORED, is(note.getId()));
        note.save(DatabaseHelper.getDatabase());


        assertThat(note.getId(), is(1L));
        verify(mMockContentValues).put(MyNotesDatabaseModel.Columns.NOTE_TEXT, "");
        verify(mMockContentValues).put(MyNotesDatabaseModel.Columns.NOTE_COLOR, 0);

        verify(mMockDatabase)
                .insertWithOnConflict(eq(MyNotesDatabaseModel.Tables.NOTES), eq((String) null),
                                      (ContentValues) anyObject(),
                                      eq(SQLiteDatabase.CONFLICT_REPLACE));

        verify(mMockObserver, never()).update((Observable) anyObject(), anyObject());
    }


    @Test
    public void testSavingModifiedNote() throws Exception {
        Note note = new Note(new NoteAttributes());
        note.setText("HELLO");
        note.setColor(NoteColor.BLUE);
        note.save(DatabaseHelper.getDatabase());

        assertThat(note.getId(), is(1L));
        verify(mMockDatabase)
                .insertWithOnConflict(eq(MyNotesDatabaseModel.Tables.NOTES), eq((String) null),
                                      (ContentValues) anyObject(),
                                      eq(SQLiteDatabase.CONFLICT_REPLACE));

        verify(mMockObserver, never()).update((Observable) anyObject(), anyObject());
    }


    /**
     * Test {@link Note#delete(android.database.sqlite.SQLiteDatabase)}
     */
    @Test
    public void testDeletingUnstoredNote() throws Exception {

        Note note = new Note(new NoteAttributes());
        assertThat((long) Note.NOT_STORED, is(note.getId()));

        note.delete(mMockDatabase);

        verify(mMockDatabase, never()).delete(eq(MyNotesDatabaseModel.Tables.NOTES),
                                              eq(MyNotesDatabaseModel.Columns.NOTE_ID + " = ?"),
                                              AdditionalMatchers.aryEq(new String[] { "-1" }));

        assertThat((long) Note.NOT_STORED, is(note.getId()));
        verify(mMockObserver, never()).update((Observable) anyObject(), anyObject());
    }


    @Test
    public void testDeletingStoredNoteThatGivesZeroRows() throws Exception {

        NoteAttributes noteAttributes = new NoteAttributes();
        noteAttributes.setId(1);
        Note note = new Note(noteAttributes);

        when(mMockDatabase.delete(eq(MyNotesDatabaseModel.Tables.NOTES),
                                  eq(MyNotesDatabaseModel.Columns.NOTE_ID + " = ?"),
                                  (String[]) anyObject())).thenReturn(0);

        note.delete(mMockDatabase);

        verify(mMockDatabase).delete(eq(MyNotesDatabaseModel.Tables.NOTES),
                                     eq(MyNotesDatabaseModel.Columns.NOTE_ID + " = ?"),
                                     (String[]) anyObject());

        assertThat(note.getId(), is(1L));
        verify(mMockObserver, never()).update((Observable) anyObject(), anyObject());
    }


    @Test
    public void testDeletingStoredNote() throws Exception {

        NoteAttributes noteAttributes = new NoteAttributes();
        noteAttributes.setId(1);
        Note note = new Note(noteAttributes);
        note.addObserver(mMockObserver);

        when(mMockDatabase.delete(eq(MyNotesDatabaseModel.Tables.NOTES),
                                  eq(MyNotesDatabaseModel.Columns.NOTE_ID + " = ?"),
                                  (String[]) anyObject())).thenReturn(1);

        note.delete(mMockDatabase);

        verify(mMockDatabase).delete(eq(MyNotesDatabaseModel.Tables.NOTES),
                                     eq(MyNotesDatabaseModel.Columns.NOTE_ID + " = ?"),
                                     (String[]) anyObject());

        assertThat(note.getId(), is((long) Note.NOT_STORED));
        verify(mMockObserver).update((Observable) anyObject(), anyObject());
    }


    @Test
    public void testCreateContentValuesWithStoredId() throws Exception {
        NoteAttributes noteAttributes = new NoteAttributes();
        noteAttributes.setId(1);
        noteAttributes.setColor(NoteColor.GREEN);
        noteAttributes.setText("hello");

        Note note = new Note(noteAttributes);
        note.createContentValues();

        verify(mMockContentValues)
                .put(MyNotesDatabaseModel.Columns.NOTE_ID, noteAttributes.getId());
        verify(mMockContentValues)
                .put(MyNotesDatabaseModel.Columns.NOTE_COLOR, noteAttributes.getColor().ordinal());
        verify(mMockContentValues)
                .put(MyNotesDatabaseModel.Columns.NOTE_TEXT, noteAttributes.getText());
    }


    @Test
    public void testCreateContentValuesWithUnstoredId() throws Exception {
        NoteAttributes noteAttributes = new NoteAttributes();
        noteAttributes.setColor(NoteColor.GREEN);
        noteAttributes.setText("hello");

        Note note = new Note(noteAttributes);
        note.createContentValues();

        verify(mMockContentValues, never())
                .put(MyNotesDatabaseModel.Columns.NOTE_ID, noteAttributes.getId());
        verify(mMockContentValues)
                .put(MyNotesDatabaseModel.Columns.NOTE_COLOR, noteAttributes.getColor().ordinal());
        verify(mMockContentValues)
                .put(MyNotesDatabaseModel.Columns.NOTE_TEXT, noteAttributes.getText());
    }

}