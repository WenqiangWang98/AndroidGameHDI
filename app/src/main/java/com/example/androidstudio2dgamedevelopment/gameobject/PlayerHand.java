package com.example.androidstudio2dgamedevelopment.gameobject;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PlayerHand extends Hand {
    private List<Card> cardList = new ArrayList<>();
    private int cardPressedIndex;
    private boolean cardPressed;

    public PlayerHand( int x , int y){
        super(x,y);
    }

    public boolean isPressed(float x, float y) {
        for (int i=cardList.size()-1;i>=0;i--){

            if (cardList.get(i).isPressed(x,y)){
                Log.d("hand.java","x: "+x+" y: "+y);
                cardPressedIndex=i;
                cardPressed=true;
                return true;
            }
        }
        return false;
    }

    public boolean getIsPressed() {
        return cardPressed;
    }


    public Card getCardPressed() {
        return cardList.get(cardPressedIndex);
    }

    public void setIsPressed(boolean b) {
        cardPressed=b;
    }

    public void draw(Canvas canvas) {
        float middle=displayMetricsX/2f;
        float height=displayMetricsY+500;
        float theta=(7f+cardList.size())/cardList.size();
        for (int i=0;i<cardList.size();i++){
            canvas.save();
            cardList.get(i).setRotation(theta*(i+0.5f)-cardList.size()/2f*theta,middle,height);
            cardList.get(i).draw(canvas);
            canvas.restore();
        }
    }

    public void update() {
        for (Card card: cardList)card.update();
    }

    public void refresh() {
        float middle=displayMetricsX/2f;
        float height=displayMetricsY-100;
        float distance=(50f+cardList.size()*10)/cardList.size()*10f;
        Log.d("PlayerHand.java", "X: "+displayMetricsX+"y: "+displayMetricsY);
        for (int i=0;i<cardList.size();i++){
            cardList.get(i).setPosition(
                    middle+(distance*(i+0.5f)-cardList.size()/2f*distance),height);
        }
    }

    public void discard(Card cardPressed) {

        cardList.remove(cardPressed);
    }
    public void add(Card c){
        cardList.add(c);
    }


}

