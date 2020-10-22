package com.example.dczs.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.dczs.MyApplication;
import com.example.dczs.R;
import com.example.dczs.activity.OrderHistoryActivity;
import com.example.dczs.adapter.TabFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FinishedUnfinishedFragment extends Fragment {
    private TextView mHeadTitle;
    private TextView mTvOrderHistory;
    private TabFragmentPagerAdapter mAdapter;
    private TabLayout mTxlTabLayout1;
    private ViewPager mTxlViewpager1;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> stringList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.finished_unfinished_fragment, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDate();
    }

    private void initView(View rootView) {
        mHeadTitle = (TextView) rootView.findViewById(R.id.head_title);
        mTvOrderHistory = (TextView) rootView.findViewById(R.id.tv_order_history);
        mTxlTabLayout1 = (TabLayout) rootView.findViewById(R.id.txl_tab_layout);
        mTxlViewpager1 = (ViewPager) rootView.findViewById(R.id.txl_viewpager);

        mHeadTitle.setText("订单");
    }

    private void initDate() {
        OrderFormFragment formFragment = new OrderFormFragment();
        OrderProcessingFragment processingFragment = new OrderProcessingFragment();
        OrderCompletedFragment completedFragment = new OrderCompletedFragment();

        fragmentList.add(formFragment);
        fragmentList.add(processingFragment);
        fragmentList.add(completedFragment);

        stringList.add("未完成");
        stringList.add("处理中");
        stringList.add("已完成");

        FragmentManager childFragmentManager = getActivity().getSupportFragmentManager();
        mAdapter = new TabFragmentPagerAdapter(childFragmentManager, stringList, fragmentList);
        mTxlViewpager1.setAdapter(mAdapter);
        mTxlTabLayout1.setupWithViewPager(mTxlViewpager1);

        if (MyApplication.getAnEntitySet().getUserInfoBean().getUserLevel().equals("1")){
            mTvOrderHistory.setVisibility(View.VISIBLE);
            mTvOrderHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getContext(), OrderHistoryActivity.class));
                }
            });
        }
    }

}
