package com.infinitahighway.bino.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;
//    {name: 'Posto São José', latitude:'35.4562', longitude: '25.4578', description: 'Posto de combustível', date: '', avaliation: 'true', visible: 'true'},

@Entity
public class Point {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "latitude")
    private double latitude;

    @ColumnInfo(name = "longitude")
    private double longitude;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "avaliation")
    private boolean avaliation;

    @ColumnInfo(name = "visible")
    private boolean visible;

    public Point() {
    }

    public Point(JSONObject pointJson) {
        try {
            this.id = pointJson.getInt("id");
            this.name = pointJson.getString("name");
            this.latitude = pointJson.getDouble("latitude");
            this.longitude = pointJson.getDouble("longitude");
            this.description = pointJson.getString("description");
            this.date = pointJson.getString("date");
            this.avaliation = pointJson.getBoolean("avaliation");
            this.visible = pointJson.getBoolean("visible");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isAvaliation() {
        return avaliation;
    }

    public void setAvaliation(boolean avaliation) {
        this.avaliation = avaliation;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
