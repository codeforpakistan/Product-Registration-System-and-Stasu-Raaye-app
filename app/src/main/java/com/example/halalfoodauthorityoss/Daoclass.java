package com.example.halalfoodauthorityoss;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.halalfoodauthorityoss.model.RoomModel;

import java.util.List;

@Dao
public interface Daoclass {

    @Insert
    void insertAllData(RoomModel model);

    //Select All Data
    @Query("select * from  user")
    List<RoomModel> getAllData();

    //DELETE DATA
    @Query("delete from user")
    void deleteData();

}
