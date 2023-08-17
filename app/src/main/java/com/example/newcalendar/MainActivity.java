package com.example.newcalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.EventLogTags;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<User> userList;
    User user;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);

        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("Id");
        String name = extras.getString("Name");
        String description = extras.getString("Description");
        boolean bool = extras.getBoolean("Bool", false);
        position = extras.getInt("position");

        //for hello text
        Intent receivingEnd = getIntent();
        String message = receivingEnd.getStringExtra("Key");
        TextView myTextView = findViewById(R.id.editTextText2);
        myTextView.setText(message);

        user = new User(id, name, description, bool);
        //before clicking anyt, whats displayed
        if (bool == true){
            button.setText("Unfollow");
        }
        else if (bool == false){
            button.setText("Follow");
        }

        button.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (user.followed == true)
                {
                    button.setText("Follow");
                    user.followed = false;
                    Toast.makeText(getApplicationContext(), "Unfollowed", Toast.LENGTH_SHORT).show();
                }
                //if not fllwing yet, button shows 'Follow'
                else if (user.followed == false)
                {
                    button.setText("Unfollow");
                    user.followed = true;
                    Toast.makeText(getApplicationContext(), "Followed", Toast.LENGTH_SHORT).show();
                }
                if(userList == null){
                    //loadData();
                }

                userList.get(position).followed = user.followed;
                //saveData(user);
            }
        });

        Button button2 = findViewById(R.id.button2);
        //passing info via intent
        button2.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent message = new Intent(MainActivity.this, MessageGroup.class);
                startActivity(message);
            }
        });

        /*
        private void loadData(){
            SharedPreferences sp = getSharedPreferences("contactDb", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sp.getString("users", null);
            Type type = new TypeToken<ArrayList<User>>() {}.getType();
            userList = gson.fromJson(json, type);
            //MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
            //userList = dbHandler.getUsers();
        }

        private void saveData(){
            SharedPreferences sp = getSharedPreferences("contactDb", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            Gson gson = new Gson();
            String json = gson.toJson(activityList);
            editor.putString("users", json);
            editor.apply();

        }

        public void saveData(User user){
            DBHandler dbHandler = new DBHandler(this, null, null, 1);
            dbHandler.updateUser(user);

        }
         */

    }
}