package com.infinitahighway.bino;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationInitializer extends Application {

    public static final String CHANNEL_ID = "binoServiceChannel";
    public static NotificationManager mNotificationManager;

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel binoServiceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Bino Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            mNotificationManager = getSystemService(NotificationManager.class);
            mNotificationManager.createNotificationChannel(binoServiceChannel);
        }
    }
}
