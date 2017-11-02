package com.fsk.common;

import com.fsk.common.Strings;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by me on 10/2/2015.
 */
@RunWith(PowerMockRunner.class)
public class StringsTest {

    @Test
    public void testNullToEmpty() {
        assertThat(Strings.nullToEmpty(null), is(""));
        assertThat(Strings.nullToEmpty(""), is(""));
        assertThat(Strings.nullToEmpty("ABCD"), is("ABCD"));
    }
}
