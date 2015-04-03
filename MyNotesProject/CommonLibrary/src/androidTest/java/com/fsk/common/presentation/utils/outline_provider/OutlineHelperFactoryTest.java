package com.fsk.common.presentation.utils.outline_provider;


import android.os.Build;
import android.test.AndroidTestCase;

/**
 * Tests {@link OutlineHelperFactory}
 */
public class OutlineHelperFactoryTest extends AndroidTestCase {


    public void testGetOutlineHelper() {
        OutlineHelper actual = OutlineHelperFactory.getOutlineHelper();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            assertFalse(actual instanceof OutlineHelperCompat);
        }
        else {
            assertTrue(actual instanceof OutlineHelperCompat);
        }
    }
}