package com.example.dczs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dczs.R;
import com.example.dczs.utils.MyActivityManager;
import com.example.dczs.utils.ToastUtils;

//设置
public class SettingActivity extends CheckPermissionsActivity implements View.OnClickListener {

    private ImageView mHeadBack;
    private TextView mHeadTitle;
    /**
     * 修改登录密码
     */
    private TextView mTvSettingAlytermima;
    /**
     * 修改个人信息
     */
    private TextView mTvSettingGrxx;
    /**
     * 关于我们
     */
    private LinearLayout mLoutAbout;
    private LinearLayout mWipeCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        mHeadBack = (ImageView) findViewById(R.id.head_back);
        mHeadBack.setOnClickListener(this);
        mHeadTitle = (TextView) findViewById(R.id.head_title);
        mTvSettingAlytermima = (TextView) findViewById(R.id.tv_setting_alytermima);
        mTvSettingAlytermima.setOnClickListener(this);
        mTvSettingGrxx = (TextView) findViewById(R.id.tv_setting_grxx);
        mTvSettingGrxx.setOnClickListener(this);
        mLoutAbout = (LinearLayout) findViewById(R.id.lout_about);
        mLoutAbout.setOnClickListener(this);
        mWipeCache = (LinearLayout) findViewById(R.id.wipe_cache);
        mWipeCache.setOnClickListener(this);

        mHeadTitle.setText("设置");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.head_back:
                finish();
                break;
            case R.id.tv_setting_alytermima:
                startActivity(new Intent(SettingActivity.this, AlterUserMiMaActivity.class));
                break;
            case R.id.tv_setting_grxx:
                startActivity(new Intent(SettingActivity.this, EditInfoActivity.class));
                break;
            case R.id.lout_about:
                MyActivityManager.getInstance().AppExit(this);
                break;
            case R.id.wipe_cache:
                ToastUtils.showShort("清除缓存成功！");
                break;
        }
    }
}
