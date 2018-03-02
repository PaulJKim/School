package com.example.mom.mom.Model;

/**
 * Created by Nat on 3/9/2016.
 * bc things are being annoying
 */
public class MajorRate {

    private int iRaters;
    private float fRating;

    public MajorRate(int i, float f) {
        iRaters = i;
        fRating = f;
    }

    public void changeRating(float c) {
        fRating += c/iRaters;
    }

    public void newRating(float f) {
        iRaters++;
        fRating = (fRating + f)/iRaters;
    }

    public int getRaters() {
        return iRaters;
    }

    public float getRating(){
        return fRating;
    }
}
