package com.example.androidstudio2dgamedevelopment.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.androidstudio2dgamedevelopment.R;

public class Card extends Rectangle{
    private final float MARGIN=10;
    private final Context context;
    private Rectangle marginRect;
    public Card(Context context) {
        super( ContextCompat.getColor(context, R.color.player),200,200,200,125);
        this.context=context;

        marginRect=new Rectangle(
                ContextCompat.getColor(context, R.color.colorPrimaryDark),
                positionX ,
                positionY,
                height - MARGIN,
                width - MARGIN);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        marginRect.draw(canvas);
    }

    @Override
    public void update() {
        super.update();
        marginRect.update();
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x,y);
        marginRect.setPosition(x,y);
        if(pressed) {
            zoom(2);
            marginRect.zoom(2);
            marginRect.setIsPressed(true);
        }else{
            marginRect.setIsPressed(false);
        }
    }

    @Override
    public void setRotation(float t, float rx, float ry) {
        super.setRotation(t, rx, ry);
        marginRect.setRotation(t, rx, ry);
    }

    @Override
    public void resetRotation() {
        super.resetRotation();
        marginRect.resetRotation();
    }
}
