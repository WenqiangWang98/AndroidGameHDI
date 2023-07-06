package com.example.androidstudio2dgamedevelopment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.androidstudio2dgamedevelopment.game.Game;

/**
 * MainActivity is the entry point to our application.
 */
public class MainActivity extends Activity {
    String logTag="MainActivity.java" ;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(logTag, "onCreate()");
        super.onCreate(savedInstanceState);

        // Set content view to game, so that objects in the Game class can be rendered to the screen
        Intent intent = getIntent();
        String username=intent.getStringExtra("username");
        String password=intent.getStringExtra("password");
        String game_name=intent.getStringExtra("game_name");
        game=new Game(this,username,password,game_name);
        setContentView(R.layout.activity_main);
        LinearLayout surface = findViewById(R.id.main);
        surface.addView(game);
    }

    @Override
    protected void onStart() {
        Log.d(logTag, "onStart()");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(logTag, "onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(logTag, "onPause()");
        game.pause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(logTag, "onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(logTag, "onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        // Comment out "super.onBackPressed()" to disable button
        //super.onBackPressed();
    }
}
