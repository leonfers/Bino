package com.infinitahighway.bino.persistence;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.infinitahighway.bino.model.Point;

import java.util.List;

@Dao
public interface PointDao {

    @Query("SELECT * FROM Point")
    List<Point> getAll();

    @Insert
    void insertAll(Point... points);

    @Query("delete FROM point")
    void deleteAll();

}
