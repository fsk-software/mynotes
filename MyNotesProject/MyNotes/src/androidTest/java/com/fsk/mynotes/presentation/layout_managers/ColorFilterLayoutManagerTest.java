package com.fsk.mynotes.presentation.layout_managers;


import android.support.v7.widget.LinearLayoutManager;
import android.test.AndroidTestCase;

/**
 * Created by Me on 3/20/2015.
 */
public class ColorFilterLayoutManagerTest extends AndroidTestCase {

    public void testConstruction() {
        ColorFilterLayoutManager manager = new ColorFilterLayoutManager(getContext());
        assertEquals(LinearLayoutManager.VERTICAL, manager.getOrientation());

        manager = new ColorFilterLayoutManager(getContext(), LinearLayoutManager.VERTICAL);
        assertEquals(LinearLayoutManager.VERTICAL, manager.getOrientation());

        manager = new ColorFilterLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL);
        assertEquals(LinearLayoutManager.HORIZONTAL, manager.getOrientation());
    }

    //Todo: Figure out a good way to test the measurement methods.
}
