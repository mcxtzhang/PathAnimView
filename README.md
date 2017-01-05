# PathAnimView
用于做Path动画的自定义View。

**I have a path.I have a view. (Oh~),Path(Anim)View.**

现已经找到图片->SVG->PATH的正确姿势，

**Now i have a pic.I have a view. Oh~,Path(Anim)View.**

相关博文：

实现详解：

http://blog.csdn.net/zxt0601/article/details/53040506

图片->SVG->Path的正确姿势  ，用法进阶：

http://blog.csdn.net/zxt0601/article/details/54018970


# 使用：
Step 1. 在项目根build.gradle文件中增加JitPack仓库依赖。
```
    allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```
Step 2. Add the dependency
```
	dependencies {
	        compile 'com.github.mcxtzhang:PathAnimView:V1.0.0'
	}
```




# 一 概述
原本只是想模仿一下我魂牵梦萦的StoreHouse效果，没想到意外撸出来一个工具库。

最简单用法，给我一个path，我还你一个动画。

**I have a path.I have a view. (Oh~),Path(Anim)View.**

```
    <com.mcxtzhang.pathanimlib.PathAnimView
        android:id="@+id/pathAnimView1"
        android:layout_width="wrap_content"
        android:layout_height="60dp"
        android:background="@color/blue"
        android:padding="5dp"/>
```
```
    Path sPath = new Path();
    sPath.moveTo(0, 0);
    sPath.addCircle(40, 40, 30, Path.Direction.CW);
    pathAnimView1.setSourcePath(sPath);
```

先看效果图：(真机效果很棒哦，我自己的手机是去年某款599的手机，算是低端的了，6个View一起动画，不会卡，查看GPU呈现模式，95%时间都处于16ms线以下。性能还可以的)


