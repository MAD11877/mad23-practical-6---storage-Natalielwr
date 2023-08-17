package com.example.newcalendar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

public class ListActivity extends AppCompatActivity {

    ArrayList<User> userList;
    UserAdapter uAdapter;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //loadData();

        userList = new ArrayList<User>();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        uAdapter = new UserAdapter(userList, (ActivityResultListener) this);

        LinearLayoutManager uLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(uLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(uAdapter);
    }

    //vh
    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView profilePicture;
        public TextView contactName;
        public TextView messagePreview;
        private ActivityResultLauncher<Intent> activityResultLauncher;
        private ActivityResultListener resultListener;

        public UserViewHolder(View itemView) {
            super(itemView);
            this.resultListener = resultListener;
            profilePicture = itemView.findViewById(R.id.imageView);
            contactName = itemView.findViewById(R.id.textView);
            messagePreview = itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int row = getLayoutPosition();

            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setTitle("Profile");
            builder.setMessage(contactName.getText().toString());
            builder.setCancelable(true);
            builder.setPositiveButton("View", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(ListActivity.this, MainActivity.class);
                    intent.putExtra("Key", contactName.getText().toString());
                    startActivity(intent); //changes to the destination activity
                }
            });
            builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //action
                }
            });

            AlertDialog alert = builder.create();
            alert.show();

        }
    }

    //adapter
    public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
        ArrayList<User> data;
        private ActivityResultListener resultListener;

        public UserAdapter(ArrayList<User> input, ActivityResultListener resultListener) {
            data = input;
            this.resultListener = resultListener;
        }
        public void setUserList(ArrayList<User> uList){
            data = uList;
        }

        @Override
        public int getItemViewType(int position) {
            // Return the view type based on your logic
            User user = data.get(position);
            String name = user.getName();
            if(name.charAt(name.length()-1) == '7') {
                return 1;
            }
            return 0;
        }

        public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View item = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.view_holder,
                    parent,
                    false);
            return new UserViewHolder(item);
        }

        public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
            User user = userList.get(position);
            holder.contactName.setText(user.getName());
            holder.messagePreview.setText(user.getDescription());

            Log.v("MAD", user.getName() + " is at " + String.valueOf(position));
        }

        public int getItemCount() {
            return userList.size();
        }

    }

    public void onResult(Intent data) {
        Bundle extras = getIntent().getExtras();
        int index = extras.getInt("position");
        boolean hasFollowed = extras.getBoolean("hasFollowed");
        System.out.println();
    }

    //generate users
    private ArrayList<User> generateUserList(int count) {
        ArrayList<User> userList = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < count; i++) {
            boolean followed = random.nextBoolean();

            long min1 = 100000000L;  // Minimum 9-digit number
            long max1 = 9999999999L; // Maximum 10-digit number
            long randomNumber1 = min1 + ((long) (random.nextDouble() * (max1 - min1)));
            String name = "User " + randomNumber1;

            long min2 = 100000000L;  // Minimum 9-digit number
            long max2 = 9999999999L; // Maximum 10-digit number
            long randomNumber2 = min2 + ((long) (random.nextDouble() * (max2 - min2)));
            String description = "Description " + randomNumber2;

            User user = new User(name, description, followed);
            userList.add(user); // adding to the ArrayList for adaptor to use.
            dbHandler.addUser(user); // adding to db table
        }

        return userList;
    }
}