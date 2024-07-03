package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

public class AdapterForShowingData extends RecyclerView.Adapter<AdapterForShowingData.ViewHolder>{
    public interface editingUser{
        void editUser( UserData userData);
    }
    private editingUser listener;
    private Context context;
    private List<UserData> allUsers;

    public void updatingData(Context context, List<UserData> allUsers, editingUser listener) {
        this.context = context;
        this.allUsers = allUsers;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterForShowingData.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForShowingData.ViewHolder holder, int position) {
        Toast.makeText(context, "getting users", Toast.LENGTH_SHORT).show();
        holder.userName.setText( allUsers.get(position).userName);
        holder.userEmail.setText( allUsers.get(position).userMail);
        holder.userContactNumber.setText( allUsers.get(position).userNumber);

        holder.singleUser.setOnLongClickListener(view -> {
            AlertDialog.Builder builder = getBuilder(position, view);
            builder.setNegativeButton("Update", (dialog, which) -> {
                Log.d( "userdata", allUsers.get(position).userName);
                listener.editUser(allUsers.get(position));
            });
            builder.show();
            return false;
        });
    }

    @NonNull
    private AlertDialog.Builder getBuilder(int position, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Select operation:");
        builder.setMessage("What kind of changes do you want to do with this user?");
        builder.setPositiveButton("Delete", (dialog, which) -> {
            new deleteUserFromDatabase(allUsers.get(position).id).start();
            notifyDataSetChanged();
            allUsers.remove(position);
        });
        return builder;
    }

    @Override
    public int getItemCount() {
        return allUsers.size();
    }

    class deleteUserFromDatabase extends Thread{
        private int id;
        public deleteUserFromDatabase(int id){
            this.id = id;
        }
        public void run(){
            super.run();
            UserDBHelper db = Room.databaseBuilder(context, UserDBHelper.class, "customersData").fallbackToDestructiveMigration().build();
            UserDao data = db.userDao();
            data.deleteUser(id);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout singleUser;
        TextView userName, userEmail, userContactNumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.nameUser);
            userEmail = itemView.findViewById(R.id.mailUser);
            userContactNumber = itemView.findViewById(R.id.phoneUser);
            singleUser = itemView.findViewById(R.id.singleUser);
        }
    }

}
