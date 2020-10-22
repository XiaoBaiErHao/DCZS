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
import com.example.dczs.adapter.OrderFormAdapter;
import com.example.dczs.entity.OrderFormBean;
import com.example.dczs.greendao.OrderFormBeanDao;
import com.example.dczs.myinterface.OnItemClickLisenterOrder;
import com.example.dczs.utils.LogUtil;
import com.example.dczs.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderFormFragment extends Fragment {
    private SwipeRefreshLayout mSrOrderForm;
    private RecyclerView mRcOrderForm;
    private OrderFormAdapter orderdapter;
    private List<OrderFormBean> orderFormBeansly = new ArrayList<>();

    private BroadcastReceiver receiverThree = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean is = intent.getBooleanExtra(ToastUtils.isMenuRefreshThree, false);
            if(is) {
                orderFormBeansly.clear();
                initDateTwo();
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filterThree = new IntentFilter();
        filterThree.addAction(ToastUtils.menuRefreshThree);
        getContext().registerReceiver(receiverThree, filterThree);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(receiverThree);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.order_form_fragment, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDate();

    }


    private void initView(View view) {
        mSrOrderForm = (SwipeRefreshLayout) view.findViewById(R.id.sr_order_form);
        mRcOrderForm = (RecyclerView) view.findViewById(R.id.rc_order_form);

        mSrOrderForm.setColorSchemeColors(getResources().getColor(R.color.NavigationBarColor));
        mSrOrderForm.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                orderFormBeansly.clear();
                initDateTwo();
                mSrOrderForm.setRefreshing(false);

            }
        });
    }

    private void initDate() {
        orderdapter = new OrderFormAdapter(getContext(), null);
        mRcOrderForm.setLayoutManager(new LinearLayoutManager(getContext()));
        mRcOrderForm.setAdapter(orderdapter);
        initDateTwo();

        orderdapter.setOnItemClickLisenter(new OnItemClickLisenterOrder() {
            @Override
            public void onItemClick(int position, int check) {
                if (check == 0) {
                    MyApplication.getAnEntitySet().setAddModifyMenuId(orderFormBeansly.get(position).getOrderId());
                    startActivity(new Intent(getContext(), OrderDetailsActivity.class));
                }else {
                    //如果后厨把菜做好就点击更新表
                    OrderFormBean orderFormBean = MyApplication.getDaoSession().getOrderFormBeanDao().queryBuilder()
                            .where(OrderFormBeanDao.Properties.OrderId.eq(orderFormBeansly.get(position).getOrderId())).unique();
                    if (orderFormBean != null){
                        orderFormBean.setOrderStatus("1");
                        MyApplication.getDaoSession().getOrderFormBeanDao().update(orderFormBean);
                        ToastUtils.showShort("该菜已做好！");

                        Intent intent=new Intent();
                        intent.setAction(ToastUtils.menuRefreshFive);
                        intent.putExtra(ToastUtils.isMenuRefreshFive, true);
                        getActivity().sendBroadcast(intent);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                orderFormBeansly.clear();
                                initDateTwo();
                            }
                        }, 500);

                    }else {
                        ToastUtils.showShort("查询这条订单失败！");
                    }
                }
            }
        });
    }

    private void initDateTwo() {
        orderFormBeansly.clear();

        List<OrderFormBean> orderFormBeansList = MyApplication.getDaoSession().getOrderFormBeanDao().loadAll();

        for (int i = 0; i < orderFormBeansList.size(); i++){
            //判断用户类型，并只加载未完成订单
            if (MyApplication.getAnEntitySet().getUserInfoBean().getUserLevel().equals("0")){
                if (orderFormBeansList.get(i).getCreateUserId().equals(String.valueOf(MyApplication.getAnEntitySet().getUserInfoBean().getUserId()))){
                    if (orderFormBeansList.get(i).getOrderStatus().equals("0")){
                        orderFormBeansly.add(orderFormBeansList.get(i));
                    }else{
                        LogUtil.e("该单不是用户的", String.valueOf(orderFormBeansList.get(i).getOrderId()));
                    }

                }else {
                    LogUtil.e("该单不属于业务范围", String.valueOf(orderFormBeansList.get(i).getOrderId()));
                }
            }else {
                if (orderFormBeansList.get(i).getOrderStatus().equals("0")){
                    orderFormBeansly.add(orderFormBeansList.get(i));
                }else {
                    LogUtil.e("该单不是管理员的", String.valueOf(orderFormBeansList.get(i).getOrderId()));
                }

            }
        }

        if (orderFormBeansly != null){
            orderdapter.setOrderFormList(orderFormBeansly);
            orderdapter.notifyDataSetChanged();
        }else {
            ToastUtils.showShort("查询订单失败！");
        }


    }
}
