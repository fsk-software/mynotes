package com.fsk.common.utils;

/**
 * A utility class containing static convenience methods for managing strings.
 */
public final class Strings {

    /**
     * Returns a empty string if the specified string is null.
     *
     * @param arg
     *         the argument to test.
     *
     * @return the supplied string if it isn't null.  Otherwise, it returns an empty string.
     */
    public static String nullToEmpty(String arg) {
        return (arg == null) ? "" : arg;
    }
}
