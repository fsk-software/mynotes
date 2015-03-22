package com.fsk.common.presentation.components;


import android.test.AndroidTestCase;
import android.widget.Checkable;

import com.fsk.common.presentation.utils.checkable_helper.OnCheckedChangeListener;

/**
 * Tests {@link CheckableImageView}
 */
public class CheckableImageViewTest extends AndroidTestCase {


    public void testConstructor() {
        CheckableImageView button = new CheckableImageView(getContext());
        assertTrue(!button.isChecked());

        button = new CheckableImageView(getContext(), null);
        assertTrue(!button.isChecked());

        button = new CheckableImageView(getContext(), null, 0);
        assertTrue(!button.isChecked());
    }


    public void testCheckChanged() {
        CheckableImageView button = new CheckableImageView(getContext());
        assertTrue(!button.isChecked());

        button.setOnCheckedChangeListener(new LocalOnCheckedChangeListener(true));
        button.setChecked(true);

        button.setOnCheckedChangeListener(new LocalOnCheckedChangeListener(false));
        button.setChecked(false);
    }


    public void testToggle() {
        CheckableImageView button = new CheckableImageView(getContext());
        assertTrue(!button.isChecked());

        button.setOnCheckedChangeListener(new LocalOnCheckedChangeListener(true));
        button.toggle();

        button.setOnCheckedChangeListener(new LocalOnCheckedChangeListener(false));
        button.toggle();
    }


    public void testPerformClick() {
        CheckableImageView button = new CheckableImageView(getContext());
        assertTrue(!button.isChecked());

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
            assertEquals(mExpectedChecked, checkable.isChecked());
        }
    }
}
