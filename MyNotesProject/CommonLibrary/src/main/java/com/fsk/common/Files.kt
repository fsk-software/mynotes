@file:JvmName("Files")
package com.fsk.common

import java.io.Closeable


fun Closeable.safeClose() {
    try {
        this.close();
    }
    catch (e : Exception) {
        //do nothing
    }
}
