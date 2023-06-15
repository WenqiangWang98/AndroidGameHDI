package com.example.androidstudio2dgamedevelopment.gameobject;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.example.androidstudio2dgamedevelopment.R;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<Card> cardList = new ArrayList<>();
    public Deck(Context context){
        for(int i=0;i<50;i++)
            cardList.add(new Card(context));
    }

    public Card drawCard() {
        Card aux=cardList.get(0);
        cardList.remove(aux);
        return aux;
    }
}
