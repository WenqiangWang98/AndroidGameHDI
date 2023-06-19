package com.example.androidstudio2dgamedevelopment.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.example.androidstudio2dgamedevelopment.R;
import com.example.androidstudio2dgamedevelopment.gameobject.Rectangle;

public class Background extends Rectangle {
    private float RADIO_CORNER=30;
    private Drawable background;
    public Background(Context context, float x, float y) {
        super(ContextCompat.getColor(context, R.color.enemy),x/2,y/2f,x/2,y/2f);
        background=context.getDrawable(R.drawable.background);
        background.setBounds((int)left,(int)bottom,(int)right,(int)top);
    }

    public void draw(Canvas canvas) {
        // Draw background
        background.draw(canvas);
    }
}
