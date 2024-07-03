package com.example.myapplication;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserData.class}, version = 3, exportSchema = false)
public abstract class UserDBHelper extends RoomDatabase {
    private static UserDBHelper INSTANCE;
    public abstract UserDao userDao();

    public static synchronized UserDBHelper getInstance(Context context){
        if( INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserDBHelper.class, "")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
