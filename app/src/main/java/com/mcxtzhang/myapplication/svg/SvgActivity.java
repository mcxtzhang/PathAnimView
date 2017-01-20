package com.mcxtzhang.myapplication.svg;

import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mcxtzhang.myapplication.R;
import com.mcxtzhang.pathanimlib.PathAnimView;
import com.mcxtzhang.pathanimlib.utils.SvgPathParser;

import java.text.ParseException;

public class SvgActivity extends AppCompatActivity {
    PathAnimView storeView3;

    //SVG转-》path tools
    SvgPathParser svgPathParser = new SvgPathParser();

    String mSuccessString;
    String mMeiziString;
    String mTietaString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg);

        storeView3 = (PathAnimView) findViewById(R.id.storeView3);


        mTietaString = getString(R.string.tieta);


        String del = getString(R.string.del);
        String toys = getString(R.string.toys);
        String testGIMP = getString(R.string.testGIMP);

        String testGIMP2 = "\"M 90.00,11.00\n" +
                "           C 90.00,11.00 119.00,11.00 119.00,11.00\n" +
                "             121.18,11.00 124.85,10.79 126.69,12.02\n" +
                "             129.62,13.98 129.43,18.71 125.85,20.83\n" +
                "             123.52,22.21 119.67,22.00 117.00,22.00\n" +
                "             117.00,22.00 83.00,22.00 83.00,22.00\n" +
                "             79.27,21.99 73.44,22.62 72.33,17.89\n" +
                "             71.68,15.12 72.58,13.88 74.31,12.02" +
                "             78.99,10.24 84.99,11.00 90.00,11.00 Z";


        String tempVector = "M928.2,828.4L130,828.4c-32.8,0 -59.1,-26.3 -59.1,-59.1L70.9,190.4c0,-32.8 26.3,-59.1 59.1,-59.1h798.2c32.8,0 59.1,26.3 59.1,59.1v579c0,32.8 -26.3,59.1 -59.1,59.1zM136.5,762.7h785.1L921.6,196.9L136.5,196.9v565.8z";

        String faildVector = "M6,18c0,0.55 0.45,1 1,1h1v3.5c0,0.83 0.67,1.5 1.5,1.5s1.5,-0.67 1.5,-1.5L11,19h2v3.5c0,0.83 0.67,1.5 1.5,1.5s1.5,-0.67 1.5,-1.5L16,19h1c0.55,0 1,-0.45 1,-1L18,8L6,8v10zM3.5,8C2.67,8 2,8.67 2,9.5v7c0,0.83 0.67,1.5 1.5,1.5S5,17.33 5,16.5v-7C5,8.67 4.33,8 3.5,8zM20.5,8c-0.83,0 -1.5,0.67 -1.5,1.5v7c0,0.83 0.67,1.5 1.5,1.5s1.5,-0.67 1.5,-1.5v-7c0,-0.83 -0.67,-1.5 -1.5,-1.5zM15.53,2.16l1.3,-1.3c0.2,-0.2 0.2,-0.51 0,-0.71 -0.2,-0.2 -0.51,-0.2 -0.71,0l-1.48,1.48C13.85,1.23 12.95,1 12,1c-0.96,0 -1.86,0.23 -2.66,0.63L7.85,0.15c-0.2,-0.2 -0.51,-0.2 -0.71,0 -0.2,0.2 -0.2,0.51 0,0.71l1.31,1.31C6.97,3.26 6,5.01 6,7h12c0,-1.99 -0.97,-3.75 -2.47,-4.84zM10,5L9,5L9,4h1v1zM15,5h-1L14,4h1v1z";

        String settings = getResources().getString(R.string.settings);
        String qrcode2 = getString(R.string.qrcode2);
        mMeiziString = getString(R.string.qianbihua);

        mSuccessString = "M 92.25 10.34 C 111.88 8.58 132.06 13.56 148.61 24.29 C 166.17 35.55 179.69\n" +
                "53.05 185.87 73.00 C 191.06 89.48 191.35 107.43 186.77 124.08 C 181.11 145.24\n" +
                "167.22 163.97 148.80 175.77 C 131.30 187.10 109.74 191.90 89.08 189.32 C 72.31\n" +
                "187.41 56.16 180.57 43.08 169.90 C 26.03 156.08 14.33 135.83 11.10 114.10 C 7.91\n" +
                "94.17 11.66 73.19 21.67 55.66 C 35.93 30.23 63.14 12.60 92.25 10.34 M 86.38\n" +
                "20.54 C 72.89 22.84 60.04 28.70 49.42 37.33 C 33.89 49.80 23.21 68.19 20.29\n" +
                "87.92 C 18.37 101.25 19.60 115.10 24.32 127.75 C 31.72 148.00 47.60 164.91 67.29\n" +
                "173.66 C 83.17 180.82 101.40 182.40 118.35 178.50 C 147.32 171.95 171.44 148.09\n" +
                "178.28 119.18 C 183.15 99.38 180.25 77.71 169.95 60.06 C 160.50 43.44 144.93\n" +
                "30.43 126.92 24.02 C 114.00 19.37 99.90 18.33 86.38 20.54 Z"
                + "M 98.47 119.50 C 115.62 101.54 132.82 83.63 149.94 65.64 C 152.30 67.69 154.62\n" +
                "69.80 156.88 71.96 C 139.77 89.82 122.86 107.88 105.84 125.81 C 102.48 129.50\n" +
                "98.89 133.40 93.92 134.80 C 87.33 137.00 79.80 134.87 74.94 130.01 C 66.27\n" +
                "121.60 57.39 113.41 48.80 104.93 C 50.86 102.68 52.91 100.42 54.96 98.16 C 63.73\n" +
                "106.34 72.31 114.72 81.04 122.94 C 83.40 125.27 86.87 126.63 90.17 125.77 C\n" +
                "93.75 125.04 96.10 122.01 98.47 119.50 Z";
        try {
            Path path = svgPathParser.parsePath(mSuccessString);
            path = new Path();
            path.moveTo(0,0);
            path.lineTo(getResources().getDisplayMetrics().widthPixels,getResources().getDisplayMetrics().heightPixels);
            Log.d("zxt", "onCreate() called with: getResources().getDisplayMetrics().widthPixels = [" + getResources().getDisplayMetrics().widthPixels + "]");
            Log.d("zxt", "onCreate() called with: getResources().getDisplayMetrics().heightPixels = [" + getResources().getDisplayMetrics().heightPixels + "]");
            path.lineTo(getResources().getDisplayMetrics().widthPixels*2,0);
            storeView3.setSourcePath(path);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //storeView3.getPathAnimHelper().setAnimTime(2000);
        storeView3.setAnimInfinite(false).setAnimTime(2000).startAnim();
    }


    public void onChange(View view) {
        try {
            Path path = null;
            switch (view.getId()) {
                case R.id.changePay:
                    path = svgPathParser.parsePath(mSuccessString);
                    break;
                case R.id.changeMeizi:
                    path = svgPathParser.parsePath(mMeiziString);
                    break;
                case R.id.changeTieta:
                    path = svgPathParser.parsePath(mTietaString);
                    break;
                case R.id.changeBigPath:
                    path = svgPathParser.parsePath(getResources().getString(R.string.bukemiaoshu));
                    break;

            }
            storeView3.clearAnim();
            storeView3.setSourcePath(path);
            storeView3.requestLayout();
            storeView3.setAnimTime(2000).startAnim();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