![](http://ac-mhke0kuv.clouddn.com/40416b47fdacaebd21be.gif)
其中
图1 是普通逐渐填充的效果，无限循环。
图2 是仿StoreHouse 残影流动效果。（但与原版并不是完全一模一样，估计原版不是用Path做的）
图3 是逐渐填充的效果，设置了只执行一次。
图4 是仿StoreHouse效果。数据源来自R.array.xxxx
图5 是另一种自定义PathAnimHelper实现的自定义动画效果。类似Android L+ 系统进度条效果。
图6 是仿StoreHouse效果，但是将动画时长设置的很大，所以能看到它逐渐的过程。

---
#### 2017 01 05 更新：
I have a pic.I have a view. Oh~,Path(Anim)View.
效果先随便上几个图，以后**你找到的图有多精彩，gif就有多精彩**：

![随便搜了一个铅笔画的图，丢进去](https://github.com/mcxtzhang/PathAnimView/blob/master/gif/qianbihua.gif)

![支付成功动画](https://github.com/mcxtzhang/PathAnimView/blob/master/gif/success.gif)

![随手复制的二维码icon](https://github.com/mcxtzhang/PathAnimView/blob/master/gif/qrcode.gif)

![来自大佬wing的铁塔](https://github.com/mcxtzhang/PathAnimView/blob/master/gif/tieta.gif)



---

StoneHouse效果如下简单使用：
```
    <com.mcxtzhang.pathanimlib.StoreHouseAnimView
        android:id="@+id/pathAnimView1"
        android:layout_width="wrap_content"
        android:layout_height="60dp"
        android:background="@android:color/black"
        android:padding="5dp"/>
```


```
        pathAnimView1 = (StoreHouseAnimView) findViewById(R.id.pathAnimView1);
        Path sPath = new Path();
        sPath.moveTo(0, 0);
        sPath.addCircle(40, 40, 30, Path.Direction.CW);
        pathAnimView1.setSourcePath(PathParserUtils.getPathFromArrayFloatList(StoreHousePath.getPath("McXtZhang")));
        pathAnimView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pathAnimView1.startAnim();
            }
        });
```

## 参数
**目前可配**参数：
1 绘制方面，支持绘制Path的**前景 背景**色。
```
    //设置颜色
    fillView2.setColorBg(Color.WHITE).setColorFg(Color.BLACK);
```

2 动画方面，目前支持设置动画的**时长**，是否**无限循环**等。
```
    //设置了动画总时长，只执行一次的动画
    fillView2.setAnimTime(3000).setAnimInfinite(false).startAnim();
```

3 仿StoreHouse风格的View，还支持设置**残影的长度**。
```
//设动画时长，设置了stoneHouse残影长度
    storeView3.setPathMaxLength(1200).setAnimTime(20000).startAnim();
```
4 当然你可以拿到Paint自己搞事情：
```
    //当然你可以自己拿到Paint，然后搞事情，我这里设置线条宽度
    pathAnimView1.getPaint().setStrokeWidth(10);
```

## 数据源：
PathAnimView的**数据源是Path**。（给我一个Path，还你一个动画View）
所以内置了几种将别的**资源->Path**的方法。
1 直接传string。 StoreHouse风格支持的A-Z,0-9 "." "- " " "（[源自百万大神的库文末也有鸣谢](https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh)，）
```
    //根据String 转化成Path
    setSourcePath(PathParserUtils.getPathFromArrayFloatList(StoreHousePath.getPath("ZhangXuTong", 1.1f, 16)));
```
2 定义在R.array.xxx里
```
    //动态设置 从StringArray里取
    storeView2.setSourcePath(PathParserUtils.getPathFromStringArray(this, R.array.storehouse, 3));
```
3 简单的SVG(半成品)
以前从gayHub上找了一个SVG-PATH的转换类：SvgPathParser，现在派上了用场，简单的SVG-PATH，可以，复杂的还有问题，还需要继续寻找更加方案。
```
        //SVG转-》path
        //还在完善中，我从github上找了如下工具类，发现简单的SVG可以转path，复杂点的 就乱了
/*        SvgPathParser svgPathParser = new SvgPathParser();
        try {
            Path path = svgPathParser.parsePath("M1,1 L1,50 L50,50 L50,50 L50,1 Z");
            storeView3.setSourcePath(path);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
```


## 简单用法API
### 1 xml定义
普通PathAnimView
效果如图1 3。动画是 进度填充直到满的效果。 
```
    <com.mcxtzhang.pathanimlib.PathAnimView
        android:id="@+id/pathAnimView1"
        android:layout_width="wrap_content"
        android:layout_height="60dp"
        android:background="@color/blue"
        android:padding="5dp"/>
```

高仿StoreHouse风格AnimView：
这种View显示出来的效果如图2 4 6 。动画是 残影流动的效果。
```
    <com.mcxtzhang.pathanimlib.StoreHouseAnimView
        android:id="@+id/storeView3"
        android:layout_width="wrap_content"
        android:layout_height="60dp"
        android:background="@android:color/black"
        android:padding="5dp"/>
```

### 2 开始动画
```
    fillView1.startAnim();
```
### 3 停止动画
```
    fillView1.stopAnim();
```
### 4 清除并停止动画
```
    fillView1.clearAnim();
```


## 稍微 ~~搞基~~ 高级点的用法预览
看到这里细心的朋友可能会发现，上一节，我没有提第5个图View是怎么定义的， 而且第五个View的效果，貌似和其他的不一样，仔细看动画是不是像Android L+的系统自带进度条ProgressBar的效果？
那说明它的动画效果和我先前提到的两种不一样，是的，一开始我撸是照着StoreHouse那种效果撸的，这是我第二天才扩展的。
高级的用法，就是本控件动画的**扩展性**。
你完全可以通过继承`PathAnimHelper类`，重写`onPathAnimCallback（）`方法，扩展动画，图5就是这么来的。
先讲用法预览，稍后章节会详解。
**用法**：
对任意一个普通的PathAnimView，设置一个自定义的PathAnimHelper类即可：
```
        //代码示例 动态对path加工，通过Helper
        pathAnimView1.setPathAnimHelper(new CstSysLoadAnimHelper(pathAnimView1, pathAnimView1.getSourcePath(), pathAnimView1.getAnimPath()));
```

自定义的PathAnimHelper类:
```
/**
 * 介绍：自定义的PathAnimHelper，实现类似Android L+ 进度条效果
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/11/3.
 */

public class CstSysLoadAnimHelper extends PathAnimHelper {
    public CstSysLoadAnimHelper(View view, Path sourcePath, Path animPath) {
        super(view, sourcePath, animPath);
    }

    public CstSysLoadAnimHelper(View view, Path sourcePath, Path animPath, long animTime, boolean isInfinite) {
        super(view, sourcePath, animPath, animTime, isInfinite);
    }

    @Override
    public void onPathAnimCallback(View view, Path sourcePath, Path animPath, PathMeasure pathMeasure, ValueAnimator animation) {
        float value = (float) animation.getAnimatedValue();
        //获取一个段落
        float end = pathMeasure.getLength() * value;
        float begin = (float) (end - ((0.5 - Math.abs(value - 0.5)) * pathMeasure.getLength()));
        animPath.reset();
        animPath.lineTo(0, 0);
        pathMeasure.getSegment(begin, end, animPath, true);
    }
}
```

# 二 逐个介绍
这里我简单画了一下本文介绍的几个类的类图：
对于重要方法和属性标注了一下。


![](http://ac-mhke0kuv.clouddn.com/1c53f000b03e84b6a9f5.png)
我们的主角`PathAnimView`继承自View，是一个自定义View。
它内部持有一个`PathAnimHelper`，专注做**Path动画**。它默认的实现是 **逐渐填充** 的动画效果。

一般情况下只需要更换`PathAnimHelper`,`PathAnimView`即可做出**不同的动画**。（图1第5个View）

但是如果需要扩充一些动画属性**供用户设置**，例如仿StoreHouse风格的动画View，想暴露 **残影长度** 属性供设置。
我这里采用的是：继承自`PathAnimView`，并增加属性get、set 方法，并重写`getInitAnimHeper()`方法，返回自定义的`PathAnimHelper`。
如`StoreHouseAnimView`继承自`PathAnimView`,增加了残影长度的get、set方法。并重写`getInitAnimHeper()`方法，返回`StoreHouseAnimHelper`对象。 `StoreHouseAnimHelper`类继承的是`PathAnimHelper`。

先看`PathAnimView`:
这里我将一些不重要的get、set方法和构造方法剔除，留下比较重要的方法。
```
/**
 * 介绍：一个路径动画的View
 * 利用源Path绘制“底”
 * 利用动画Path 绘制 填充动画
 * <p>
 * 一个SourcePath 内含多段Path，循环取出每段Path，并做一个动画,
 * <p>
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/11/2.
 */

public class PathAnimView extends View {
    protected Path mSourcePath;//需要做动画的源Path
    protected Path mAnimPath;//用于绘制动画的Path
    protected Paint mPaint;
    protected int mColorBg = Color.GRAY;//背景色
    protected int mColorFg = Color.WHITE;//前景色 填充色
    protected PathAnimHelper mPathAnimHelper;//Path动画工具类

    protected int mPaddingLeft, mPaddingTop;

    public PathAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 这个方法可能会经常用到，用于设置源Path
     *
     * @param sourcePath
     * @return
     */
    public PathAnimView setSourcePath(Path sourcePath) {
        mSourcePath = sourcePath;
        initAnimHelper();
        return this;
    }

    /**
     * INIT FUNC
     **/
    protected void init() {
        //Paint
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        //动画路径只要初始化即可
        mAnimPath = new Path();

        //初始化动画帮助类
        initAnimHelper();

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

        mPaint.setColor(mColorBg);
        canvas.drawPath(mSourcePath, mPaint);


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

```
代码本身不难，注释也比较详细，核心的话，就是`onDraw()`方法咯：
我这里用平移做的paddingLeft、paddingTop。
先利用源Path绘制底边的样子。
再利用animPath绘制前景，这样animPath不断变化，并且重绘View->onDraw()，前景就会不断变化，形成动画效果。
那么核心就是animPath的的变化了，animPath的变化交由 mPathAnimHelper去做。
```
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
```

