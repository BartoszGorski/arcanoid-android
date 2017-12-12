package com.s219195.arcanoid;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

public class FieldMap {
    private List<FieldModel> mFieldModels;
    private int mWidthFieldCount;
    private int mHeightFieldCount;
    private int mFieldWidth;
    private int mFieldHeight;

    FieldMap(int aWidthFieldCount, int aHeightFieldCount, int aScreenWidth, int aScreenHeight) {
        mWidthFieldCount = aWidthFieldCount;
        mHeightFieldCount = aHeightFieldCount;
        mFieldModels = new ArrayList<FieldModel>();
        mFieldWidth = (int) ((float) aScreenWidth / (float) mWidthFieldCount);
        mFieldHeight = (int) ((float) aScreenHeight / (float) mHeightFieldCount);
    }

    public List<FieldModel> getFieldModels() {
        return mFieldModels;
    }

    public boolean spawnNextRow() {
        moveExistingFieldsDown(1);
        for (int y = 0; y < 1; y++) {
            for (int x = 1; x < mWidthFieldCount - 1; x++) {
                mFieldModels.add(new FieldModel(
                        mFieldWidth,
                        mFieldHeight,
                        new Point(x * mFieldWidth, y * mFieldHeight)
                ));
            }
        }
        return checkIsGameOver();
    }

    private boolean checkIsGameOver() {
        for (FieldModel fieldModel : mFieldModels) {
            if(fieldModel.mPosition.y >= mHeightFieldCount){
                return true;
            }
        }
        return false;
    }

    private void moveExistingFieldsDown(int aMoveCount) {
        for (FieldModel fieldModel : mFieldModels) {
            fieldModel.mPosition.y += mFieldHeight * aMoveCount;
        }
    }
}
