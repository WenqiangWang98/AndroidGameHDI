package com.example.androidstudio2dgamedevelopment.game.gameobject;

import android.content.Context;

import com.example.androidstudio2dgamedevelopment.Country;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cardList = new ArrayList<>();
    Context context;
    List<Country> countries;
    float x,y;
    public Deck(Context context, List<Country> countries, float x,float y){
        //for tests
//        cardList.add(new Card(context,countries.get(3)));
//        cardList.add(new Card(context,countries.get(25)));
//        cardList.add(new Card(context,countries.get(50)));
//        cardList.add(new Card(context,countries.get(51)));
//        cardList.add(new Card(context,countries.get(56)));
//        cardList.add(new Card(context,countries.get(70)));
//        cardList.add(new Card(context,countries.get(73)));
//        cardList.add(new Card(context,countries.get(77)));
//        cardList.add(new Card(context,countries.get(79)));
//        cardList.add(new Card(context,countries.get(88)));
//        cardList.add(new Card(context,countries.get(131)));
//        cardList.add(new Card(context,countries.get(133)));
//        cardList.add(new Card(context,countries.get(137)));
//        cardList.add(new Card(context,countries.get(144)));
//        cardList.add(new Card(context,countries.get(149)));
//        cardList.add(new Card(context,countries.get(152)));
//        cardList.add(new Card(context,countries.get(154)));
//        cardList.add(new Card(context,countries.get(156)));
//        cardList.add(new Card(context,countries.get(178)));
//        cardList.add(new Card(context,countries.get(187)));

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
