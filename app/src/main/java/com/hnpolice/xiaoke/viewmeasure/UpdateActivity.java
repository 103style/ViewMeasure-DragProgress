package com.hnpolice.xiaoke.viewmeasure;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by luoxiaoke on 2016/3/19 11:10.
 * 更新进度界面
 */
public class UpdateActivity extends AppCompatActivity {

    @InjectView(R.id.tips)
    TextView tips;
    @InjectView(R.id.progesss_value)
    TextView progesssValue;
    @InjectView(R.id.progesss)
    ProgressBar progesss;
    @InjectView(R.id.full)
    LinearLayout full;

    private int x0,x1, x2, dx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.inject(this);
        tips.setText(getResources().getString(R.string.update_here));
        String s = getIntent().getStringExtra("PROGRESS");
        progesss.setProgress(Integer.parseInt(s));


//        progesss.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int w = getWindowManager().getDefaultDisplay().getWidth();
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        x0 = (int) event.getRawX();
//                        progesss.setProgress(100 * x0 / w);
//                        setPos();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        break;
//                }
//                return true;
//            }
//        });

        full.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int w = getWindowManager().getDefaultDisplay().getWidth();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = (int) event.getRawX();
                        progesss.setProgress(100 * x1 / w);
                        setPos();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x2 = (int) event.getRawX();
                        dx = x2 - x1;
                        if (Math.abs(dx) > w / 100) { //改变条件 调整进度改变速度
                            x1 = x2; // 去掉已经用掉的距离， 去掉这句 运行看看会出现效果
                            progesss.setProgress(progesss.getProgress() + dx * 100 / w);
                            setPos();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            setPos();
        }
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
        if (w * pro / 100 + tW * 0.3 > w) {
            params.leftMargin = (int) (w - tW * 1.1);
        } else if (w * pro / 100 < tW * 0.7) {
            params.leftMargin = 0;
        } else {
            params.leftMargin = (int) (w * pro / 100 - tW * 0.7);
        }
        progesssValue.setLayoutParams(params);
        progesssValue.setText(new StringBuffer().append(progesss.getProgress()).append("%"));
    }
}
