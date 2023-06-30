package com.example.androidstudio2dgamedevelopment.game.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.androidstudio2dgamedevelopment.R;


public class Desk extends Rectangle{
    private final float RADIO_CORNER=50;
    private String description;
    private float DESCRIPTION_TEXT_SIZE;

    private  Context context;
    private Card playedCard1;
    private Card playedCard2;
    public Desk(Context context,String description, float x,float y,float screenWidth, float screenHeight) {
        super(ContextCompat.getColor(context, R.color.player),x,y,screenHeight*0.15f,screenHeight*0.4f);
        this.description=description;
        this.DESCRIPTION_TEXT_SIZE=0.007f*screenHeight;
        this.context=context;
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
        canvas.drawText(description , positionX, positionY+2*DESCRIPTION_TEXT_SIZE, paint);

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
            hand.add(playedCard1);
            playedCard1=null;
            return true;
        }else{
            playedCard1.setPosition(positionX,positionY+ playedCard1.getHeight());
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
}