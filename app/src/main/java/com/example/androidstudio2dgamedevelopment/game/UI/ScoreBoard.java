package com.example.androidstudio2dgamedevelopment.game.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.androidstudio2dgamedevelopment.R;
import com.example.androidstudio2dgamedevelopment.game.object.Rectangle;

public class ScoreBoard extends Rectangle {
    private final float RADIO_CORNER;
    private final float SCORE_TEXT_SIZE;
    private final float SCORE_TEXT_WIDTH;
    private final float SCORE_TEXT_HEIGHT;
    private final float SCREEN_WIDTH;
    private final float SCREEN_HEIGHT;


    public ScoreBoard(Context context, float x,float y) {
        super(ContextCompat.getColor(context,R.color.enemy),0.15f*y,y/2f,0.11f*y,0.04f*y);
        SCORE_TEXT_SIZE=0.02f*x;
        SCORE_TEXT_WIDTH=0.015f*x;
        SCORE_TEXT_HEIGHT=0.003f*x;
        SCREEN_WIDTH=x;
        SCREEN_HEIGHT=y;
        RADIO_CORNER=0.02f*y;
    }

    public void draw(Canvas canvas, int player, int opponent,int round,boolean roundOfPlayer,String playerName,String opponentName) {
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
        paint=new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(SCORE_TEXT_SIZE);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Round "+round , positionX, positionY+2f*SCORE_TEXT_HEIGHT, paint);

        // Draw Shadow
        Paint paintCircleStroke = new Paint();
        paintCircleStroke.setColor(Color.WHITE);
        paintCircleStroke.setStyle(Paint.Style.STROKE);
        paintCircleStroke.setStrokeWidth(SCORE_TEXT_HEIGHT*0.5f);
        Paint paintInCircle =new Paint();
        paintInCircle.setColor(Color.GREEN);
        //paintInCircle.setStyle(Paint.Style.FILL);
        for(int i=0;i<5;i++){
            if(i<opponent) {
                canvas.drawCircle(positionX + (i - 1.5f) * SCREEN_HEIGHT * 0.05f, positionY + 2 * SCORE_TEXT_HEIGHT - SCREEN_HEIGHT * 0.4f, width * 0.1f, paintInCircle);
            }
            canvas.drawCircle(positionX + (i - 1.5f) * SCREEN_HEIGHT * 0.05f, positionY + 2 * SCORE_TEXT_HEIGHT - SCREEN_HEIGHT * 0.4f, width * 0.1f, paintCircleStroke);
        }

        for(int i=0;i<5;i++){
            if(i<player) {
                canvas.drawCircle(positionX + (i - 1.5f) * SCREEN_HEIGHT * 0.05f, positionY +   2 * SCORE_TEXT_HEIGHT +SCREEN_HEIGHT * 0.4f, width * 0.1f, paintInCircle);
            }
            canvas.drawCircle(positionX + (i - 1.5f) * SCREEN_HEIGHT * 0.05f, positionY +   2 * SCORE_TEXT_HEIGHT+ SCREEN_HEIGHT * 0.4f, width * 0.1f, paintCircleStroke);
        }


        // Draw background
        paint =new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(SCORE_TEXT_SIZE);
        canvas.drawText(opponentName+"", positionX+SCORE_TEXT_WIDTH, positionY-5*SCORE_TEXT_HEIGHT-SCREEN_HEIGHT*0.4f, paint);


        canvas.drawText(playerName+"", positionX+SCORE_TEXT_WIDTH, positionY-5*SCORE_TEXT_HEIGHT+SCREEN_HEIGHT*0.4f, paint);

    }
}
