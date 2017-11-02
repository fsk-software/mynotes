package com.fsk.common;


import android.os.Build;


import com.fsk.common.Versions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(PowerMockRunner.class)
public class VersionsTest {


    @Test
    public void testIsAtLeastVersion() {
        int testVersion = Build.VERSION.SDK_INT;
        for (int i=testVersion-1; i<=testVersion+1; ++i) {
            assertThat(testVersion >= i, is(Versions.isAtLeastVersion(i)));
        }
    }

}