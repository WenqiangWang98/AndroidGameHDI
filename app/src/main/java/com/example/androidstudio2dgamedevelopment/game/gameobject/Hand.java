package com.example.androidstudio2dgamedevelopment.game.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.service.autofill.AutofillService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

public abstract class Hand {
    protected List<Card> cardList = new ArrayList<>();
    protected Context context;
    protected int displayMetricsX;
    protected int displayMetricsY;


    public Hand(int x , int y){
        this.displayMetricsX=x;
        this.displayMetricsY=y;
    }

    public void draw(Canvas canvas) {
        for (int i=0;i<cardList.size();i++){
            cardList.get(i).draw(canvas);
        };
    }

    public void update() {
        for (Card card: cardList)card.update();
    }
    public abstract void  sort();

    public void discard(Card card) {
        cardList.remove(card);
    }

    public void reset(){cardList = new ArrayList<>();}
    public void add(Card c){
        if(c!=null) {
            cardList.add(c);
            sort();
        }
    }


}

