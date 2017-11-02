package com.fsk.common.presentation.utils.checkable;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;

import com.fsk.common.R;
import com.fsk.common.presentation.components.CheckableImageView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Tests {@link com.fsk.common.presentation.animations.SimpleAnimatorListener}
 */
@RunWith(AndroidJUnit4.class)
public class CheckableViewHelperTest {

        private Context mContext;

        @Before
        public void prepareForTest() {
            mContext = InstrumentationRegistry.getInstrumentation().getContext();
        }
    
    @Test
    public void testConstructorWithNullContext() {
        try {
            new CheckableViewHelper(mContext);
            assert false;
        }
        catch (NullPointerException e) {
        }
    }


    @Test
    public void testConstructorSunnyDay() {
        CheckableViewHelper helper = new CheckableViewHelper(mContext);
    }

    @Test
    public void testReadAttributesWithNullAttrs() {
        CheckableViewHelper helper = new CheckableViewHelper(mContext);
        assertThat(helper.readCheckableAttributes(mContext, null), is(false));
    }

    @Test
    public void testReadAttributesWithNullContext() {
        CheckableViewHelper helper = new CheckableViewHelper(mContext);
        helper.readCheckableAttributes(null, null);
        assertThat(helper.readCheckableAttributes(mContext, null), is(false));
    }

    @Test
    public void testSetCheckedWithDefaultAttributes() {

        View rootView = LayoutInflater.from(mContext)
                                      .inflate(R.layout.test_checkable_view_helper, null);
        String defaultOffText = mContext.getString(R.string.accessibility_description_not_checked);

        String defaultOnText = mContext.getString(R.string.accessibility_description_checked);
        CheckableImageView button =
                (CheckableImageView) rootView.findViewById(R.id.test_checkable_view_helper_no_attributes);

        assertThat(button.isChecked(), is(false));
        assertThat(defaultOffText, is(button.getContentDescription()));

        button.setChecked(true);
        assertThat(button.isChecked(), is(true));
        assertThat(defaultOnText, is(button.getContentDescription()));

        button.setChecked(false);
        assertThat(button.isChecked(), is(false));
        assertThat(defaultOffText, is(button.getContentDescription()));
    }


    @Test
    public void testCheckedInAttributes() {

        View rootView = LayoutInflater.from(mContext)
                                      .inflate(R.layout.test_checkable_view_helper, null);
        String defaultOnText = mContext.getString(R.string.accessibility_description_checked);

        CheckableImageView button =
                (CheckableImageView) rootView.findViewById(R.id.test_checkable_view_helper_initially_checked);

        assertThat(button.isChecked(), is(true));
        assertThat(defaultOnText, is(button.getContentDescription()));
    }


    @Test
    public void testSetCheckedWithCustomOnTextAttribute() {

        View rootView = LayoutInflater.from(mContext)
                                      .inflate(R.layout.test_checkable_view_helper, null);
        String defaultOffText = mContext.getString(R.string.accessibility_description_not_checked);

        CheckableImageView button =
                (CheckableImageView) rootView.findViewById(R.id.test_checkable_view_helper_on_text);

        assertThat(button.isChecked(), is(false));
        assertThat(defaultOffText, is(button.getContentDescription()));

        button.setChecked(true);
        assertThat(button.isChecked(), is(true));
        assertThat("TestOn", is(button.getContentDescription()));

        button.setChecked(false);
        assertThat(button.isChecked(), is(false));
        assertThat(defaultOffText, is(button.getContentDescription()));
    }



    @Test
    public void testSetCheckedWithCustomOffTextAttribute() {

        View rootView = LayoutInflater.from(mContext)
                                      .inflate(R.layout.test_checkable_view_helper, null);
        String defaultOnText = mContext.getString(R.string.accessibility_description_checked);

        CheckableImageView button =
                (CheckableImageView) rootView.findViewById(R.id.test_checkable_view_helper_off_text);

        assertThat(button.isChecked(), is(false));
        assertThat("TestOff", is(button.getContentDescription()));

        button.setChecked(true);
        assertThat(button.isChecked(), is(true));
        assertThat(defaultOnText, is(button.getContentDescription()));

        button.setChecked(false);
        assertThat(button.isChecked(), is(false));
        assertThat("TestOff", is(button.getContentDescription()));
    }


    @Test
    public void testSetCheckedWithCustomOnOffTextAttribute() {

        View rootView = LayoutInflater.from(mContext)
                                      .inflate(R.layout.test_checkable_view_helper, null);

        CheckableImageView button =
                (CheckableImageView) rootView.findViewById(R.id.test_checkable_view_helper_on_off_text);

        assertThat(button.isChecked(), is(false));
        assertThat("TestOff", is(button.getContentDescription()));

        button.setChecked(true);
        assertThat(button.isChecked(), is(true));
        assertThat("TestOn", is(button.getContentDescription()));

        button.setChecked(false);
        assertThat(button.isChecked(), is(false));
        assertThat("TestOff", is(button.getContentDescription()));
    }



    @Test
    public void testNotifyListener() {
        MockCheckable mockCheckable =new MockCheckable();
        mockCheckable.mChecked = true;

        LocalOnCheckedChangeListener listener = new LocalOnCheckedChangeListener(mockCheckable);
        CheckableViewHelper helper = new CheckableViewHelper(mContext);
        helper.setOnCheckedChangeListener(listener);
        helper.sendChangedNotification(mockCheckable);
        assertThat(listener.mReceivedNotification, is(true));
    }


    @Test
    public void testNotifyListenerWithNullCheckable() {

        LocalOnCheckedChangeListener listener = new LocalOnCheckedChangeListener(null);
        CheckableViewHelper helper = new CheckableViewHelper(mContext);
        helper.setOnCheckedChangeListener(listener);
        helper.sendChangedNotification(null);
        assertThat(listener.mReceivedNotification, is(false));
    }

    /**
     * Local listener to validate the checked status.
     */
    private static class LocalOnCheckedChangeListener implements OnCheckedChangeListener {

        private final Checkable mExpectedCheckable;

        boolean mReceivedNotification;

        LocalOnCheckedChangeListener(Checkable expected) {
            mExpectedCheckable = expected;
        }


        @Override
        public void onCheckedChanged(final Checkable checkable) {
            assertThat(mExpectedCheckable, is(checkable));
            mReceivedNotification = true;
        }
    }

    private class MockCheckable implements Checkable {

        boolean mChecked = false;
        @Override
        public void setChecked(final boolean checked) {

        }


        @Override
        public boolean isChecked() {
            return mChecked;
        }


        @Override
        public void toggle() {

        }
    }
}
