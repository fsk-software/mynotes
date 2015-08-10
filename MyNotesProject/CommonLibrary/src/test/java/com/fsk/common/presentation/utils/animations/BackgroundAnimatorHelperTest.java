package com.fsk.common.presentation.utils.animations;


import android.animation.ObjectAnimator;

import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Tests {@link BackgroundAnimatorHelper}
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ObjectAnimator.class)
public class BackgroundAnimatorHelperTest {
//
//    @Mock
//    private View mMockView;
//
//
//    @Mock
//    private ObjectAnimator mMockObjectAnimator;
//
//
//    @Mock
//    private ViewPropertyAnimator mMockViewPropertyAnimator;
//
//
//    @Mock
//    private Animator.AnimatorListener mMockListener;
//
//
//    @Mock
//    private Resources mMockResources;
//
//
//    @Before
//    public void prepareForTest() {
//        mockStatic(ObjectAnimator.class);
//
//        PowerMockito.when(ObjectAnimator.ofObject(eq(mMockView), eq("backgroundColor"),
//                                                  (ArgbEvaluator) anyObject(), anyInt(), anyInt()))
//                    .thenReturn(mMockObjectAnimator);
//        doNothing().when(mMockObjectAnimator).addListener((Animator.AnimatorListener) anyObject());
//        when(mMockObjectAnimator.setDuration(anyLong())).thenReturn(mMockObjectAnimator);
//        doNothing().when(mMockObjectAnimator).start();
//
//        when(mMockView.animate()).thenReturn(mMockViewPropertyAnimator);
//        Mockito.doNothing().when(mMockViewPropertyAnimator).cancel();
//
//        when(mMockView.getResources()).thenReturn(mMockResources);
//        when(mMockResources.getColor(anyInt())).thenReturn(1);
//    }
//
//
//    @Test
//    public void crossBlendNullView() {
//        try {
//            BackgroundAnimatorHelper.crossBlendColors(null, 0, 0, 0, null);
//        }
//        catch (NullPointerException e) {
//        }
//    }
//
//
//    @Test
//    public void crossBlendViewWithoutListener() {
//        BackgroundAnimatorHelper.crossBlendColors(mMockView, 0, 0, 20, null);
//        verify(mMockViewPropertyAnimator).cancel();
//        verify(mMockObjectAnimator).setDuration(20L);
//        verify(mMockObjectAnimator).start();
//        verify(mMockObjectAnimator, never()).addListener((Animator.AnimatorListener) anyObject());
//    }
//
//
//    @Test
//    public void crossBlendViewWithListener() {
//        BackgroundAnimatorHelper.crossBlendColors(mMockView, 0, 0, 20, mMockListener);
//        verify(mMockViewPropertyAnimator).cancel();
//        verify(mMockObjectAnimator).setDuration(20L);
//        verify(mMockObjectAnimator).start();
//        verify(mMockObjectAnimator).addListener(mMockListener);
//    }
//
//
//    @Test
//    public void crossBlendColorResourcesNullView() {
//        try {
//            BackgroundAnimatorHelper.crossBlendColorResource(null, 0, 0, 0, null);
//        }
//        catch (NullPointerException e) {
//        }
//    }
//
//
//    @Test
//    public void crossBlendColorResourcesWithoutListener() {
//        BackgroundAnimatorHelper.crossBlendColorResource(mMockView, 0, 0, 20, null);
//        verify(mMockViewPropertyAnimator).cancel();
//        verify(mMockObjectAnimator).setDuration(20L);
//        verify(mMockObjectAnimator).start();
//        verify(mMockObjectAnimator, never()).addListener((Animator.AnimatorListener) anyObject());
//    }
//
//
//    @Test
//    public void crossBlendColorResourcesWithListener() {
//        BackgroundAnimatorHelper.crossBlendColorResource(mMockView, 0, 0, 20, mMockListener);
//        verify(mMockViewPropertyAnimator).cancel();
//        verify(mMockObjectAnimator).setDuration(20L);
//        verify(mMockObjectAnimator).start();
//        verify(mMockObjectAnimator).addListener(mMockListener);
//    }
}
