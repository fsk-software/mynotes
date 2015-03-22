package com.fsk.common.versions;


import android.os.Build;

/**
 * Utility that helps with SDK Version checks.
 */
public abstract class Versions {

    /**
     * Verify that the SDK Version is at the specified version.
     *
     * @param versionCode
     *         the numeric code for the minimum version.
     *
     * @return true if the SDK Version is at least the specified version.
     */
    public static boolean isAtLeastVersion(int versionCode) {
        return (Build.VERSION.SDK_INT >= versionCode);
    }


    /**
     * Verify that the SDK Version is at {@link android.os.Build.VERSION_CODES#LOLLIPOP}.
     *
     * @return true if the SDK Version is at least {@link android.os.Build.VERSION_CODES#LOLLIPOP}.
     */
    public static boolean isAtLeastLollipop() {
        return isAtLeastVersion(Build.VERSION_CODES.LOLLIPOP);
    }


    /**
     * Verify that the SDK Version is at {@link android.os.Build.VERSION_CODES#KITKAT}.
     *
     * @return true if the SDK Version is at least {@link android.os.Build.VERSION_CODES#KITKAT}.
     */
    public static boolean isAtLeastKitKat() {
        return isAtLeastVersion(Build.VERSION_CODES.KITKAT);
    }


    /**
     * Verify that the SDK Version is at {@link android.os.Build.VERSION_CODES#JELLY_BEAN}.
     *
     * @return true if the SDK Version is at least {@link android.os.Build
     * .VERSION_CODES#JELLY_BEAN}.
     */
    public static boolean isAtLeastJellyBean() {
        return isAtLeastVersion(Build.VERSION_CODES.JELLY_BEAN);
    }


    /**
     * Verify that the SDK Version is at {@link android.os.Build.VERSION_CODES#JELLY_BEAN_MR1}.
     *
     * @return true if the SDK Version is at least {@link android.os.Build
     * .VERSION_CODES#JELLY_BEAN_MR1}.
     */
    public static boolean isAtLeastJellyBeanMR1() {
        return isAtLeastVersion(Build.VERSION_CODES.JELLY_BEAN_MR1);
    }


    /**
     * Verify that the SDK Version is at {@link android.os.Build.VERSION_CODES#JELLY_BEAN_MR2}.
     *
     * @return true if the SDK Version is at least {@link android.os.Build
     * .VERSION_CODES#JELLY_BEAN_MR2}.
     */
    public static boolean isAtLeastJellyBeanMR2() {
        return isAtLeastVersion(Build.VERSION_CODES.JELLY_BEAN_MR2);
    }
}
