package com.s219195.arcanoid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Painter extends View {

    private BallModel mBallModel;
    private Paint mPaintBall;
    private PlayerPlatform mPlayerPlatform;
    private Paint mPaintPlayer;
    private FieldMap mFieldMap;
    private Paint mPaintField;
    private int mWidth;
    private int mHeight;
    private Paint mPaintStroke;

    public Painter(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaintBall = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBall.setColor(Color.BLACK);
        mPaintPlayer = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintPlayer.setColor(Color.GRAY);
        mPaintField = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintField.setColor(Color.GREEN);
        mPaintStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintStroke.setColor(Color.BLACK);
        mPaintStroke.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(
                (int)mBallModel.getxPosition(),
                (int)mBallModel.getyPosition(),
                mBallModel.getRadius(),
                mPaintBall
        );

        drawPlayerPlatform(canvas);
        drawAllFields(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    private void drawPlayerPlatform(Canvas aCanvas)
    {
        float left = mPlayerPlatform.getPosition().x;
        float top = mPlayerPlatform.getPosition().y;
        float right = left + mPlayerPlatform.getWidth();
        float bottom = top + mPlayerPlatform.getHeight();
        MyColor color = mPlayerPlatform.getColor();
        mPaintPlayer.setARGB(color.getAlpha(), color.getRed(), color.getGreen(), color.getBlue());
        aCanvas.drawRect(left, top, right, bottom, mPaintPlayer);
    }

    private void drawAllFields(Canvas aCanvas) {
        for (FieldModel fieldModel : mFieldMap.getFieldModels()) {
            float left = fieldModel.getPosition().x;
            float top = fieldModel.getPosition().y;
            float right = left + fieldModel.getWidth();
            float bottom = top + fieldModel.getHeight();
            MyColor color = fieldModel.getColor();
            mPaintField.setARGB(color.getAlpha(), color.getRed(), color.getGreen(), color.getBlue());
            aCanvas.drawRect(left, top, right, bottom, mPaintField);
            aCanvas.drawRect(left, top, right, bottom, mPaintStroke);
        }
    }

    public void setBallModel(BallModel aBallModel) {
        mBallModel = aBallModel;
    }

    public void setPlatformModel(PlayerPlatform aPlayerPlatform) {
        mPlayerPlatform = aPlayerPlatform;
    }

    public void setFieldMap(FieldMap aFieldMap) {
        mFieldMap = aFieldMap;
    }
}
