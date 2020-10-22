package com.example.dczs.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.dczs.MyApplication;
import com.example.dczs.R;
import com.example.dczs.entity.UserInfoBean;
import com.example.dczs.greendao.UserInfoBeanDao;
import com.example.dczs.utils.BottomDialog;
import com.example.dczs.utils.FileUtilser;
import com.example.dczs.utils.LogUtil;
import com.example.dczs.utils.PictureUtil;
import com.example.dczs.utils.ToastUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
//修改个人信息
public class EditInfoActivity extends CheckPermissionsActivity implements View.OnClickListener {
    /**
     * 取消
     */
    private TextView mTxtCance;
    /**
     * 编辑信息
     */
    private TextView mHeadTitle;
    /**
     * 保存
     */
    private TextView mTxtSave;
    private ImageView mImgHeaderSettingBg;
    private RelativeLayout mViewSettingCamere;
    /**
     * 填写姓名
     */
    private EditText mEdtUsername;
    /**
     * 填写号码
     */
    private EditText mEdtPhone;
    /**
     * 男或女
     */
    private LinearLayout mEditSex;
    /**
     * 填写邮箱
     */
    private EditText mEditEmail;
    /**
     * 填写办公号码
     */
    private EditText mEdtedtMberphone;
    /**
     * 选择出生日期
     */
    private EditText mEditDatePicket;
    private LinearLayout mlayImag;

    private TextView mAddress;
    private TextView mGenderly;

    private SimpleDateFormat sdf;
    private SimpleDateFormat sdf1;
    private int yearInt, monthInt, dayInt;

    private DatePickerDialog datePickerDialog;
    private String usersex;

    private BottomDialog bottomDialog;

