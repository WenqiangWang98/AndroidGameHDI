package com.example.androidstudio2dgamedevelopment.game.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.example.androidstudio2dgamedevelopment.game.Country;
import com.example.androidstudio2dgamedevelopment.R;

public class Card extends Rectangle{

    private final float CORNER_RADIUS=0.25f;
    private final float WIDTH_FLAG=0.8f;
    private final float HEIGHT_FLAG_TOP=0.9f;
    private final float HEIGHT_FLAG_BOTTOM=-0.3f;
    private final float HEIGHT_FLAG_TOP_OP=0.3f;
    private final float HEIGHT_FLAG_BOTTOM_OP=0.3f;
    private final float HEIGHT_NAME=-0.05f;
    private final float HEIGHT_NAME_1=-0.15f;
    private final float HEIGHT_NAME_2=0.0f;
    private final float HEIGHT_INDEX_VALUE=0.25f;
    private final float WIDTH_INDEX_VALUE=0.8f;
    private final float HEIGHT_LONGEVITY=0.4f;
    private final float WIDTH_LONGEVITY=0.8f;
    private final float HEIGHT_EDUCATION=0.55f;
    private final float WIDTH_EDUCATION=0.8f;
    private final float HEIGHT_GNI=0.7f;
    private final float WIDTH_GNI=0.8f;
    private final float STROKE_WIDTH=0.2f;
    private final float BORDER_MARGIN=0.1f;

    private final Context context;
    private final Paint shadow;
    private final Paint shadow2;
    private final Country country;
    private final float TEXT_SIZE_BIG;
    private final float TEXT_SIZE_MEDIUM;
    private final boolean opponent;
    private Paint textPaint;


    public Card(Context context, Country country,float screenWidth,float screenHeight,boolean opponent) {
        super( ContextCompat.getColor(context, R.color.player),200,200,screenHeight*0.1f,screenHeight*0.1618f);
        TEXT_SIZE_BIG=screenHeight*0.030f;
        TEXT_SIZE_MEDIUM=screenHeight*0.023f;
        this.context=context;

        shadow = new Paint(Paint.ANTI_ALIAS_FLAG);
        shadow.setShadowLayer(20, 0, 0, Color.GRAY);
        shadow.setStyle(Paint.Style.FILL);

        shadow2 = new Paint();
        shadow2.setColor(ContextCompat.getColor(context,R.color.cardBorderLight ));
        shadow2.setStyle(Paint.Style.STROKE);
        shadow2.setStrokeWidth(STROKE_WIDTH*width);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        this.opponent=opponent;

        this.country=country;
    }

