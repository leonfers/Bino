package com.infinitahighway.bino;

import android.location.Location;
import android.util.Log;

import com.infinitahighway.bino.model.Point;

public class VoiceNotifications {

    public static String getMedia(Location current) {
        for (Point point : Global.getPoints()) {
            Location destiny = new Location("");
            destiny.setLatitude(point.getLatitude());
            destiny.setLongitude(point.getLongitude());
            double distance = current.distanceTo(destiny);
            Log.e("DISTANCIAS",String.valueOf(distance));
            if (distance < 1500) {
                Global.getPoints().remove(Global.getPoints().indexOf(point));
                return point.getDescription() + " a aproximadamente ' " + ((int) distance) + " metros.";
            }
        }
        return "";
    }
}
