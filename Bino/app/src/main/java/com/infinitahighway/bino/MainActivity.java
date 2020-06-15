package com.infinitahighway.bino;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceFragmentCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.infinitahighway.bino.model.Point;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        this.startService();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

    public void startService() {
        if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.FOREGROUND_SERVICE,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 1);
        } else {
            String input = "O Bino esta ligado.";
            Intent serviceIntent = new Intent(this, MainService.class);
            serviceIntent.putExtra("inputExtra", input);
            ContextCompat.startForegroundService(this, serviceIntent);
            registerNetworkCallback(this);
        }
    }

    public void stopService(View v) {
        Intent serviceIntent = new Intent(this, MainService.class);
        stopService(serviceIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startService();
                } else {
                    Toast.makeText(this, "Por favor, abra o bino e conceda o acesso a localizacao.", Toast.LENGTH_SHORT).show();
                }
        }
    }


    private void registerNetworkCallback(final Context context) {
        final ConnectivityManager manager =
                (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        manager.registerNetworkCallback(
                new NetworkRequest.Builder()
                        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                        .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
                        .build(),
                new ConnectivityManager.NetworkCallback() {
                    @Override
                    public void onAvailable(Network network) {
                        if (Global.shouldReload()) {

                            RequestQueue queue = Volley.newRequestQueue(context);
                            String url = " https://4ffa6230a176.ngrok.io/points";

                            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                        @Override
                                        public void onResponse(JSONArray response) {
                                            Log.e("ATUALIZADO", "PONTOS");
                                            List<Point> points = new ArrayList<>();
                                            for (int i = 0; i < response.length(); i++) {
                                                try {
                                                    points.add(new Point(response.getJSONObject(i)));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            Log.e("QUANTIDADE", "PONTOS");

                                            if (points.size() > 0) {
                                                DatabaseSingleton.getDatabase(context).pointDao().deleteAll();
                                                try {
                                                    Point[] array = points.toArray(new Point[0]);
                                                    DatabaseSingleton.getDatabase(context).pointDao().insertAll(array);
                                                    Global.setPoints(points);
                                                } catch (Exception e) {
                                                    System.out.println(e);
                                                }
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.e("ERRO", "REQUEST POINTS");
                                        }
                                    });
                            queue.add(jsonObjectRequest);
                        }
                    }

                    @Override
                    public void onLost(Network network) {
                        try {
                            if (Global.getPoints().size() == 0) {
                                Global.setPoints(DatabaseSingleton.getDatabase(context).pointDao().getAll());
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        Log.e("DESCONECTADO", "DESCONECTADO");// Global Static Variable
                    }
                });
    }
}