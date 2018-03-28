package com.s219195.arcanoid;

import android.graphics.Point;


public class FieldModel {
    protected int mWidth;
    protected int mHeight;
    protected Point mPosition;
    protected MyColor color;

    FieldModel(int aWidth, int aHeight, Point aPosition) {
        mWidth = aWidth;
        mHeight = aHeight;
        mPosition = aPosition;
        color = new MyColor(255,
                (int) (Math.random() * 256),
                (int) (Math.random() * 256),
                (int) (Math.random() * 256)
        );
    }

    public MyColor getColor() {
        return color;
    }

    public Point getPosition() {
        return mPosition;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }
}
