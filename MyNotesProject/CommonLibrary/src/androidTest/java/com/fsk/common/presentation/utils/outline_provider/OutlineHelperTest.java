package com.fsk.common.presentation.utils.outline_provider;


import android.content.Context;
import android.graphics.Outline;
import android.test.AndroidTestCase;
import android.view.View;

import com.fsk.common.presentation.components.FloatingFrameLayout;
import com.fsk.common.versions.Versions;

/**
 * Tests {@link FloatingFrameLayout}
 */
public class OutlineHelperTest extends AndroidTestCase {


    public void testSetupWithNullView() {
        if (!Versions.isAtLeastLollipop()) {
            return;
        }

        try {
            new OutlineHelper().setTarget(null).setOutlineShape(OutlineShape.OVAL)
                               .setup();
            assert false;
        }
        catch (NullPointerException e) {
            assert true;
        }
    }

    public void testSetupWithNullShape() {
        if (!Versions.isAtLeastLollipop()) {
            return;
        }

        Context context = getContext();
        try {
            new OutlineHelper().setTarget(new View(context)).setup();
            assert false;
        }
        catch (NullPointerException e) {
            assert true;
        }
    }

    public void testSetupWithNegativeRoundedRectCorners() {
        if (!Versions.isAtLeastLollipop()) {
            return;
        }

        Context context = getContext();
        try {
            new OutlineHelper().setTarget(new View(context)).setOutlineShape(OutlineShape.OVAL).setRoundRectRadius(-1).setup();
            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
    }


    public void testSunnyDay() {
        if (!Versions.isAtLeastLollipop()) {
            return;
        }

        Context context = getContext();
        View view = new View(context);
        for (OutlineShape shape : OutlineShape.values()) {
            OutlineHelper helper = new OutlineHelper();
            helper.setTarget(view).setOutlineShape(shape).setRoundRectRadius(10).setup();
            assertNotNull(view.getOutlineProvider());
            helper.invalidateOutline();
            helper.updateViewOutline(new Outline());
        }

    }
}