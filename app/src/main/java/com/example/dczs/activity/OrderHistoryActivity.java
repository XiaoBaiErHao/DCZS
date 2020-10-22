package com.example.dczs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.dczs.MyApplication;
import com.example.dczs.R;
import com.example.dczs.adapter.OrderHistoryAdapter;
import com.example.dczs.entity.OrderFormBean;
import com.example.dczs.myinterface.OnItemClickLisenterOrder;
import com.example.dczs.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//管理员查询所有历史订单
public class OrderHistoryActivity extends CheckPermissionsActivity implements View.OnClickListener {

    private ImageView mHeadBack;
    private TextView mHeadTitle;
    private RecyclerView mRcOrderHistory;
    private SwipeRefreshLayout mSrOrderHistory;
    private OrderHistoryAdapter orderdapter;
    private List<OrderFormBean> orderFormBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        initView();
        initDate();
    }

    private void initView() {
        mHeadBack = (ImageView) findViewById(R.id.head_back);
        mHeadBack.setOnClickListener(this);
        mHeadTitle = (TextView) findViewById(R.id.head_title);
        mRcOrderHistory = (RecyclerView) findViewById(R.id.rc_order_history);
        mSrOrderHistory = (SwipeRefreshLayout) findViewById(R.id.sr_order_history);

        mHeadTitle.setText("历史订单");

        mSrOrderHistory.setColorSchemeColors(getResources().getColor(R.color.NavigationBarColor));
        mSrOrderHistory.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                orderFormBeans.clear();
                initDateTwo();
                mSrOrderHistory.setRefreshing(false);

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
        orderdapter = new OrderHistoryAdapter(this, null);
        mRcOrderHistory.setLayoutManager(new LinearLayoutManager(this));
        mRcOrderHistory.setAdapter(orderdapter);
        initDateTwo();

        orderdapter.setOnItemClickLisenter(new OnItemClickLisenterOrder() {
            @Override
            public void onItemClick(int position, int check) {
                if (check == 0) {
                    MyApplication.getAnEntitySet().setAddModifyMenuId(orderFormBeans.get(position).getOrderId());
                    startActivity(new Intent(OrderHistoryActivity.this, OrderDetailsActivity.class));
                }else {

                }
            }
        });
    }

    private void initDateTwo() {

        List<OrderFormBean> orderFormBeansList = MyApplication.getDaoSession().getOrderFormBeanDao().loadAll();

        for (int i = 0; i < orderFormBeansList.size(); i++){
            orderFormBeans.add(orderFormBeansList.get(i));
        }

        if (orderFormBeans != null){
            Collections.reverse(orderFormBeans);
            orderdapter.setOrderHistoryList(orderFormBeans);
            orderdapter.notifyDataSetChanged();
        }else {
            ToastUtils.showShort("查询订单失败！");
        }
    }
}
