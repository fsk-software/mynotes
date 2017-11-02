package com.fsk.common.presentation.components;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Checkable;

import com.fsk.common.presentation.utils.checkable.OnCheckedChangeListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests {@link CheckableImageView}
 */
@RunWith(AndroidJUnit4.class)
public class CheckableImageViewTest {

    private Context mContext;

    @Before
    public void prepareForTest() {
        mContext = InstrumentationRegistry.getInstrumentation().getContext();
    }

    @Test
    public void testConstructor() {
        CheckableImageView button = new CheckableImageView(mContext);
        assertThat(button.isChecked(), is(false));

        button = new CheckableImageView(mContext, null);
        assertThat(button.isChecked(), is(false));

        button = new CheckableImageView(mContext, null, 0);
        assertThat(button.isChecked(), is(false));
    }


    @Test
    public void testCheckChanged() {
        CheckableImageView button = new CheckableImageView(mContext);
        assertThat(button.isChecked(), is(false));

        button.setOnCheckedChangeListener(new LocalOnCheckedChangeListener(true));
        button.setChecked(true);

        button.setOnCheckedChangeListener(new LocalOnCheckedChangeListener(false));
        button.setChecked(false);
    }


    @Test
    public void testToggle() {
        CheckableImageView button = new CheckableImageView(mContext);
        assertThat(button.isChecked(), is(false));

        button.setOnCheckedChangeListener(new LocalOnCheckedChangeListener(true));
        button.toggle();

        button.setOnCheckedChangeListener(new LocalOnCheckedChangeListener(false));
        button.toggle();
    }


    @Test
    public void testPerformClick() {
        CheckableImageView button = new CheckableImageView(mContext);
        assertThat(button.isChecked(), is(false));

        button.setOnCheckedChangeListener(new LocalOnCheckedChangeListener(true));
        button.performClick();

        button.setOnCheckedChangeListener(new LocalOnCheckedChangeListener(false));
        button.performClick();
    }


    /**
     * Local listener to validate the checked status.
     */
    private static class LocalOnCheckedChangeListener implements OnCheckedChangeListener {

        private final boolean mExpectedChecked;


        LocalOnCheckedChangeListener(boolean expected) {
            mExpectedChecked = expected;
        }


        @Override
        public void onCheckedChanged(final Checkable checkable) {
            assertThat(mExpectedChecked, is(checkable.isChecked()));
        }
    }
}
