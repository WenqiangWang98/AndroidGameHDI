package com.example.androidstudio2dgamedevelopment.game.gameobject;

import android.graphics.Canvas;

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

    public abstract void checkAnyCardClicking(float x, float y);



    public void draw(Canvas canvas) {
        for (Card card: cardList)card.draw(canvas);
    }

    public void update() {
        for (Card card: cardList)card.update();
    }

    public abstract void  sort();

    public void discard(Card card) {
        cardList.remove(card);
    }
    public void add(Card c){
        cardList.add(c);
    }


}

