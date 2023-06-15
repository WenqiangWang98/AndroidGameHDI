package com.example.androidstudio2dgamedevelopment.gameobject;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PlayerHand extends Hand {
    private List<Card> cardList = new ArrayList<>();
    private int indexOfCardClicking;
    private boolean hasCardClicking;

    public PlayerHand( int x , int y){
        super(x,y);
    }

    public void checkAnyCardClicking(float x, float y) {
        for (int i=cardList.size()-1;i>=0;i--){
            if (cardList.get(i).checkClicking(x,y)){
                Log.d("hand.java","x: "+x+" y: "+y);
                indexOfCardClicking=i;
                hasCardClicking=true;
                return;
            }
        }
        hasCardClicking=false;
    }

    public boolean getHasCardClicking() {
        return hasCardClicking;
    }


    public Card getClickingCard() {
        return cardList.get(indexOfCardClicking);
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

    public void sort() {
        float middle=displayMetricsX/2f;
        float height=displayMetricsY-100;
        float distance=(50f+cardList.size()*10)/cardList.size()*10f;
        Log.d("PlayerHand.java", "X: "+displayMetricsX+"y: "+displayMetricsY);
        for (int i=0;i<cardList.size();i++){
            cardList.get(i).setPosition(
                    middle+(distance*(i+0.5f)-cardList.size()/2f*distance),height);
            cardList.get(i).setClicking(false);

        }

    }

    public void discard(Card cardPressed) {

        cardList.remove(cardPressed);
    }
    public void add(Card c){
        cardList.add(c);
    }


    public void dragCardTo(float x, float y) {
        if(hasCardClicking){
            getClickingCard().setPosition(x,y);
        }
    }

    public void playCardOnDesk(DeskManager deskManager) {
        if(hasCardClicking) {
            getClickingCard().setClicking(false);
            if(deskManager.hasInside(getClickingCard())){
                deskManager.getReceiverDesk().exchangeCard(this);
            }
        }
    }
}

