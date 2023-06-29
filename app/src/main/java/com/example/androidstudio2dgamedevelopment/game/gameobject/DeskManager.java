package com.example.androidstudio2dgamedevelopment.game.gameobject;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

public class DeskManager {
    private List<Desk> deskList = new ArrayList<>();
    private Desk deskWithClickingCard;
    private boolean hasDeskWihClickingCard;
    private Desk receiverDesk;

    public void add(Desk desk) {
        deskList.add(desk);
    }


    

    public void update() {
        for (Desk desk :deskList){
            desk.update();
        }
    }

    public void draw(Canvas canvas) {
        for (Desk desk :deskList){
            desk.draw(canvas);
        }
    }

    public void checkCardAnyDeskClicking(float x, float y) {
        for(Desk desk:deskList){
            if(desk.checkPlayedCardClicking(x,y)){
                deskWithClickingCard= desk;
                hasDeskWihClickingCard=true;
                return;
            }
        }
        hasDeskWihClickingCard=false;
    }

    public void dragCardTo(float x, float y) {
        if(hasDeskWihClickingCard){
            deskWithClickingCard.getPlayedCard().setPosition(x,y);
        }
    }

    public boolean hasInside(GameObject ob) {
        for(Desk desk:deskList){
            if(desk.hasInside( ob)){
                receiverDesk=desk;
                return true;
            }
        }
        return false;
    }

    public Desk getReceiverDesk() {
        return receiverDesk;
    }

    public void returnCardToHand(PlayerHand hand) {
        if(hasDeskWihClickingCard){
            if(deskWithClickingCard.returnCardToHand(hand)){
                hasDeskWihClickingCard=false;
                deskWithClickingCard=null;
            };
        }
    }
}
