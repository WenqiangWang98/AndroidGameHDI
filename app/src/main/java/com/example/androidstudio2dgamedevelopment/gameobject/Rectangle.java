package com.example.androidstudio2dgamedevelopment.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;

import com.example.androidstudio2dgamedevelopment.Utils;

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
    protected float zoom=1f;

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
        paint.setColor(color);
    }



    public void draw(Canvas canvas) {
        if(rotated){
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
        update();
    }

    public void update(){
        if(clicking)zoom=1.5f;
        else zoom=1f;
        this.left=positionX-width*zoom;
        this.top=positionY+height*zoom;
        this.right=positionX+width*zoom;
        this.bottom=positionY-height*zoom;
    }

    public boolean hasInside(GameObject ob) {
        return ob.getPositionY() < top && ob.getPositionY() > bottom && ob.getPositionX() < right && ob.getPositionX() > left;
    }

    public boolean checkClicking(float x, float y) {
        if(rotated){
            clicking=false;
            if(Utils.getDistanceBetweenPoints(positionX, positionY,x,y)>Math.sqrt(2)*Math.max(height,width))return false;
            PointF[] rectCorners={
                    new PointF(left,top),
                    new PointF(right,top),
                    new PointF(right,bottom),
                    new PointF(left,bottom),
            };
            PointF origin = new PointF(rotationX, rotationY); // center of rotation
            double angleRadians = Math.toRadians(theta);
            PointF[] rotatedCorners = {
                    Utils.rotatedRectangle(origin, rectCorners[0],angleRadians),
                    Utils.rotatedRectangle(origin, rectCorners[1],angleRadians),
                    Utils.rotatedRectangle(origin, rectCorners[2],angleRadians),
                    Utils.rotatedRectangle(origin, rectCorners[3],angleRadians),
            };
            if(Utils.isPointInsideRotatedRectangle(new PointF(x,y),rotatedCorners)){
                clicking=true;
                return true;
            }else{
                return false;
            }

        }else{
            if (y < top && y > bottom && x < right && x > left){
                clicking=true;
                return true;
            }else{
                clicking=false;
                return false;
            }
        }

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