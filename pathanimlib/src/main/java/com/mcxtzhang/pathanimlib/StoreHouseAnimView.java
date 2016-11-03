package com.mcxtzhang.pathanimlib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.SparseArray;

import java.util.ArrayList;

/**
 * 介绍：一个StoreHouse风格动画的PathAnimView
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/11/2.
 */

public class StoreHouseAnimView extends PathAnimView {

    public StoreHouseAnimView(Context context) {
        this(context, null);
    }

    public StoreHouseAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StoreHouseAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private final static long MAX_LENGTH = 400;
    PathMeasure pathMeasure = new PathMeasure();
    Path stonePath;
    private ArrayList<Float> mPathLengthArray;
    private SparseArray<Boolean> mPathNeedAddArray;
    private int partIndex;//残缺的index
    private float partLength;//残缺部分的长度

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(20, 20);
        setBackgroundColor(Color.BLACK);
        mPaint.setColor(Color.GRAY);
        canvas.drawPath(mSourcePath, mPaint);


        mPaint.setColor(Color.WHITE);
        //仿StoneHouse效果 ,现在的做法很挫
        stonePath = new Path();

        mPathLengthArray = new ArrayList<>();//顺序存放path的length
        pathMeasure.setPath(mAnimPath, false);
        while (pathMeasure.getLength() != 0) {
            mPathLengthArray.add(pathMeasure.getLength());
            pathMeasure.nextContour();
        }
        mPathNeedAddArray = new SparseArray<>();
        float totalLength = 0;
        partIndex = 0;
        partLength = 0;
        for (int i = mPathLengthArray.size() - 1; i >= 0; i--) {
            if (totalLength + mPathLengthArray.get(i) <= MAX_LENGTH) {//加上了也没满
                mPathNeedAddArray.put(i, true);
                totalLength = totalLength + mPathLengthArray.get(i);
            } else if (totalLength < MAX_LENGTH) {//加上了满了，但是不加就没满
                partIndex = i;
                partLength = MAX_LENGTH - totalLength;
                totalLength = totalLength + mPathLengthArray.get(i);
            }
        }

        pathMeasure.setPath(mAnimPath, false);
        int i = 0;
        while (pathMeasure.getLength() != 0) {
            if (mPathNeedAddArray.get(i, false)) {
                pathMeasure.getSegment(0, pathMeasure.getLength(), stonePath, true);
            } else if (i == partIndex) {
                pathMeasure.getSegment(pathMeasure.getLength() - partLength, pathMeasure.getLength(), stonePath, true);
            }
            pathMeasure.nextContour();
            i++;
        }

        canvas.drawPath(stonePath, mPaint);
    }
}
