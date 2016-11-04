package com.mcxtzhang.pathanimlib.utils;

import android.content.Context;
import android.graphics.Path;

import java.util.ArrayList;

/**
 * 介绍： 解析成Path的工具类
 * 目前支持从
 * 1 从R.array.xxx里取出点阵 解析
 * 2 根据ArrayList<float[]> path 解析
 * <p>
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/11/4.
 */

public class PathParserUtils {
    /**
     * 从R.array.xxx里取出点阵，
     *
     * @param context
     * @param arrayId
     * @param zoomSize
     * @return
     */
    public static Path getPathFromStringArray(Context context, int arrayId, float zoomSize) {
        Path path = new Path();
        String[] points = context.getResources().getStringArray(arrayId);
        for (int i = 0; i < points.length; i++) {
            String[] x = points[i].split(",");
            for (int j = 0; j < x.length; j = j + 2) {
                if (j == 0) {
                    path.moveTo(Float.parseFloat(x[j]) * zoomSize, Float.parseFloat(x[j + 1]) * zoomSize);
                } else {
                    path.lineTo(Float.parseFloat(x[j]) * zoomSize, Float.parseFloat(x[j + 1]) * zoomSize);
                }
            }
        }
        return path;
    }

    /**
     * 根据ArrayList<float[]> path 解析
     *
     * @param path
     * @return
     */
    public static Path getPathFromArrayFloatList(ArrayList<float[]> path) {
        Path sPath = new Path();
        for (int i = 0; i < path.size(); i++) {
            float[] floats = path.get(i);
            sPath.moveTo(floats[0], floats[1]);
            sPath.lineTo(floats[2], floats[3]);
        }
        return sPath;
    }
}
