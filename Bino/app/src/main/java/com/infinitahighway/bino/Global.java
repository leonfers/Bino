package com.infinitahighway.bino;

import com.infinitahighway.bino.model.Point;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Global {

    private static Date lastUpdate;
    private static List<Point> points;

    public static boolean shouldReload() {

        if (lastUpdate != null) {
            long difference = lastUpdate.getTime() - new Date().getTime();
            lastUpdate = new Date();
            return TimeUnit.MINUTES.convert(difference, TimeUnit.MILLISECONDS) < 10;
        } else {
            lastUpdate = new Date();
        }
        return true;
    }

    public static List<Point> getPoints() {
        if (points == null){
            points = new ArrayList<>();
        }
        return points;
    }

    public static void setPoints(List<Point> points) {
        Global.points = points;
    }
}
