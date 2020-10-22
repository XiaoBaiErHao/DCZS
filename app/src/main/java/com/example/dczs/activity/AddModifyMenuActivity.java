package com.example.dczs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dczs.MyApplication;
import com.example.dczs.R;
import com.example.dczs.entity.AddModifyMenuBean;
import com.example.dczs.greendao.AddModifyMenuBeanDao;
import com.example.dczs.utils.FileUtilser;
import com.example.dczs.utils.LogUtil;
import com.example.dczs.utils.PictureUtil;
import com.example.dczs.utils.ToastUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;
//菜单添加与修改
public class AddModifyMenuActivity extends CheckPermissionsActivity implements View.OnClickListener {
    private ImageView mHeadBack;
    private TextView mHeadTitle;
    private TextView mTvDeleteMenu;
    /**
     * 请输入菜名
     */
    private EditText mEdtDishName;
    /**
     * 请输入价格
     */
    private EditText mEdtPrice;
    /**
     * 请输入地点
     */
    private TextView mEdtmerchantsites;
    /**
     * 请输入简介
     */
    private EditText mEdtSynopsis;
    private ImageView mImgMenues;
    private Button mBtSaveDish;

    public static final String ADDMODIFY_DETAILS = "ADDMODIFY_DETAILS";
    private String menuesName;
    private String dishName;
    private String dishPrice;
    private String merchantsites;
    private String dishSynopsis;
    private int maxSelectNum = 19;
    private int code =  PictureConfig.CHOOSE_REQUEST;
    private int chooseMode = PictureMimeType.ofImage();
    private List<LocalMedia> selectList = new ArrayList<>();
    private String pathe;
    private String pathes;
    private AddModifyMenuBean addModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modify_menu);
        menuesName =  getIntent().getStringExtra(ADDMODIFY_DETAILS);
        initView();
        initCheck();
    }

    private void initView() {
        mHeadBack = (ImageView) findViewById(R.id.head_back);
        mHeadBack.setOnClickListener(this);
        mHeadTitle = (TextView) findViewById(R.id.head_title);
        mTvDeleteMenu = (TextView) findViewById(R.id.tv_delete_menu);
        mTvDeleteMenu.setOnClickListener(this);
        mEdtDishName = (EditText) findViewById(R.id.edt_dish_name);
        mEdtPrice = (EditText) findViewById(R.id.edt_price);
        mEdtmerchantsites = (TextView) findViewById(R.id.edt_merchant_sites);
        mEdtmerchantsites.setOnClickListener(this);
        mEdtSynopsis = (EditText) findViewById(R.id.edt_synopsis);
        mImgMenues = (ImageView) findViewById(R.id.img_menues);
        mImgMenues.setOnClickListener(this);
        mBtSaveDish = (Button) findViewById(R.id.bt_save_dish);
        mBtSaveDish.setOnClickListener(this);

        mHeadTitle.setText(menuesName);    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.head_back:
                finish();
                break;
            case R.id.img_menues:
                PictureUtil.Album(this, chooseMode, selectList, code, maxSelectNum, false);
                break;
            case R.id.bt_save_dish:
                initDate();
                break;
            case R.id.tv_delete_menu:
                //移除菜单
                AddModifyMenuBean addModifyes = MyApplication.getDaoSession().getAddModifyMenuBeanDao().queryBuilder()
                        .where(AddModifyMenuBeanDao.Properties.GreensId.eq(MyApplication.getAnEntitySet().getAddModifyMenuId())).unique();
                if (addModifyes != null){
                    MyApplication.getDaoSession().getAddModifyMenuBeanDao().delete(addModifyes);
                    ToastUtils.showShort("删除菜单成功！");

                    Intent intent=new Intent();
                    intent.setAction(ToastUtils.menuRefresh);
                    intent.putExtra(ToastUtils.isMenuRefresh, true);
                    sendBroadcast(intent);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1000);

                }else {
                    ToastUtils.showShort("删除菜单失败！");
                }

                break;
            case R.id.edt_merchant_sites:
                Intent init = new Intent(this, MapChoosingActivity.class);
                if (menuesName.equals("修改菜单")){
                    init.putExtra(MapChoosingActivity.ADDRESSONE_DETAILS,"000");

                    if (addModify != null){
                        if (addModify.getLatitude() != null){
                            MyApplication.getAnEntitySet().setCity(addModify.getCity());
                            MyApplication.getAnEntitySet().setAddressName(addModify.getMerchantSites());
                            MyApplication.getAnEntitySet().setLatitude( addModify.getLatitude());
                            MyApplication.getAnEntitySet().setLongitude(addModify.getLongitude());
                        }else {
                            ToastUtils.showShort("经纬度为空！");
                        }
                    }else {
                        ToastUtils.showShort("菜单为空！");
                    }

                }else {
                    init.putExtra(MapChoosingActivity.ADDRESSONE_DETAILS,"one");
                }
                startActivityForResult(init, MapChoosingActivity.ADDRESS_CODEFVER);
                break;

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
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
                    Glide.with(this).load(compress).into
                            (mImgMenues);

                    break;
                case MapChoosingActivity.ADDRESS_CODEFVER:
                    mEdtmerchantsites.setText(MyApplication.getAnEntitySet().getAddressName());
                    mEdtmerchantsites.setTextColor(getResources().getColor(R.color.black));
                    break;
            }
        }
    }

    private void initCheck() {
        //加载菜单表
        if (menuesName.equals("修改菜单")){
            mTvDeleteMenu.setVisibility(View.VISIBLE);
            mImgMenues.setScaleType(ImageView.ScaleType.CENTER_CROP);
            addModify = MyApplication.getDaoSession().getAddModifyMenuBeanDao().queryBuilder()
                    .where(AddModifyMenuBeanDao.Properties.GreensId.eq(MyApplication.getAnEntitySet().getAddModifyMenuId())).unique();

            if (addModify != null){
                mEdtDishName.setText(addModify.getGreensName());
                mEdtPrice.setText(addModify.getGreensPrice());
                mEdtmerchantsites.setText(addModify.getMerchantSites());
                mEdtmerchantsites.setTextColor(getResources().getColor(R.color.black));
                mEdtSynopsis.setText(addModify.getGreensSynopsis());
                mImgMenues.setImageBitmap(ToastUtils.stringtoBitmap(addModify.getMenuPictures()));
                pathes = addModify.getMenuPictures();
            }else {
                ToastUtils.showShort("查询菜单失败！");
            }
        }else {
            Glide.with(this).load(R.mipmap.ic_addes).into
                    (mImgMenues);
        }
    }

    private void initDate() {
        dishName = mEdtDishName.getText().toString();
        dishPrice = mEdtPrice.getText().toString();
        merchantsites = mEdtmerchantsites.getText().toString();
        dishSynopsis = mEdtSynopsis.getText().toString();

        if (TextUtils.isEmpty(dishName)){
            ToastUtils.showShort("请输入菜名！");
            return;
        }

        if (TextUtils.isEmpty(dishPrice)){
            ToastUtils.showShort("请输入价格！");
            return;
        }

        if (TextUtils.isEmpty(merchantsites)){
            ToastUtils.showShort("请输入商家地点！");
            return;
        }

        if (TextUtils.isEmpty(dishSynopsis)){
            ToastUtils.showShort("请输入主要原料！");
            return;
        }

        if (addModify != null){
            LogUtil.e("选择的是", "修改");
        }else {
            LogUtil.e("选择的是", "添加");
            if (TextUtils.isEmpty(pathes)){
                ToastUtils.showShort("请选择菜单图片！");
                return;
            }
        }


        //更改菜单数据表
        if (menuesName.equals("修改菜单")){
            AddModifyMenuBean addModifyds = MyApplication.getDaoSession().getAddModifyMenuBeanDao().queryBuilder()
                    .where(AddModifyMenuBeanDao.Properties.GreensId.eq(MyApplication.getAnEntitySet().getAddModifyMenuId())).unique();

            if (addModifyds != null){
                addModifyds.setGreensName(dishName);
                addModifyds.setGreensPrice(dishPrice);
                addModifyds.setMerchantSites(merchantsites);
                addModifyds.setGreensSynopsis(dishSynopsis);
                addModifyds.setLatitude(MyApplication.getAnEntitySet().getLatitude());
                addModifyds.setLongitude(MyApplication.getAnEntitySet().getLongitude());
                addModifyds.setCity(MyApplication.getAnEntitySet().getCity());
                if (addModifyds.getMenuPictures() == null){
                    addModifyds.setMenuPictures(ToastUtils.imageToBase64(pathes));
                }else {
                    LogUtil.e("不需要更改","");
                }

                MyApplication.getDaoSession().getAddModifyMenuBeanDao().update(addModifyds);


                ToastUtils.showShort("保存成功！");

                Intent intent=new Intent();
                intent.setAction(ToastUtils.menuRefresh);
                intent.putExtra(ToastUtils.isMenuRefresh, true);
                sendBroadcast(intent);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);

            }else {
                ToastUtils.showShort("该菜单不存在！");
            }
        }else {
            //添加菜单表
            AddModifyMenuBean addModifyes = MyApplication.getDaoSession().getAddModifyMenuBeanDao().queryBuilder()
                    .where(AddModifyMenuBeanDao.Properties.GreensName.eq(dishName)).unique();

            if (addModifyes == null){
                AddModifyMenuBean addModify = new AddModifyMenuBean(null, dishName, dishPrice, dishSynopsis,
                        ToastUtils.getTime("yyyy-MM-dd HH-mm-ss"), ToastUtils.runVerifyCode(10),
                        MyApplication.getAnEntitySet().getUserInfoBean().getUserLoginName(), merchantsites,
                        ToastUtils.imageToBase64(pathes), MyApplication.getAnEntitySet().getLatitude(),
                        MyApplication.getAnEntitySet().getLongitude(),
                        MyApplication.getAnEntitySet().getCity());
                MyApplication.getDaoSession().getAddModifyMenuBeanDao().insert(addModify);

                ToastUtils.showShort("保存成功！");

                Intent intent=new Intent();
                intent.setAction(ToastUtils.menuRefresh);
                intent.putExtra(ToastUtils.isMenuRefresh, true);
                sendBroadcast(intent);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);

            }else {
                ToastUtils.showShort("该菜名已重复，请换一个！");
            }
        }


    }
}