    private int maxSelectNum = 19;
    private int code =  PictureConfig.CHOOSE_REQUEST;
    private int chooseMode = PictureMimeType.ofImage();
    private List<LocalMedia> selectList = new ArrayList<>();
    private String pathe;
    private String pathes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        initView();
        initDate();

    }

    private void initView() {
        mTxtCance = (TextView) findViewById(R.id.txt_cance);
        mTxtCance.setOnClickListener(this);
        mHeadTitle = (TextView) findViewById(R.id.head_title);
        mHeadTitle.setOnClickListener(this);
        mTxtSave = (TextView) findViewById(R.id.txt_save);
        mTxtSave.setOnClickListener(this);

        mlayImag = (LinearLayout) findViewById(R.id.lay_image);
        mImgHeaderSettingBg = (ImageView) findViewById(R.id.img_header_setting_bg);
        mViewSettingCamere = (RelativeLayout) findViewById(R.id.view_setting_camere);
        mViewSettingCamere.setOnClickListener(this);

        mEdtUsername = (EditText) findViewById(R.id.edt_username);
        mEditSex = (LinearLayout) findViewById(R.id.edit_sex);
        mGenderly = (TextView) findViewById(R.id.genderly);
        mEdtPhone = (EditText) findViewById(R.id.edt_phone);
        mEditSex.setOnClickListener(this);
        mEditEmail = (EditText) findViewById(R.id.edit_email);
        mEdtedtMberphone = (EditText) findViewById(R.id.edt_mberphone);
        mEditDatePicket = (EditText) findViewById(R.id.edit_date_picket);
        mEditDatePicket.setOnClickListener(this);
        mAddress = (TextView) findViewById(R.id.edit_addres);
        mAddress.setOnClickListener(this);

    }

    private void initDate() {
        if(MyApplication.getAnEntitySet().getUserInfoBean().getUserHeadUrl() != null){
            mlayImag.setVisibility(View.GONE);
/*
            Glide.with(this).load(ToastUtils.getStrToBytes(MyApplication.getAnEntitySet().getUserInfoBean().getUserHeadUrl())).into
                    (mImgHeaderSettingBg);
*/
            mImgHeaderSettingBg.setImageBitmap(ToastUtils.stringtoBitmap(MyApplication.getAnEntitySet().getUserInfoBean().getUserHeadUrl()));

        }

        sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        inithhj();
    }

    private void inithhj() {
       try {
           if (MyApplication.getAnEntitySet().getUserInfoBean().getUserSex() != null){
               mGenderly.setText(MyApplication.getAnEntitySet().getUserInfoBean().getUserSex());
               mGenderly.setTextColor(Color.BLACK);
           }

           if (MyApplication.getAnEntitySet().getUserInfoBean().getUserAddress() != null) {
               mAddress.setText(MyApplication.getAnEntitySet().getUserInfoBean().getUserAddress());
               mAddress.setTextColor(getResources().getColor(R.color.black));
           }

           if (MyApplication.getAnEntitySet().getUserInfoBean().getUserTel() != null){
               mEdtPhone.setText(MyApplication.getAnEntitySet().getUserInfoBean().getUserTel());
           }

           if (MyApplication.getAnEntitySet().getUserInfoBean().getUserName() != null){
               mEdtUsername.setText(MyApplication.getAnEntitySet().getUserInfoBean().getUserName());
           }

           if (MyApplication.getAnEntitySet().getUserInfoBean().getUserEmail() != null){
               mEditEmail.setText(MyApplication.getAnEntitySet().getUserInfoBean().getUserEmail());
           }

           if (MyApplication.getAnEntitySet().getUserInfoBean().getUserPhone() != null){
               mEdtedtMberphone.setText(MyApplication.getAnEntitySet().getUserInfoBean().getUserPhone());
           }



           String bir = MyApplication.getAnEntitySet().getUserInfoBean().getUserBrithday();
            Date date;
            if (bir.contains(":")) {
                date = sdf.parse(bir);
            } else {
                date = sdf1.parse(bir);
            }
            // 获取日期实例
            Calendar calendar = Calendar.getInstance();
            // 将日历设置为指定的时间
            calendar.setTime(date);
            // 获取年
            yearInt = calendar.get(Calendar.YEAR);
            // 这里要注意，月份是从0开始。
            monthInt = calendar.get(Calendar.MONTH) + 1;
            // 获取天
            dayInt = calendar.get(Calendar.DAY_OF_MONTH);

            mEditDatePicket.setText("  " + yearInt + "年" + monthInt + "月" + dayInt + "日");
        }catch (Exception e) {
            e.printStackTrace();
            Log.e("空异常报错", e.toString() + "");
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.view_setting_camere:
                showBottomDialog("选择图片类型", "从手机相册选择", "拍照", 2);
                break;
            case R.id.edit_date_picket:
                showDatePicketDialog();
                break;
            case R.id.txt_cance:
                finish();
                break;
            case R.id.txt_save:
                submitUserInfo();
                finish();
                break;
            case R.id.edit_sex:
                showBottomDialog("选择性别", "男", "女", 1);
                break;
            case R.id.edit_addres:
                Intent init = new Intent(this, MapChoosingActivity.class);
                init.putExtra(MapChoosingActivity.ADDRESSONE_DETAILS,"one");
                startActivityForResult(init, MapChoosingActivity.ADDRESS_CODEFVER);
                break;

        }
    }


    //仿IOS弹跳窗
    private void showBottomDialog(String teil, String textone, String texttwo, final int i) {
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
                if (i == 1){
                    usersex = bottomDialog.getOneTv().getText().toString();
                    mGenderly.setText(usersex);
                }else {
                    PictureUtil.Album(EditInfoActivity.this, chooseMode, selectList, code, maxSelectNum, false);
                }
                bottomDialog.dismissDialog();
            }

            @Override
            public void onTwoClick() {
                if (i == 1){
                    usersex = bottomDialog.getTwoTv().getText().toString();
                    mGenderly.setText(usersex);
                }else{
                    PictureUtil.Camera(EditInfoActivity.this, chooseMode, selectList, code, maxSelectNum, false);
                }

                bottomDialog.dismissDialog();
            }
        });
    }

    /**
     * 自带时间选择器
     */
    private void showDatePicketDialog() {
        Calendar c = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mEditDatePicket.setText("  " + year + "年" + (month + 1) + "月" + dayOfMonth + "日");
                yearInt = year;
                monthInt = month + 1;
                dayInt = dayOfMonth;
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        if (!datePickerDialog.isShowing()) {
            datePickerDialog.show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null){
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的

                    for (LocalMedia media : selectList) {
                        LogUtil.e("图片-----》", media.getPath());
                        pathe = media.getPath();
                    }

                    String compress = FileUtilser.compressImageUpload(pathe);
                    pathes = compress;
                    //mImgHeaderSettingBg.setImageBitmap(BitmapFactory.decodeFile(compress));
                    Glide.with(EditInfoActivity.this).load(compress).into
                            (mImgHeaderSettingBg);

                    uploadFile();
                    break;
                case MapChoosingActivity.ADDRESS_CODEFVER:
                    mAddress.setText(MyApplication.getAnEntitySet().getAddressName());
                    mAddress.setTextColor(getResources().getColor(R.color.black));
                    break;
            }
        }

    }

    //上传头像
    private void uploadFile() {
        mlayImag.setVisibility(View.GONE);

        UserInfoBean findUseres = MyApplication.getDaoSession().getUserInfoBeanDao().queryBuilder().where(UserInfoBeanDao.Properties.UserId.eq(MyApplication.getAnEntitySet().getUserInfoBean().getUserId())).build().unique();
        if(findUseres != null) {
            findUseres.setUserHeadUrl(ToastUtils.imageToBase64(pathes));
            MyApplication.getDaoSession().getUserInfoBeanDao().update(findUseres);
            ToastUtils.showShort("修改用户头像成功！");
        }else {
            ToastUtils.showShort("修改用户头像失败！");
        }
    }

    /**
     * 提交用户修改信息
     */
    private void submitUserInfo() {
        String username = mEdtUsername.getText().toString().trim();
        String usertel = mEdtPhone.getText().toString().trim();
        String email = mEditEmail.getText().toString().trim();
        String phone = mEdtedtMberphone.getText().toString().trim();
        String date = yearInt + "-" + monthInt + "-" + dayInt ;
        String useraddress = mAddress.getText().toString().trim();

        UserInfoBean findUser = MyApplication.getDaoSession().getUserInfoBeanDao().queryBuilder().where(UserInfoBeanDao.Properties.UserId.eq(MyApplication.getAnEntitySet().getUserInfoBean().getUserId())).build().unique();
        if(findUser != null) {
            findUser.setUserName(username);
            findUser.setUserTel(usertel);
            findUser.setUserSex(usersex);
            findUser.setUserEmail(email);
            findUser.setUserPhone(phone);
            findUser.setUserAddress(useraddress);
            findUser.setUserBrithday(date);
            MyApplication.getDaoSession().getUserInfoBeanDao().update(findUser);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    ToastUtils.showShort( "修改用户成功!");
                    finish();
                }
            }, 2000);    //延时2s执行

        } else {

            ToastUtils.showShort("修改用户失败！");
        }
    }

    Boolean up = false;//默认false不刷新
    @Override
    public void onPause() {
        super.onPause();
        up = true;//不可见的时候将刷新开启
    }

    @Override
    public void onResume() {
        super.onResume();
        if (up) {
            //（方法）;//向服务器发送请求

            up = false;//刷新一次即可，不需要一直刷新
        }
    }
}
