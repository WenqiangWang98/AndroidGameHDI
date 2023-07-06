package com.example.androidstudio2dgamedevelopment.game.manager;

import android.util.Log;

import com.example.androidstudio2dgamedevelopment.MQTTModule;
import com.example.androidstudio2dgamedevelopment.game.object.Card;

public class PlayerHand extends Hand {
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

    public Card getClickingCard() {
        return cardList.get(indexOfCardClicking);
    }



    public void sort() {
        float middle=displayMetricsX/2f;
        float height=displayMetricsY*0.95f;
        float rotationHeight=displayMetricsY*2f;
        float distance=displayMetricsX*0.05f;
        float theta=displayMetricsY*0.002f;
        Log.d("PlayerHand.java", "X: "+displayMetricsX+" y: "+displayMetricsY);
        for (int i=0;i<cardList.size();i++){
            cardList.get(i).setRotation(theta*(i+0.5f)-cardList.size()/2f*theta,middle,rotationHeight);
            cardList.get(i).setPosition(
                    middle+(distance*(i+0.5f)-cardList.size()/2f*distance),height);
            cardList.get(i).setClicking(false);

        }

    }


    public void dragCardTo(float x, float y) {
        if(hasCardClicking){
            getClickingCard().setPosition(x,y);
        }
    }

    public void playCardOnDesk(DeskManager deskManager, MQTTModule mqtt_handler, String username) {
        if(hasCardClicking) {
            getClickingCard().setClicking(false);
            if(deskManager.hasInside(getClickingCard())){
                //deskManager.getReceiverDesk().exchangeCard(this);
                mqtt_handler.sendMSGToTopic("CTS/"+username+"/set_card_desk",
                        deskManager.getReceiverDesk().getIndex()+","+getClickingCard().getIndex(),2);
                //sort();
            }
        }
    }


}

