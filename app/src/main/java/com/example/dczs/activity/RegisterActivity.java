package com.example.dczs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.example.dczs.MyApplication;
import com.example.dczs.R;
import com.example.dczs.entity.AnEntitySetBean;
import com.example.dczs.entity.UserInfoBean;
import com.example.dczs.greendao.UserInfoBeanDao;
import com.example.dczs.utils.LogUtil;
import com.example.dczs.utils.MyActivityManager;
import com.example.dczs.utils.ToastUtils;
//登录
public class RegisterActivity extends CheckPermissionsActivity implements View.OnClickListener{
    /**
     * 请输入用户名
     */
    private EditText mUserName;
    /**
     * 请输入密码
     */
    private EditText mUserPassword;
    private ImageView mIvLonginPasswrod;
    /**
     * 忘记密码?
     */
    private TextView mForgetPassword;
    /**
     * 新用户注册
     */
    private TextView mUserRegistration;
    /**
     * 登录
     */
    private Button mLogIn;
    private Boolean showPassword = true;
    private String strAccount = "";
    private String name = "";
    private String password = "";
    private LoadingDailog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        AnEntitySetBean anset = new AnEntitySetBean();
        MyApplication.setAnEntitySet(anset);
    }


    private void initView() {
        mUserName = (EditText) findViewById(R.id.user_name);
        mUserPassword = (EditText) findViewById(R.id.user_password);
        mIvLonginPasswrod = (ImageView) findViewById(R.id.iv_longin_passwrod);
        mIvLonginPasswrod.setOnClickListener(this);
        mForgetPassword = (TextView) findViewById(R.id.forget_password);
        mForgetPassword.setOnClickListener(this);
        mUserRegistration = (TextView) findViewById(R.id.user_registration);
        mUserRegistration.setOnClickListener(this);
        mLogIn = (Button) findViewById(R.id.log_in);
        mLogIn.setOnClickListener(this);

        strAccount = ToastUtils.getUserName();
        if (!strAccount.equals("")){
            mUserName.setText(strAccount);
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_longin_passwrod:
                if (showPassword) {// 显示密码
                    mIvLonginPasswrod.setImageDrawable(getResources().getDrawable(R.mipmap.show_psw));
                    mUserPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mUserPassword.setSelection( mUserPassword.getText().toString().length());
                    showPassword = !showPassword;
                } else {// 隐藏密码
                    mIvLonginPasswrod.setImageDrawable(getResources().getDrawable(R.mipmap.show_psw_press));
                    mUserPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mUserPassword.setSelection( mUserPassword.getText().toString().length());
                    showPassword = !showPassword;
                }
                break;
            case R.id.log_in:
                initDate();
                break;
            case R.id.forget_password:
                startActivity(new Intent(RegisterActivity.this, ForgetPasswordActivity.class));
                break;
            case R.id.user_registration:
                Intent initds = new Intent(this, SignInActivity.class);
                initds.putExtra(SignInActivity.SIGNNAME_DETAILS,"用户注册");
                startActivity(initds);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis() - MainActivity.exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                MainActivity.exitTime = System.currentTimeMillis();
            } else {
                MyActivityManager.getInstance().AppExit(this);
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //保存编辑框数据
    public void initDate() {
        name = mUserName.getText().toString().trim();
        password = mUserPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort("请输入用户名！");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShort("请输入密码！");
            return;
        }

        ToastUtils.setAccountInformation(name, password, true);

        initLogin(name, password);
    }

    //登录服务器
    private void initLogin(String nameer, String passworder) {
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(this)
                .setMessage("登录中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog = loadBuilder.create();
        dialog.show();

        LogUtil.e("数据为", "" + MyApplication.getDaoSession().getUserInfoBeanDao().loadAll().toString());


        UserInfoBean user = MyApplication.getDaoSession().getUserInfoBeanDao().queryBuilder()
                .where(UserInfoBeanDao.Properties.UserLoginName.eq(nameer)).unique();
        if (user != null){
            MyApplication.getAnEntitySet().setUserInfoBean(user);
            if(user.getUserLoginPwd().equals(passworder)){

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    }
                }, 2000);    //延时2s执行


            }else {
                dialog.dismiss();
                ToastUtils.showShort("用户密码错误，请重试！");
            }
        }else {
            dialog.dismiss();
            ToastUtils.showShort("用户不存在，请先注册！");
        }

    }
}
