package com.mcxtzhang.myapplication;

import android.content.Context;
import android.util.AttributeSet;

import com.mcxtzhang.pathanimlib.StoreHouseAnimView;
import com.mcxtzhang.pathanimlib.utils.PathParserUtils;
import com.mcxtzhang.pathanimlib.res.StoreHousePath;

/**
 * 介绍：一个StoreHouse风格动画的View
 * 继承View的好处是 xml可以预览 ，也可以代码动态设置
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/11/2.
 */

public class CstStoreHouseAnimView extends StoreHouseAnimView {

    public CstStoreHouseAnimView(Context context) {
        this(context, null);
    }

    public CstStoreHouseAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CstStoreHouseAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //根据String 转化成Path
        setSourcePath(PathParserUtils.getPathFromArrayFloatList(StoreHousePath.getPath("ZhangXuTong", 1.1f, 16)));
    }

}
