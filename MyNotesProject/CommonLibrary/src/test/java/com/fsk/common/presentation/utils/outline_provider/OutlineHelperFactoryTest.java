package com.fsk.common.presentation.utils.outline_provider;


import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Tests {@link OutlineHelperFactory}
 */
@RunWith(PowerMockRunner.class)
public class OutlineHelperFactoryTest {


    @Test
    public void testGetOutlineHelper() {
        OutlineHelper actual = OutlineHelperFactory.getOutlineHelper();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            assertThat(actual instanceof OutlineHelperCompat, is(false));
        }
        else {
            assertThat(actual instanceof OutlineHelperCompat, is(true));
        }
    }
}