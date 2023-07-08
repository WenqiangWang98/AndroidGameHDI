package com.example.androidstudio2dgamedevelopment.game.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.androidstudio2dgamedevelopment.R;
import com.example.androidstudio2dgamedevelopment.game.manager.PlayerHand;


public class Desk extends Rectangle{
    private static final float STROKE_WIDTH = 0.03f;
    private final float RADIO_CORNER;
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
        this.RADIO_CORNER=screenHeight*0.05f;
        this.DESCRIPTION_TEXT_SIZE=0.035f*screenHeight;
        this.context=context;
        this.index=index;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint;

        // Draw Shadow
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setARGB(255,255,255,255);
        paint.setShadowLayer(20, 0, 0, Color.GRAY);

        paint.setStrokeWidth(STROKE_WIDTH*width);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAlpha(255);
        canvas.drawRoundRect(left, top, right, bottom, RADIO_CORNER,RADIO_CORNER, paint);
        canvas.drawRoundRect(left, top, right, bottom, RADIO_CORNER,RADIO_CORNER, paint);

        paint =new Paint();
        paint.setARGB(255,255,255,255);
        // Draw background
        paint.setStyle(Paint.Style.STROKE);

        paint.setStrokeWidth(STROKE_WIDTH*width);
        canvas.drawRoundRect(left, positionY+DESCRIPTION_TEXT_SIZE, right, positionY-DESCRIPTION_TEXT_SIZE, 0,0, paint);

        paint =new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(DESCRIPTION_TEXT_SIZE);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(description[type] , positionX, positionY+0.5f*DESCRIPTION_TEXT_SIZE, paint);

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


    public boolean returnCardToHand(PlayerHand hand) {
        playedCard1.setClicking(false);
        if(!hasInside(playedCard1)){
            //outside
            //hand.add(playedCard1);
            //playedCard1=null;
            return true;
        }else{
            playedCard1.setPosition(positionX,positionY+ 1.3f*playedCard1.getHeight());
            return false;
        }
    }


    public Card getOpponentCard(){
        return playedCard2;
    }

    public void setCard(Card card1, Card card2) {
        playedCard2=card2;
        playedCard1=card1;
        if(card1!=null){

            playedCard1.setPosition(positionX,positionY+ 1.3f*playedCard1.getHeight());
        }
        if(card2!=null){

            playedCard2.setPosition(positionX,positionY- 1.3f*playedCard2.getHeight());
        }
    }

    public int getIndex() {
        return index;
    }
}