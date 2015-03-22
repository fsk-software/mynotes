package com.fsk.common.presentation.utils.outline_provider;


/**
 * The available shapes for the outlines
 */
public enum OutlineShape {

    OVAL,
    RECTANGLE,
    ROUNDED_RECTANGLE;


    /**
     * Get the shape associated with the ordinal value.
     *
     * @param ordinal
     *         the ordinal value of the shape to return.
     *
     * @return the shape associated with the ordinal or null.
     */
    public static OutlineShape getShape(int ordinal) {
        if ((ordinal >= 0) && (ordinal < OutlineShape.values().length)) {
            return OutlineShape.values()[ordinal];
        }

        return null;
    }
}