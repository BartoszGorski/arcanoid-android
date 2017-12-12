package com.s219195.arcanoid;

import android.graphics.Point;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

class Controller implements Runnable {
    long startTime = System.currentTimeMillis();
    long time = 0;
    private Handler mHandler;
    private float mX = 0.0f, mY = 0.0f;
    private GameManager mGameManager;
    private Painter mPainter;
    private BallModel mBallModel;
    private PlayerPlatform mPlayerPlatform;
    private FieldMap mFieldMap;
    private TextView mTextView;

    public Controller(Handler aHandler, Painter aPainter, TextView aTextView, Point defaultWindowSize) {
        mHandler = aHandler;
        mPainter = aPainter;
        mTextView = aTextView;

        mGameManager = new GameManager(10000, 3000, 400);
        mBallModel = new BallModel();
        mPlayerPlatform = new PlayerPlatform(
                140, 30,
                new Point(defaultWindowSize.x / 2, defaultWindowSize.y - 100)
        );
        mFieldMap = new FieldMap(10, 24, defaultWindowSize.x, defaultWindowSize.y);
        mFieldMap.spawnNextRow();

        mPainter.setBallModel(mBallModel);
        mPainter.setPlatformModel(mPlayerPlatform);
        mPainter.setFieldMap(mFieldMap);
    }

    private boolean checkIntersections() {
        if (mBallModel.isIntersectingWithRectangle(mPlayerPlatform)) {
            return true;
        }

        List<FieldModel> fieldModels = mFieldMap.getFieldModels();
        for (Iterator<FieldModel> iterator = fieldModels.iterator(); iterator.hasNext(); ) {
            FieldModel fieldModel = iterator.next();
            if (mBallModel.isIntersectingWithRectangle(fieldModel)) {
                iterator.remove();
                mGameManager.addPoints(PointsValue.SCORE_FIELD.getValue());
                return true;
            }
        }
        return false;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Update();
                        Render();
                    }
                });
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void Update() {
        if (checkTime()) {
            mGameManager.addPoints(PointsValue.SCORE_SPAWNED_ROW.getValue());
            mGameManager.changeTimeToSpawnNextRow();
            if(!mFieldMap.spawnNextRow()) {
                mGameManager.gameOver();
            }
            mBallModel.speedUp(0.3f);
        }
        mPlayerPlatform.setPosition(mX, mPainter);
        checkIntersections();
        mBallModel.setPosition(mPainter);
    }

    private void Render() {
        mPainter.invalidate();
    }

    private boolean checkTime() {
        time = System.currentTimeMillis() - startTime;
        if (time >= mGameManager.getTimeToSpawnNextRow()) {
            startTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public void setPhoneRotation(float x, float y) {
        mX = x;
        mY = y;
    }
}