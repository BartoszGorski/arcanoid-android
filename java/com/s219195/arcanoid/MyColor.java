package com.s219195.arcanoid;

import java.io.Serializable;

public class MyColor implements Serializable {
    private int alpha;
    private int red;
    private int green;
    private int blue;

    public MyColor(int aAlpha, int aRed, int aGreen, int aBlue) {
        alpha = aAlpha;
        red = aRed;
        green = aGreen;
        blue = aBlue;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
}
