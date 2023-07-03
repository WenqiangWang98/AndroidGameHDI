package com.example.androidstudio2dgamedevelopment.game.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.androidstudio2dgamedevelopment.R;


public class Desk extends Rectangle{
    private final float RADIO_CORNER=50;
    private int type;
    private float DESCRIPTION_TEXT_SIZE;
    private final String[] description={"HDI","Longevity","Education","GNI"};
    private  Context context;
    private Card playedCard1;
    private Card playedCard2;
    private int index;
    public Desk(Context context,int type, float x,float y,float screenWidth, float screenHeight,int index) {
        super(ContextCompat.getColor(context, R.color.player),x,y,screenHeight*0.15f,screenHeight*0.4f);
        this.type=type;
        this.DESCRIPTION_TEXT_SIZE=0.03f*screenHeight;
        this.context=context;
        this.index=index;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint;

        // Draw Shadow
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShadowLayer(20, 0, 0, Color.GRAY);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(left, top, right, bottom, RADIO_CORNER,RADIO_CORNER, paint);
        paint =new Paint();
        paint.setColor(Color.LTGRAY);
        // Draw background
        canvas.drawRoundRect(left, top, right, bottom, RADIO_CORNER,RADIO_CORNER, paint);

        paint.setColor(Color.BLUE);
        paint.setTextSize(DESCRIPTION_TEXT_SIZE);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(description[type] , positionX, positionY+DESCRIPTION_TEXT_SIZE, paint);

    }

    @Override
    public void update() {
        super.update();
        if (playedCard1!=null)playedCard1.update();
        if (playedCard2!=null)playedCard2.update();
    }

    public Card getPlayedCard() {
        return playedCard1;
    }

    public boolean checkPlayedCardClicking(float x, float y) {
        if(playedCard1!=null)return playedCard1.checkClicking(x,y);
        return false;
    }

    public void exchangeCard(PlayerHand hand) {
        Card aux;
        if(playedCard1!=null){
            aux=playedCard1;
            hand.add(aux);
        }
        playedCard1=hand.getClickingCard();
        hand.discard(playedCard1);
        playedCard1.setPosition(positionX,positionY+ playedCard1.getHeight());
        playedCard1.resetRotation();
    }

    public boolean returnCardToHand(PlayerHand hand) {
        playedCard1.setClicking(false);
        if(!hasInside(playedCard1)){
            //outside
            //hand.add(playedCard1);
            //playedCard1=null;
            return true;
        }else{
            playedCard1.setPosition(positionX,positionY+ 1.2f*playedCard1.getHeight());
            return false;
        }
    }

    public void setOpponentCard(Card card){
        playedCard2 = card;
        if(card!=null) {
            playedCard2.setPosition(positionX, positionY - playedCard2.getHeight());
        }
    }

    public Card getOpponentCard(){
        return playedCard2;
    }

    public void setCard(Card card1, Card card2) {
        playedCard2=card2;
        playedCard1=card1;
        if(card1!=null){

            playedCard1.setPosition(positionX,positionY+ 1.2f*playedCard1.getHeight());
        }
        if(card2!=null){

            playedCard2.setPosition(positionX,positionY- 1.2f*playedCard2.getHeight());
        }
    }

    public int getIndex() {
        return index;
    }
}