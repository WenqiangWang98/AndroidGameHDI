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

    public String getMatchInfo(){
        String postData="";
        for (int i=0;i<deskList.size();i++){
            Card d=deskList.get(i).getPlayedCard();
            if( d!=null){
                postData+="&desk"+i+"="+d.getIndex();
            }
            else{
                postData+="&desk"+i+"="+0;
            }
            d=deskList.get(i).getOpponentCard();
            if( d!=null){
                postData+="&deskOpponent"+i+"="+d.getIndex();
            }
            else{
                postData+="&deskOpponent"+i+"="+0;
            }
        }
        return postData;
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
        for (Desk desk :deskList){
            if(desk.getPlayedCard()!=null)desk.getPlayedCard().draw(canvas);
            if(desk.getOpponentCard()!=null)desk.getOpponentCard().draw(canvas);
        }
    }

    public Desk getDesk(int i){
        return deskList.get(i);
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

    public void reset() {
        deskList = new ArrayList<>();
    }
}
