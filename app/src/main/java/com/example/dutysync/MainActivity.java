package com.example.dutysync;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    EditText editText_username,editText_password;
    Button button_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        editText_username= (EditText) findViewById(R.id.username);
        editText_password= (EditText) findViewById(R.id.password);
        button_signin= (Button) findViewById(R.id.signin);


        button_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateUsername() | !validatePassword()){

                }else {
                    checkUser();
                }
            }
        });

    }
    public Boolean validateUsername(){
        String val=editText_username.getText().toString();
        if (val.isEmpty()){
            editText_username.setError("Username is Empty");
            return false;
        }else {
            editText_username.setError(null);
            return true;
        }
    }
    public Boolean validatePassword(){
        String val=editText_password.getText().toString();
        if (val.isEmpty()){
            editText_password.setError("Password is Empty");
            return false;
        }else {
            editText_password.setError(null);
            return true;
        }
    }

    public void checkUser(){
        String userUsername=editText_username.getText().toString().trim();
        String userPassword=editText_password.getText().toString().trim();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase=reference.orderByChild("username").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    editText_username.setError(null);
                    String passwordFromBD=snapshot.child(userUsername).child("password").getValue(String.class);
                    if (!Objects.equals(passwordFromBD,userPassword)){
                        editText_username.setError(null);
                        Intent intent=new Intent(MainActivity.this, SideNavigation.class);
                        startActivity(intent);
                    }else {
                        editText_password.setError("Invalid Credentials");
                        editText_password.requestFocus();
                    }
                }else {
                    editText_username.setError("Invalid Credentials");
                    editText_username.requestFocus();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}