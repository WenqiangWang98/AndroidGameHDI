package com.example.androidstudio2dgamedevelopment.game.object;

import android.graphics.Canvas;

/**
 * GameObject is an abstract class which is the foundation of all world objects in the game.
 */
public abstract class GameObject {
    protected float positionX, positionY = 0f;
    protected boolean clicking;


    public GameObject(float positionX, float positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public float getPositionX() { return positionX; }
    public float getPositionY() { return positionY; }

    public abstract void draw(Canvas canvas);
    public abstract void update();

    public void setClicking(boolean b) {
        this.clicking=b;
    }



}
