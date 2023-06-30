package com.example.androidstudio2dgamedevelopment.game.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PlayerHand extends Hand {
    private int indexOfCardClicking;
    private boolean hasCardClicking;

    public PlayerHand( int x , int y){
        super(x,y);
    }

    public void checkAnyCardClicking(float x, float y) {
        for (int i=cardList.size()-1;i>=0;i--){
            if (cardList.get(i).checkClicking(x,y)){
                Log.d("hand.java","x: "+x+" y: "+y);
                indexOfCardClicking=i;
                hasCardClicking=true;
                return;
            }
        }
        hasCardClicking=false;
    }


    public boolean getHasCardClicking() {
        return hasCardClicking;
    }


    public Card getClickingCard() {
        return cardList.get(indexOfCardClicking);
    }



    public void sort() {
        float middle=displayMetricsX/2f;
        float height=displayMetricsY*0.95f;
        float rotationHeight=displayMetricsY*2f;
        float distance=displayMetricsX*0.05f;
        float theta=displayMetricsY*0.002f;
        Log.d("PlayerHand.java", "X: "+displayMetricsX+" y: "+displayMetricsY);
        for (int i=0;i<cardList.size();i++){
            cardList.get(i).setRotation(theta*(i+0.5f)-cardList.size()/2f*theta,middle,rotationHeight);
            cardList.get(i).setPosition(
                    middle+(distance*(i+0.5f)-cardList.size()/2f*distance),height);
            cardList.get(i).setClicking(false);

        }

    }


    public void dragCardTo(float x, float y) {
        if(hasCardClicking){
            getClickingCard().setPosition(x,y);
        }
    }

    public void playCardOnDesk(DeskManager deskManager) {
        if(hasCardClicking) {
            getClickingCard().setClicking(false);
            if(deskManager.hasInside(getClickingCard())){
                deskManager.getReceiverDesk().exchangeCard(this);
            }
        }
    }

    public String getMatchInfo() {
        String postData="&playerCards="+cardList.size();
        for (int i=0;i<cardList.size();i++){
            Card d=cardList.get(i);
            postData+="&playerHand"+i+"="+d.getIndex();
        }
        return postData;
    }
}

