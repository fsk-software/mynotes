package com.fsk.common.presentation.utils.outline_provider;


import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.fsk.common.presentation.components.FloatingFrameLayout;
import com.fsk.common.versions.Versions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests {@link FloatingFrameLayout}
 */
@RunWith(PowerMockRunner.class)
public class OutlineHelperTest {


    @Mock
    private View mMockedView;

    @Before
    public void prepareForTest() {
        PowerMockito.mockStatic(Versions.class);
        doNothing().when(mMockedView).invalidateOutline();
        doNothing().when(mMockedView).setOutlineProvider((ViewOutlineProvider) anyObject());
        doNothing().when(mMockedView).setClipToOutline(anyBoolean());

    }

    @Test
    public void testSetupWithNullViewLollipop() {
        try {
            new OutlineHelper().setTarget(null).setOutlineShape(OutlineShape.OVAL)
                               .setup();
            assert false;
        }
        catch (NullPointerException e) {
            assert true;
        }
    }

    @Test
    public void testSetupWithNullView() {
       try {
            new OutlineHelper().setTarget(null).setOutlineShape(OutlineShape.OVAL)
                               .setup();
            assert false;
        }
        catch (NullPointerException e) {
            assert true;
        }
    }

    @Test
    public void testSetupWithNullShape() {
        try {
            new OutlineHelper().setTarget(mMockedView).setup();
            assert false;
        }
        catch (NullPointerException e) {
        }
    }

    @Test
    public void testSetupWithNegativeRoundedRectCorners() {
        try {
            new OutlineHelper().setTarget(mMockedView).setOutlineShape(OutlineShape.OVAL).setRoundRectRadius(-1).setup();
            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
    }


    @Test
    public void testSunnyDay() {
        int count = 0;
        for (OutlineShape shape : OutlineShape.values()) {
            ++count;
            OutlineHelper helper = new OutlineHelper();
            helper.setTarget(mMockedView).setOutlineShape(shape).setRoundRectRadius(10).setup();
            verify(mMockedView, times(count)).setOutlineProvider((ViewOutlineProvider)anyObject());
            verify(mMockedView, times(count)).setClipToOutline(true);

            helper.invalidateOutline();
            verify(mMockedView, times(count)).invalidateOutline();

            Outline mockOutline = mock(Outline.class);
            helper.updateViewOutline(mockOutline);
            switch(shape) {
                case OVAL:
                    verify(mockOutline).setOval(anyInt(), anyInt(), anyInt(), anyInt());
                    break;

                case RECTANGLE:
                    verify(mockOutline).setRect(anyInt(), anyInt(), anyInt(), anyInt());
                    break;

                case ROUNDED_RECTANGLE:
                    verify(mockOutline).setRoundRect(anyInt(), anyInt(), anyInt(), anyInt(),
                                                     anyFloat());
                    break;
            }
        }

    }
}