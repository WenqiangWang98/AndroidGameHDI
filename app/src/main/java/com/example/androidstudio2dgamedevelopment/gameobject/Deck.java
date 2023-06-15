package com.example.androidstudio2dgamedevelopment.gameobject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;

import com.example.androidstudio2dgamedevelopment.R;
import com.example.androidstudio2dgamedevelopment.graphics.SpriteSheet;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<Card> cardList = new ArrayList<>();
    public Deck(Context context){
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        Bitmap aux=BitmapFactory.decodeResource(context.getResources(), R.drawable.ac);
        for(int i=0;i<50;i++)cardList.add(new Card(context,context.getDrawable(R.drawable.ac)));
    }

    public Card drawCard() {
        Card aux=cardList.get(0);
        cardList.remove(aux);
        return aux;
    }
}
