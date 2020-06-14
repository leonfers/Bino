package com.infinitahighway.bino;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class Startup extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            String input = "Oi mundo";
            Intent serviceIntent = new Intent(context, MainService.class);
            serviceIntent.putExtra("inputExtra", input);
            ContextCompat.startForegroundService(context, serviceIntent);
            Toast.makeText(context, "Bino esta ativo", Toast.LENGTH_SHORT).show();
        }
    }
}
