package com.example.androidstudio2dgamedevelopment.game;

import android.graphics.drawable.Drawable;

public class Country {
    private final int index;
    private final int rank;
    private final String name;
    private final float indexValue;
    private final float lifeExpect;
    private final float educationExpect;
    private final int GNI;
    private final Drawable flag;

    public Country(int index,int rank,String name,float indexValue,float lifeExpect,float educationExpect,int GNI, Drawable flag){
        this.name=name;
        this.index=index;
        this.rank=rank;
        this.indexValue=indexValue;
        this.lifeExpect=lifeExpect;
        this.educationExpect=educationExpect;
        this.GNI=GNI;
        this.flag=flag;
    }

    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }

    public float getIndexValue() {
        return indexValue;
    }

    public float getLifeExpect() {
        return lifeExpect;
    }

    public float getEducationExpect() {
        return educationExpect;
    }

    public int getGNI() {
        return GNI;
    }

    public int getIndex(){return index;}

    public Drawable getFlag() {
        return flag;
    }
}
