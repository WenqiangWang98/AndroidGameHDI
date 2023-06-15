package com.example.androidstudio2dgamedevelopment.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/**
 * Circle is an abstract class which implements a draw method from GameObject for drawing the object
 * as a circle.
 */
public class Rectangle extends GameObject {
    protected float height;
    protected float width;
    protected Paint paint;
    protected float left;
    protected float top;
    protected float right;
    protected float bottom;
    protected boolean rotated=false;
    protected float theta;
    protected float rotationX;
    protected float rotationY;

    public Rectangle(int color, float positionX, float positionY, float height, float width) {
        super(positionX, positionY);
        this.height =height;
        this.width =width;
        this.left=positionX-width;
        this.top=positionY+height;
        this.right=positionX+width;
        this.bottom=positionY-height;

        // Set colors of circle
        paint = new Paint();
        paint.setShadowLayer(3f,0f,0f, Color.GRAY);
        paint.setColor(color);
    }



    public void draw(Canvas canvas) {
        if(rotated&&!pressed){
            canvas.save();
            canvas.rotate(theta,rotationX,rotationY);
            canvas.drawRect(left, top, right, bottom, paint);
            canvas.restore();
        }
        else{
            canvas.drawRect(left, top, right, bottom, paint);
        }
    }

    public void setPosition(float x, float y) {
        this.positionX=x;
        this.positionY=y;
        this.left=positionX-width;
        this.top=positionY+height;
        this.right=positionX+width;
        this.bottom=positionY-height;
    }

    public void zoom(float z){
        this.left = positionX - width*z;
        this.top = positionY + height*z;
        this.right = positionX + width*z;
        this.bottom = positionY - height*z;
    }

    public void update(){

    }

    public boolean isPressed(float x, float y) {
        return y < top && y > bottom && x < right && x > left;
    }


    public void setRotation(float t, float rx, float ry) {
        rotated=true;
        theta = t;
        rotationX=rx;
        rotationY=ry;
    }


    public void resetRotation(){
        rotated=false;
    }
}