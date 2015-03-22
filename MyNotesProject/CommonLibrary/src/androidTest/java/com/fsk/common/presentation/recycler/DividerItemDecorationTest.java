package com.fsk.common.presentation.recycler;


import android.graphics.Rect;
import android.test.AndroidTestCase;

/**
 * Tests {@link DividerItemDecoration}
 */
public class DividerItemDecorationTest extends AndroidTestCase {


    public void testRectangularConstructorWithNull() {
        try {
            new DividerItemDecoration(null);
            assert false;
        }
        catch (NullPointerException e) {
            assert true;
        }
    }

    public void testRectangularConstructor() {
        Rect expected = new Rect(0,1,2,3);
        DividerItemDecoration decoration = new DividerItemDecoration(expected);
        assertEquals(expected, decoration.mDividerRect);

        Rect actual = new Rect();
        decoration.getItemOffsets(actual, null, null, null);
        assertEquals(expected.left, actual.left);
        assertEquals(expected.top, actual.top);
        assertEquals(expected.right, actual.right);
        assertEquals(expected.bottom, actual.bottom);
    }


    public void testHeightAndWidthConstructorWithNegativeHeight() {
        try {
            new DividerItemDecoration(-1, 10);
            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
    }


    public void testHeightAndWidthConstructorWithNegativeWidth() {
        try {
            new DividerItemDecoration(10, -1);
            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
    }

    public void testHeightAndWidthConstructor() {
        int expectedHeight = 10;
        int expectedWidth = 20;
        DividerItemDecoration decoration = new DividerItemDecoration(expectedHeight, expectedWidth);
        assertEquals(expectedHeight, decoration.mDividerRect.left);
        assertEquals(expectedWidth, decoration.mDividerRect.top);
        assertEquals(expectedHeight, decoration.mDividerRect.right);
        assertEquals(expectedWidth, decoration.mDividerRect.bottom);

        Rect actual = new Rect();
        decoration.getItemOffsets(actual, null, null, null);
        assertEquals(expectedHeight, actual.left);
        assertEquals(expectedWidth, actual.top);
        assertEquals(expectedHeight, actual.right);
        assertEquals(expectedWidth, actual.bottom);
    }


    public void testPixelConstructorWhenNegative() {
        try {
            new DividerItemDecoration(-1);
            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
    }

    public void testPixelConstructor() {
        int expected = 10;
        DividerItemDecoration decoration = new DividerItemDecoration(expected);
        assertEquals(expected, decoration.mDividerRect.left);
        assertEquals(expected, decoration.mDividerRect.top);
        assertEquals(expected, decoration.mDividerRect.right);
        assertEquals(expected, decoration.mDividerRect.bottom);

        Rect actual = new Rect();
        decoration.getItemOffsets(actual, null, null, null);
        assertEquals(expected, actual.left);
        assertEquals(expected, actual.top);
        assertEquals(expected, actual.right);
        assertEquals(expected, actual.bottom);
    }
}
