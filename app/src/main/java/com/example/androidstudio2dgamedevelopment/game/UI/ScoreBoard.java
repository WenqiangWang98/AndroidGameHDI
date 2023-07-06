package com.example.androidstudio2dgamedevelopment.game.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.androidstudio2dgamedevelopment.R;
import com.example.androidstudio2dgamedevelopment.game.object.Rectangle;

public class ScoreBoard extends Rectangle {
    private float RADIO_CORNER=30;
    private float SCORE_TEXT_SIZE;
    private float SCORE_TEXT_WIDTH;
    private float SCORE_TEXT_HEIGHT;


    public ScoreBoard(Context context, float x,float y) {
        super(ContextCompat.getColor(context,R.color.enemy),0.15f*y,y/2f,0.15f*y,0.06f*y);
        SCORE_TEXT_SIZE=0.02f*x;
        SCORE_TEXT_WIDTH=0.015f*x;
        SCORE_TEXT_HEIGHT=0.007f*x;
    }

    public void draw(Canvas canvas, int player, int opponent,int round,boolean roundOfPlayer) {
        Paint paint;

        // Draw Shadow
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShadowLayer(20, 0, 0, Color.GRAY);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(left, top, right, bottom, RADIO_CORNER,RADIO_CORNER, paint);
        paint =new Paint();

        if(roundOfPlayer)paint.setColor(Color.GREEN);
        else paint.setColor(Color.LTGRAY);
        // Draw background
        canvas.drawRoundRect(left, top, right, bottom, RADIO_CORNER,RADIO_CORNER, paint);
        // Draw score
        paint.setColor(Color.BLACK);
        paint.setTextSize(SCORE_TEXT_SIZE);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Round: "+round , positionX, positionY-SCORE_TEXT_HEIGHT, paint);
        canvas.drawText(" : " , positionX, positionY+2*SCORE_TEXT_HEIGHT, paint);
        canvas.drawText(""+player, positionX-SCORE_TEXT_WIDTH, positionY+2*SCORE_TEXT_HEIGHT, paint);
        canvas.drawText(""+opponent, positionX+SCORE_TEXT_WIDTH, positionY+2*SCORE_TEXT_HEIGHT, paint);

    }
}
