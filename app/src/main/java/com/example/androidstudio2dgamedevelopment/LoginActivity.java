package com.example.androidstudio2dgamedevelopment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {
    EditText editUsername, editPassword;
    String username;
    String password;

    Handler handler;
    ExecutorService es;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        es= Executors.newSingleThreadExecutor();


        editUsername = findViewById(R.id.username);
        editPassword = findViewById(R.id.password);
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                boolean result = inputMessage.getData().getBoolean("login", false);
                if(!result){
                    Toast.makeText(getApplicationContext(),"Wrong username or password, try again.",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(getApplicationContext(),"Hello, "+username ,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent( LoginActivity.this, MainActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    startActivity(intent);
                }
            }
        };
    }

    public void loginPressed(View view) throws IOException {
        username=editUsername.getText().toString();
        password=editPassword.getText().toString();

        LoginTask task=new LoginTask(handler,username,password);
        es.execute(task);
    }


}
