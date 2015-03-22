package com.fsk.mynotes.data.cache;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.test.AndroidTestCase;

import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.receivers.NoteFilterBroadcast;
import com.google.common.util.concurrent.Uninterruptibles;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Test the {@link com.fsk.mynotes.data.cache.NoteFilterCache}.
 */
public class NoteFilterCacheTest extends AndroidTestCase {

    /**
     * Test constructor
     */
    public void testConstructorFailure() {
        try {
            new NoteFilterCache(null);
            assert true;
        }
        catch (NullPointerException e) {
        }
    }


    /**
     * Tests method {@link NoteFilterCache#isColorEnabled(com.fsk.mynotes.constants.NoteColor)} and
     * method {@link NoteFilterCache#getEnabledColors()}
     */
    public void testRetrievingTheDefaultValuesForEachFilter() {
        NoteFilterCache cache = new NoteFilterCache(getContext());
        cache.clear();

        Set<NoteColor> actualEnabledColors = cache.getEnabledColors();
        for (NoteColor color : NoteColor.values()) {
            assertTrue(cache.isColorEnabled(color));
            assertTrue(actualEnabledColors.contains(color));
        }
    }


    /**
     * Tests method {@link NoteFilterCache#isColorEnabled(com.fsk.mynotes.constants.NoteColor)}
     */
    public void testCheckingEnabledColorForNullValue() {
        try {
            new NoteFilterCache(getContext()).isColorEnabled(null);
            assert true;
        }
        catch (NullPointerException e) {
        }
    }


    /**
     * Tests method {@link NoteFilterCache#enableColor(com.fsk.mynotes.constants.NoteColor,
     * boolean)}.
     */
    public void testDisablingEachColor() {
        final NoteFilterCache cache = new NoteFilterCache(getContext());
        cache.clear();

        for (final NoteColor color : NoteColor.values()) {
            final AtomicBoolean receivedBroadcast = new AtomicBoolean(false);
            BroadcastReceiver receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(final Context context, final Intent intent) {
                    assertEquals(NoteFilterBroadcast.ACTION, intent.getAction());
                    assertEquals(color.ordinal(),
                                 intent.getIntExtra(NoteFilterBroadcast.Extras.COLOR, -1));
                    assertFalse(intent.getBooleanExtra(NoteFilterBroadcast.Extras.ENABLED, true));
                    receivedBroadcast.set(true);
                }
            };
            LocalBroadcastManager.getInstance(getContext())
                                 .registerReceiver(receiver,
                                                   NoteFilterBroadcast.createIntentFilter());
            cache.enableColor(color, false);

            for (int i = 0; i < 10 && !receivedBroadcast.get(); ++i) {
                Uninterruptibles.sleepUninterruptibly(100, TimeUnit.MILLISECONDS);
            }
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
        }
    }


    /**
     * Tests method {@link NoteFilterCache#enableColor(com.fsk.mynotes.constants.NoteColor,
     * boolean)}.
     */
    public void testEnablingEachColor() {
        final NoteFilterCache cache = new NoteFilterCache(getContext());
        cache.clear();
        for (NoteColor color : NoteColor.values()) {
            cache.enableColor(color, false);
        }

        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        assertTrue(cache.getEnabledColors().isEmpty());

        for (final NoteColor color : NoteColor.values()) {
            final AtomicBoolean receivedBroadcast = new AtomicBoolean(false);
            BroadcastReceiver receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(final Context context, final Intent intent) {
                    assertEquals(NoteFilterBroadcast.ACTION, intent.getAction());
                    assertEquals(color.ordinal(),
                                 intent.getIntExtra(NoteFilterBroadcast.Extras.COLOR, -1));
                    assertTrue(intent.getBooleanExtra(NoteFilterBroadcast.Extras.ENABLED, true));
                    receivedBroadcast.set(true);
                }
            };

            LocalBroadcastManager.getInstance(getContext())
                                 .registerReceiver(receiver,
                                                   NoteFilterBroadcast.createIntentFilter());
            cache.enableColor(color, true);

            for (int i = 0; i < 10 && !receivedBroadcast.get(); ++i) {
                Uninterruptibles.sleepUninterruptibly(100, TimeUnit.MILLISECONDS);
            }

            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
        }
    }
}