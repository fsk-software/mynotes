package com.fsk.mynotes.data;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.fsk.common.database.DatabaseHelper;
import com.fsk.common.threads.ThreadUtils;
import com.fsk.mynotes.MyNotesApplication;

import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Test the {@link Note class}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({DatabaseHelper.class, SQLiteDatabase.class, ContentValues.class,
                 ThreadUtils.class, MyNotesApplication.class, Note.class})
public class NoteTest {

//
//    @Mock
//    private SQLiteDatabase mMockDatabase;
//
//    @Mock
//    private ThreadUtils mMockThreadUtils;
//
//    @Mock
//    private ContentValues mMockContentValues;
//
//    @Before
//    public void prepareForTest() throws Exception {
//        PowerMockito.whenNew(ThreadUtils.class).withNoArguments().thenReturn(mMockThreadUtils);
//        doNothing().when(mMockThreadUtils).checkOffUIThread();
//
//        PowerMockito.mockStatic(DatabaseHelper.class);
//        when(DatabaseHelper.getDatabase()).thenReturn(mMockDatabase);
//
//        when(mMockDatabase.insertWithOnConflict(eq(MyNotesDatabaseModel.Tables.NOTES),
//                                                eq((String)null),
//                                                (ContentValues) anyObject(),
//                                                eq(SQLiteDatabase.CONFLICT_REPLACE)))
//                          .thenReturn(1L);
//
//        PowerMockito.mockStatic(MyNotesApplication.class);
//        PowerMockito.doNothing().when(MyNotesApplication.class, "sendLocalBroadcast", (Intent) anyObject());
//
//        PowerMockito.whenNew(ContentValues.class).withNoArguments().thenReturn(mMockContentValues);
//    }
//
//
//    /**
//     * Test the basic constructor and default values.
//     */
//    @Test
//    public void testConstruction() {
//        Note note = new Note();
//        assertThat(note.getId(), is((long) Note.NOT_STORED));
//        assertThat(NoteColor.YELLOW, is(note.getColor()));
//        assertThat("", is(note.getText()));
//    }
//
//
//    /**
//     * Test change the note id to a valid value
//     */
//    @Test
//    public void testChangingNoteIdSunnyDay() {
//        Note note = new Note();
//        note.setId(1);
//        assertThat(note.getId(), is(1L));
//
//        //Set it to the not stored case
//        note.setId(Note.NOT_STORED);
//        assertThat((long) Note.NOT_STORED, is(note.getId()));
//    }
//
//
//    /**
//     * Test change the note id to invalid values.
//     */
//    @Test
//    public void testChangingNoteIdRainyDay() {
//        Note note = new Note();
//        try {
//            note.setId(-2);
//            assert false;
//        }
//        catch (IllegalArgumentException e) {}
//    }
//
//    /**
//     * Test {@link Note#setColor(NoteColor)} to each color.
//     */
//    @Test
//    public void testChangingNoteColorToEachValidColor() {
//        Note note = new Note();
//        for (NoteColor noteColor : NoteColor.values()) {
//            note.setColor(noteColor);
//            assertThat(noteColor, is(note.getColor()));
//        }
//    }
//
//
//    /**
//     * Test {@link Note#setColor(NoteColor)} to null.
//     */
//    @Test
//    public void testChangeNoteColorToNull() {
//        Note note = new Note();
//        try {
//            note.setColor(null);
//            assert false;
//        }
//        catch (NullPointerException npe) {
//        }
//    }
//
//
//    /**
//     * Change the note string to a valid value.
//     */
//    @Test
//    public void testChangeNoteTextToValidString() {
//        Note note = new Note();
//
//        //Set the non-null and not empty string.
//        String testString = "AA";
//        note.setText(testString);
//        assertThat(testString, is(note.getText()));
//    }
//
//
//    /**
//     * Change the note text to null.
//     */
//    @Test
//    public void testNoteTextToNull() {
//        Note note = new Note();
//
//        //First set the string to a valid string.
//        note.setText("Hello");
//
//        //Now set the null string and verify that the empty string is set.
//        note.setText(null);
//        assertThat("", is(note.getText()));
//    }
//
//    /**
//     * Change the note text to empty.
//     */
//    @Test
//    public void testNoteTextToEmptyString() {
//        Note note = new Note();
//
//        //First set the string to a valid string.
//        note.setText("Hello");
//
//        //Now set the empty string and verify that the empty string is set.
//        note.setText("");
//        assertThat("", is(note.getText()));
//    }
//
//
//    /**
//     * Test {@link Note#save(android.database.sqlite.SQLiteDatabase)}
//     */
//    @Test
//    public void testSavingDefaultNote() {
//        doNothing().when(mMockContentValues).put(MyNotesDatabaseModel.Columns.NOTE_TEXT, "");
//        doNothing().when(mMockContentValues).put(MyNotesDatabaseModel.Columns.NOTE_COLOR, 0);
//
//        Note note = new Note();
//        assertThat((long) Note.NOT_STORED, is(note.getId()));
//        note.save(DatabaseHelper.getDatabase());
//
//
//        assertThat(note.getId(), is(1L));
//        verify(mMockContentValues).put(MyNotesDatabaseModel.Columns.NOTE_TEXT, "");
//        verify(mMockContentValues).put(MyNotesDatabaseModel.Columns.NOTE_COLOR, 0);
//
//        verify(mMockDatabase).insertWithOnConflict(eq(MyNotesDatabaseModel.Tables.NOTES),
//                                                  eq((String) null), (ContentValues) anyObject(),
//                                                  eq(SQLiteDatabase.CONFLICT_REPLACE));
//
//        PowerMockito.verifyStatic();
//        MyNotesApplication.sendLocalBroadcast((Intent)anyObject());
//    }
//
//    @Test
//    public void testSavingModifiedNote() {
//        Note note = new Note();
//        note.setText("HELLO");
//        note.setColor(NoteColor.BLUE);
//        note.save(DatabaseHelper.getDatabase());
//
//        assertThat(note.getId(), is(1L));
//        verify(mMockDatabase).insertWithOnConflict(eq(MyNotesDatabaseModel.Tables.NOTES),
//                                                  eq((String)null),
//                                                  (ContentValues) anyObject(),
//                                                  eq(SQLiteDatabase.CONFLICT_REPLACE));
//
//        PowerMockito.verifyStatic();
//        MyNotesApplication.sendLocalBroadcast((Intent)anyObject());
//    }
//
//    /**
//     * Test {@link Note#delete(android.database.sqlite.SQLiteDatabase)}
//     */
//    @Test
//    public void testDeletingUnstoredNote() {
//
//        Note note = new Note();
//        assertThat((long) Note.NOT_STORED, is(note.getId()));
//
//        note.delete(mMockDatabase);
//
//        verify(mMockDatabase, never()).delete(eq(MyNotesDatabaseModel.Tables.NOTES),
//                                     eq(MyNotesDatabaseModel.Columns.NOTE_ID + " = ?"),
//                                     AdditionalMatchers.aryEq(new String[] { "-1" }));
//
//        assertThat((long) Note.NOT_STORED, is(note.getId()));
//    }
//
//
//    @Test
//    public void testDeletingStoredNoteThatGivesZeroRows() {
//
//        Note note = new Note();
//        note.setId(1);
//        when(mMockDatabase.delete(eq(MyNotesDatabaseModel.Tables.NOTES),
//                                  eq(MyNotesDatabaseModel.Columns.NOTE_ID + " = ?"),
//                                  (String[])anyObject())).thenReturn(0);
//
//        note.delete(mMockDatabase);
//
//        verify(mMockDatabase).delete(eq(MyNotesDatabaseModel.Tables.NOTES),
//                                     eq(MyNotesDatabaseModel.Columns.NOTE_ID + " = ?"),
//                                     (String[])anyObject());
//
//        assertThat(note.getId(), is(1L));
//    }
//
//    @Test
//    public void testDeletingStoredNote() {
//
//        Note note = new Note();
//        note.setId(1);
//        when(mMockDatabase.delete(eq(MyNotesDatabaseModel.Tables.NOTES),
//                                  eq(MyNotesDatabaseModel.Columns.NOTE_ID + " = ?"),
//                                     (String[]) anyObject())).thenReturn(1);
//
//        note.delete(mMockDatabase);
//
//        verify(mMockDatabase).delete(eq(MyNotesDatabaseModel.Tables.NOTES),
//                                     eq(MyNotesDatabaseModel.Columns.NOTE_ID + " = ?"),
//                                     (String[])anyObject());
//
//        assertThat(note.getId(), is((long) Note.NOT_STORED));
//
//        PowerMockito.verifyStatic();
//        MyNotesApplication.sendLocalBroadcast((Intent)anyObject());
//
//    }
//    /**
//     * test {@link Note#describeContents()}
//     */
//    @Test
//    public void testDescribeContents() {
//        assertThat(new Note().describeContents(), is(0));
//    }
}