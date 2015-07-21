package com.fsk.common.versions;


import android.os.Build;

import com.fsk.common.presentation.components.FloatingFrameLayout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Tests {@link FloatingFrameLayout}
 */
@RunWith(PowerMockRunner.class)
public class VersionsTest {


    @Test
    public void testIsAtLeastVersion() {
        int testVersion = Build.VERSION.SDK_INT;
        for (int i=testVersion-1; i<=testVersion+1; ++i) {
            assertThat(testVersion >= i, is(Versions.isAtLeastVersion(i)));
        }
    }

    @Test
    public void testIsAtLeastLollipop() {
        int testVersion = Build.VERSION.SDK_INT;
        assertThat(testVersion >= Build.VERSION_CODES.LOLLIPOP, is(Versions.isAtLeastLollipop()));
    }

    @Test
    public void testIsAtLeastKitKat() {
        int testVersion = Build.VERSION.SDK_INT;
        assertThat(testVersion >= Build.VERSION_CODES.KITKAT, is(Versions.isAtLeastKitKat()));
    }

    @Test
    public void testIsAtLeastJellyBeanMR2() {
        int testVersion = Build.VERSION.SDK_INT;
        assertThat(testVersion >= Build.VERSION_CODES.JELLY_BEAN_MR2,
                   is(Versions.isAtLeastJellyBeanMR2()));
    }

    @Test
    public void testIsAtLeastJellyBeanMR1() {
        int testVersion = Build.VERSION.SDK_INT;
        assertThat(testVersion >= Build.VERSION_CODES.JELLY_BEAN_MR1,
                   is(Versions.isAtLeastJellyBeanMR1()));
    }


    @Test
    public void testIsAtLeastJellyBean() {
        int testVersion = Build.VERSION.SDK_INT;
        assertThat(testVersion >= Build.VERSION_CODES.JELLY_BEAN, is(Versions.isAtLeastJellyBean()));
    }
}