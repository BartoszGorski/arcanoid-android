package com.s219195.arcanoid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.TextView;

enum PointsValue {
    SCORE_FIELD(1),
    SCORE_SPAWNED_ROW(2);

    private final int points;

    PointsValue(int aPoints) {
        points = aPoints;
    }

    public int getValue() {
        return points;
    }
}

public class GameManager {
    private static final String SCORE_RECORD = "ScoreRecord";
    private final Context mContext;

    private int mScore = 0;
    private int mBestScore = 0;
    private int mStartTimeToSpawnNextRow;
    private int mMinTimeToSpawnNextRow;
    private int mTimeSubtractionValue;
    private int delayToStartGame = 3000;
    private boolean gameOver = false;
    private static GameManager instance = null;

    private GameManager(Context aContext, int aStartTimeToSpawnNextRow, int aMinTimeToSpawnNextRow, int aTimeSubtractionValue) {
        mContext = aContext;
        mStartTimeToSpawnNextRow = aStartTimeToSpawnNextRow;
        mMinTimeToSpawnNextRow = aMinTimeToSpawnNextRow;
        mTimeSubtractionValue = aTimeSubtractionValue;
    }

    public static GameManager getInstance() {
        return instance;
    }

    public static void init(Context aContext, int aStartTimeToSpawnNextRow, int aMinTimeToSpawnNextRow, int aTimeSubtractionValue) {
        instance = new GameManager(aContext, aStartTimeToSpawnNextRow, aMinTimeToSpawnNextRow, aTimeSubtractionValue);
    }

    public void addPoints(int aPoints, TextView aScoreTextView) {
        mScore += aPoints;
        aScoreTextView.setText("Score: " + mScore);
    }

    public float getTimeToSpawnNextRow() {
        return mStartTimeToSpawnNextRow;
    }

    public void changeTimeToSpawnNextRow() {
        if (mStartTimeToSpawnNextRow > mMinTimeToSpawnNextRow) {
            mStartTimeToSpawnNextRow -= mTimeSubtractionValue;
        }
    }

    public void gameOver() {
        saveBestScore();
        gameOver = true;

        Intent myIntent = new Intent(mContext, EndGameActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ((Activity) mContext).finish();
        mContext.startActivity(myIntent);
    }

    public int getScore() {
        return mScore;
    }

    public int getDelayToStartGame() {
        return delayToStartGame;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int loadBestScore() {
        SharedPreferences savedBestScore = mContext.getSharedPreferences(SCORE_RECORD, 0);
        mBestScore = savedBestScore.getInt(SCORE_RECORD, 0);
        return mBestScore;
    }

    private void saveBestScore() {
        if (mScore > mBestScore) {
            SharedPreferences savedBestScore = mContext.getSharedPreferences(SCORE_RECORD, 0);
            SharedPreferences.Editor editor = savedBestScore.edit();
            editor.putInt(SCORE_RECORD, mScore);
            editor.commit();
        }
    }
}
