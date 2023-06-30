package com.example.androidstudio2dgamedevelopment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.androidstudio2dgamedevelopment.task.MatchTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MatchActivity extends AppCompatActivity {

    String logTag="MatchActivity";

    String username;
    String password;

    Handler handler;
    ExecutorService es;
    Button buttonAsync;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        Intent intent = getIntent();
        username=intent.getStringExtra("username");
        password=intent.getStringExtra("password");

        es= Executors.newSingleThreadExecutor();

        buttonAsync = findViewById(R.id.buttonAsync);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(10);

        es = Executors.newSingleThreadExecutor();
        buttonAsync.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(logTag, "Scheduling new task in background thread");
                MatchTask task = new MatchTask(handler,username,password);
                es.execute(task);
            }
        });
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                int match = inputMessage.getData().getInt("match", -1);
                Log.d(logTag, "Match = " + match);
                if(match<0){
                    int i = inputMessage.getData().getInt("progress", -1);
                    Log.d(logTag, "Message Received with progress = " + i);
                    progressBar.setProgress(i);
                }else {
                    Toast.makeText(getApplicationContext(),"Match:  "+match ,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent( MatchActivity.this, MainActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    intent.putExtra("match", match);
                    startActivity(intent);
                }
            }
        };
    }


}
