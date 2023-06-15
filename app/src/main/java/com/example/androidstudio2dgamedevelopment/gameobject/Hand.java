package com.example.androidstudio2dgamedevelopment.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public abstract class Hand {
    protected List<Card> cardList = new ArrayList<>();
    protected int displayMetricsX;
    protected int displayMetricsY;

    public Hand( int x , int y){
        this.displayMetricsX=x;
        this.displayMetricsY=y;
    }

    public abstract boolean isPressed(float x, float y);



    public void draw(Canvas canvas) {
        for (Card card: cardList)card.draw(canvas);
    }

    public void update() {
        for (Card card: cardList)card.update();
    }

    public abstract void  refresh();

    public void discard(Card cardPressed) {
        cardList.remove(cardPressed);
    }
    public void add(Card c){
        cardList.add(c);
        refresh();
    }


}

