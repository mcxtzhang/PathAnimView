package com.mcxtzhang.myapplication;

import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.util.Printer;
import android.view.Choreographer;

/**
 * Intro:
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/10/26.
 * History:
 */

public class PerformanceMonitorUtils {
    private static final String TAG = "zxt/PerformanceMonitorUtils";

    public static void monitorMainLooper() {

        Looper.getMainLooper().setMessageLogging(new Printer() {
            boolean isStarted = false;
            long lastTime;

            @Override
            public void println(String x) {
                if (isStarted) {
                    isStarted = false;
                    Log.d("TAG", "本次主线程 操作执行时间 : x = [" + (System.currentTimeMillis() - lastTime) + "]");
                } else {
                    isStarted = true;
                    lastTime = System.currentTimeMillis();
                }
            }
        });
    }

    public static void monitorChoreoGrapher() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
                long lastTime;

                @Override
                public void doFrame(long frameTimeNanos) {
                    long gap = ((frameTimeNanos - lastTime) / 1000000);
                    Log.d(TAG, "doFrame: frameTimeNanos = [" + frameTimeNanos + "]" + "lasttime:" + lastTime + ", gap:" + gap);
                    if (gap > 16) {
                        long count = (gap - 16) / 16;
                        Log.e(TAG, "丢帧 : frameTimeNanos = [" + frameTimeNanos + "]" + "lasttime:" + lastTime + ", gap:" + gap + ",丢了几帧:" + count);

                    }
                    lastTime = frameTimeNanos;
                    Choreographer.getInstance().postFrameCallback(this);
                }
            });
        }
    }
}
