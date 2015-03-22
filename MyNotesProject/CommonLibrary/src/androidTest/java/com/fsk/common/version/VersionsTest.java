package com.fsk.common.version;


import android.os.Build;
import android.test.AndroidTestCase;

import com.fsk.common.presentation.components.FloatingFrameLayout;
import com.fsk.common.versions.Versions;

/**
 * Tests {@link FloatingFrameLayout}
 */
public class VersionsTest extends AndroidTestCase {


    public void testIsAtLeastVersion() {
        int testVersion = Build.VERSION.SDK_INT;
        for (int i=testVersion-1; i<=testVersion+1; ++i) {
            assertEquals(testVersion >= i, Versions.isAtLeastVersion(i));
        }
    }

    public void testIsAtLeastLollipop() {
        int testVersion = Build.VERSION.SDK_INT;
        assertEquals(testVersion >= Build.VERSION_CODES.LOLLIPOP, Versions.isAtLeastLollipop());
    }

    public void testIsAtLeastKitKat() {
        int testVersion = Build.VERSION.SDK_INT;
        assertEquals(testVersion >= Build.VERSION_CODES.KITKAT, Versions.isAtLeastKitKat());
    }

    public void testIsAtLeastJellyBeanMR2() {
        int testVersion = Build.VERSION.SDK_INT;
        assertEquals(testVersion >= Build.VERSION_CODES.JELLY_BEAN_MR2, Versions.isAtLeastJellyBeanMR2());
    }

    public void testIsAtLeastJellyBeanMR1() {
        int testVersion = Build.VERSION.SDK_INT;
        assertEquals(testVersion >= Build.VERSION_CODES.JELLY_BEAN_MR1, Versions.isAtLeastJellyBeanMR1());
    }

    public void testIsAtLeastJellyBean() {
        int testVersion = Build.VERSION.SDK_INT;
        assertEquals(testVersion >= Build.VERSION_CODES.JELLY_BEAN, Versions.isAtLeastJellyBean());
    }
}