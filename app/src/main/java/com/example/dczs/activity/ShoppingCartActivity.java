package com.example.dczs.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.dczs.MyApplication;
import com.example.dczs.R;
import com.example.dczs.adapter.ShoppingCartAdapter;
import com.example.dczs.entity.OrderFormBean;
import com.example.dczs.entity.ShoppingCartBean;
import com.example.dczs.greendao.ShoppingCartBeanDao;
import com.example.dczs.myinterface.OnItemClickLisenterOrder;
import com.example.dczs.utils.LogUtil;
import com.example.dczs.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//查询所有购物车列表
public class ShoppingCartActivity extends CheckPermissionsActivity implements View.OnClickListener {

    private ImageView mHeadBack;
    private TextView mHeadTitle;
    private RecyclerView mRcShoppingCart;
    private SwipeRefreshLayout mSrShoppingCart;
    private ShoppingCartAdapter shoppingapter;
    private List<ShoppingCartBean> shoppingCartBeanList = new ArrayList();


    private BroadcastReceiver receiverTwo = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean is = intent.getBooleanExtra(ToastUtils.isMenuRefreshTwo, false);
            if(is) {
                shoppingCartBeanList.clear();
                initDateTwo();
            }
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiverTwo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        IntentFilter filterTwo = new IntentFilter();
        filterTwo.addAction(ToastUtils.menuRefreshTwo);
        registerReceiver(receiverTwo, filterTwo);

        initView();
        initDate();
    }



    private void initView() {
        mHeadBack = (ImageView) findViewById(R.id.head_back);
        mHeadBack.setOnClickListener(this);
        mHeadTitle = (TextView) findViewById(R.id.head_title);
        mRcShoppingCart = (RecyclerView) findViewById(R.id.rc_shopping_cart);
        mSrShoppingCart = (SwipeRefreshLayout) findViewById(R.id.sr_shopping_cart);

        mHeadTitle.setText("我的购物车");

        mSrShoppingCart.setColorSchemeColors(getResources().getColor(R.color.NavigationBarColor));
        mSrShoppingCart.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                shoppingCartBeanList.clear();
                initDateTwo();
                mSrShoppingCart.setRefreshing(false);

            }
        });
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

    private void initDate() {

        shoppingapter = new ShoppingCartAdapter(this, null);
        mRcShoppingCart.setLayoutManager(new LinearLayoutManager(this));
        mRcShoppingCart.setAdapter(shoppingapter);
        initDateTwo();

        shoppingapter.setOnItemClickLisenter(new OnItemClickLisenterOrder() {
            @Override
            public void onItemClick(int position, int check) {
                if (check == 0){

                    MyApplication.getAnEntitySet().setAddModifyMenuId(shoppingCartBeanList.get(position).getArticleId());
                    startActivity(new Intent(ShoppingCartActivity.this, ShoppingCartDetailsActivity.class));
                }else {
                    if (shoppingCartBeanList != null){
                        if (TextUtils.isEmpty(MyApplication.getAnEntitySet().getUserInfoBean().getUserAddress())){
                            ToastUtils.showShort("您的地址为空，请重新填写");
                            MyApplication.getAnEntitySet().setAddModifyMenuId(shoppingCartBeanList.get(position).getArticleId());
                            startActivity(new Intent(ShoppingCartActivity.this, ShoppingCartDetailsActivity.class));
                        }else {
                            OrderFormBean orderFormBean = new OrderFormBean(null, shoppingCartBeanList.get(position).getArticleName(),
                                    shoppingCartBeanList.get(position).getArticlePrice(), shoppingCartBeanList.get(position).getMerchantSites(),
                                    MyApplication.getAnEntitySet().getUserInfoBean().getUserAddress(), shoppingCartBeanList.get(position).getArticleSynopsis(),
                                    ToastUtils.getTime("yyyy-MM-dd HH-mm-ss"), String.valueOf(MyApplication.getAnEntitySet().getUserInfoBean().getUserId()),
                                    MyApplication.getAnEntitySet().getUserInfoBean().getUserLoginName(), "0", shoppingCartBeanList.get(position).getMenuPictures(),
                                    shoppingCartBeanList.get(position).getLatitude(), shoppingCartBeanList.get(position).getLongitude(), shoppingCartBeanList.get(position).getCity(),
                                    MyApplication.getAnEntitySet().getUserInfoBean().getUserLatitude(),MyApplication.getAnEntitySet().getUserInfoBean().getUserLongitude());
                            MyApplication.getDaoSession().getOrderFormBeanDao().insert(orderFormBean);

                            ToastUtils.showShort("下单成功,请耐心等候!");

                           final Long menuId = shoppingCartBeanList.get(position).getArticleId();

                            Intent intent=new Intent();
                            intent.setAction(ToastUtils.menuRefreshThree);
                            intent.putExtra(ToastUtils.isMenuRefreshThree, true);
                            sendBroadcast(intent);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    ShoppingCartBean shoppingCartBeanTwo = MyApplication.getDaoSession().getShoppingCartBeanDao().queryBuilder()
                                            .where(ShoppingCartBeanDao.Properties.ArticleId.eq(menuId)).unique();
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
                        }

                    }else {
                        ToastUtils.showShort("下单失败,该订单不存在!");
                    }

                }

            }
        });

    }


    private void initDateTwo() {

        List<ShoppingCartBean> shopingcat = MyApplication.getDaoSession().getShoppingCartBeanDao().loadAll();
        for (int i = 0; i < shopingcat.size(); i++){
            if (shopingcat.get(i).getCreateUserId().equals(String.valueOf(MyApplication.getAnEntitySet().getUserInfoBean().getUserId()))){
                shoppingCartBeanList.add(shopingcat.get(i));
            }else {
                LogUtil.e("购物车查询出错",shopingcat.get(i).toString());
            }

        }

        if (shoppingCartBeanList != null){
            Collections.reverse(shoppingCartBeanList);
            shoppingapter.setShoppingCartList(shoppingCartBeanList);
            shoppingapter.notifyDataSetChanged();
        }else {
            ToastUtils.showShort("查询菜单失败!");
        }

    }
}
