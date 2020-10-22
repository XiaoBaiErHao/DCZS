package com.example.dczs.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.dczs.MyApplication;
import com.example.dczs.R;
import com.example.dczs.entity.UserInfoBean;
import com.example.dczs.greendao.UserInfoBeanDao;
import com.example.dczs.utils.DigitCheck;
import com.example.dczs.utils.ToastUtils;

//修改密码
public class AlterUserMiMaActivity extends CheckPermissionsActivity implements View.OnClickListener {

    private ImageView mHeadBack;
    private TextView mHeadTitle;
    /**
     * 旧密码
     */
    private EditText mEtAltermimaYuan;
    /**
     * 新密码(至少八位)
     */
    private EditText mEtAltermimaXin;
    /**
     * 重复新密码
     */
    private EditText mEtAltermimaOkxin;
    /**
     * 提交修改
     */
    private Button mBtnUserOkaltermima;
    private ImageView mIvSwichPasswrodsOne;
    private ImageView mIvSwichPasswrodsTwo;
    private ImageView mIvSwichPasswrodsTher;
    private Boolean showPassword = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_user_mi_ma);
        initView();
    }

    private void initView() {
        mHeadBack = (ImageView) findViewById(R.id.head_back);
        mHeadBack.setOnClickListener(this);
        mHeadTitle = (TextView) findViewById(R.id.head_title);
        mEtAltermimaYuan = (EditText) findViewById(R.id.et_altermima_yuan);
        mEtAltermimaXin = (EditText) findViewById(R.id.et_altermima_xin);
        mEtAltermimaOkxin = (EditText) findViewById(R.id.et_altermima_okxin);
        mBtnUserOkaltermima = (Button) findViewById(R.id.btn_user_okaltermima);
        mBtnUserOkaltermima.setOnClickListener(this);
        mIvSwichPasswrodsOne = (ImageView) findViewById(R.id.iv_swich_passwrodone);
        mIvSwichPasswrodsOne.setOnClickListener(this);
        mIvSwichPasswrodsTwo = (ImageView) findViewById(R.id.iv_swich_passwrodtwo);
        mIvSwichPasswrodsTwo.setOnClickListener(this);
        mIvSwichPasswrodsTher = (ImageView) findViewById(R.id.iv_swich_passwrodthe);
        mIvSwichPasswrodsTher.setOnClickListener(this);

        mHeadTitle.setText("修改密码");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.head_back:
                finish();
                break;
            case R.id.btn_user_okaltermima:
                initAlter();
                break;
            case R.id.iv_swich_passwrodone:
                if (showPassword) {// 显示密码
                    mIvSwichPasswrodsOne.setImageDrawable(getResources().getDrawable(R.mipmap.show_psw));
                    mEtAltermimaYuan.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mEtAltermimaYuan.setSelection(mEtAltermimaYuan.getText().toString().length());
                    showPassword = !showPassword;
                } else {// 隐藏密码
                    mIvSwichPasswrodsOne.setImageDrawable(getResources().getDrawable(R.mipmap.show_psw_press));
                    mEtAltermimaYuan.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mEtAltermimaYuan.setSelection(mEtAltermimaYuan.getText().toString().length());
                    showPassword = !showPassword;
                }
                break;
            case R.id.iv_swich_passwrodtwo:
                if (showPassword) {// 显示密码
                    mIvSwichPasswrodsTwo.setImageDrawable(getResources().getDrawable(R.mipmap.show_psw));
                    mEtAltermimaXin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mEtAltermimaXin.setSelection(mEtAltermimaXin.getText().toString().length());
                    showPassword = !showPassword;
                } else {// 隐藏密码
                    mIvSwichPasswrodsTwo.setImageDrawable(getResources().getDrawable(R.mipmap.show_psw_press));
                    mEtAltermimaXin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mEtAltermimaXin.setSelection(mEtAltermimaXin.getText().toString().length());
                    showPassword = !showPassword;
                }
                break;
            case R.id.iv_swich_passwrodthe:
                if (showPassword) {// 显示密码
                    mIvSwichPasswrodsTher.setImageDrawable(getResources().getDrawable(R.mipmap.show_psw));
                    mEtAltermimaOkxin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mEtAltermimaOkxin.setSelection(mEtAltermimaOkxin.getText().toString().length());
                    showPassword = !showPassword;
                } else {// 隐藏密码
                    mIvSwichPasswrodsTher.setImageDrawable(getResources().getDrawable(R.mipmap.show_psw_press));
                    mEtAltermimaOkxin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mEtAltermimaOkxin.setSelection(mEtAltermimaOkxin.getText().toString().length());
                    showPassword = !showPassword;
                }
                break;
        }
    }

    private void initAlter() {
        String yuanmima = mEtAltermimaYuan.getText().toString().trim();
        String xinmima1 = mEtAltermimaXin.getText().toString().trim();
        String xinmima2 = mEtAltermimaOkxin.getText().toString().trim();

        if (yuanmima != null && !yuanmima.equals("")) {
            if (xinmima1 != null && !xinmima1.equals("")) {
                if (xinmima1.length()<=20 && xinmima1.length()>=6) {
                    if (DigitCheck.isSpecialChar(xinmima1)){
                        if(DigitCheck.isSpecialChar(xinmima1) && !DigitCheck.isLetterDigit(xinmima1)){
                            if (xinmima2 != null && !xinmima2.equals("") ) {
                                if (xinmima1.equals(xinmima2)) {
                                    if (!xinmima2.equals(yuanmima)) {
                                        initDate(yuanmima, xinmima2);
                                    } else {
                                        ToastUtils.showShort("新密码与旧密码相同");
                                    }
                                } else {
                                    ToastUtils.showShort("两次输入的新密码不一样");
                                }
                            } else {
                                ToastUtils.showShort("请输入确认新密码");
                            }
                        }else {
                            ToastUtils.showShort("密码强度过低（请检查密码是否包括数字、字母）");
                        }
                    }else{
                        if(DigitCheck.isLetterDigit(xinmima1)){
                            if (xinmima2 != null && !xinmima2.equals("") ) {
                                if (xinmima1.equals(xinmima2)) {
                                    if (!xinmima2.equals(yuanmima)) {
                                        initDate(yuanmima, xinmima2);
                                    } else {
                                        ToastUtils.showShort("新密码与旧密码相同");
                                    }
                                } else {
                                    ToastUtils.showShort("两次输入的新密码不一样");
                                }
                            } else {
                                ToastUtils.showShort("请输入确认新密码");
                            }
                        }else {
                            ToastUtils.showShort("密码强度过低（请检查密码是否包括数字、字母）");
                        }
                    }

                }else {
                    ToastUtils.showShort("密码长度应大于6小于20");
                }
            }else {
                ToastUtils.showShort("请输入新密码");
            }
        }else {
            ToastUtils.showShort("请输入原密码");
        }

    }

    private void initDate(String yuanmima, final String xinmima2) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("修改密码");
        dialog.setMessage("服务器正在修改密码");
        dialog.show();

        //修改登录密码
        UserInfoBean findUser = MyApplication.getDaoSession().getUserInfoBeanDao().queryBuilder()
                .where(UserInfoBeanDao.Properties.UserId.eq(MyApplication.getAnEntitySet().getUserInfoBean().getUserId())).build().unique();
        if(findUser != null) {
            if (findUser.getUserLoginPwd().equals(yuanmima)){
                findUser.setUserLoginPwd(xinmima2);
                MyApplication.getDaoSession().getUserInfoBeanDao().update(findUser);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShort( "修改密码成功!");
                        dialog.dismiss();

                        ToastUtils.getLogout();
                        Intent intent = new Intent(AlterUserMiMaActivity.this, RegisterActivity.class);
                        startActivity(intent);

                    }
                }, 2000);    //延时2s执行
            }else {
                dialog.dismiss();
                ToastUtils.showShort("你输入的旧密码不对，请重试！");
            }


        } else {
            dialog.dismiss();
            ToastUtils.showShort("修改密码失败！");
        }
    }


}
