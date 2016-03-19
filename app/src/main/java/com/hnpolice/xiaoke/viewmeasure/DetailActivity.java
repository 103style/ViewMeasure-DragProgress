package com.hnpolice.xiaoke.viewmeasure;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by luoxiaoke on 2016/3/19 11:10.
 * 更新进度详情界面
 */
public class DetailActivity extends AppCompatActivity {

    @InjectView(R.id.progesss_value)
    TextView progesssValue;
    @InjectView(R.id.progesss)
    ProgressBar progesss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.inject(this);

        String s = getIntent().getStringExtra("PROGRESS");
        progesss.setProgress(Integer.parseInt(s));
        progesssValue.setText(new StringBuffer().append(progesss.getProgress()).append("%"));


        //方法1
//          setPosWay1();
        //方法2
//        setPosWay2();

        //方法3  重写activity的onWindowFocusChanged方法。

    }


    //方法3 onWindowFocusChanged（true） 表示view已经初始化完毕了
    // 不过注意：onWindowFocusChanged 方法会在activity获得焦点和失去焦点的时候调用
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            setPos();
        }
    }


    /**
     * 方法 1
     * 通过post将一个runnable投递要消息队列的尾部，然后等待looper调用此方法的时候，视图也已经初始化好了
     */
    private void setPosWay1() {
        progesssValue.post(new Runnable() {
            @Override
            public void run() {
                setPos();
            }
        });
    }


    /**
     * ViewTreeObserver
     * Call requires API level 16 : android.view.ViewTreeObserver#removeOnGlobalLayoutListener
     */
    private void setPosWay2() {
        final ViewTreeObserver observer = progesssValue.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                //此处不能写 observer.removeOnGlobalLayoutListener(this); 否则会报错
                progesssValue.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                setPos();

            }
        });
    }


    /**
     * 设置进度显示在对应的位置
     */
    public void setPos() {
        int w = getWindowManager().getDefaultDisplay().getWidth();
        Log.e("w=====", "" + w);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) progesssValue.getLayoutParams();
        int pro = progesss.getProgress();
        int tW = progesssValue.getWidth();
        if (w * pro / 100 < tW * 0.7) {
            params.leftMargin = 0;
        } else if (w * pro / 100 + tW * 0.3 > w) {
            params.leftMargin = w - tW;
        } else {
            params.leftMargin = (int) (w * pro / 100 - tW * 0.7);
        }

        progesssValue.setLayoutParams(params);
    }
}
