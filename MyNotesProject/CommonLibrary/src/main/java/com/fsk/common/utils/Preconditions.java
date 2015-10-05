package com.fsk.common.utils;

import java.util.Collection;

/**
 * A utility class containing static convenience methods for validating preconditions.
 */
public final class Preconditions {

    /**
     * Check that the argument is not null.
     *
     * @param arg
     *         the arg to test for null.
     *
     * @throws java.lang.NullPointerException
     *         when the argument is null.
     */
    public static void checkNotNull(Object arg) {
        if (arg == null) {
            throw new NullPointerException();
        }
    }

    /**
     * Check that the argument is not null.
     *
     * @param arg
     *         the arg to test for null.
     * @param message
     *         the message to include in the exception.
     *
     * @throws java.lang.NullPointerException
     *         when the argument is null.
     */
    public static void checkNotNull(Object arg, String message) {
        if (arg == null) {
            throw new NullPointerException(message);
        }
    }


    /**
     * Check that the supplied condition is true.
     *
     * @param condition
     *         the condition to test.
     * @param message
     *         the message to include in the exception.
     *
     * @throws java.lang.IllegalArgumentException
     *         when the condition is false
     */
    public static void checkArgument(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }


    /**
     * Check that the supplied condition is true.
     *
     * @param condition
     *         the condition to test.
     *
     * @throws java.lang.IllegalArgumentException
     *         when the condition is false
     */
    public static void checkArgument(boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException();
        }
    }
}