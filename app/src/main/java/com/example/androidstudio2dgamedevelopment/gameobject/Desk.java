package com.example.androidstudio2dgamedevelopment.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.androidstudio2dgamedevelopment.R;


public class Desk extends Rectangle{
    private  Context context;
    private Card playedCard;
    public Desk(Context context, int widthPixels, int heightPixels) {
        super(ContextCompat.getColor(context, R.color.white),widthPixels/2f,heightPixels/2f,300,300);
        this.context=context;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (playedCard!=null){
            playedCard.draw(canvas);
            Log.d("Desk.java", "player:"+playedCard);
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