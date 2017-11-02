package com.fsk.common.utils.threads;


import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Unit Tests for the {@link ThreadValidator}
 */
public class ThreadExceptionTest {

    private static final String MESSAGE = "This is the test message";


    private static final Throwable CAUSE = new RuntimeException();


    @Test
    public void testConstructors() throws Exception {
        //Base Constructor
        ThreadException exception = new ThreadException();
        assertThat(exception.getMessage(), is(nullValue()));
        assertThat(exception.getCause(), is(nullValue()));

        //message constructor case 1
        exception = new ThreadException(MESSAGE);
        assertThat(exception.getMessage(), is(MESSAGE));
        assertThat(exception.getCause(), is(nullValue()));

        //message constructor case 2
        exception = new ThreadException((String) null);
        assertThat(exception.getMessage(), is(nullValue()));
        assertThat(exception.getCause(), is(nullValue()));


        //throwable constructor case 1
        exception = new ThreadException(CAUSE);
        assertThat(exception.getMessage(), is("java.lang.RuntimeException"));
        assertThat(exception.getCause(), is(CAUSE));

        //throwable constructor case 2
        exception = new ThreadException(CAUSE);
        assertThat(exception.getMessage(), is("java.lang.RuntimeException"));
        assertThat(exception.getCause(), is(CAUSE));
    }
}
