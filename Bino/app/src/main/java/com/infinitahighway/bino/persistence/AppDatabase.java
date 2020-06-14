package com.infinitahighway.bino.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.infinitahighway.bino.model.Point;

@Database(entities = {Point.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PointDao pointDao();
}