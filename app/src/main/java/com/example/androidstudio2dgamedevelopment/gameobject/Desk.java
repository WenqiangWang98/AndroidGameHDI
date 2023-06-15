package com.example.androidstudio2dgamedevelopment.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.androidstudio2dgamedevelopment.R;


public class Desk extends Rectangle{
    private  Context context;
    private Card player;
    public Desk(Context context, int widthPixels, int heightPixels) {
        super(ContextCompat.getColor(context, R.color.desk),widthPixels/2f,heightPixels/2f,300,300);
        this.context=context;
    }

    public void isInclude(PlayerHand hand) {
        Card card=hand.getCardPressed();
        if(isPressed(card.getPositionX(),card.getPositionY())){
            card.resetRotation();
            if (player!=null){
                hand.add(player);
            }
            player=card;
            player.setPosition(positionX,positionY);
            hand.discard(card);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (player!=null){
            player.draw(canvas);
            Log.d("Desk.java", "player:"+player.getPositionX());
        }
    }

    @Override
    public void update() {
        super.update();
        if (player!=null)player.update();
    }
}