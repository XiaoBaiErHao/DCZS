package com.example.dczs.activity;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dczs.MyApplication;
import com.example.dczs.R;
import com.example.dczs.entity.UserInfoBean;
import com.example.dczs.greendao.UserInfoBeanDao;
import com.example.dczs.utils.BottomDialog;
import com.example.dczs.utils.DigitCheck;
import com.example.dczs.utils.PictureUtil;
import com.example.dczs.utils.ToastUtils;
//注册
public class SignInActivity extends CheckPermissionsActivity implements View.OnClickListener{
    private ImageView mHeadBack;
    private TextView mHeadTitle;
    /**
     * 请输入手机号
     */
    private EditText mUserName;
    /**
     * 8-20位数字和密码组合
     */
    private EditText mEtPassword;
    private ImageView mIvSwichPasswrod;
    /**
     * 确认注册
     */
    private Button mPresent;
    private TextView mTvCreateAdm;
    private LinearLayout mLoutyAndmtyp;
    private TextView mTvAdmiType;
    private Boolean showPassword = true;
    private String AtTheWheel;
    private String EnterPassword;
    public static final String SIGNNAME_DETAILS = "SIGNNAME_DETAILS";
    private String signName;
    private BottomDialog bottomDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signName =  getIntent().getStringExtra(SIGNNAME_DETAILS);
        initView();
    }


    private void initView() {
        mHeadBack = (ImageView) findViewById(R.id.head_back);
        mHeadBack.setOnClickListener(this);
        mHeadTitle = (TextView) findViewById(R.id.head_title);
        mUserName = (EditText) findViewById(R.id.user_name);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mIvSwichPasswrod = (ImageView) findViewById(R.id.iv_swich_passwrod);
        mIvSwichPasswrod.setOnClickListener(this);
        mPresent = (Button) findViewById(R.id.present);
        mPresent.setOnClickListener(this);

        mTvCreateAdm = (TextView) findViewById(R.id.tv_create_adm);
        mTvCreateAdm.setOnClickListener(this);
        mLoutyAndmtyp = (LinearLayout) findViewById(R.id.louty_andmtyp);
        mTvAdmiType = (TextView) findViewById(R.id.tv_admi_type);
        mTvAdmiType.setOnClickListener(this);

        mHeadTitle.setText(signName);

        if (signName.equals("创建管理员")){
            mTvCreateAdm.setVisibility(View.GONE);
            mLoutyAndmtyp.setVisibility(View.VISIBLE);
            mUserName.setHint("请输入管理员名称");
        }else {
            mUserName.setInputType(InputType.TYPE_CLASS_NUMBER);
            mUserName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.head_back:
                finish();
                break;
            case R.id.iv_swich_passwrod:
                if (showPassword) {// 显示密码
                    mIvSwichPasswrod.setImageDrawable(getResources().getDrawable(R.mipmap.show_psw));
                    mEtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mEtPassword.setSelection(mEtPassword.getText().toString().length());
                    showPassword = !showPassword;
                } else {// 隐藏密码
                    mIvSwichPasswrod.setImageDrawable(getResources().getDrawable(R.mipmap.show_psw_press));
                    mEtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mEtPassword.setSelection(mEtPassword.getText().toString().length());
                    showPassword = !showPassword;
                }
                break;
            case R.id.present:
                initpresent();
                break;
            case R.id.tv_create_adm:
                initAdmin("admin", "1234567a");
                break;
            case R.id.tv_admi_type:
                showBottomDialog("管理员类型", "普通管理员", "送餐管理员");
                break;

        }
    }

    private void showBottomDialog(String teil, String textone, String texttwo) {
        bottomDialog = new BottomDialog(this);
        bottomDialog.setTitleText(teil);
        bottomDialog.setOneText(textone);
        bottomDialog.setTwoText(texttwo);
        bottomDialog.setClicklistener(new BottomDialog.ClickListenerInterface() {
            @Override
            public void onTitleClick() {

            }

            @Override
            public void onOneClick() {
                mTvAdmiType.setText(bottomDialog.getOneTv().getText().toString());
                bottomDialog.dismissDialog();
            }

            @Override
            public void onTwoClick() {
                mTvAdmiType.setText(bottomDialog.getTwoTv().getText().toString());

                bottomDialog.dismissDialog();
            }
        });
    }

    private void initpresent() {
        AtTheWheel = mUserName.getText().toString();
        if(signName.equals("用户注册")){
            if (TextUtils.isEmpty(AtTheWheel)) {
                ToastUtils.showShort("手机号码为空！");
                return;
            }

            if(AtTheWheel.length() != 11){
                ToastUtils.showShort("手机号位数不够，请输入正确手机号!");
                return;
            }

            if(!DigitCheck.isMobileNO(AtTheWheel)){
                ToastUtils.showShort("手机号不格式不正确，请输入正确手机号!");
                return;
            }
        }else {
            if (TextUtils.isEmpty(AtTheWheel)) {
                ToastUtils.showShort("管理员名称为空！");
                return;
            }

            if (mTvAdmiType.getText().toString().equals("请选择管理员类型")){
                ToastUtils.showShort("请选择管理员类型！");
                return;
            }

        }

        EnterPassword = mEtPassword.getText().toString();
        if (TextUtils.isEmpty(EnterPassword)) {
            ToastUtils.showShort("密码名为空！");
            return;
        }
        if (!(EnterPassword.length()<=20 && EnterPassword.length()>=6)) {
            ToastUtils.showShort("密码长度应大于6位小于20位");
            return;
        }


        if (DigitCheck.isSpecialChar(EnterPassword)){
            if (!DigitCheck.isLetterDigit(EnterPassword) && !DigitCheck.isSpecialChar(EnterPassword)) {
                ToastUtils.showShort("密码强度过低（密码包括应数字、字母）");
                return;
            }
        }else {
            if (!DigitCheck.isLetterDigit(EnterPassword)) {
                ToastUtils.showShort("密码强度过低（密码包括应数字、字母）");
                return;
            }
        }

        if (signName.equals("用户注册")){
            UserInfoBean user = MyApplication.getDaoSession().getUserInfoBeanDao().queryBuilder()
                    .where(UserInfoBeanDao.Properties.UserLoginName.eq(AtTheWheel)).unique();
            if (user == null ){
                //userLevel默认为0，是普通用户。1是管理员
                UserInfoBean userInfoBean = new UserInfoBean(null, AtTheWheel, EnterPassword, null,
                        null, null, null, null,
                        null, null, null, "0",
                        ToastUtils.getTime("yyyy-MM-dd HH-mm-ss"), ToastUtils.runVerifyCode(10), AtTheWheel,null,null);
                MyApplication.getDaoSession().getUserInfoBeanDao().insert(userInfoBean);

                ToastUtils.showShort("注册成功!");
                finish();
            }else {
                ToastUtils.showShort("该用户已存在，请换一个!");
            }
        }else {
            initAdmin(AtTheWheel, EnterPassword);
        }


    }

    private void initAdmin(String name, String passwd) {
        String type = "";
        if (mTvAdmiType.getText().toString().equals("普通管理员")){
            type = "1";
        } else if (mTvAdmiType.getText().toString().equals("送餐管理员")){
            type = "2";
        }else {
            type = "1";
        }

        UserInfoBean users = MyApplication.getDaoSession().getUserInfoBeanDao().queryBuilder()
                .where(UserInfoBeanDao.Properties.UserLoginName.eq(name)).unique();
        if (users == null){
            //userLevel默认为0，是普通用户。1是管理员
            UserInfoBean userInfosBean = new UserInfoBean(null, name, passwd, null,
                    null, null, null, null,
                    null, null, null, type,
                    ToastUtils.getTime("yyyy-MM-dd HH-mm-ss"), ToastUtils.runVerifyCode(10), name,null,null);
            MyApplication.getDaoSession().getUserInfoBeanDao().insert(userInfosBean);

            ToastUtils.showShort("创建管理员成功!");
            finish();
        }else {
            if (signName.equals("用户注册")){
                ToastUtils.showShort("该管理员已存在，请登录管理员后创建新的管理员！");
            }else {
                ToastUtils.showShort("该管理员已存在，请换一个！");
            }

        }

    }

}
