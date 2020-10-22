package com.example.dczs.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dczs.R;


//关于界面
public class AboutActivity extends CheckPermissionsActivity implements View.OnClickListener {
    private ImageView mHeadBack;
    private TextView mHeadTitle;
    /**
     * 当前版本1.0.0
     */
    private TextView mCurrentVersion;
    private LinearLayout mUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();

        initDate();
    }

    private void initDate() {
        String update = "当前版本 v 1.0.0" ;
        mCurrentVersion.setText(update);
    }

    private void initView() {
        mHeadBack = (ImageView) findViewById(R.id.head_back);
        mHeadBack.setOnClickListener(this);
        mHeadTitle = (TextView) findViewById(R.id.head_title);
        mHeadTitle.setText("关于");
        mCurrentVersion = (TextView) findViewById(R.id.current_version);
        mUpdate = (LinearLayout) findViewById(R.id.update);
        mUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.head_back:
                finish();
                break;
            case R.id.update:

                break;
        }
    }

}
