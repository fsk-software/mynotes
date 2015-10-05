package com.fsk.common.presentation.utils.outline_provider;


import android.content.Context;
import android.view.View;

import com.fsk.common.presentation.components.FloatingFrameLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Tests {@link FloatingFrameLayout}
 */
@RunWith(PowerMockRunner.class)
public class OutlineHelperCompatTest {


    @Mock
    private Context mContext;

    @Before
    public void prepareForTest() {
    }


    @Test
    public void testSetupWithNullView() {
        OutlineHelperCompat helper = new OutlineHelperCompat();
        helper.setTarget(null).setOutlineShape(OutlineShape.OVAL).setRoundRectRadius(-1).setup();
        helper.invalidateOutline();
        helper.updateViewOutline(null);
    }

    @Test
    public void testSetupWithNullShape() {
        OutlineHelperCompat helper = new OutlineHelperCompat();
        helper.setTarget(new View(mContext)).setOutlineShape(null).setRoundRectRadius(-1).setup();
        helper.invalidateOutline();
        helper.updateViewOutline(null);
    }

    @Test
    public void testSetupWithNegativeRoundedRectCorners() {

        OutlineHelperCompat helper = new OutlineHelperCompat();
        helper.setTarget(new View(mContext)).setOutlineShape(OutlineShape.OVAL).setRoundRectRadius(-1).setup();
        helper.invalidateOutline();
        helper.updateViewOutline(null);
    }


    @Test
    public void testSunnyDay() {
        View view = new View(mContext);
        for (OutlineShape shape : OutlineShape.values()) {
            OutlineHelperCompat helper = new OutlineHelperCompat();
            helper.setTarget(view).setOutlineShape(shape).setRoundRectRadius(10).setup();
            helper.invalidateOutline();
            helper.updateViewOutline(null);
        }
    }
}