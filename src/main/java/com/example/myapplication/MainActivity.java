package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterForShowingData.editingUser{
    private EditText name, mail, phone;
    private AppCompatButton addUser, updateUser;
    private RecyclerView recyclerView;
    private final Context context = this;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        new getUserFromDatabase().start();

        addUser.setOnClickListener(view -> {
            UserData userData = new UserData( name.getText().toString(), mail.getText().toString(), phone.getText().toString() );
            new addUserInDatabase(userData).start();
            name.setText("");
            mail.setText("");
            phone.setText("");
            new getUserFromDatabase().start();
        });

        updateUser.setOnClickListener(view -> {
            UserData userData = new UserData( id, name.getText().toString(), mail.getText().toString(), phone.getText().toString());
            new updateUserInDatabase(userData).start();
            name.setText("");
            mail.setText("");
            phone.setText("");
            new getUserFromDatabase().start();
            addUser.setVisibility(View.VISIBLE);
            updateUser.setVisibility(View.GONE);
        });
    }
    private void init(){
        name = findViewById(R.id.userName);
        mail = findViewById(R.id.userEmail);
        phone = findViewById(R.id.userNumber);
        addUser = findViewById(R.id.addUser);
        updateUser = findViewById(R.id.updateUser);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void puttingDataInRecyclerView(List<UserData> allUsers) {
        AdapterForShowingData adapter = new AdapterForShowingData();
        adapter.updatingData(context, allUsers, this);
        recyclerView.setLayoutManager( new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void editUser(UserData userData) {
        Log.d( "userdata", "sunny boy");
        addUser.setVisibility(View.GONE);
        updateUser.setVisibility(View.VISIBLE);
        this.id = userData.id;
        name.setText(userData.userName);
        mail.setText(userData.userMail);
        phone.setText(userData.userNumber);
    }
    class updateUserInDatabase extends Thread{
        UserData user;
        public updateUserInDatabase(UserData user) {
            this.user = user;
        }
        public void run(){
            super.run();
            UserDBHelper db = Room.databaseBuilder(getApplicationContext(), UserDBHelper.class, "customersData").fallbackToDestructiveMigration().build();
            UserDao data = db.userDao();
            data.updateUser(user);
        }
    }
    class addUserInDatabase extends Thread{
        UserData user;
        public addUserInDatabase(UserData user) {
            this.user = user;
        }
        public void run(){
            super.run();
            UserDBHelper db = Room.databaseBuilder(getApplicationContext(), UserDBHelper.class, "customersData").fallbackToDestructiveMigration().build();
            UserDao data = db.userDao();
            data.addUser(user);
        }
    }
    class getUserFromDatabase extends Thread{
        public void run(){
            super.run();
            UserDBHelper db = Room.databaseBuilder(getApplicationContext(), UserDBHelper.class, "customersData").fallbackToDestructiveMigration().build();
            UserDao data = db.userDao();
            List<UserData> allUsers;
            allUsers = data.gettingUsers();
            runOnUiThread(() -> puttingDataInRecyclerView(allUsers));
        }
    }
}