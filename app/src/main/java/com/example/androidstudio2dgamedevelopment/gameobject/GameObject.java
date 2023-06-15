package com.example.androidstudio2dgamedevelopment.gameobject;

import android.graphics.Canvas;

/**
 * GameObject is an abstract class which is the foundation of all world objects in the game.
 */
public abstract class GameObject {
    protected float positionX, positionY = 0f;
    protected boolean clicking;

    public GameObject() { }

    public GameObject(float positionX, float positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public float getPositionX() { return positionX; }
    public float getPositionY() { return positionY; }

    public abstract void draw(Canvas canvas);
    public abstract void update();


    public static double getDistanceBetweenObjects(GameObject obj1, GameObject obj2) {
        return Math.sqrt(
            Math.pow(obj2.getPositionX() - obj1.getPositionX(), 2) +
            Math.pow(obj2.getPositionY() - obj1.getPositionY(), 2)
        );
    }

    public void setClicking(boolean b) {
        this.clicking=b;
    }

    public boolean getClicking() {
        return clicking;
    }


}