    public void draw(Canvas canvas,boolean turnOfPlayer) {
        //rotate

        boolean rotating=false;
        if(rotated&&!clicking){
            rotating=true;
            canvas.save();
            canvas.rotate(theta,rotationX,rotationY);
        }
        if(opponent){
            canvas.save();
            canvas.rotate(180,positionX,positionY);
        }



        //Draw shadow
        if(!turnOfPlayer)canvas.drawRoundRect(left, top, right, bottom, CORNER_RADIUS*width*zoom,CORNER_RADIUS*width*zoom, shadow);
        else canvas.drawRoundRect(left, top, right, bottom, CORNER_RADIUS*width*zoom,CORNER_RADIUS*width*zoom, shadow2);

        //Draw background
        canvas.drawRoundRect(left, top, right, bottom, CORNER_RADIUS*width*zoom,CORNER_RADIUS*width*zoom, paint);

        //Draw flag
        if(opponent) {
//            Paint paintOpponent=new Paint();
//            paintOpponent.setColor(Color.BLUE);
//            paintOpponent.setStyle(Paint.Style.STROKE);
//            //canvas.drawRoundRect(left+BORDER_MARGIN*width, top-BORDER_MARGIN*width, right-BORDER_MARGIN*width, bottom+BORDER_MARGIN*width, CORNER_RADIUS*width*zoom,CORNER_RADIUS*width*zoom, paintOpponent);
//            Drawable pattern =context.getResources().getDrawable(R.drawable.pattern);
//            pattern.setBounds((int) (left+BORDER_MARGIN*width),
//                    (int) (top-BORDER_MARGIN*width),
//                    (int) (right-BORDER_MARGIN*width),
//                    (int) (bottom+BORDER_MARGIN*width* height * zoom)
//            );
//
//            pattern.draw(canvas);
            country.getFlag().setBounds(
                    (int) (positionX - WIDTH_FLAG * width),
                    (int) (positionY - HEIGHT_FLAG_TOP_OP * height ),
                    (int) (positionX + WIDTH_FLAG * width),
                    (int) (positionY + HEIGHT_FLAG_BOTTOM_OP * height)
            );
            country.getFlag().draw(canvas);

        }
        else {
            country.getFlag().setBounds(
                    (int) (positionX - WIDTH_FLAG * width * zoom),
                    (int) (positionY - HEIGHT_FLAG_TOP * height * zoom),
                    (int) (positionX + WIDTH_FLAG * width * zoom),
                    (int) (positionY + HEIGHT_FLAG_BOTTOM * height * zoom)
            );
            country.getFlag().draw(canvas);

            //Draw name
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(TEXT_SIZE_BIG * zoom);
            if (!country.getName().contains("_")) {
                canvas.drawText(country.getName(), positionX, positionY + HEIGHT_NAME * height * zoom, textPaint);
            } else {
                textPaint.setTextSize(TEXT_SIZE_MEDIUM * zoom);
                String[] v = country.getName().split("_");
                canvas.drawText(v[0], positionX, positionY + HEIGHT_NAME_1 * height * zoom, textPaint);
                canvas.drawText(v[1], positionX, positionY + HEIGHT_NAME_2 * height * zoom, textPaint);

            }
            //Draw data names
            textPaint.setColor(Color.BLACK);
            textPaint.setTextAlign(Paint.Align.RIGHT);
            textPaint.setTextSize(TEXT_SIZE_MEDIUM * zoom);
            canvas.drawText(String.valueOf(country.getIndexValue()), positionX + width * WIDTH_INDEX_VALUE * zoom, positionY + HEIGHT_INDEX_VALUE * height * zoom, textPaint);
            canvas.drawText(String.valueOf(country.getLifeExpect()), positionX + width * WIDTH_LONGEVITY * zoom, positionY + HEIGHT_LONGEVITY * height * zoom, textPaint);
            canvas.drawText(String.valueOf(country.getEducationExpect()), positionX + width * WIDTH_EDUCATION * zoom, positionY + HEIGHT_EDUCATION * height * zoom, textPaint);
            canvas.drawText(String.valueOf(country.getGNI()), positionX + width * WIDTH_GNI * zoom, positionY + HEIGHT_GNI * height * zoom, textPaint);

            textPaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText("Index:", positionX - width * WIDTH_INDEX_VALUE * zoom, positionY + HEIGHT_INDEX_VALUE * height * zoom, textPaint);

            //Draw longevity
            canvas.drawText("Longevity:", positionX - width * WIDTH_LONGEVITY * zoom, positionY + HEIGHT_LONGEVITY * height * zoom, textPaint);

            //Draw education
            canvas.drawText("Education:", positionX - width * WIDTH_EDUCATION * zoom, positionY + HEIGHT_EDUCATION * height * zoom, textPaint);

            //Draw GNI
            canvas.drawText("GNI:", positionX - width * WIDTH_GNI * zoom, positionY + HEIGHT_GNI * height * zoom, textPaint);
        }
        //rotate back
        if(rotating)canvas.restore();
        if(opponent)canvas.restore();

    }



    @Override
    public void update() {
        if(clicking)zoom=1.5f;
        else zoom=1f;
        super.update();
    }
    public int getIndex(){return country.getIndex();}
}
