package com.example.androidstudio2dgamedevelopment;

import android.graphics.drawable.Drawable;

public class Country {
    private int rank;
    private String name;
    private float indexValue;
    private float lifeExpect;
    private float educationExpect;
    private int GNI;
    private Drawable flag;

    public Country(int rank,String name,float indexValue,float lifeExpect,float educationExpect,int GNI, Drawable flag){
        this.name=name;
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

    public Drawable getFlag() {
        return flag;
    }
}
