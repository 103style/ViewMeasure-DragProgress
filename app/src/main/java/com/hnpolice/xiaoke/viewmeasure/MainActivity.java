package com.hnpolice.xiaoke.viewmeasure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by luoxiaoke on 2016/3/19 11:09.
 * 主界面
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.progress)
    EditText progress;
    @InjectView(R.id.show)
    Button show;
    @InjectView(R.id.update)
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initParam();
    }

    private void initParam() {
        show.setOnClickListener(this);
        update.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show:
                startIntent(DetailActivity.class);
                break;
            case R.id.update:
                startIntent(UpdateActivity.class);
                break;
        }
    }

    public void startIntent(Class<?> c) {
        Intent intent = new Intent(MainActivity.this, c);
        intent.putExtra("PROGRESS", getProgress());
        startActivity(intent);
    }


    /**
     * 获取进度
     *
     * @return 进度值
     */
    public String getProgress() {
        String pro = progress.getText().toString();
        if (TextUtils.isEmpty(pro)) {
            pro = "0";
        }
        if (Integer.parseInt(pro) > 100) {
            pro = "100";
        }
        return pro;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); // 调用双击退出函数
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 双击退出函数
     */
    private Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit;
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, "再按一次退出应用!", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);

        } else {
            finish();
        }
    }
}
