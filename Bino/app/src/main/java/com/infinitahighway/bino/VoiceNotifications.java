package com.infinitahighway.bino;

import android.location.Location;

import com.infinitahighway.bino.model.Point;

public class VoiceNotifications {

    public static String getMedia(Location current) {
        for (Point point : Global.getPoints()) {
            Location destiny = new Location("");
            destiny.setLatitude(point.getLatitude());
            destiny.setLongitude(point.getLongitude());
            double distance = current.distanceTo(destiny) / 1000;
            if (distance < 5) {
                Global.getPoints().remove(Global.getPoints().indexOf(point));
                return point.getDescription() + " a aproximadamente ' " + ((int) distance) + " quilômetros.";
            }
        }
        return "";
    }
}
