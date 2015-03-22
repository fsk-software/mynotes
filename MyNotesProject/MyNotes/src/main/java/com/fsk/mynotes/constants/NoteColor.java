package com.fsk.mynotes.constants;


import com.fsk.mynotes.R;

/**
 * An enumerator containing all of the valid note colors. Each color corresponds to three resource ids:
 *<ul>
 *   <li>The resource id for the printable color name.</li>
 *   <li>The resource id for notes rgb color.</li>
 *   <li>The resource id for note filter toggle drawable.</li>
 */
public enum NoteColor {

    YELLOW(R.string.yellow, R.color.note_yellow, R.drawable.yellow_note_selector),
    BLUE(R.string.blue, R.color.note_blue, R.drawable.blue_note_selector),
    GREEN(R.string.green, R.color.note_green, R.drawable.green_note_selector),
    PINK(R.string.pink, R.color.note_pink, R.drawable.pink_note_selector),
    GREY(R.string.gray, R.color.note_gray, R.drawable.gray_note_selector),
    PURPLE(R.string.purple, R.color.note_purple, R.drawable.purple_note_selector);


    /**
     * The id of the string resource containing the printable name for the color.
     */
    public final int nameResourceId;


    /**
     * The id of the color resource containing the ARGB color for the note color.
     */
    public final int colorResourceId;


    /**
     * The id of the drawable resource containing the selector for the note color.
     */
    public final int selectorResourceId;


    /**
     * Constructor
     *
     * @param nameResource
     *         The string resource id for the name.
     * @param colorResourceId
     *         The color resource id.
     * @param selectorResourceId
     *         The drawable resource id for the selector.
     */
    private NoteColor(int nameResource, int colorResourceId, int selectorResourceId) {
        this.nameResourceId = nameResource;
        this.colorResourceId = colorResourceId;
        this.selectorResourceId = selectorResourceId;
    }


    /**
     * Get the {@link NoteColor} associated with the supplied ordinal.
     *
     * @param index
     *         the ordinal of the {@link NoteColor} to retrieve.
     *
     * @return the {@link NoteColor} associated with the ordinal or null.
     */
    static public final NoteColor getColor(long index) {
        NoteColor[] colors = NoteColor.values();

        for (int i = 0; i < colors.length; ++i) {
            if (index == colors[i].ordinal()) {
                return colors[i];
            }
        }
        return null;
    }
}
