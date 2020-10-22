package com.example.dczs.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dczs.R;
import com.example.dczs.utils.ToastUtils;


public class DoThingActivity extends CheckPermissionsActivity implements View.OnClickListener {
    private ImageView mHeadBack;
    private TextView mHeadTitle;
    /**
     * 请输入姓名
     */
    private EditText mAlarmName;
    /**
     * 请输入联系电话
     */
    private EditText mAlarmCall;
    /**
     * 请输入QQ号码
     */
    private EditText mAlarmQq;
    /**
     * 请填写建议内容
     */
    private EditText mEventContent;
    /**
     * 提交
     */
    private Button mSubscribely;
    private String Content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_thing);
        initView();
    }

    private void initView() {
        mHeadBack = (ImageView) findViewById(R.id.head_back);
        mHeadBack.setOnClickListener(this);
        mHeadTitle = (TextView) findViewById(R.id.head_title);
        mAlarmName = (EditText) findViewById(R.id.alarm_name);
        mAlarmCall = (EditText) findViewById(R.id.alarm_call);
        mAlarmQq = (EditText) findViewById(R.id.alarm_qq);
        mEventContent = (EditText) findViewById(R.id.event_content);
        mSubscribely = (Button) findViewById(R.id.subscribely);
        mSubscribely.setOnClickListener(this);

        mHeadTitle.setText("我的建议");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.head_back:
                finish();
                break;
            case R.id.subscribely:
                initSubscribely();
                break;
        }
    }

    private void initSubscribely() {
        Content = mEventContent.getText().toString().trim();
        if (TextUtils.isEmpty(Content)) {
            ToastUtils.showShort("请输入建议内容！");
            return;
        }

        ToastUtils.showShort("提交成功！");
        finish();
    }

}
