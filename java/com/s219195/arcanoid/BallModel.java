package com.s219195.arcanoid;

import android.graphics.Point;

public class BallModel {
    private final int M_RADIUS = 12;
    private float xPos = M_RADIUS + 100.0f;
    private float yPos = M_RADIUS + 600.0f;
    private float mVelocityX = 1.2f;
    private float mVelocityY = 1.2f;

    private final float BOUNCE_STRENGTH = 1f;

    BallModel() {}

    void setPosition(Painter aPainter) {

        xPos += mVelocityX;
        yPos += mVelocityY;

        if (xPos - M_RADIUS <= 0) {
            xPos = 1 + M_RADIUS;
            mVelocityX *= -BOUNCE_STRENGTH;
        }
        if (xPos + M_RADIUS >= aPainter.getWidth()) {
            xPos = aPainter.getWidth() - 1 - M_RADIUS;
            mVelocityX *= -BOUNCE_STRENGTH;
        }

        if (yPos - M_RADIUS <= 0) {
            yPos = 1 + M_RADIUS;
            mVelocityY *= -BOUNCE_STRENGTH;
        }
        if (yPos + M_RADIUS >= aPainter.getHeight()) {
            yPos = aPainter.getHeight() - 1 - M_RADIUS;
            mVelocityY *= -BOUNCE_STRENGTH;
        }
    }

    public boolean isIntersectingWithRectangle(FieldModel fieldModel) {

        int leftRect = fieldModel.getPosition().x;
        int topRect = fieldModel.getPosition().y;
        int rectWidth = fieldModel.getWidth();
        int rectHeight = fieldModel.getHeight();

        int circleDistanceX = Math.abs((int) (xPos + mVelocityX) - (leftRect + rectWidth / 2));
        int circleDistanceY = Math.abs((int) (yPos + mVelocityY) - (topRect + rectHeight / 2));

        if (circleDistanceX >= M_RADIUS + rectWidth || circleDistanceY >= M_RADIUS + rectHeight ) {
            return false;
        }

        if (circleDistanceX > (rectWidth / 2 + M_RADIUS)) {
            return false;
        }
        if (circleDistanceY > (rectHeight / 2 + M_RADIUS)) {
            return false;
        }

        if (circleDistanceX <= (rectWidth / 2)) {
            mVelocityY *= -BOUNCE_STRENGTH;
            return true;
        }
        if (circleDistanceY <= (rectHeight / 2)) {
            mVelocityX *= -BOUNCE_STRENGTH;
            return true;
        }

        float cornerDistance_sq = (circleDistanceX - rectWidth / 2) ^ 2 + (circleDistanceY - rectHeight / 2) ^ 2;
        if (cornerDistance_sq <= (M_RADIUS ^ 2)) {
            mVelocityY *= -BOUNCE_STRENGTH;
            mVelocityX *= -BOUNCE_STRENGTH;
            return true;
        }
        return false;
    }

    public float getxPos() {
        return xPos;
    }

    public float getyPos() {
        return yPos;
    }

    float getRadius() {
        return M_RADIUS;
    }

    public void speedUp(float aSpeedUp) {
        mVelocityX += aSpeedUp;
        mVelocityY += aSpeedUp;
    }
}
