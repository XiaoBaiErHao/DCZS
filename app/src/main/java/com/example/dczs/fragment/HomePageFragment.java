package com.example.dczs.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.dczs.MyApplication;
import com.example.dczs.R;
import com.example.dczs.activity.AddModifyMenuActivity;
import com.example.dczs.activity.MenuDetailsActivity;
import com.example.dczs.activity.ShoppingCartActivity;
import com.example.dczs.adapter.HomePageAdapter;
import com.example.dczs.entity.AddModifyMenuBean;
import com.example.dczs.entity.ShoppingCartBean;
import com.example.dczs.greendao.AddModifyMenuBeanDao;
import com.example.dczs.myinterface.OnItemClickLisenterOrder;
import com.example.dczs.utils.LogUtil;
import com.example.dczs.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends Fragment {
    private TextView mHeadTitle;
    private ImageView mImgShoppingCart;
    private SwipeRefreshLayout mSrHomePage;
    private RecyclerView mRcHomePage;
    private HomePageAdapter homedapter;
    private List<AddModifyMenuBean> addModifyMenuBeanLists = new ArrayList();

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean is = intent.getBooleanExtra(ToastUtils.isMenuRefresh, false);
            if(is) {
                addModifyMenuBeanLists.clear();
                initDateTwo();
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ToastUtils.menuRefresh);
        getContext().registerReceiver(receiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(receiver);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_page_fragment, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDate();
    }


    private void initView(View view) {
        mHeadTitle = (TextView) view.findViewById(R.id.head_title);
        mImgShoppingCart = (ImageView) view.findViewById(R.id.img_shopping_cart);
        mSrHomePage = (SwipeRefreshLayout) view.findViewById(R.id.sr_home_page);
        mRcHomePage = (RecyclerView) view.findViewById(R.id.rc_home_page);
        mHeadTitle.setText("菜单");

        //下拉刷新
        mSrHomePage.setColorSchemeColors(getResources().getColor(R.color.NavigationBarColor));
        mSrHomePage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                addModifyMenuBeanLists.clear();
                initDateTwo();
                mSrHomePage.setRefreshing(false);

            }
        });
    }



    private void initDate() {

        mImgShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {startActivity(new Intent(getContext(), ShoppingCartActivity.class));
            }
        });

        homedapter = new HomePageAdapter(getContext(), null);
        mRcHomePage.setLayoutManager(new LinearLayoutManager(getContext()));
        mRcHomePage.setAdapter(homedapter);
        initDateTwo();

        homedapter.setOnItemClickLisenter(new OnItemClickLisenterOrder() {
            @Override
            public void onItemClick(int position, int check) {
                if (check == 0){
                    //判断登录用户类型
                    if (MyApplication.getAnEntitySet().getUserInfoBean().getUserLevel().equals("1")){
                        MyApplication.getAnEntitySet().setAddModifyMenuId(MyApplication.getDaoSession().getAddModifyMenuBeanDao().loadAll().get(position).getGreensId());
                        Intent initd = new Intent(getContext(), AddModifyMenuActivity.class);
                        initd.putExtra(AddModifyMenuActivity.ADDMODIFY_DETAILS,"修改菜单");
                        startActivity(initd);
                    }else {
                        MyApplication.getAnEntitySet().setAddModifyMenuId(MyApplication.getDaoSession().getAddModifyMenuBeanDao().loadAll().get(position).getGreensId());
                        startActivity(new Intent(getContext(), MenuDetailsActivity.class));
                    }
                }else {
                    //购物车表，添加订单
                    List<AddModifyMenuBean> addModifyMenuBeanList = MyApplication.getDaoSession().getAddModifyMenuBeanDao().loadAll();
                    ShoppingCartBean shoppingCartBean = new ShoppingCartBean(null, addModifyMenuBeanList.get(position).getGreensName(),
                            addModifyMenuBeanList.get(position).getGreensPrice(),
                            addModifyMenuBeanList.get(position).getGreensSynopsis(),
                            ToastUtils.getTime("yyyy-MM-dd HH-mm-ss"), String.valueOf(MyApplication.getAnEntitySet().getUserInfoBean().getUserId()),
                            MyApplication.getAnEntitySet().getUserInfoBean().getUserLoginName(),
                            addModifyMenuBeanList.get(position).getMerchantSites(),
                            addModifyMenuBeanList.get(position).getMenuPictures(),
                            addModifyMenuBeanList.get(position).getLatitude(),
                            addModifyMenuBeanList.get(position).getLongitude(),
                            addModifyMenuBeanList.get(position).getCity());

                    MyApplication.getDaoSession().getShoppingCartBeanDao().insert(shoppingCartBean);

                    ToastUtils.showShort("添加成功！");
                }
            }
        });

    }

    private void initDateTwo() {
        //查询所有菜单
        for (int i = 0; i < MyApplication.getDaoSession().getAddModifyMenuBeanDao().loadAll().size(); i++){
            addModifyMenuBeanLists.add(MyApplication.getDaoSession().getAddModifyMenuBeanDao().loadAll().get(i));
        }

        if (addModifyMenuBeanLists != null){
            homedapter.setAddModifyMenuList(addModifyMenuBeanLists);
            homedapter.notifyDataSetChanged();
        }else {
            ToastUtils.showShort("查询菜单失败！");
        }

    }
}
