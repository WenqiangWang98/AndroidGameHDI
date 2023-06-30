package com.example.androidstudio2dgamedevelopment.game.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class OpponentHand extends Hand {
    public OpponentHand( int x , int y){
        super(x,y);
    }

    public void sort() {
        float middle=displayMetricsX/2f;
        float height=displayMetricsY*0.05f;
        float rotationHeight=displayMetricsY*-1f;
        float distance=displayMetricsX*0.05f;
        float theta=displayMetricsY*-0.002f;
        Log.d("OpponentHand.java", "X: "+displayMetricsX+" y: "+displayMetricsY);
        for (int i=0;i<cardList.size();i++){
            cardList.get(i).setRotation(theta*(i+0.5f)-cardList.size()/2f*theta,middle,rotationHeight);
            cardList.get(i).setPosition(
                    middle+(distance*(i+0.5f)-cardList.size()/2f*distance),height);
            cardList.get(i).setClicking(false);

        }

    }




}

