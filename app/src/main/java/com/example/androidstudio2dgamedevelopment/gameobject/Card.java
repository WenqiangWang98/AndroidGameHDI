package com.example.androidstudio2dgamedevelopment.gameobject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import com.example.androidstudio2dgamedevelopment.Country;
import com.example.androidstudio2dgamedevelopment.R;
import com.example.androidstudio2dgamedevelopment.graphics.SpriteSheet;

import java.util.Arrays;

public class Card extends Rectangle{
    private final float WIDTH_FLAG=0.8f;
    private final float HEIGHT_FLAG_TOP=0.9f;
    private final float HEIGHT_FLAG_BOTTOM=-0.3f;
    private final int TEXT_SIZE_NAME=30;
    private final int TEXT_SIZE_NAME_2=20;
    private final float HEIGHT_NAME=-0.05f;
    private final float HEIGHT_NAME_1=-0.15f;
    private final float HEIGHT_NAME_2=0.0f;
    private final int TEXT_SIZE_INDEX_VALUE=20;
    private final float HEIGHT_INDEX_VALUE=0.25f;
    private final float WIDTH_INDEX_VALUE=0.8f;
    private final int TEXT_SIZE_LONGEVITY=20;
    private final float HEIGHT_LONGEVITY=0.4f;
    private final float WIDTH_LONGEVITY=0.8f;
    private final int TEXT_SIZE_EDUCATION=20;
    private final float HEIGHT_EDUCATION=0.55f;
    private final float WIDTH_EDUCATION=0.8f;
    private final int TEXT_SIZE_GNI=20;
    private final float HEIGHT_GNI=0.7f;
    private final float WIDTH_GNI=0.8f;

    private final Context context;
    private float cornerRadio=15;
    private Paint shadow;
    private Country country;


    public Card(Context context, Country country) {
        super( ContextCompat.getColor(context, R.color.player),200,200,150,93);
        this.context=context;
        shadow = new Paint(Paint.ANTI_ALIAS_FLAG);
        shadow.setShadowLayer(20, 0, 0, Color.GRAY);
        shadow.setStyle(Paint.Style.FILL);
        this.country=country;
    }

    @Override
    public void draw(Canvas canvas) {
        //rotate
        if(rotated&&!clicking){
            canvas.save();
            canvas.rotate(theta,rotationX,rotationY);
        }
        //Draw shadow
        canvas.drawRoundRect(left, top, right, bottom, cornerRadio,cornerRadio, shadow);

        //Draw background
        canvas.drawRoundRect(left, top, right, bottom, cornerRadio,cornerRadio, paint);

        //Draw flag
        country.getFlag().setBounds(
                (int) (positionX-WIDTH_FLAG*width*zoom),
                (int) (positionY-HEIGHT_FLAG_TOP*height*zoom),
                (int)(positionX+WIDTH_FLAG*width*zoom),
                (int)(positionY+HEIGHT_FLAG_BOTTOM*height*zoom)
        );
        country.getFlag().draw(canvas);

        //Draw name
        Paint namePaint = new Paint();
        namePaint.setColor(Color.BLACK);
        namePaint.setTextAlign(Paint.Align.CENTER);
        Rect r=canvas.getClipBounds();
        if(clicking) namePaint.setTextSize(TEXT_SIZE_NAME*zoom);
        else namePaint.setTextSize(TEXT_SIZE_NAME);
        namePaint.getTextBounds(country.getName(),0,country.getName().length(),r);
        if(!country.getName().contains("_")){
            canvas.drawText(country.getName(), positionX, positionY+HEIGHT_NAME*height*zoom, namePaint);
        }else{
            namePaint.setTextSize(TEXT_SIZE_NAME_2*zoom);
            String[] v=country.getName().split("_");
            canvas.drawText(v[0], positionX, positionY+HEIGHT_NAME_1*height*zoom, namePaint);
            canvas.drawText(v[1], positionX, positionY+HEIGHT_NAME_2*height*zoom, namePaint);

        }
        //Draw Index value
        Paint indexValuePaint = new Paint();
        indexValuePaint.setColor(Color.BLACK);
        indexValuePaint.setTextAlign(Paint.Align.RIGHT);
        if(clicking) indexValuePaint.setTextSize(TEXT_SIZE_INDEX_VALUE*zoom);
        else indexValuePaint.setTextSize(TEXT_SIZE_INDEX_VALUE);
        canvas.drawText(String.valueOf(country.getIndexValue()), positionX+width*WIDTH_INDEX_VALUE*zoom, positionY+HEIGHT_INDEX_VALUE*height*zoom, indexValuePaint);
        indexValuePaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Index:", positionX-width*WIDTH_INDEX_VALUE*zoom, positionY+HEIGHT_INDEX_VALUE*height*zoom, indexValuePaint);

        //Draw longevity
        Paint longevityPaint = new Paint();
        longevityPaint.setColor(Color.BLACK);
        longevityPaint.setTextAlign(Paint.Align.RIGHT);
        if(clicking) longevityPaint.setTextSize(TEXT_SIZE_LONGEVITY*zoom);
        else longevityPaint.setTextSize(TEXT_SIZE_LONGEVITY);
        canvas.drawText(String.valueOf(country.getLifeExpect()), positionX+width*WIDTH_LONGEVITY*zoom, positionY+HEIGHT_LONGEVITY*height*zoom, longevityPaint);
        longevityPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Longevity:", positionX-width*WIDTH_LONGEVITY*zoom, positionY+HEIGHT_LONGEVITY*height*zoom, longevityPaint);

        //Draw education
        Paint educationPaint = new Paint();
        educationPaint.setColor(Color.BLACK);
        educationPaint.setTextAlign(Paint.Align.RIGHT);
        if(clicking) educationPaint.setTextSize(TEXT_SIZE_EDUCATION*zoom);
        else educationPaint.setTextSize(TEXT_SIZE_EDUCATION);
        canvas.drawText(String.valueOf(country.getEducationExpect()), positionX+width*WIDTH_EDUCATION*zoom, positionY+HEIGHT_EDUCATION*height*zoom, educationPaint);
        educationPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Education:", positionX-width*WIDTH_EDUCATION*zoom, positionY+HEIGHT_EDUCATION*height*zoom, educationPaint);

        //Draw GNI
        Paint GNIPaint = new Paint();
        GNIPaint.setColor(Color.BLACK);
        GNIPaint.setTextAlign(Paint.Align.RIGHT);
        if(clicking) GNIPaint.setTextSize(TEXT_SIZE_GNI*zoom);
        else GNIPaint.setTextSize(TEXT_SIZE_GNI);
        canvas.drawText(String.valueOf(country.getGNI()), positionX+width*WIDTH_GNI*zoom, positionY+HEIGHT_GNI*height*zoom, GNIPaint);
        GNIPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("GNI:", positionX-width*WIDTH_GNI*zoom, positionY+HEIGHT_GNI*height*zoom, GNIPaint);

        //rotate back
        if(rotated&&!clicking)canvas.restore();

    }

    @Override
    public void update() {
        if(clicking)zoom=1.5f;
        else zoom=1f;
        super.update();
    }
}
