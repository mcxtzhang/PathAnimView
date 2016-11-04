package com.mcxtzhang.pathanimlib;

import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.SparseArray;
import android.view.View;

import java.util.ArrayList;

/**
 * 介绍：仿StoreHouse风格的PathAnimHepler
 * 增加了一个动画残影长度的属性：mPathMaxLength,默认值是400
 *
 * 因没有找到有用的API，这里实现StoreHouse的方法，是手工计算的，不是很爽。
 * 思路是是循环一遍AnimPath，记录里面每一段小Path的length。
 * 然后再逆序遍历AnimPath，从后面截取 残影长度 的Path，
 * 再复制给AnimPath。
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
    public void onPathAnimCallback(View view, Path sourcePath, Path animPath, PathMeasure pathMeasure, ValueAnimator animation) {
        super.onPathAnimCallback(view, sourcePath, animPath, pathMeasure, animation);
        //仿StoneHouse效果 ,现在的做法很挫
        //重置变量
        mStonePath.reset();
        mStonePath.lineTo(0, 0);
        mPathLengthArray.clear();

        //循环一遍AnimPath，记录里面每一段小Path的length。
        mPm.setPath(animPath, false);
        while (mPm.getLength() != 0) {
            mPathLengthArray.add(mPm.getLength());
            mPm.nextContour();
        }

        //逆序遍历AnimPath，记录哪些子Path是需要add的，并且记录那段需要部分add的path的下标
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
        //循环Path，并得到最终要显示的AnimPath
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
