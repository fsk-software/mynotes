package com.fsk.common.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by me on 10/2/2015.
 */
@RunWith(PowerMockRunner.class)
public class PreconditionsTest {

    @Test
    public void testCheckNotNullWithNoMessage() {
        try {
            Preconditions.checkNotNull(null);
            assert false;
        } catch (NullPointerException e) {
            assertThat(e.getMessage(), is(nullValue()));
        }

        Preconditions.checkNotNull(new Object());
    }

    @Test
    public void testCheckNotNullWithMessage() {
        try {
            Preconditions.checkNotNull(null, "Hello");
            assert false;
        } catch (NullPointerException e) {
            assertThat(e.getMessage(), is("Hello"));
        }

        Preconditions.checkNotNull(new Object(), "Hello");
    }

    @Test
    public void testCheckArgumentWithNoMessage() {
        try {
            Preconditions.checkArgument(false);
            assert false;
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is(nullValue()));
        }

        Preconditions.checkArgument(true);
    }

    @Test
    public void testCheckArgumentWithMessage() {
        try {
            Preconditions.checkArgument(false, "Hello");
            assert false;
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Hello"));
        }

        Preconditions.checkArgument(true, "Hello");
    }
}
