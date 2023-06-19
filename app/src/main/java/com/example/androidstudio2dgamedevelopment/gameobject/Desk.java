package com.example.androidstudio2dgamedevelopment.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.androidstudio2dgamedevelopment.R;


public class Desk extends Rectangle{
    private final float RADIO_CORNER=50;


    private  Context context;
    private Card playedCard;
    public Desk(Context context, float x,float y,float screenWidth, float screenHeight) {
        super(ContextCompat.getColor(context, R.color.player),x,y,screenHeight*0.25f,screenHeight*0.25f);
        this.context=context;
    }

    @Override
    public void draw(Canvas canvas) {
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

        if (playedCard!=null){
            playedCard.draw(canvas);
            Log.d("Desk.java", "playedCard: "+playedCard);
        }
    }

    @Override
    public void update() {
        super.update();
        if (playedCard!=null)playedCard.update();
    }

    public Card getPlayedCard() {
        return playedCard;
    }

    public boolean checkPlayedCardClicking(float x, float y) {
        if(playedCard!=null)return playedCard.checkClicking(x,y);
        return false;
    }

    public void exchangeCard(PlayerHand hand) {
        Card aux;
        if(playedCard!=null){
            aux=playedCard;
            hand.add(aux);
        }
        playedCard=hand.getClickingCard();
        hand.discard(playedCard);
        playedCard.setPosition(positionX,positionY);
        playedCard.resetRotation();
    }

    public boolean returnCardToHand(PlayerHand hand) {
        playedCard.setClicking(false);
        if(!hasInside(playedCard)){
            //outside
            hand.add(playedCard);
            playedCard=null;
            return true;
        }else{
            playedCard.setPosition(positionX,positionY);
            return false;
        }

    }
}