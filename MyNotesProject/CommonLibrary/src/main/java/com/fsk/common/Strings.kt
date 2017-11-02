@file:JvmName("Strings")

package com.fsk.common;

/**
 * Returns a empty string if the specified string is null.
 *
 * @param arg
 *         the argument to test.
 *
 * @return the supplied string if it isn't null.  Otherwise, it returns an empty string.
 */
fun nullToEmpty(arg: String?): String {
    return if (arg != null) arg else "";
}
