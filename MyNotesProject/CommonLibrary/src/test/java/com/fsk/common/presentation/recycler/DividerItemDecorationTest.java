package com.fsk.common.presentation.recycler;


import android.graphics.Rect;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests {@link DividerItemDecoration}
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ Rect.class, DividerItemDecoration.class })
public class DividerItemDecorationTest {

    @Test
    public void testRectangularConstructorWithNull() {
        try {
            new DividerItemDecoration(null);
            assert false;
        }
        catch (NullPointerException e) {
            assert true;
        }
    }


    @Test
    public void testRectangularConstructor() {
        Rect expected = new Rect(0, 1, 2, 3);
        expected.left = 0;
        expected.top = 1;
        expected.right = 2;
        expected.bottom = 3;

        DividerItemDecoration decoration = new DividerItemDecoration(expected);
        assertThat(expected.left, sameInstance(decoration.dividerRect.left));
        assertThat(expected.top, is(decoration.dividerRect.top));
        assertThat(expected.right, is(decoration.dividerRect.right));
        assertThat(expected.bottom, is(decoration.dividerRect.bottom));

        Rect actual = new Rect();
        decoration.getItemOffsets(actual, null, null, null);
        assertThat(expected.left, is(actual.left));
        assertThat(expected.top, is(actual.top));
        assertThat(expected.right, is(actual.right));
        assertThat(expected.bottom, is(actual.bottom));
    }


    @Test
    public void testHeightAndWidthConstructorWithNegativeHeight() {
        try {
            new DividerItemDecoration(-1, 10);
            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
    }


    @Test
    public void testHeightAndWidthConstructorWithNegativeWidth() {
        try {
            new DividerItemDecoration(10, -1);
            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
    }


    @Test
    public void testHeightAndWidthConstructor() throws Exception {
        int expectedVertical = 10;
        int expectedHorizontal = 20;

        Rect actual = new Rect();

        Rect expectedRect = new Rect();
        expectedRect.left = expectedHorizontal;
        expectedRect.top = expectedVertical;
        expectedRect.right = expectedHorizontal;
        expectedRect.bottom = expectedVertical;

        PowerMockito.whenNew(Rect.class)
                    .withArguments(expectedHorizontal, expectedVertical, expectedHorizontal,
                                   expectedVertical).thenReturn(expectedRect);
        DividerItemDecoration decoration =
                new DividerItemDecoration(expectedVertical, expectedHorizontal);
        assertThat(expectedHorizontal, is(decoration.dividerRect.left));
        assertThat(expectedVertical, is(decoration.dividerRect.top));
        assertThat(expectedHorizontal, is(decoration.dividerRect.right));
        assertThat(expectedVertical, is(decoration.dividerRect.bottom));

        decoration.getItemOffsets(actual, null, null, null);
        assertThat(expectedHorizontal, is(actual.left));
        assertThat(expectedVertical, is(actual.top));
        assertThat(expectedHorizontal, is(actual.right));
        assertThat(expectedVertical, is(actual.bottom));
    }


    @Test
    public void testPixelConstructorWhenNegative() {
        try {
            new DividerItemDecoration(-1);
            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
    }


    @Test
    public void testPixelConstructor() throws Exception {
        int expected = 10;

        Rect actual = new Rect();

        Rect expectedRect = new Rect();
        expectedRect.left = expected;
        expectedRect.top = expected;
        expectedRect.right = expected;
        expectedRect.bottom = expected;

        PowerMockito.whenNew(Rect.class).withArguments(expected, expected, expected, expected)
                    .thenReturn(expectedRect);
        DividerItemDecoration decoration = new DividerItemDecoration(expected);
        assertThat(expected, is(decoration.dividerRect.left));
        assertThat(expected, is(decoration.dividerRect.top));
        assertThat(expected, is(decoration.dividerRect.right));
        assertThat(expected, is(decoration.dividerRect.bottom));

        decoration.getItemOffsets(actual, null, null, null);
        assertThat(expected, is(actual.left));
        assertThat(expected, is(actual.top));
        assertThat(expected, is(actual.right));
        assertThat(expected, is(actual.bottom));
    }
}
