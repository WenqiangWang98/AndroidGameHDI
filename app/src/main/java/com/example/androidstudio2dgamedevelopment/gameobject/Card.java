package com.example.androidstudio2dgamedevelopment.gameobject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.androidstudio2dgamedevelopment.R;
import com.example.androidstudio2dgamedevelopment.graphics.SpriteSheet;

public class Card extends Rectangle{
    private final float MARGIN=50;
    private final Context context;
    private float cornerRadio=15;
    private String countryName;
    private Paint shadow;
    private Drawable flag;
    public Card(Context context,Drawable f) {
        super( ContextCompat.getColor(context, R.color.player),200,200,200,125);
        this.context=context;
        shadow = new Paint(Paint.ANTI_ALIAS_FLAG);
        shadow.setShadowLayer(20, 0, 0, Color.GRAY);
        shadow.setStyle(Paint.Style.FILL);
        this.flag=f;

    }

    @Override
    public void draw(Canvas canvas) {

        if(rotated&&!clicking){
            canvas.save();
            canvas.rotate(theta,rotationX,rotationY);
            canvas.drawRoundRect(left, top, right, bottom, cornerRadio,cornerRadio, shadow);
            canvas.drawRoundRect(left, top, right, bottom, cornerRadio,cornerRadio, paint);
            flag.setBounds((int) (positionX-0.8*width), (int) (positionY-0.7*height), (int)(positionX+0.8*width), (int)(positionY-0.1*height));
            flag.draw(canvas);
            canvas.restore();
        }else if(clicking){
            canvas.drawRoundRect(left, top, right, bottom, cornerRadio,cornerRadio,shadow);
            canvas.drawRoundRect(left, top, right, bottom, cornerRadio,cornerRadio, paint);
            flag.setBounds((int) (positionX-0.8*width*zoom), (int) (positionY-0.7*height*zoom), (int)(positionX+0.8*width*zoom), (int)(positionY-0.1*height*zoom));
            flag.draw(canvas);
        }else{
            canvas.drawRoundRect(left, top, right, bottom, cornerRadio,cornerRadio,shadow);
            canvas.drawRoundRect(left, top, right, bottom, cornerRadio,cornerRadio, paint);
            flag.setBounds((int) (positionX-0.8*width), (int) (positionY-0.7*height), (int)(positionX+0.8*width), (int)(positionY-0.1*height));
            flag.draw(canvas);
        }

    }

}
