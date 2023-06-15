package com.example.androidstudio2dgamedevelopment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.androidstudio2dgamedevelopment.gameobject.Card;
import com.example.androidstudio2dgamedevelopment.gameobject.Deck;
import com.example.androidstudio2dgamedevelopment.gameobject.Desk;
import com.example.androidstudio2dgamedevelopment.gameobject.Hand;
import com.example.androidstudio2dgamedevelopment.gameobject.PlayerHand;
import com.example.androidstudio2dgamedevelopment.gamepanel.Performance;
import com.example.androidstudio2dgamedevelopment.graphics.SpriteSheet;

import java.util.ArrayList;
import java.util.List;

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
    List<Desk> deskList = new ArrayList<>();

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
        SpriteSheet spriteSheet = new SpriteSheet(context);
        //Animator animator = new Animator(spriteSheet.getPlayerSpriteArray());
        // Initialize game objects
        deck =new Deck(context);
        playerHand= new PlayerHand(displayMetrics.widthPixels, displayMetrics.heightPixels);
        for(int i=0;i<10;i++)playerHand.add(deck.drawCard());
        playerHand.refresh();
        deskList.add(new Desk(context,displayMetrics.widthPixels, displayMetrics.heightPixels));


        // Initialize Tilemap

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Handle user input touch event actions
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (playerHand.isPressed( event.getX(), event.getY())) {
                    // Card is pressed in this event -> setIsPressed(true) and store pointer id
                    CardPointerId = event.getPointerId(event.getActionIndex());
                    playerHand.getCardPressed().setIsPressed(true);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (playerHand.getIsPressed()) {
                    // Joystick was pressed previously and is now moved
                    playerHand.getCardPressed().setPosition(event.getX(), event.getY());
                   // Log.d("Game.java", "X: "+event.getX()+"y: "+event.getY());
                }
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (CardPointerId == event.getPointerId(event.getActionIndex())) {
                    // joystick pointer was let go off -> setIsPressed(false) and resetActuator()
                    playerHand.getCardPressed().setIsPressed(false);
                    playerHand.setIsPressed(false);
                    for(Desk desk:deskList){
                        desk.isInclude(playerHand);
                    }
                    playerHand.refresh();
                }
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
        for (Desk desk :deskList){
            desk.draw(canvas);
        }

        playerHand.draw(canvas);

        // Draw game panels

        performance.draw(canvas);

        // Draw Game over if the player is dead

    }

    public void update() {
        // Stop updating the game if the player is dead


        // Update game state
        for (Desk desk :deskList){
            desk.update();
        }
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
