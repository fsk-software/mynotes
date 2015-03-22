package com.fsk.mynotes.presentation.animations.filter_toolbar;


import android.animation.Animator;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.view.View;

/**
 * Test the {@link com.fsk.mynotes.presentation.animations.filter_toolbar.ToolbarAnimatorBuilderCompat}.
 */
public class ToolbarAnimatorBuilderCompatTest extends ActivityUnitTestCase<MockActivity> {


    public ToolbarAnimatorBuilderCompatTest() {
        super(MockActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        startActivity(new Intent(getInstrumentation().getTargetContext(), MockActivity.class), null,
                      null);
    }

    public void testBuildingWithoutSettingAnyValues() {
        try {
            new ToolbarAnimatorBuilderCompat().build();
            assert false;
        }
        catch (NullPointerException e) {
            assert true;
        }
    }


    public void testBuildingWithNullTarget() {
        try {
            new ToolbarAnimatorBuilderCompat().setFocus(0, 0).setRadius(1).setTarget(null).build();
            assert false;
        }
        catch (NullPointerException e) {
            assert true;
        }
    }


    public void testBuildingWithInvalidRadius() {
        try {
            new ToolbarAnimatorBuilderCompat().setFocus(0, 0).setRadius(-1)
                                              .setTarget(getActivity().mView).build();
            assert false;
        }
        catch (IllegalArgumentException e) {
            assert true;
        }
    }


    public void testBuildingHideAnimatorSunnyDay() {

        View target = getActivity().mView;
        ToolbarAnimatorBuilder builder =
                new ToolbarAnimatorBuilderCompat().setFocus(-1, -2).setRadius(1).setTarget(target);
        assertEquals(-1, builder.mX);
        assertEquals(-2, builder.mY);
        assertEquals(1, builder.mRadius);
        assertEquals(target, builder.mTarget);
        assertTrue(!builder.mReveal);
        assertEquals(View.GONE, builder.getEndVisibility());
        assertEquals(builder.mRadius, builder.getStartRadius());
        assertEquals(0, builder.getEndRadius());

        Animator animator = builder.build();
        assertNotNull(animator);
        assertEquals(1, animator.getListeners().size());
    }


    public void testBuildingRevealAnimatorSunnyDay() {
        View target = getActivity().mView;
        ToolbarAnimatorBuilder builder =
                new ToolbarAnimatorBuilderCompat().setFocus(1, 2).setRadius(3).setReveal(true)
                                                  .setTarget(target);
        assertEquals(1, builder.mX);
        assertEquals(2, builder.mY);
        assertEquals(3, builder.mRadius);
        assertEquals(target, builder.mTarget);
        assertTrue(builder.mReveal);
        assertEquals(View.VISIBLE, builder.getEndVisibility());
        assertEquals(builder.mRadius, builder.getEndRadius());
        assertEquals(0, builder.getStartRadius());
        Animator animator = builder.build();

        assertNotNull(animator);
        assertEquals(1, animator.getListeners().size());
    }
}