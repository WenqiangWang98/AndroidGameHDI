package com.example.androidstudio2dgamedevelopment.game.manager;

import android.graphics.Canvas;

import com.example.androidstudio2dgamedevelopment.MQTTModule;
import com.example.androidstudio2dgamedevelopment.game.object.Desk;
import com.example.androidstudio2dgamedevelopment.game.object.GameObject;

import java.util.ArrayList;
import java.util.List;

public class DeskManager {
    private List<Desk> deskList = new ArrayList<>();
    private Desk deskWithClickingCard;
    private boolean hasDeskWihClickingCard;
    private Desk receiverDesk;
    protected int displayMetricsX;
    protected int displayMetricsY;
    public DeskManager(int windowSizeX, int windowSizeY) {
        this.displayMetricsX=windowSizeX;
        this.displayMetricsY=windowSizeY;
    }

    public void add(Desk desk) {

        deskList.add(desk);
        for(int i=0;i<deskList.size();i++){
            deskList.get(i).setPosition(displayMetricsX/(float)(deskList.size()+1)*(i+1),displayMetricsY/2f);
        }
    }

    public void update() {
        for (Desk desk :deskList){
            desk.update();
        }
    }

    public void draw(Canvas canvas) {
        for(int i=0;i<deskList.size();i++){
            deskList.get(i).draw(canvas);
        }
        for (Desk desk :deskList){
            if(desk.getPlayedCard()!=null)desk.getPlayedCard().draw(canvas);
            if(desk.getOpponentCard()!=null)desk.getOpponentCard().draw(canvas);
        }
    }

    public Desk getDesk(int i){
        for (Desk d:deskList){
            if (d.getIndex()==i)return d;
        }
        return null;
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



    public void reset() {
        deskWithClickingCard=null;
        deskList = new ArrayList<>();
        hasDeskWihClickingCard=false;
        receiverDesk=null;
    }

    public void returnCardToHand(PlayerHand hand, MQTTModule mqtt_handler, String username) {
        if(hasDeskWihClickingCard){
            if(deskWithClickingCard.returnCardToHand(hand)){
                String aux=""+deskWithClickingCard.getIndex();
                mqtt_handler.sendMSGToTopic("CTS/"+username+"/return_card_desk",aux,2);
                hasDeskWihClickingCard=false;
                deskWithClickingCard=null;
            };
        }
    }
}
