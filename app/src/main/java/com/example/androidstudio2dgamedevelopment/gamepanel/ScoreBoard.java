package com.example.androidstudio2dgamedevelopment.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;

import com.example.androidstudio2dgamedevelopment.GameLoop;
import com.example.androidstudio2dgamedevelopment.R;
import com.example.androidstudio2dgamedevelopment.gameobject.Rectangle;

public class ScoreBoard extends Rectangle {
    private float RADIO_CORNER=30;
    private float SCORE_TEXT_SIZE;
    private float SCORE_TEXT_WIDTH;
    private float SCORE_TEXT_HEIGHT;


    public ScoreBoard(Context context, float x,float y) {
        super(ContextCompat.getColor(context,R.color.enemy),0.05f*x,y/2f,0.03f*x,0.015f*x);
        SCORE_TEXT_SIZE=0.02f*x;
        SCORE_TEXT_WIDTH=0.015f*x;
        SCORE_TEXT_HEIGHT=0.007f*x;
    }

    public void draw(Canvas canvas, String player, String opponent) {
        Paint paint;

        // Draw Shadow
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShadowLayer(20, 0, 0, Color.GRAY);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(left, top, right, bottom, RADIO_CORNER,RADIO_CORNER, paint);
        paint =new Paint();
        paint.setColor(Color.LTGRAY);
        // Draw background
        canvas.drawRoundRect(left, top, right, bottom, RADIO_CORNER,RADIO_CORNER, paint);
        // Draw score
        paint.setColor(Color.BLUE);
        paint.setTextSize(SCORE_TEXT_SIZE);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(" : " , positionX, positionY+SCORE_TEXT_HEIGHT, paint);
        canvas.drawText(player, positionX-SCORE_TEXT_WIDTH, positionY+SCORE_TEXT_HEIGHT, paint);
        canvas.drawText(opponent, positionX+SCORE_TEXT_WIDTH, positionY+SCORE_TEXT_HEIGHT, paint);

    }
}
