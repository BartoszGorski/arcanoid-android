package com.s219195.arcanoid;

import android.widget.TextView;

enum PointsValue {
    SCORE_FIELD(1),
    SCORE_SPAWNED_ROW(3);

    private final int points;

    PointsValue(int aPoints) {
        points = aPoints;
    }

    public int getValue() {
        return points;
    }
}

public class GameManager {
    private int mScore = 0;
    private int mStartTimeToSpawnNextRow;
    private int mMinTimeToSpawnNextRow;
    private int mTimeSubtractionValue;

    GameManager(int aStartTimeToSpawnNextRow, int aMinTimeToSpawnNextRow, int aTimeSubtractionValue) {
        mStartTimeToSpawnNextRow = aStartTimeToSpawnNextRow;
        mMinTimeToSpawnNextRow = aMinTimeToSpawnNextRow;
        mTimeSubtractionValue = aTimeSubtractionValue;
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
        //TODO save score
        //TODO end game, popup window with score and best score?
        //TODO menu layout
        //TODO show score
        return;
    }

    public int getScore() {
        return mScore;
    }
}
