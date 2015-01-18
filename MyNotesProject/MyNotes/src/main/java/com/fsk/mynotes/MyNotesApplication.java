package com.fsk.mynotes;


import android.app.Application;
import android.content.Context;

import com.fsk.common.database.DatabaseHelper;
import com.fsk.mynotes.data.database.MyNotesDatabaseModel;

/**
 * Custom Application class that setups the Application wide local resources (ie. Database).
 */
public class MyNotesApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        prepareDatabase(this);
    }


    /**
     * Initialize the database and its associated {@link com.fsk.common.database.DatabaseHelper}
     *
     * @param context
     *         The context to associate with the {@link com.fsk.common.database.DatabaseHelper}.
     */
    private void prepareDatabase(Context context) {
        DatabaseHelper.initialize(context, new MyNotesDatabaseModel());
    }
}

