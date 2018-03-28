package com.s219195.arcanoid;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;

class Controller implements Runnable {
    private long startTime = System.currentTimeMillis();
    private long time = 0;
    private Handler mHandler;
    private float mX = 0.0f;
    private GameManager mGameManager;
    private Painter mPainter;
    private BallModel mBallModel;
    private PlayerPlatform mPlayerPlatform;
    private FieldMap mFieldMap;
    private TextView mBestScoreTextView;
    private TextView mScoreTextView;

    public Controller(Handler aHandler, Context aContex, Painter aPainter, TextView aScoreTextView,
                      TextView aBestScoreTextView, Point defaultWindowSize) {
        mHandler = aHandler;
        mPainter = aPainter;
        mScoreTextView = aScoreTextView;
        mBestScoreTextView = aBestScoreTextView;

        mGameManager.init(aContex, 10000, 3000, 400);
        mGameManager = GameManager.getInstance();

        mScoreTextView.setText("Score: " + mGameManager.getScore());
        mBestScoreTextView.setText("Best Score: " + mGameManager.loadBestScore());
        mBallModel = new BallModel(
                defaultWindowSize.x / 2,
                defaultWindowSize.y - 100 - 12 - 1,
                12,
                defaultWindowSize.x,
                defaultWindowSize.y
        );
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
        if (mBallModel.checkIntersections(mPlayerPlatform)) {
            return true;
        }

        List<FieldModel> fieldModels = mFieldMap.getFieldModels();
        for (Iterator<FieldModel> iterator = fieldModels.iterator(); iterator.hasNext(); ) {
            FieldModel fieldModel = iterator.next();
            if (mBallModel.checkIntersections(fieldModel)) {
                iterator.remove();
                mGameManager.addPoints(PointsValue.SCORE_FIELD.getValue(), mScoreTextView);
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
                        if (!mGameManager.isGameOver()) {
                            Update();
                            Render();
                        }
                    }
                });
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void Update() {
        if (mBallModel.isBallReleased()) {
            if (checkTime()) {
                mGameManager.addPoints(PointsValue.SCORE_SPAWNED_ROW.getValue(), mScoreTextView);
                mGameManager.changeTimeToSpawnNextRow();
                if (!mFieldMap.spawnNextRow()) {
                    mGameManager.gameOver();
                }
                mBallModel.speedUp(0.3f);
            }
        } else {
            time = System.currentTimeMillis() - startTime;
            if (time >= mGameManager.getDelayToStartGame()) {
                startTime = System.currentTimeMillis();
                mBallModel.releaseBall();
            }
        }
        mPlayerPlatform.setPosition(mX, mPainter);
        checkIntersections();
        if (!mBallModel.updatePosition()) {
            mGameManager.gameOver();
        }
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

    public void setPhoneRotation(float x) {
        mX = x;
    }
}