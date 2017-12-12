package com.s219195.arcanoid;

public class BallModel {
    private final float BOUNCE_STRENGTH = 1f;

    private int mRadius = 12;
    private float xPosition = mRadius + 100.0f;
    private float yPosition = mRadius + 600.0f;
    private float mVelocityX = 0.0f;
    private float mVelocityY = 0.0f;
    private int mWindowWidth;
    private int mWindowHeight;
    private boolean isReleased = false;

    BallModel(float xPosition, float yPosition, int radius, int windowWidth, int windowHeight) {

        this.xPosition = xPosition;
        this.yPosition = yPosition;
        mRadius = radius;
        mWindowWidth = windowWidth;
        mWindowHeight = windowHeight;
    }

    boolean updatePosition() {

        xPosition += mVelocityX;
        yPosition += mVelocityY;

        return (yPosition - mRadius < mWindowHeight);
    }

    public boolean checkIntersections(FieldModel aFieldModel) {

        if (isIntersectiongWithWalls()) {
            return true;
        }
        if (isIntersectingWithRectangle(aFieldModel)) {
            return true;
        }
        return false;
    }

    private boolean isIntersectiongWithWalls() {
        if (xPosition - mRadius <= 0) {
            xPosition = 1 + mRadius;
            mVelocityX *= -BOUNCE_STRENGTH;
            return true;
        }
        if (xPosition + mRadius >= mWindowWidth) {
            xPosition = mWindowWidth - 1 - mRadius;
            mVelocityX *= -BOUNCE_STRENGTH;
            return true;
        }

        if (yPosition - mRadius <= 0) {
            yPosition = 1 + mRadius;
            mVelocityY *= -BOUNCE_STRENGTH;
            return true;
        }
        return false;
    }

    private boolean isIntersectingWithRectangle(FieldModel fieldModel) {

        int leftRect = fieldModel.getPosition().x;
        int topRect = fieldModel.getPosition().y;
        int rectWidth = fieldModel.getWidth();
        int rectHeight = fieldModel.getHeight();

        int circleDistanceX = Math.abs((int) (xPosition + mVelocityX) - (leftRect + rectWidth / 2));
        int circleDistanceY = Math.abs((int) (yPosition + mVelocityY) - (topRect + rectHeight / 2));

        if (circleDistanceX >= mRadius + rectWidth || circleDistanceY >= mRadius + rectHeight) {
            return false;
        }

        if (circleDistanceX > (rectWidth / 2 + mRadius)) {
            return false;
        }
        if (circleDistanceY > (rectHeight / 2 + mRadius)) {
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
        if (cornerDistance_sq <= (mRadius ^ 2)) {
            mVelocityY *= -BOUNCE_STRENGTH;
            mVelocityX *= -BOUNCE_STRENGTH;
            return true;
        }
        return false;
    }

    public float getxPosition() {
        return xPosition;
    }

    public float getyPosition() {
        return yPosition;
    }

    float getRadius() {
        return mRadius;
    }

    public void speedUp(float aSpeedUp) {
        mVelocityX += aSpeedUp;
        mVelocityY += aSpeedUp;
    }

    public void releaseBall() {
        int direction = (int) (Math.random() * 2);
        if(direction == 0)
            direction = -1;

        mVelocityX = 1.5f * direction;
        mVelocityY = -2.6f;

        isReleased = true;
    }

    public boolean isBallReleased() {
        return isReleased;
    }
}
