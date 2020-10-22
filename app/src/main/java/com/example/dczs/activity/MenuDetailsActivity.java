package com.example.dczs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dczs.MyApplication;
import com.example.dczs.R;
import com.example.dczs.entity.AddModifyMenuBean;
import com.example.dczs.entity.OrderFormBean;
import com.example.dczs.entity.ShoppingCartBean;
import com.example.dczs.greendao.AddModifyMenuBeanDao;
import com.example.dczs.utils.ToastUtils;
//菜单详情
public class MenuDetailsActivity extends CheckPermissionsActivity implements View.OnClickListener {

    private ImageView mHeadBack;
    private TextView mHeadTitle;
    private ImageView mImgPhotoIntroduction;
    /**
     * 菜单名
     */
    private TextView mTvProductName;
    /**
     * 21
     */
    private TextView mTvCommodityPrices;
    /**
     * 地点
     */
    private TextView mTvMerchantSites;
    /**
     * 鸡蛋
     */
    private TextView mTvPrincipalMaterial;
    /**
     * 加入购物车
     */
    private Button mBtAddShopping;
    /**
     * 立即下单
     */
    private Button mBtOrderImmediately;
    private AddModifyMenuBean addModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_details);
        initView();
        initDate();
    }

    private void initView() {
        mHeadBack = (ImageView) findViewById(R.id.head_back);
        mHeadBack.setOnClickListener(this);
        mHeadTitle = (TextView) findViewById(R.id.head_title);
        mImgPhotoIntroduction = (ImageView) findViewById(R.id.img_photo_introduction);
        mTvProductName = (TextView) findViewById(R.id.tv_product_name);
        mTvCommodityPrices = (TextView) findViewById(R.id.tv_commodity_prices);
        mTvMerchantSites = (TextView) findViewById(R.id.tv_merchant_sites);
        mTvPrincipalMaterial = (TextView) findViewById(R.id.tv_principal_material);
        mBtAddShopping = (Button) findViewById(R.id.bt_add_shopping);
        mBtAddShopping.setOnClickListener(this);
        mBtOrderImmediately = (Button) findViewById(R.id.bt_order_immediately);
        mBtOrderImmediately.setOnClickListener(this);

        mHeadTitle.setText("菜单详情");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.head_back:
                finish();
                break;
            case R.id.bt_add_shopping:
                if (addModify != null){
                    ShoppingCartBean shoppingCartBean = new ShoppingCartBean(null, addModify.getGreensName(),
                            addModify.getGreensPrice(), addModify.getGreensSynopsis(),
                            ToastUtils.getTime("yyyy-MM-dd HH-mm-ss"), String.valueOf(MyApplication.getAnEntitySet().getUserInfoBean().getUserId()),
                            MyApplication.getAnEntitySet().getUserInfoBean().getUserLoginName(), addModify.getMerchantSites(), addModify.getMenuPictures(),
                            addModify.getLatitude(), addModify.getLongitude(), addModify.getCity());
                    MyApplication.getDaoSession().getShoppingCartBeanDao().insert(shoppingCartBean);

                    ToastUtils.showShort("添加成功！");
                }else {
                    ToastUtils.showShort("添加失败！");
                }

                break;
            case R.id.bt_order_immediately:
                if (addModify != null){
                    OrderFormBean orderFormBean = new OrderFormBean(null, addModify.getGreensName(),
                            addModify.getGreensPrice(), addModify.getMerchantSites(),
                            MyApplication.getAnEntitySet().getUserInfoBean().getUserAddress(), addModify.getGreensSynopsis(),
                            ToastUtils.getTime("yyyy-MM-dd HH-mm-ss"), String.valueOf(MyApplication.getAnEntitySet().getUserInfoBean().getUserId()),
                            MyApplication.getAnEntitySet().getUserInfoBean().getUserLoginName(), "0", addModify.getMenuPictures(),
                            addModify.getLatitude(), addModify.getLongitude(), addModify.getCity(), MyApplication.getAnEntitySet().getUserInfoBean().getUserLatitude(),
                            MyApplication.getAnEntitySet().getUserInfoBean().getUserLongitude());
                    MyApplication.getDaoSession().getOrderFormBeanDao().insert(orderFormBean);

                    ToastUtils.showShort("下单成功,请耐心等候!");

                    Intent intent=new Intent();
                    intent.setAction(ToastUtils.menuRefreshThree);
                    intent.putExtra(ToastUtils.isMenuRefreshThree, true);
                    sendBroadcast(intent);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1000);
                }else {
                    ToastUtils.showShort("下单失败,该订单不存在!");
                }

                break;
        }
    }


    private void initDate() {
        addModify = MyApplication.getDaoSession().getAddModifyMenuBeanDao().queryBuilder()
                .where(AddModifyMenuBeanDao.Properties.GreensId.eq(MyApplication.getAnEntitySet().getAddModifyMenuId())).unique();

        if (addModify != null){
            mTvProductName.setText(addModify.getGreensName());
            mTvCommodityPrices.setText(addModify.getGreensPrice());
            mTvMerchantSites.setText(addModify.getMerchantSites());
            mTvPrincipalMaterial.setText(addModify.getGreensSynopsis());
            mImgPhotoIntroduction.setImageBitmap(ToastUtils.stringtoBitmap(addModify.getMenuPictures()));

        }else {
            ToastUtils.showShort("查询菜单失败！");
        }

        if (MyApplication.getAnEntitySet().getUserInfoBean().getUserAddress() == null){
            mBtOrderImmediately.setVisibility(View.GONE);
        }
    }

}
