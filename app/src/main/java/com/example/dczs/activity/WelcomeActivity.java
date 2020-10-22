package com.example.dczs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.dczs.MyApplication;
import com.example.dczs.R;
import com.example.dczs.entity.AnEntitySetBean;
import com.example.dczs.entity.UserInfoBean;
import com.example.dczs.greendao.UserInfoBeanDao;
import com.example.dczs.utils.LogUtil;
import com.example.dczs.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;
//启动页
public class WelcomeActivity extends CheckPermissionsActivity implements View.OnClickListener {
    private TextView mTvSkip;
    // 用于记录帐号和密码
    private String strAccount = "";
    private String strPassword = "";
    private int recLen = 5;//跳过倒计时提示5秒
    Timer timer = new Timer();
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        initView();
        AnEntitySetBean anset = new AnEntitySetBean();
        MyApplication.setAnEntitySet(anset);
        initLog();
    }

    private void initView() {
        mTvSkip = (TextView) findViewById(R.id.tv_skip);
        mTvSkip.setOnClickListener(this);
    }

    private void initLog() {

        strAccount = ToastUtils.getUserName();
        strPassword = ToastUtils.getUserPassword();
        // 判断是否是之前有登录过
        if (strAccount.equals("")) {
            initDatelys();
        } else {
            // 判断是否刚注销
            if (ToastUtils.getWhetherCancellation()) {
                initDate();
            } else {
                LogUtil.e("自动登录","失败");
                initDatelys();
            }
        }
    }

    private void initDate() {
        UserInfoBean user = MyApplication.getDaoSession().getUserInfoBeanDao().queryBuilder()
                .where(UserInfoBeanDao.Properties.UserLoginName.eq(strAccount)).unique();

        if (user != null){
            MyApplication.getAnEntitySet().setUserInfoBean(user);

            if (MyApplication.getAnEntitySet().getUserInfoBean().getUserLoginName().equals(strAccount)){
                init();
            }else {
                initDatelys();
            }

        }else {
            initDatelys();
        }
    }

    private void init() {

        timer.schedule(task, 1000, 1000);//等待时间一秒，停顿时间一秒
        /**
         * 正常情况下不点击跳过
         */
        handler = new Handler();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        }, 5000);//延迟5S后发送handler信息
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    recLen--;
                    mTvSkip.setText("点击跳过 " + recLen);
                    if (recLen < 0) {
                        timer.cancel();
                        mTvSkip.setVisibility(View.GONE);//倒计时到0隐藏字体
                    }
                }
            });
        }
    };

    private void initDatelys() {
        startActivity(new Intent(WelcomeActivity.this, RegisterActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_skip:
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
                break;
        }
    }

}
