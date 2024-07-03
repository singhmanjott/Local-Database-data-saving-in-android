package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "userData")
public class UserData {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;
    @ColumnInfo(name = "userName")
    public String userName;
    @ColumnInfo(name = "userMail")
    public String userMail;
    @ColumnInfo(name = "userNumber")
    public String userNumber;

    @Ignore
    public UserData(@NonNull int id, String userName, String userMail, String userNumber) {
        this.id = id;
        this.userName = userName;
        this.userMail = userMail;
        this.userNumber = userNumber;
    }

    public UserData(String userName, String userMail, String userNumber) {
        this.userName = userName;
        this.userMail = userMail;
        this.userNumber = userNumber;
    }
}
