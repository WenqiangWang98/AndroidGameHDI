package com.example.androidstudio2dgamedevelopment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.example.androidstudio2dgamedevelopment.gameobject.Deck;
import com.example.androidstudio2dgamedevelopment.gameobject.Desk;
import com.example.androidstudio2dgamedevelopment.gameobject.DeskManager;
import com.example.androidstudio2dgamedevelopment.gameobject.PlayerHand;
import com.example.androidstudio2dgamedevelopment.gamepanel.Performance;
import com.example.androidstudio2dgamedevelopment.gamepanel.ScoreBoard;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderHeaderAware;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Game manages all objects in the game and is responsible for updating all states and render all
 * objects to the screen
 */
class Game extends SurfaceView implements SurfaceHolder.Callback {

    private int CardPointerId = 0;
    private GameLoop gameLoop;

    private final Performance performance;
    private final PlayerHand playerHand;
    private final Deck deck;
    private final DeskManager deskManager;
    private final ScoreBoard scoreBoard;



    public Game(Context context) {
        super(context);

        // Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);
        // Initialize display and center it around the player
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        // Initialize game panels
        performance = new Performance(context, gameLoop);
        // Initialize game UI
        scoreBoard=new ScoreBoard(context,displayMetrics.widthPixels, displayMetrics.heightPixels);
        // Initialize game objects
        deck =new Deck(context,Utils.initializeCSV(context));
        playerHand= new PlayerHand(displayMetrics.widthPixels, displayMetrics.heightPixels);
        for(int i=0;i<10;i++)playerHand.add(deck.drawCard());
        playerHand.sort();
        deskManager=new DeskManager();
        deskManager.add(new Desk(context,displayMetrics.widthPixels, displayMetrics.heightPixels));




    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Handle user input touch event actions
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                playerHand.checkAnyCardClicking(event.getX(), event.getY());
                deskManager.checkCardAnyDeskClicking(event.getX(), event.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                playerHand.dragCardTo(event.getX(), event.getY());
                deskManager.dragCardTo(event.getX(), event.getY());
                return true;

            case MotionEvent.ACTION_UP:
                playerHand.playCardOnDesk(deskManager);
                deskManager.returnCardToHand(playerHand);
                playerHand.sort();
                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("Game.java", "surfaceCreated()");
        if (gameLoop.getState().equals(Thread.State.TERMINATED)) {
            SurfaceHolder surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
            gameLoop = new GameLoop(this, surfaceHolder);
        }
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("Game.java", "surfaceChanged()");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("Game.java", "surfaceDestroyed()");
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        // Draw background

        // Draw game objects

        deskManager.draw(canvas);
        playerHand.draw(canvas);

        // Draw game panels

        performance.draw(canvas);

        // Draw game UI
        scoreBoard.draw(canvas,1,2);

    }

    public void update() {
        // Stop updating the game if the player is dead


        // Update game state

        deskManager.update();
        playerHand.update();

        // Iterate through enemyList and Check for collision between each enemy and the player and
        // spells in spellList.

        
        // Update gameDisplay so that it's center is set to the new center of the player's 
        // game coordinates
    }

    public void pause() {
        gameLoop.stopLoop();
    }
}
