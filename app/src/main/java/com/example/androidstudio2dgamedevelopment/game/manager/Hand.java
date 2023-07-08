package com.example.androidstudio2dgamedevelopment.game.manager;

import android.content.Context;
import android.graphics.Canvas;

import com.example.androidstudio2dgamedevelopment.game.object.Card;

import java.util.ArrayList;
import java.util.List;

public abstract class Hand {
    protected List<Card> cardList = new ArrayList<>();
    protected Context context;
    protected int displayMetricsX;
    protected int displayMetricsY;


    public Hand(int x , int y){
        this.displayMetricsX=x;
        this.displayMetricsY=y;
    }

    public void draw(Canvas canvas, boolean turnOfPlayer) {
        for (int i=0;i<cardList.size();i++){
            cardList.get(i).draw(canvas,turnOfPlayer);
        };
    }

    public void update() {
        for (Card card: cardList)card.update();
    }
    public abstract void  sort();

    public void discard(Card card) {
        cardList.remove(card);
    }
    public void discard(int index) {
        cardList.removeIf(card -> card.getIndex() == index);
        sort();
    }


    public void add(Card c){
        if(c!=null) {
            cardList.add(c);
            sort();
        }
    }


}

