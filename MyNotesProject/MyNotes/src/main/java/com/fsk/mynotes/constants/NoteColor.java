package com.fsk.mynotes.constants;


import android.content.Context;
import android.support.annotation.NonNull;

import com.fsk.mynotes.R;

/**
 * An enumerator containing all of the valid note colors. Each color corresponds to three resource
 * ids: <ul> <li>The resource id for the printable color name.</li> <li>The resource id for notes
 * rgb color.</li> <li>The resource id for notes rgb dark color.</li>
 */
public enum NoteColor {

    YELLOW(R.string.yellow, R.color.note_yellow, R.color.note_yellow_dark),

    BLUE(R.string.blue, R.color.note_blue, R.color.note_blue_dark),

    GREEN(R.string.green, R.color.note_green, R.color.note_green_dark),

    PINK(R.string.pink, R.color.note_pink, R.color.note_pink_dark),

    GREY(R.string.gray, R.color.note_gray, R.color.note_gray_dark),

    PURPLE(R.string.purple, R.color.note_purple, R.color.note_purple_dark);


    /**
     * The id of the string resource containing the printable name for the color.
     */
    public final int nameResourceId;


    /**
     * The id of the color resource containing the ARGB color for the note color.
     */
    public final int colorResourceId;


    /**
     * The id of the color resource containing the ARGB color for the dark note color.
     */
    public final int darkColorResourceId;


    /**
     * Constructor
     *
     * @param nameResource
     *         The string resource id for the name.
     * @param colorResourceId
     *         The color resource id.
     * @param darkColorResourceId
     *         The color header resource id.
     */
    NoteColor(int nameResource, int colorResourceId, int darkColorResourceId) {
        this.nameResourceId = nameResource;
        this.colorResourceId = colorResourceId;
        this.darkColorResourceId = darkColorResourceId;
    }


    /**
     * Get the argb value for the color.
     *
     * @param context
     *         The context to use for reading argb from the resources.
     *
     * @return The argb value for the color.
     */
    public int getColorArgb(@NonNull Context context) {
        return context.getResources().getColor(colorResourceId);
    }


    /**
     * Get the argb value for the dark color.
     *
     * @param context
     *         The context to use for reading argb from the resources.
     *
     * @return The argb value for the dark color.
     */
    public int getDarkColorArgb(@NonNull Context context) {
        return context.getResources().getColor(darkColorResourceId);
    }


    /**
     * Get the {@link NoteColor} associated with the supplied ordinal.
     *
     * @param index
     *         the ordinal of the {@link NoteColor} to retrieve.
     *
     * @return the {@link NoteColor} associated with the ordinal or null.
     */
    static public NoteColor getColor(long index) {
        NoteColor[] colors = NoteColor.values();
        NoteColor returnValue = null;
        if ((index >= 0) && (index < colors.length)) {
            returnValue = colors[(int) index];
        }
        return returnValue;
    }
}
