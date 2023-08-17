package com.example.newcalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Button btn = findViewById(R.id.login_btn);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText username = findViewById(R.id.username_login);
                EditText password = findViewById(R.id.password_login);

                FirebaseDatabase database = FirebaseDatabase.getInstance("https://mad-prac-51d2f-default-rtdb.asia-southeast1.firebasedatabase.app/");

                DatabaseReference dbRef = database.getReference("Users").child(username.getText().toString());
                Log.d("Database Reference", dbRef.toString());

                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("Log", "Login 1");
                        LoginUser value = dataSnapshot.getValue(LoginUser.class);
                        Log.d("Log", "Login 2");
                        try {
                            if (value.password.equals(password.getText().toString())) {
                                Log.d("Log", "Login Sucess");
                                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LoginPage.this, ListActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Log.d("Log", "Login Fail");
                                Toast.makeText(getApplicationContext(), "Login Fail", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch(Exception e){
                            Toast.makeText(getApplicationContext(), "Invalid Login User", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("Log", "Login Fail");
                        Toast.makeText(getApplicationContext(), "Invalid Login", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}