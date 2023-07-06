package com.example.androidstudio2dgamedevelopment.game.manager;

import android.content.Context;

import com.example.androidstudio2dgamedevelopment.game.Country;
import com.example.androidstudio2dgamedevelopment.game.object.Card;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<Card> cardList = new ArrayList<>();
    Context context;
    List<Country> countries;
    float x,y;
    public Deck(Context context, List<Country> countries, float x,float y){


        this.context=context;
        this.countries=countries;
        this.x=x;
        this.y=y;
    }

    public Card drawCard(int index, boolean opponent) {
        if(index>0){
            return new Card(context,countries.get(index-1) ,x,y,opponent);
        }
        else return null;
    }
}
