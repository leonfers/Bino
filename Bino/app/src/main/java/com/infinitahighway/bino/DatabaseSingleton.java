package com.infinitahighway.bino;

import android.content.Context;

import androidx.room.Room;

import com.infinitahighway.bino.persistence.AppDatabase;

public class DatabaseSingleton {

    private static AppDatabase database;

    public static AppDatabase getDatabase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context,
                    AppDatabase.class, "database-name").allowMainThreadQueries().build();
        }
        return database;
    }
}
