package com.fsk.mynotes.presentation.layout_managers;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.LinearLayoutManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Me on 3/20/2015.
 */
@RunWith(AndroidJUnit4.class)
public class ColorFilterLayoutManagerTest {

    private Context mContext;


    @Before
    public void prepareForTest() {
        mContext = InstrumentationRegistry.getInstrumentation().getContext();
    }

    @Test
    public void testConstruction() {
        ColorFilterLayoutManager manager = new ColorFilterLayoutManager(mContext);
        assertThat(LinearLayoutManager.VERTICAL, is(manager.getOrientation()));

        manager = new ColorFilterLayoutManager(mContext, LinearLayoutManager.VERTICAL);
        assertThat(LinearLayoutManager.VERTICAL, is(manager.getOrientation()));

        manager = new ColorFilterLayoutManager(mContext, LinearLayoutManager.HORIZONTAL);
        assertThat(LinearLayoutManager.HORIZONTAL, is(manager.getOrientation()));
    }

    //Todo: Figure out a good way to test the measurement methods.
}
