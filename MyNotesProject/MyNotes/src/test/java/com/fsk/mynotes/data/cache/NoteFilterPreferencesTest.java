package com.fsk.mynotes.data.cache;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;

import com.fsk.common.utils.threads.ThreadUtils;
import com.fsk.mynotes.MyNotesApplication;
import com.fsk.mynotes.constants.NoteColor;

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
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Test the {@link NoteFilterPreferences}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({LocalBroadcastManager.class, ThreadUtils.class, MyNotesApplication.class, Intent.class})
public class NoteFilterPreferencesTest {

    @Mock
    private Context mMockContext;


    @Mock
    private SharedPreferences mMockSharedPreferences;


    @Mock
    private SharedPreferences.Editor mMockSharedEditorPreferences;


    @Mock
    private Intent mMockIntent;


    @Before
    public void prepareForTest() throws Exception {
        when(mMockContext
                     .getSharedPreferences(NoteFilterPreferences.CACHE_NAME, Context.MODE_PRIVATE))
                .thenReturn(mMockSharedPreferences);

        PowerMockito.mockStatic(LocalBroadcastManager.class);

        when(mMockSharedPreferences.edit()).thenReturn(mMockSharedEditorPreferences);
        when(mMockSharedEditorPreferences.clear()).thenReturn(mMockSharedEditorPreferences);
        doNothing().when(mMockSharedEditorPreferences).apply();
        when(mMockSharedEditorPreferences.putBoolean(anyString(), anyBoolean()))
                .thenReturn(mMockSharedEditorPreferences);

        PowerMockito.whenNew(Intent.class).withAnyArguments().thenReturn(mMockIntent);
    }


    /**
     * Test constructor
     */
    @Test
    public void testConstructorFailure() {
        try {
            new NoteFilterPreferences(null);
            assert false;
        }
        catch (NullPointerException e) {
        }
    }


    /**
     * Tests method {@link NoteFilterPreferences#isColorEnabled(NoteColor)} and method {@link
     * NoteFilterPreferences#getEnabledColors()}
     */
    @Test
    public void testRetrievingTheDefaultValuesForEachFilter() {
        NoteFilterPreferences cache = new NoteFilterPreferences(mMockContext);

        when(mMockSharedPreferences.getBoolean(anyString(), eq(true))).thenReturn(true);
        Set<NoteColor> actualEnabledColors = cache.getEnabledColors();
        for (NoteColor color : NoteColor.values()) {
            assertThat(cache.isColorEnabled(color), is(true));
            assertThat(actualEnabledColors.contains(color), is(true));
        }
    }


    /**
     * Tests method {@link NoteFilterPreferences#isColorEnabled(NoteColor)}
     */
    @Test
    public void testCheckingEnabledColorForNullValue() {
        try {
            new NoteFilterPreferences(mMockContext).isColorEnabled(null);
            assert false;
        }
        catch (NullPointerException e) {
        }
    }


    /**
     * Tests method {@link NoteFilterPreferences#enableColor(NoteColor, boolean)}.
     */
    @Test
    public void testDisablingEachColor() {
        final NoteFilterPreferences cache = new NoteFilterPreferences(mMockContext);

        for (final NoteColor color : NoteColor.values()) {
            cache.enableColor(color, false);
        }

        verify(mMockSharedEditorPreferences, times(NoteColor.values().length))
                .putBoolean(anyString(), eq(false));
     }

    @Test
    public void testClear() {

        final NoteFilterPreferences cache = new NoteFilterPreferences(mMockContext);
        cache.clear();
        verify(mMockSharedEditorPreferences).clear();
        verify(mMockSharedEditorPreferences).apply();

    }
    /**
     * Tests methods {@link NoteFilterPreferences#registerListener(SharedPreferences.OnSharedPreferenceChangeListener)} and
     * {@link NoteFilterPreferences#unregisterListener(SharedPreferences.OnSharedPreferenceChangeListener)}.
     */
    @Test
    public void testListenerRegistration() {
        final NoteFilterPreferences cache = new NoteFilterPreferences(mMockContext);

        cache.unregisterListener(null);
        verify(mMockSharedPreferences).unregisterOnSharedPreferenceChangeListener(null);

        SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {


            @Override
            public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences,
                                                  final String key) {

            }
        };

        cache.registerListener(listener);
        verify(mMockSharedPreferences).registerOnSharedPreferenceChangeListener(listener);

        cache.unregisterListener(listener);
        verify(mMockSharedPreferences).unregisterOnSharedPreferenceChangeListener(listener);

    }


    /**
     * Tests method {@link NoteFilterPreferences#enableColor(NoteColor, boolean)}.
     */
    public void testEnablingEachColor() {
        final NoteFilterPreferences cache = new NoteFilterPreferences(mMockContext);

        for (final NoteColor color : NoteColor.values()) {
            cache.enableColor(color, true);
            verify(mMockSharedEditorPreferences.putBoolean(color.name(), true));
        }
    }
}