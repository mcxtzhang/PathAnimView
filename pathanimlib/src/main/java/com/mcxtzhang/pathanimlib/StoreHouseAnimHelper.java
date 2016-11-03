package com.mcxtzhang.pathanimlib;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.SparseArray;
import android.view.View;

import java.util.ArrayList;

/**
 * 介绍：仿StoreHouse风格的PathAnimHepler
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/11/2.
 */

public class StoreHouseAnimHelper extends PathAnimHelper {
    private final static long MAX_LENGTH = 400;

    private long mPathMaxLength;//残影路径最大长度
    Path mStonePath;//暂存一下路径，最终要复制给animPath的
    PathMeasure mPm;
    private ArrayList<Float> mPathLengthArray;//路径长度array
    private SparseArray<Boolean> mPathNeedAddArray;//路径是否需要被全部Add的Array
    private int partIndex;//残缺的index
    private float partLength;//残缺部分的长度

    public StoreHouseAnimHelper(View view, Path sourcePath, Path animPath) {
        this(view, sourcePath, animPath, mDefaultAnimTime, true);
    }

    public StoreHouseAnimHelper(View view, Path sourcePath, Path animPath, long animTime, boolean isInfinite) {
        super(view, sourcePath, animPath, animTime, isInfinite);
        mPathMaxLength = MAX_LENGTH;
        mStonePath = new Path();
        mPm = new PathMeasure();
        mPathLengthArray = new ArrayList<>();//顺序存放path的length
        mPathNeedAddArray = new SparseArray<>();
    }
    /**
     * GET SET FUNC
     **/
    public long getPathMaxLength() {
        return mPathMaxLength;
    }

    public StoreHouseAnimHelper setPathMaxLength(long pathMaxLength) {
        mPathMaxLength = pathMaxLength;
        return this;
    }

    @Override
    public void onPathAnimCallback(View view, Path sourcePath, Path animPath, PathMeasure pathMeasure) {
        //仿StoneHouse效果 ,现在的做法很挫
        mStonePath.reset();
        mStonePath.lineTo(0, 0);
        mPathLengthArray.clear();

        mPm.setPath(animPath, false);
        while (mPm.getLength() != 0) {
            mPathLengthArray.add(mPm.getLength());
            mPm.nextContour();
        }
        mPathNeedAddArray.clear();
        float totalLength = 0;
        partIndex = 0;
        partLength = 0;
        for (int i = mPathLengthArray.size() - 1; i >= 0; i--) {
            if (totalLength + mPathLengthArray.get(i) <= mPathMaxLength) {//加上了也没满
                mPathNeedAddArray.put(i, true);
                totalLength = totalLength + mPathLengthArray.get(i);
            } else if (totalLength < mPathMaxLength) {//加上了满了，但是不加就没满
                partIndex = i;
                partLength = mPathMaxLength - totalLength;
                totalLength = totalLength + mPathLengthArray.get(i);
            }
        }

        mPm.setPath(animPath, false);
        int i = 0;
        while (mPm.getLength() != 0) {
            if (mPathNeedAddArray.get(i, false)) {
                mPm.getSegment(0, mPm.getLength(), mStonePath, true);
            } else if (i == partIndex) {
                mPm.getSegment(mPm.getLength() - partLength, mPm.getLength(), mStonePath, true);
            }
            mPm.nextContour();
            i++;
        }

        animPath.set(mStonePath);
    }
}
