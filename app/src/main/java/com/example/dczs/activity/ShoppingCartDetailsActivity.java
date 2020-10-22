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

import com.example.dczs.MyApplication;
import com.example.dczs.R;
import com.example.dczs.entity.OrderFormBean;
import com.example.dczs.entity.ShoppingCartBean;
import com.example.dczs.entity.UserInfoBean;
import com.example.dczs.greendao.ShoppingCartBeanDao;
import com.example.dczs.greendao.UserInfoBeanDao;
import com.example.dczs.utils.LogUtil;
import com.example.dczs.utils.ToastUtils;
//购物车详情
public class ShoppingCartDetailsActivity extends CheckPermissionsActivity implements View.OnClickListener {

    private ImageView mHeadBack;
    private TextView mHeadTitle;
    private ImageView mImgPhotoIntroductiones;
    /**
     * 菜单名
     */
    private TextView mTvProductNamees;
    /**
     * 21
     */
    private TextView mTvCommodityPriceses;
    /**
     * 铜仁
     */
    private TextView mTvMerchantSiteses;
    /**
     * 铜仁
     */
    private TextView mTvYourAddresses;
    /**
     * 鸡蛋
     */
    private TextView mTvPrincipalMateriales;
    /**
     * 移除购物车
     */
    private Button mBtDeletShopping;
    /**
     * 立即下单
     */
    private Button mBtOrderImmediatelyes;
    private ShoppingCartBean shoppingCartBean;
    private String address;
    private Double userLatitude;
    private Double userLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart_details);
        initView();
        initDate();
    }


    private void initView() {
        mHeadBack = (ImageView) findViewById(R.id.head_back);
        mHeadBack.setOnClickListener(this);
        mHeadTitle = (TextView) findViewById(R.id.head_title);
        mImgPhotoIntroductiones = (ImageView) findViewById(R.id.img_photo_introductiones);
        mTvProductNamees = (TextView) findViewById(R.id.tv_product_namees);
        mTvCommodityPriceses = (TextView) findViewById(R.id.tv_commodity_priceses);
        mTvMerchantSiteses = (TextView) findViewById(R.id.tv_merchant_siteses);
        mTvYourAddresses = (TextView) findViewById(R.id.tv_your_addresses);
        mTvYourAddresses.setOnClickListener(this);
        mTvPrincipalMateriales = (TextView) findViewById(R.id.tv_principal_materiales);
        mBtDeletShopping = (Button) findViewById(R.id.bt_delet_shopping);
        mBtDeletShopping.setOnClickListener(this);
        mBtOrderImmediatelyes = (Button) findViewById(R.id.bt_order_immediatelyes);
        mBtOrderImmediatelyes.setOnClickListener(this);

        mHeadTitle.setText("购物车详情");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.head_back:
                finish();
                break;
            case R.id.bt_delet_shopping:
                ShoppingCartBean shoppingCartBeanTwo = MyApplication.getDaoSession().getShoppingCartBeanDao().queryBuilder()
                        .where(ShoppingCartBeanDao.Properties.ArticleId.eq(MyApplication.getAnEntitySet().getAddModifyMenuId())).unique();
                if (shoppingCartBeanTwo != null){
                    MyApplication.getDaoSession().getShoppingCartBeanDao().delete(shoppingCartBeanTwo);
                    ToastUtils.showShort("删除成功！");

                    Intent intent=new Intent();
                    intent.setAction(ToastUtils.menuRefreshTwo);
                    intent.putExtra(ToastUtils.isMenuRefreshTwo, true);
                    sendBroadcast(intent);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1000);

                }else {
                    ToastUtils.showShort("删除购物车失败！");
                }
                break;
            case R.id.bt_order_immediatelyes:
                address = mTvYourAddresses.getText().toString();
                if (TextUtils.isEmpty(address)){
                    ToastUtils.showShort("请选择您的地址！");
                    return;
                }else if (address.equals("请选择您的地址")){
                    ToastUtils.showShort("请选择您的地址！");
                    return;
                }


                if (shoppingCartBean != null){
                    if (MyApplication.getAnEntitySet().getUserInfoBean().getUserAddress() != null){
                        userLatitude = MyApplication.getAnEntitySet().getUserInfoBean().getUserLatitude();
                        userLongitude = MyApplication.getAnEntitySet().getUserInfoBean().getUserLongitude();

                    }else {
                        userLatitude = MyApplication.getAnEntitySet().getLatitude();
                        userLongitude = MyApplication.getAnEntitySet().getLongitude();

                        UserInfoBean users = MyApplication.getDaoSession().getUserInfoBeanDao().queryBuilder()
                                .where(UserInfoBeanDao.Properties.UserId.eq(MyApplication.getAnEntitySet().getUserInfoBean().getUserId())).unique();
                        if (users != null){
                            users.setUserLatitude(userLatitude);
                            users.setUserLongitude(userLongitude);
                            users.setUserAddress(MyApplication.getAnEntitySet().getAddressName());
                            MyApplication.getDaoSession().getUserInfoBeanDao().update(users);
                        }

                    }

                    OrderFormBean orderFormBean = new OrderFormBean(null, shoppingCartBean.getArticleName(),
                            shoppingCartBean.getArticlePrice(), shoppingCartBean.getMerchantSites(),
                            address, shoppingCartBean.getArticleSynopsis(),
                            ToastUtils.getTime("yyyy-MM-dd HH-mm-ss"), String.valueOf(MyApplication.getAnEntitySet().getUserInfoBean().getUserId()),
                            MyApplication.getAnEntitySet().getUserInfoBean().getUserLoginName(), "0", shoppingCartBean.getMenuPictures(),
                            shoppingCartBean.getLatitude(), shoppingCartBean.getLongitude(), shoppingCartBean.getCity(),
                            userLatitude, userLongitude);
                    MyApplication.getDaoSession().getOrderFormBeanDao().insert(orderFormBean);


                    ToastUtils.showShort("下单成功,请耐心等候!");

                    Intent intent=new Intent();
                    intent.setAction(ToastUtils.menuRefreshThree);
                    intent.putExtra(ToastUtils.isMenuRefreshThree, true);
                    sendBroadcast(intent);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            ShoppingCartBean shoppingCartBeanTwo = MyApplication.getDaoSession().getShoppingCartBeanDao().queryBuilder()
                                    .where(ShoppingCartBeanDao.Properties.ArticleId.eq(MyApplication.getAnEntitySet().getAddModifyMenuId())).unique();
                            if (shoppingCartBeanTwo != null){
                                MyApplication.getDaoSession().getShoppingCartBeanDao().delete(shoppingCartBeanTwo);

                                Intent intent=new Intent();
                                intent.setAction(ToastUtils.menuRefreshTwo);
                                intent.putExtra(ToastUtils.isMenuRefreshTwo, true);
                                sendBroadcast(intent);

                                finish();

                            }else {
                                ToastUtils.showShort("查询购物车失败！");
                            }


                        }
                    }, 1000);
                }else {
                    ToastUtils.showShort("下单失败,该订单不存在!");
                }

                break;
            case R.id.tv_your_addresses:
                Intent init = new Intent(this, MapChoosingActivity.class);
                init.putExtra(MapChoosingActivity.ADDRESSONE_DETAILS,"one");
                startActivityForResult(init, MapChoosingActivity.ADDRESS_CODEFVER);
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case MapChoosingActivity.ADDRESS_CODEFVER:
                    mTvYourAddresses.setText(MyApplication.getAnEntitySet().getAddressName());
                    mTvYourAddresses.setTextColor(getResources().getColor(R.color.black));
                    break;
            }
        }
    }

    private void initDate() {
        address = MyApplication.getAnEntitySet().getUserInfoBean().getUserAddress();

        shoppingCartBean = MyApplication.getDaoSession().getShoppingCartBeanDao().queryBuilder()
                .where(ShoppingCartBeanDao.Properties.ArticleId.eq(MyApplication.getAnEntitySet().getAddModifyMenuId())).unique();
        if (shoppingCartBean != null){
            mTvProductNamees.setText(shoppingCartBean.getArticleName());
            mTvCommodityPriceses.setText(shoppingCartBean.getArticlePrice());
            mTvMerchantSiteses.setText(shoppingCartBean.getMerchantSites());
            if (address != null){
                mTvYourAddresses.setText(address);
            }

            mTvPrincipalMateriales.setText(shoppingCartBean.getArticleSynopsis());
            mImgPhotoIntroductiones.setImageBitmap(ToastUtils.stringtoBitmap(shoppingCartBean.getMenuPictures()));
        }else {
            ToastUtils.showShort("查询菜单失败!");
        }
    }
}
