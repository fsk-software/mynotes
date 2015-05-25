package com.fsk.mynotes.constants;


import com.fsk.mynotes.R;

/**
 * An enumerator containing all of the valid note colors. Each color corresponds to three resource ids:
 *<ul>
 *   <li>The resource id for the printable color name.</li>
 *   <li>The resource id for notes rgb color.</li>
 *   <li>The resource id for color filter button.</li>
 */
public enum NoteColor {

    YELLOW(R.string.yellow, R.color.note_yellow, R.drawable.yellow_color_filter_background),
    BLUE(R.string.blue, R.color.note_blue, R.drawable.blue_color_filter_background),
    GREEN(R.string.green, R.color.note_green, R.drawable.green_color_filter_background),
    PINK(R.string.pink, R.color.note_pink, R.drawable.pink_color_filter_background),
    GREY(R.string.gray, R.color.note_gray, R.drawable.gray_color_filter_background),
    PURPLE(R.string.purple, R.color.note_purple, R.drawable.purple_color_filter_background);


    /**
     * The id of the string resource containing the printable name for the color.
     */
    public final int nameResourceId;


    /**
     * The id of the color resource containing the ARGB color for the note color.
     */
    public final int colorResourceId;


    /**
     * The id of the drawable resource containing the color filter background.
     */
    public final int colorFilterBackgroundResourceId;


    /**
     * Constructor
     *
     * @param nameResource
     *         The string resource id for the name.
     * @param colorResourceId
     *         The color resource id.
     * @param colorFilterResourceId
     *         The drawable resource id for the selector.
     */
    NoteColor(int nameResource, int colorResourceId, int colorFilterResourceId) {
        this.nameResourceId = nameResource;
        this.colorResourceId = colorResourceId;
        this.colorFilterBackgroundResourceId = colorFilterResourceId;
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
            returnValue = colors[(int)index];
        }
        return returnValue;
    }
}
