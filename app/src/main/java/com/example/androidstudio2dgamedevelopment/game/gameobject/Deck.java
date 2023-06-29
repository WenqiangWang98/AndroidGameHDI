package com.example.androidstudio2dgamedevelopment.game.gameobject;

import android.content.Context;

import com.example.androidstudio2dgamedevelopment.Country;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cardList = new ArrayList<>();
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

        for(Country country:countries)
            cardList.add(new Card(context,country,x,y));
        Collections.shuffle(cardList);
    }

    public Card drawCard() {
        Card aux=cardList.get(0);
        cardList.remove(aux);
        return aux;
    }
}
