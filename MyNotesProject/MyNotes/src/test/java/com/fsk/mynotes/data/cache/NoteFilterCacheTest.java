package com.fsk.mynotes.data.cache;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;

import com.fsk.common.threads.ThreadUtils;
import com.fsk.mynotes.MyNotesApplication;
import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.receivers.NoteFilterBroadcast;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test the {@link NoteFilterCache}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({LocalBroadcastManager.class, ThreadUtils.class, MyNotesApplication.class, Intent.class,
        NoteFilterBroadcast.class})
public class NoteFilterCacheTest {

    @Mock
    private Context mMockContext;

    @Mock
    private SharedPreferences mMockSharedPreferences;

    @Mock
    private SharedPreferences.Editor mMockSharedEditorPreferences;

    @Mock
    private LocalBroadcastManager mMockLocalBroadcastManager;

    @Mock
    private Intent mMockIntent;

    @Before
    public void prepareForTest() throws Exception {
        when(mMockContext.getSharedPreferences(NoteFilterCache.CACHE_NAME, Context.MODE_PRIVATE))
                .thenReturn(mMockSharedPreferences);

        PowerMockito.mockStatic(LocalBroadcastManager.class);
        when(LocalBroadcastManager.getInstance(mMockContext)).thenReturn(mMockLocalBroadcastManager);

        when(mMockSharedPreferences.edit()).thenReturn(mMockSharedEditorPreferences);
        when(mMockSharedEditorPreferences.clear()).thenReturn(mMockSharedEditorPreferences);
        doNothing().when(mMockSharedEditorPreferences).apply();
        when(mMockSharedEditorPreferences.putBoolean(anyString(), anyBoolean())).thenReturn(mMockSharedEditorPreferences);

        PowerMockito.whenNew(Intent.class).withAnyArguments().thenReturn(mMockIntent);
    }

    /**
     * Test constructor
     */
    @Test
    public void testConstructorFailure() {
        try {
            new NoteFilterCache(null);
            assert false;
        }
        catch (NullPointerException e) {
        }
    }


    /**
     * Tests method {@link NoteFilterCache#isColorEnabled(NoteColor)} and
     * method {@link NoteFilterCache#getEnabledColors()}
     */
    @Test
    public void testRetrievingTheDefaultValuesForEachFilter() {
        NoteFilterCache cache = new NoteFilterCache(mMockContext);

        when(mMockSharedPreferences.getBoolean(anyString(), eq(true))).thenReturn(true);
        Set < NoteColor > actualEnabledColors = cache.getEnabledColors();
        for (NoteColor color : NoteColor.values()) {
            assertThat(cache.isColorEnabled(color), is(true));
            assertThat(actualEnabledColors.contains(color), is(true));
        }
    }


    /**
     * Tests method {@link NoteFilterCache#isColorEnabled(NoteColor)}
     */
    @Test
    public void testCheckingEnabledColorForNullValue() {
        try {
            new NoteFilterCache(mMockContext).isColorEnabled(null);
            assert false;
        }
        catch (NullPointerException e) {
        }
    }


    /**
     * Tests method {@link NoteFilterCache#enableColor(NoteColor,
     * boolean)}.
     */
    @Test
    public void testDisablingEachColor() {
        final NoteFilterCache cache = new NoteFilterCache(mMockContext);

        when(mMockIntent.putExtra(eq(NoteFilterBroadcast.Extras.COLOR), anyInt())).thenReturn(
                mMockIntent);
        when(mMockIntent.putExtra(NoteFilterBroadcast.Extras.ENABLED, false)).thenReturn(
                mMockIntent);

        for (final NoteColor color : NoteColor.values()) {
            cache.enableColor(color, false);
        }

        verify(mMockSharedEditorPreferences,  times(NoteColor.values().length)).putBoolean(
                anyString(), eq(false));
        verify(mMockLocalBroadcastManager, times(NoteColor.values().length)).sendBroadcast((Intent) anyObject());
    }


    /**
     * Tests method {@link NoteFilterCache#enableColor(NoteColor,
     * boolean)}.
     */
    public void testEnablingEachColor() {
        final NoteFilterCache cache = new NoteFilterCache(mMockContext);

        for (final NoteColor color : NoteColor.values()) {
            cache.enableColor(color, true);
            verify(mMockSharedEditorPreferences.putBoolean(color.name(), true));
        }

        verify(mMockLocalBroadcastManager, times(NoteColor.values().length)).sendBroadcast((Intent) anyObject());
    }
}