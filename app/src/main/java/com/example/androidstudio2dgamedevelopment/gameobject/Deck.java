package com.example.androidstudio2dgamedevelopment.gameobject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.androidstudio2dgamedevelopment.Country;
import com.example.androidstudio2dgamedevelopment.R;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<Card> cardList = new ArrayList<>();
    public Deck(Context context, List<Country> countries){
        cardList.add(new Card(context,countries.get(144)));
        for(Country country:countries)
            cardList.add(new Card(context,country));
    }

    public Card drawCard() {
        Card aux=cardList.get(0);
        cardList.remove(aux);
        return aux;
    }
}
