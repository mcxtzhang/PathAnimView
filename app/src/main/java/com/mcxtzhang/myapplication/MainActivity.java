package com.mcxtzhang.myapplication;

import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mcxtzhang.pathanimlib.PathAnimView;
import com.mcxtzhang.pathanimlib.StoreHouseAnimView;

public class MainActivity extends AppCompatActivity {
    LoadingPathAnimView fillView1;
    LoadingPathAnimView fillView2;
    CstStoreHouseAnimView storeView1;
    CstStoreHouseAnimView storeView2;
    PathAnimView pathAnimView1;
    StoreHouseAnimView storeView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillView1 = (LoadingPathAnimView) findViewById(R.id.fillView1);
        fillView2 = (LoadingPathAnimView) findViewById(R.id.fillView2);
        storeView1 = (CstStoreHouseAnimView) findViewById(R.id.storeView1);
        storeView2 = (CstStoreHouseAnimView) findViewById(R.id.storeView2);

        //动态设置Path实例
        pathAnimView1 = (PathAnimView) findViewById(R.id.pathAnimView1);
        Path sPath = new Path();
        sPath.moveTo(0, 0);
        sPath.addCircle(40, 40, 30, Path.Direction.CW);
        pathAnimView1.setSourcePath(sPath);
        //代码示例 动态对path加工，通过Helper
        pathAnimView1.setPathAnimHelper(new CstSysLoadAnimHelper(pathAnimView1, sPath, pathAnimView1.getAnimPath()));

        storeView3 = (StoreHouseAnimView) findViewById(R.id.storeView3);
        sPath = new Path();
        for (int i = 1; i < 20; i = i + 2) {
            sPath.moveTo(5, 5 * i);
            sPath.lineTo(100, 5 * i);
            sPath.moveTo(150, 5 * i);
            sPath.lineTo(300, 5 * i);
        }
        storeView3.setSourcePath(sPath);
    }

    public void start(View view) {
        fillView1.startAnim();//普通可xml预览path动画
        storeView1.startAnim();//普通可预览storeHouse动画
        fillView2.setAnimTime(3000).setAnimInfinite(false).startAnim();//可预览。设置了动画总时长，只执行一次的动画
        storeView2.setAnimTime(1000).startAnim();//可预览，设置了动画时长的动画
        pathAnimView1.startAnim();//通过代码设置path的普通动画，但是xml不可预览
        storeView3.setPathMaxLength(1200).setAnimTime(20000).startAnim();//通过代码设置path的stoneHouse动画，时长设的很大，设置了stoneHouse残影长度，可以看出stoneHouse残影效果。
    }

    public void stop(View view) {
        fillView1.stopAnim();
        fillView2.stopAnim();
        storeView1.stopAnim();
        storeView2.stopAnim();
        pathAnimView1.stopAnim();
        storeView3.stopAnim();
    }

    public void reset(View view) {
        fillView1.resetAnim();
        fillView2.resetAnim();
        storeView1.resetAnim();
        storeView2.resetAnim();
        pathAnimView1.resetAnim();
        storeView3.resetAnim();
    }
}
