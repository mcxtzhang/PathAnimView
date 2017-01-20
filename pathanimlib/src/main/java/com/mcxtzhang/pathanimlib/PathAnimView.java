package com.mcxtzhang.pathanimlib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 介绍：一个路径动画的View
 * 利用源Path绘制“底”
 * 利用动画Path 绘制 填充动画
 * <p>
 * 通过mPathAnimHelper 对SourcePath做动画：
 * 一个SourcePath 内含多段Path，循环取出每段Path，并做一个动画,
 * <p>
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/11/2.
 */

public class PathAnimView extends View {
    private static final String TAG = "zxt/PathAnimView";
    protected RectF mSourceRect;
    protected Path mSourcePath;//需要做动画的源Path
    protected Path mAnimPath;//用于绘制动画的Path
    protected Paint mPaint;
    protected int mColorBg = Color.GRAY;//背景色
    protected int mColorFg = Color.WHITE;//前景色 填充色
    protected PathAnimHelper mPathAnimHelper;//Path动画工具类

    protected int mPaddingLeft, mPaddingTop;

    public PathAnimView(Context context) {
        this(context, null);
    }

    public PathAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * GET SET FUNC
     **/
    public Path getSourcePath() {
        return mSourcePath;
    }

    /**
     * 这个方法可能会经常用到，用于设置源Path
     *
     * @param sourcePath
     * @return
     */
    public PathAnimView setSourcePath(Path sourcePath) {
        mSourcePath = sourcePath;
        mSourceRect = new RectF();
        mSourcePath.computeBounds(mSourceRect, true);
        initAnimHelper();
        return this;
    }

    public Paint getPaint() {
        return mPaint;
    }

    public PathAnimView setPaint(Paint paint) {
        mPaint = paint;
        return this;
    }

    public int getColorBg() {
        return mColorBg;
    }

    public PathAnimView setColorBg(int colorBg) {
        mColorBg = colorBg;
        return this;
    }

    public int getColorFg() {
        return mColorFg;
    }

    public PathAnimView setColorFg(int colorFg) {
        mColorFg = colorFg;
        return this;
    }

    public PathAnimHelper getPathAnimHelper() {
        return mPathAnimHelper;
    }

    public PathAnimView setPathAnimHelper(PathAnimHelper pathAnimHelper) {
        mPathAnimHelper = pathAnimHelper;
        return this;
    }

    public Path getAnimPath() {
        return mAnimPath;
    }


    /**
     * INIT FUNC
     **/
    protected void init() {
        //Paint
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);

        //动画路径只要初始化即可
        mAnimPath = new Path();

        //初始化动画帮助类
        initAnimHelper();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (null != mSourceRect) {
            Log.d(TAG, "onMeasure() called with: mSourceRect = [" + mSourceRect);
            int wMode = MeasureSpec.getMode(widthMeasureSpec);
            int wSize = MeasureSpec.getSize(widthMeasureSpec);
            int hMode = MeasureSpec.getMode(heightMeasureSpec);
            int hSize = MeasureSpec.getSize(heightMeasureSpec);
            switch (wMode) {
                case MeasureSpec.EXACTLY:
                    break;
                case MeasureSpec.AT_MOST:
                    int computeSize = (int) (mSourceRect.right) + getPaddingLeft() + getPaddingRight();
                    wSize = Math.min(wSize, computeSize);
                    break;
                case MeasureSpec.UNSPECIFIED:
                    wSize = (int) (mSourceRect.right) + getPaddingLeft() + getPaddingRight();
                    break;
            }
            switch (hMode) {
                case MeasureSpec.EXACTLY:
                    break;
                case MeasureSpec.AT_MOST:
                    int computeSize = (int) (mSourceRect.bottom) + getPaddingTop() + getPaddingBottom();
                    hSize = Math.min(wSize, computeSize);
                    break;
                case MeasureSpec.UNSPECIFIED:
                    hSize = (int) (mSourceRect.bottom) + getPaddingTop() + getPaddingBottom();
                    break;
            }
            setMeasuredDimension(wSize, hSize);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }

    //auto-fit size, in this demo, for EXACTLY and UNSPEICIFIED
    private void setWrapSize() {
        setMeasuredDimension(MeasureSpec.makeMeasureSpec((int) (mSourceRect.right) + getPaddingLeft() + getPaddingRight(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec((int) (mSourceRect.bottom) + getPaddingTop() + getPaddingBottom(), MeasureSpec.EXACTLY));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPaddingLeft = getPaddingLeft();
        mPaddingTop = getPaddingTop();
    }

    /**
     * 初始化动画帮助类
     */
    protected void initAnimHelper() {
        mPathAnimHelper = getInitAnimHeper();
        //mPathAnimHelper = new PathAnimHelper(this, mSourcePath, mAnimPath, 1500, true);
    }

    /**
     * 子类可通过重写这个方法，返回自定义的AnimHelper
     *
     * @return
     */
    protected PathAnimHelper getInitAnimHeper() {
        return new PathAnimHelper(this, mSourcePath, mAnimPath);
    }

    /**
     * draw FUNC
     **/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //平移
        canvas.translate(mPaddingLeft, mPaddingTop);

        //先绘制底，
        mPaint.setColor(mColorBg);
        canvas.drawPath(mSourcePath, mPaint);

        //再绘制前景，mAnimPath不断变化，不断重绘View的话，就会有动画效果。
        mPaint.setColor(mColorFg);
        canvas.drawPath(mAnimPath, mPaint);
    }

    /**
     * 设置动画 循环
     */
    public PathAnimView setAnimInfinite(boolean infinite) {
        mPathAnimHelper.setInfinite(infinite);
        return this;
    }

    /**
     * 设置动画 总时长
     */
    public PathAnimView setAnimTime(long animTime) {
        mPathAnimHelper.setAnimTime(animTime);
        return this;
    }

    /**
     * 执行循环动画
     */
    public void startAnim() {
        mPathAnimHelper.startAnim();
    }

    /**
     * 停止动画
     */
    public void stopAnim() {
        mPathAnimHelper.stopAnim();
    }

    /**
     * 清除并停止动画
     */
    public void clearAnim() {
        stopAnim();
        mAnimPath.reset();
        mAnimPath.lineTo(0, 0);
        invalidate();
    }
}
