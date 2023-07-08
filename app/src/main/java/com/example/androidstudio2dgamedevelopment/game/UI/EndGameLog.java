package com.example.androidstudio2dgamedevelopment.game.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.androidstudio2dgamedevelopment.R;
import com.example.androidstudio2dgamedevelopment.game.object.Rectangle;

public class EndGameLog extends Rectangle {

    private float RADIO_CORNER;
    private float SCORE_TEXT_SIZE;
    private float SCORE_TEXT_WIDTH;
    private float SCORE_TEXT_HEIGHT;

    public EndGameLog(Context context, float x, float y) {
        super(ContextCompat.getColor(context, R.color.healthBarBorder),x/2f,y/2f,x*0.4f,y*0.45f);
        RADIO_CORNER=0.02f*x;
        SCORE_TEXT_SIZE=0.05f*x;
        SCORE_TEXT_WIDTH=0.015f*x;
        SCORE_TEXT_HEIGHT=0.07f*x;
        clicking=true;
    }


    public void draw(Canvas canvas,String winner) {
        if(clicking)return;
        canvas.drawRoundRect(left, top, right, bottom, RADIO_CORNER,RADIO_CORNER, paint);
        Paint titlePaint=new Paint();
        titlePaint.setColor(Color.BLACK);
        titlePaint.setTextSize(SCORE_TEXT_SIZE);
        titlePaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Game Over " , positionX, positionY-2f*SCORE_TEXT_HEIGHT, titlePaint);
        canvas.drawText(winner.equals("Tie game")?"Tie game":"Winner is "+winner , positionX, positionY+2f*SCORE_TEXT_HEIGHT, titlePaint);
    }

    public boolean endGame() {
        return !clicking;
    }
}
