package com.mcxtzhang.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mcxtzhang.myapplication.svg.SvgActivity;
import com.mcxtzhang.pathanimlib.PathAnimView;
import com.mcxtzhang.pathanimlib.StoreHouseAnimView;
import com.mcxtzhang.pathanimlib.SysLoadAnimHelper;
import com.mcxtzhang.pathanimlib.utils.PathParserUtils;

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


        //设置颜色
        fillView2.setColorBg(Color.WHITE).setColorFg(Color.BLACK);


        //动态设置 从StringArray里取
        storeView2.setSourcePath(PathParserUtils.getPathFromStringArray(this, R.array.storehouse, 2));

        //动态设置Path实例
        pathAnimView1 = (PathAnimView) findViewById(R.id.pathAnimView1);
        Path sPath = new Path();
        sPath.moveTo(0, 0);
        sPath.addCircle(50, 50, 60, Path.Direction.CW);
        sPath.addCircle(50, 50, 40, Path.Direction.CW);
        pathAnimView1.setSourcePath(sPath);
        //代码示例 动态对path加工，通过Helper
        pathAnimView1.setPathAnimHelper(new SysLoadAnimHelper(pathAnimView1, pathAnimView1.getSourcePath(), pathAnimView1.getAnimPath()));
        //设置颜色
        pathAnimView1.setColorBg(Color.WHITE).setColorFg(Color.RED);
        //当然你可以自己拿到Paint，然后搞事情，我这里设置线条宽度
        pathAnimView1.getPaint().setStrokeWidth(10);

        storeView3 = (StoreHouseAnimView) findViewById(R.id.storeView3);
        sPath = new Path();
        for (int i = 1; i < 20; i = i + 2) {
            sPath.moveTo(5, 5 * i);
            sPath.lineTo(100, 5 * i);
            sPath.moveTo(150, 5 * i);
            sPath.lineTo(300, 5 * i);
        }
        storeView3.setSourcePath(sPath);

        //SVG转-》path
        //还在完善中，我从github上找了如下工具类，发现简单的SVG可以转path，复杂点的 就乱了
/*        SvgPathParser svgPathParser = new SvgPathParser();
        try {
            Path path = svgPathParser.parsePath("M 0.00 0.00 L 326.00 0.00 L 326.00 167.15 C 323.98 168.11 323.97 170.88 326.00 171.84 L 326.00 261.00 L 0.00 261.00 L 0.00 0.00 M 7.02 33.03 C 6.98 101.68 6.98 170.33 7.03 238.97 C 112.01 239.02 216.99 239.01 321.97 238.98 C 322.02 170.33 322.02 101.68 321.97 33.03 C 216.99 32.98 112.01 32.98 7.02 33.03 Z");
            storeView3.setSourcePath(path);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        findViewById(R.id.btnSvg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SvgActivity.class));
            }
        });
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
        fillView1.clearAnim();
        fillView2.clearAnim();
        storeView1.clearAnim();
        storeView2.clearAnim();
        pathAnimView1.clearAnim();
        storeView3.clearAnim();
    }
}
