package com.fsk.common.presentation.utils.outline_provider;


import android.content.Context;
import android.graphics.Outline;
import android.test.AndroidTestCase;
import android.view.View;

import com.fsk.common.presentation.components.FloatingFrameLayout;

/**
 * Tests {@link FloatingFrameLayout}
 */
public class OutlineHelperCompatTest extends AndroidTestCase {


    public void testSetupWithNullView() {
        Context context = getContext();
        try {
            new OutlineHelperCompat().setTarget(null).setOutlineShape(OutlineShape.OVAL)
                               .setup();
            assert false;
        }
        catch (NullPointerException e) {
            assert true;
        }
    }

    public void testSetupWithNullShape() {
        Context context = getContext();
        try {
            new OutlineHelperCompat().setTarget(new View(context)).setup();
            assert false;
        }
        catch (NullPointerException e) {
            assert true;
        }
    }

    public void testSetupWithNegativeRoundedRectCorners() {
        Context context = getContext();
        try {
            new OutlineHelperCompat().setTarget(new View(context)).setOutlineShape(OutlineShape.OVAL).setRoundRectRadius(-1).setup();
            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
    }


    public void testSunnyDay() {
        Context context = getContext();
        View view = new View(context);
        for (OutlineShape shape : OutlineShape.values()) {
            OutlineHelperCompat helper = new OutlineHelperCompat();
            helper.setTarget(view).setOutlineShape(shape).setRoundRectRadius(10).setup();
            assertNotNull(view.getOutlineProvider());
            helper.invalidateOutline();
            helper.updateViewOutline(new Outline());
        }

    }
}