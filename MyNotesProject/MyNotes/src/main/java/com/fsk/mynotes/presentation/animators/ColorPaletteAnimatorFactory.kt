package com.fsk.mynotes.presentation.animators;

import android.os.Build
import com.fsk.common.isAtLeastVersion

fun getColorPaletteAnimatorHelper(reveal: Boolean): ColorPaletteAnimatorHelper {
    return if (isAtLeastVersion(Build.VERSION_CODES.LOLLIPOP))
        ColorPaletteAnimatorHelper(reveal)
    else
        ColorPaletteAnimatorHelperCompat(reveal);
}
