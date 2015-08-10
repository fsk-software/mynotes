package com.fsk.mynotes.data.cache;


import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.fsk.common.threads.ThreadUtils;
import com.fsk.mynotes.MyNotesApplication;

import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Test the {@link NoteFilterPreferences}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({LocalBroadcastManager.class, ThreadUtils.class, MyNotesApplication.class, Intent.class})
public class NoteFilterPreferencesTest {
//
//    @Mock
//    private Context mMockContext;
//
//    @Mock
//    private SharedPreferences mMockSharedPreferences;
//
//    @Mock
//    private SharedPreferences.Editor mMockSharedEditorPreferences;
//
//    @Mock
//    private LocalBroadcastManager mMockLocalBroadcastManager;
//
//    @Mock
//    private Intent mMockIntent;
//
//    @Before
//    public void prepareForTest() throws Exception {
//        when(mMockContext.getSharedPreferences(NoteFilterPreferences.CACHE_NAME, Context.MODE_PRIVATE))
//                .thenReturn(mMockSharedPreferences);
//
//        PowerMockito.mockStatic(LocalBroadcastManager.class);
//        when(LocalBroadcastManager.getInstance(mMockContext)).thenReturn(mMockLocalBroadcastManager);
//
//        when(mMockSharedPreferences.edit()).thenReturn(mMockSharedEditorPreferences);
//        when(mMockSharedEditorPreferences.clear()).thenReturn(mMockSharedEditorPreferences);
//        doNothing().when(mMockSharedEditorPreferences).apply();
//        when(mMockSharedEditorPreferences.putBoolean(anyString(), anyBoolean())).thenReturn(mMockSharedEditorPreferences);
//
//        PowerMockito.whenNew(Intent.class).withAnyArguments().thenReturn(mMockIntent);
//    }
//
//    /**
//     * Test constructor
//     */
//    @Test
//    public void testConstructorFailure() {
//        try {
//            new NoteFilterPreferences(null);
//            assert false;
//        }
//        catch (NullPointerException e) {
//        }
//    }
//
//
//    /**
//     * Tests method {@link NoteFilterPreferences#isColorEnabled(NoteColor)} and
//     * method {@link NoteFilterPreferences#getEnabledColors()}
//     */
//    @Test
//    public void testRetrievingTheDefaultValuesForEachFilter() {
//        NoteFilterPreferences cache = new NoteFilterPreferences(mMockContext);
//
//        when(mMockSharedPreferences.getBoolean(anyString(), eq(true))).thenReturn(true);
//        Set < NoteColor > actualEnabledColors = cache.getEnabledColors();
//        for (NoteColor color : NoteColor.values()) {
//            assertThat(cache.isColorEnabled(color), is(true));
//            assertThat(actualEnabledColors.contains(color), is(true));
//        }
//    }
//
//
//    /**
//     * Tests method {@link NoteFilterPreferences#isColorEnabled(NoteColor)}
//     */
//    @Test
//    public void testCheckingEnabledColorForNullValue() {
//        try {
//            new NoteFilterPreferences(mMockContext).isColorEnabled(null);
//            assert false;
//        }
//        catch (NullPointerException e) {
//        }
//    }
//
//
//    /**
//     * Tests method {@link NoteFilterPreferences#enableColor(NoteColor,
//     * boolean)}.
//     */
//    @Test
//    public void testDisablingEachColor() {
//        final NoteFilterPreferences cache = new NoteFilterPreferences(mMockContext);
//
//        when(mMockIntent.putExtra(eq(NoteFilterBroadcast.Extras.COLOR), anyInt())).thenReturn(
//                mMockIntent);
//        when(mMockIntent.putExtra(NoteFilterBroadcast.Extras.ENABLED, false)).thenReturn(
//                mMockIntent);
//
//        for (final NoteColor color : NoteColor.values()) {
//            cache.enableColor(color, false);
//        }
//
//        verify(mMockSharedEditorPreferences,  times(NoteColor.values().length)).putBoolean(
//                anyString(), eq(false));
//        verify(mMockLocalBroadcastManager, times(NoteColor.values().length)).sendBroadcast((Intent) anyObject());
//    }
//
//
//    /**
//     * Tests method {@link NoteFilterPreferences#enableColor(NoteColor,
//     * boolean)}.
//     */
//    public void testEnablingEachColor() {
//        final NoteFilterPreferences cache = new NoteFilterPreferences(mMockContext);
//
//        for (final NoteColor color : NoteColor.values()) {
//            cache.enableColor(color, true);
//            verify(mMockSharedEditorPreferences.putBoolean(color.name(), true));
//        }
//
//        verify(mMockLocalBroadcastManager, times(NoteColor.values().length)).sendBroadcast((Intent) anyObject());
//    }
}