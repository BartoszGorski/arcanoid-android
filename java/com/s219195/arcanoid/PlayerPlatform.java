package com.s219195.arcanoid;
import android.graphics.Point;

public class PlayerPlatform extends FieldModel{
    private float xPos = 1.0f;

    PlayerPlatform(int aWidth, int aHeight, Point aPosition)
    {
        super(aWidth, aHeight, aPosition);
    }

    void setPosition(float aX, Painter aPainter)
    {
        xPos -= aX;
        mPosition.x = (int)xPos;

        if(mPosition.x <= 0)
        {
            xPos = 1;
        }
        else if(mPosition.x + mWidth >= aPainter.getWidth())
        {
            xPos = aPainter.getWidth() - 1 - mWidth;
        }
    }
}
