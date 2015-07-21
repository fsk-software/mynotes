package com.fsk.common.presentation.utils.outline_provider;


import com.fsk.common.presentation.components.FloatingFrameLayout;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * Tests {@link FloatingFrameLayout}
 */
public class OutlineShapeTest {

    @Test
    public void getOutlineShapeWithInvalidNumbers() {
       assertThat(OutlineShape.getShape(-1), is(nullValue()));
        assertThat(OutlineShape.getShape(OutlineShape.values().length+1), is(nullValue()));
    }

    @Test
    public void getOutlineShapeWithValidNumbers() {
        for(OutlineShape shape : OutlineShape.values()) {
            assertThat(OutlineShape.getShape(shape.ordinal()), is(shape));
        }
    }
}