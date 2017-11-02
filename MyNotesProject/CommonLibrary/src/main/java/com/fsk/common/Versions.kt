@file:JvmName("Versions")
package com.fsk.common;


import android.os.Build;

/**
 * Verify that the SDK Version is at or above the specified version.
 *
 * @param versionCode
 *         the numeric code for the minimum version.
 *
 * @return true if the SDK Version is at or above the specified version.
 */
fun isAtLeastVersion(versionCode: Int): Boolean {
    return (Build.VERSION.SDK_INT >= versionCode);
}

/**
 * Verify that the SDK Version is at the specified version.
 *
 * @param versionCode
 *         the numeric code for the minimum version.
 *
 * @return true if the SDK Version is at least the specified version.
 */
fun validateIsAtLeastVersion(versionCode: Int) {
    require(isAtLeastVersion(versionCode));
}

/**
 * Verify that the SDK Version is above the specified version.
 *
 * @param versionCode
 *         the numeric code for the minimum version.
 *
 * @return true if the SDK Version is above the specified version.
 */
fun isAboveVersion(versionCode: Int): Boolean {
    return (Build.VERSION.SDK_INT > versionCode);
}


/**
 * Verify that the SDK Version is at the specified version.
 *
 * @param versionCode
 *         the numeric code for the minimum version.
 *
 * @return true if the SDK Version is at least the specified version.
 */
fun validateIsAboveVersion(versionCode: Int) {
    require(isAboveVersion(versionCode));
}
