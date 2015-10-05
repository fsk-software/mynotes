package com.fsk.mynotes;


import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.fsk.common.database.DatabaseHelper;
import com.fsk.common.utils.Preconditions;
import com.fsk.common.utils.Strings;
import com.fsk.mynotes.data.database.MyNotesDatabaseModel;

/**
 * Custom Application class that setups the Application wide local resources (ie. Database).
 */
public class MyNotesApplication extends Application {

    /**
     * The application context.  This is null until {@link android.app.Application#onCreate()} is
     * called. I don't like having to do this, but sometimes it is the lesser of evils.
     */
    private static Context sApplicationContext;


    @Override
    public void onCreate() {
        super.onCreate();
        sApplicationContext = getApplicationContext();
        startStrictModeForDebugging();
        prepareDatabase(this);
    }


    /**
     * Start strict mode only when the app is built in debug mode.
     */
    private void startStrictModeForDebugging() {
            String packageName = Strings.nullToEmpty(sApplicationContext.getPackageName());
            if (packageName.endsWith(".debug")) {
                StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                        .detectAll()
                        .penaltyLog()
                        .penaltyDialog()
                        .build());

                StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll()
                        .penaltyLog()
                        .build());
            }
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


    /**
     * Retrieves the application context.
     *
     * @return the application context.  This may be null.
     */
    public static Context getMyNotesContext() {
        return sApplicationContext;
    }


    /**
     * Send a local broadcast using the application context.
     *
     * @param intent
     *         the intent to broadcast.
     */
    public static void sendLocalBroadcast(@NonNull Intent intent) {
        Preconditions.checkNotNull(intent);

        LocalBroadcastManager broadcastManager =
                LocalBroadcastManager.getInstance(MyNotesApplication.getMyNotesContext());
        broadcastManager.sendBroadcast(intent);
    }
}

