package com.fsk.mynotes.presentation.animations.filter_toolbar;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.fsk.mynotes.R;

/**
 * Created by Me on 3/10/2015.
 */
public class MockActivity extends Activity {

    public View mView;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_mock_animator_activity);
        mView = findViewById(R.id.test_animator_mock_view);
    }
}
