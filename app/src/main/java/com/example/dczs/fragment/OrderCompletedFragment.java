package com.example.dczs.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dczs.MyApplication;
import com.example.dczs.R;
import com.example.dczs.activity.OrderDetailsActivity;
import com.example.dczs.adapter.OrderCompletedAdapter;
import com.example.dczs.entity.OrderFormBean;
import com.example.dczs.greendao.OrderFormBeanDao;
import com.example.dczs.myinterface.OnItemClickLisenterOrder;
import com.example.dczs.utils.LogUtil;
import com.example.dczs.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderCompletedFragment extends Fragment {
    private SwipeRefreshLayout mSrOrderCompleted;
    private RecyclerView mRcOrderCompleted;
    private OrderCompletedAdapter orderdapter;
    private List<OrderFormBean> orderFormBeans = new ArrayList<>();

    private BroadcastReceiver receiverFour = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean is = intent.getBooleanExtra(ToastUtils.isMenuRefreshFour, false);
            if(is) {
                orderFormBeans.clear();
                initDateTwo();
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filterFour = new IntentFilter();
        filterFour.addAction(ToastUtils.menuRefreshFour);
        getContext().registerReceiver(receiverFour, filterFour);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(receiverFour);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.order_completed_fragment, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDate();
    }

    private void initView(View view) {
        mSrOrderCompleted = (SwipeRefreshLayout) view.findViewById(R.id.sr_order_completed);
        mRcOrderCompleted = (RecyclerView) view.findViewById(R.id.rc_order_completed);

        mSrOrderCompleted.setColorSchemeColors(getResources().getColor(R.color.NavigationBarColor));
        mSrOrderCompleted.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                orderFormBeans.clear();
                initDateTwo();
                mSrOrderCompleted.setRefreshing(false);

            }
        });
    }

    private void initDate() {
        orderdapter = new OrderCompletedAdapter(getContext(), null);
        mRcOrderCompleted.setLayoutManager(new LinearLayoutManager(getContext()));
        mRcOrderCompleted.setAdapter(orderdapter);
        initDateTwo();

        orderdapter.setOnItemClickLisenter(new OnItemClickLisenterOrder() {
            @Override
            public void onItemClick(int position, int check) {
                if (check == 0) {
                    MyApplication.getAnEntitySet().setAddModifyMenuId(orderFormBeans.get(position).getOrderId());
                    startActivity(new Intent(getContext(), OrderDetailsActivity.class));
                }else {

                }
            }
        });
    }

    private void initDateTwo() {
        orderFormBeans.clear();
        List<OrderFormBean> orderFormBeansList = MyApplication.getDaoSession().getOrderFormBeanDao().loadAll();

        for (int i = 0; i < orderFormBeansList.size(); i++){
            //判断用户类型，并只加载已完成订单
            if (MyApplication.getAnEntitySet().getUserInfoBean().getUserLevel().equals("0")){
                if (orderFormBeansList.get(i).getCreateUserId().equals(String.valueOf(MyApplication.getAnEntitySet().getUserInfoBean().getUserId()))){
                    if (orderFormBeansList.get(i).getOrderStatus().equals("2")){
                        orderFormBeans.add(orderFormBeansList.get(i));
                    }else{
                        LogUtil.e("该单不是用户的", String.valueOf(orderFormBeansList.get(i).getOrderId()));
                    }

                }else {
                    LogUtil.e("该单不属于业务范围", String.valueOf(orderFormBeansList.get(i).getOrderId()));
                }
            }else {
                if (orderFormBeansList.get(i).getOrderStatus().equals("2")){
                    orderFormBeans.add(orderFormBeansList.get(i));
                }else {
                    LogUtil.e("该单不是管理员的", String.valueOf(orderFormBeansList.get(i).getOrderId()));
                }

            }
        }

        if (orderFormBeans != null){
            Collections.reverse(orderFormBeans);
            orderdapter.setOrderCompletedList(orderFormBeans);
            orderdapter.notifyDataSetChanged();
        }else {
            ToastUtils.showShort("查询订单失败！");
        }
    }
}
