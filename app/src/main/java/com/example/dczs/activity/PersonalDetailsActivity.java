package com.example.dczs.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dczs.MyApplication;
import com.example.dczs.R;


//个人信息
public class PersonalDetailsActivity extends CheckPermissionsActivity implements View.OnClickListener {
    private ImageView mHeadBack;
    private TextView mHeadTitle;
    private TextView mTxtEmail;
    private TextView mTxtSex;
    private TextView mTxtBrithday;
    private TextView mTxtPhone;
    private TextView mTxtUsername;
    private TextView mOfficePhone;
    private TextView mResidentialAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
        initView();
        initDate();
        initUser();
    }

    private void initDate() {
    }

    private void initView() {
        mHeadBack = (ImageView) findViewById(R.id.head_back);
        mHeadBack.setOnClickListener(this);
        mHeadTitle = (TextView) findViewById(R.id.head_title);
        mTxtEmail = (TextView) findViewById(R.id.txt_email);
        mTxtSex = (TextView) findViewById(R.id.txt_sex);
        mTxtBrithday = (TextView) findViewById(R.id.txt_brithday);
        mTxtPhone = (TextView) findViewById(R.id.txt_phone);
        mTxtUsername = (TextView) findViewById(R.id.txt_username);
        mOfficePhone = (TextView) findViewById(R.id.office_phone);
        mResidentialAddress = (TextView) findViewById(R.id.residential_address);

        mHeadTitle.setText("个人信息");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.head_back:
                finish();
                break;
        }
    }

    private void initUser() {
        try{
            String Name = MyApplication.getAnEntitySet().getUserInfoBean().getUserName();
            if (Name == null || Name.equals("")){
                mTxtUsername.setText("未设置");
            }else{
                mTxtUsername.setText(Name);
            }
            String Phone = MyApplication.getAnEntitySet().getUserInfoBean().getUserTel();
            if (Phone == null || Phone.equals("")){
                mTxtPhone.setText("未设置");
            }else{
                mTxtPhone.setText(Phone);
            }
            String Email = MyApplication.getAnEntitySet().getUserInfoBean().getUserEmail();
            if (Email == null || Email.equals("")){
                mTxtEmail.setText("未设置");
            }else{
                mTxtEmail.setText(Email);
            }
            String Sex = MyApplication.getAnEntitySet().getUserInfoBean().getUserSex();
            if (Sex == null || Sex.equals("")){
                mTxtSex.setText("未设置");
            }else{
                mTxtSex.setText(Sex);
            }
            String Brithday = MyApplication.getAnEntitySet().getUserInfoBean().getUserBrithday();
            if (Brithday == null || Brithday.equals("")){
                mTxtBrithday.setText("未设置");
            }else{
                mTxtBrithday.setText(Brithday);
            }


            String OfficePhone = MyApplication.getAnEntitySet().getUserInfoBean().getUserPhone();
            if (OfficePhone == null || OfficePhone.equals("")){
                mOfficePhone.setText("未设置");
            }else{
                mOfficePhone.setText(OfficePhone);
            }
            String Address = MyApplication.getAnEntitySet().getUserInfoBean().getUserAddress();
            if (Address == null || Address.equals("")){
                mResidentialAddress.setText("未设置");
            }else{
                mResidentialAddress.setText(Address);
            }

        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
