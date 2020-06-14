package com.infinitahighway.bino;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.Locale;

import static com.infinitahighway.bino.NotificationInitializer.CHANNEL_ID;

public class MainService extends Service {

    private TextToSpeech mTTS;

    @Override
    public void onCreate() {
        super.onCreate();
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

            }
        });

    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult != null && locationResult.getLastLocation() != null) {
                int result = mTTS.setLanguage(new Locale("pt", "POR"));
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.d("VOICE_ERROR", "ERROR");
                } else {
                    String info = VoiceNotifications.getMedia(locationResult.getLastLocation());
                    if (!info.equals("")) {
                        updateNotification(info);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mTTS.speak(info, TextToSpeech.QUEUE_ADD, null, null);
                        } else {
                            mTTS.speak(info, TextToSpeech.QUEUE_ADD, null);
                        }
                    }
                }
            } else {
                Log.d("LOCATION_NOT_UPDATED", "ERROR");
            }
        }
    };


    public void updateNotification(String info) {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Bino")
                .setContentText(info)
                .setSmallIcon(R.drawable.ic_baseline_directions_bus_24)
                .build();
        NotificationInitializer.mNotificationManager.notify(1, notification);
    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Bino")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_baseline_directions_bus_24)
                .setContentIntent(pendingIntent)
                .build();

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(this)
                .requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        startForeground(1, notification);
        //Em caso de processamento pesado, procurar sobre background threads
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
