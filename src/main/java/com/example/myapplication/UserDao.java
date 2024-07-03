package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM userData")
    List<UserData> gettingUsers();

    @Query("SELECT * FROM userData WHERE id = :id")
    List<UserData> userDataById(String id);

    @Insert
    void addUser(UserData user);

    @Update
    void updateUser(UserData user);

    @Query("DELETE FROM userData WHERE id = :id")
    void deleteUser( int id);
}
