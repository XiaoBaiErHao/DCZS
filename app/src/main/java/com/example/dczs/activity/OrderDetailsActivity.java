package com.example.dczs.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.baidu.mapapi.utils.route.BaiduMapRoutePlan;
import com.baidu.mapapi.utils.route.RouteParaOption;
import com.example.dczs.MyApplication;
import com.example.dczs.R;
import com.example.dczs.entity.OrderFormBean;
import com.example.dczs.greendao.OrderFormBeanDao;
import com.example.dczs.utils.LogUtil;
import com.example.dczs.utils.ToastUtils;
import com.necer.ndialog.ConfirmDialog;

//订单详情
public class OrderDetailsActivity extends CheckPermissionsActivity implements View.OnClickListener {

    private ImageView mHeadBack;
    private TextView mHeadTitle;
    /**
     * 16165
     */
    private TextView mTvNumber;
    /**
     * 16165
     */
    private TextView mTvTime;
    /**
     * 16165
     */
    private TextView mTvName;
    /**
     * 16165
     */
    private TextView mTvPrice;
    /**
     * 16165
     */
    private TextView mTvDosing;
    /**
     * 16165
     */
    private TextView mTvMerchantAddress;
    /**
     * 16165
     */
    private TextView mTvYousAddress;
    /**
     * 16165
     */
    private TextView mTvStatus;
    private MapView mMpvStamation;
    private TextView mTvCurrent;
    private TextView mTvStartNavigation;
    private GeoCoder mSearch = null;
    private BaiduMap mBaiduMap;
    private String address;
    private OrderFormBean orderFormBean;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        initView();
        initDate();
    }


    private void initView() {
        mHeadBack = (ImageView) findViewById(R.id.head_back);
        mHeadBack.setOnClickListener(this);
        mHeadTitle = (TextView) findViewById(R.id.head_title);
        mTvNumber = (TextView) findViewById(R.id.tv_number);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mTvDosing = (TextView) findViewById(R.id.tv_dosing);
        mTvMerchantAddress = (TextView) findViewById(R.id.tv_merchant_address);
        mTvYousAddress = (TextView) findViewById(R.id.tv_yous_address);
        mTvStatus = (TextView) findViewById(R.id.tv_status);
        mMpvStamation = (MapView) findViewById(R.id.mpv_stamation);
        mTvCurrent = (TextView) findViewById(R.id.tv_current);
        mTvStartNavigation = (TextView) findViewById(R.id.tv_start_navigation);
        mTvStartNavigation.setOnClickListener(this);

        mHeadTitle.setText("订单详情");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.head_back:
                finish();
                break;
            case R.id.tv_start_navigation:
                startRoutePlanDriving();
                break;

        }
    }

    private void initDate() {
        orderFormBean = MyApplication.getDaoSession().getOrderFormBeanDao().queryBuilder()
                .where(OrderFormBeanDao.Properties.OrderId.eq(MyApplication.getAnEntitySet().getAddModifyMenuId())).unique();

        if (orderFormBean != null){
            mTvNumber.setText(String.valueOf(orderFormBean.getOrderId()));
            mTvTime.setText(orderFormBean.getCreateTime());
            mTvName.setText(orderFormBean.getOrderName());
            mTvPrice.setText(orderFormBean.getOrderPrice());
            mTvDosing.setText(orderFormBean.getOrderSynopsis());
            mTvMerchantAddress.setText(orderFormBean.getMerchantSites());
            mTvYousAddress.setText(orderFormBean.getCurrentPosition());
            if (orderFormBean.getOrderStatus().equals("0")){
                mTvStatus.setText("正在配菜");
                address = orderFormBean.getMerchantSites();
                latLng = new LatLng(orderFormBean.getLatitude(), orderFormBean.getLongitude());
            }else if (orderFormBean.getOrderStatus().equals("1")){
                mTvStatus.setText("正在派送中");
                if (MyApplication.getAnEntitySet().getUserInfoBean().getUserLevel().equals("2")) {
                    mTvStartNavigation.setVisibility(View.VISIBLE);
                    address = orderFormBean.getCurrentPosition();
                    latLng = new LatLng(orderFormBean.getUserLatitude(), orderFormBean.getUserLongitude());
                }else {
                    address = orderFormBean.getMerchantSites();
                    latLng = new LatLng(orderFormBean.getLatitude(), orderFormBean.getLongitude());
                }

            }else if (orderFormBean.getOrderStatus().equals("2")){
                mTvStatus.setText("派送已完成");
                address = orderFormBean.getCurrentPosition();
                latLng = new LatLng(MyApplication.getAnEntitySet().getAddressMessageBean().getLatitude(), MyApplication.getAnEntitySet().getAddressMessageBean().getLongitude());
            }

            mBaiduMap = mMpvStamation.getMap();
            UiSettings settings = mBaiduMap.getUiSettings();
            settings.setAllGesturesEnabled(false); //关闭一切手势操作

            initMap();
        }else {
            ToastUtils.showShort("查询菜单失败！");
        }
    }

    private void initMap() {
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(latLng)
                .zoom(16.0f)//设置缩放比例
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.animateMapStatus(mMapStatusUpdate);

        mTvCurrent.setText(address);

    }


    /**
     * 启动百度地图驾车路线规划
     */
    public void startRoutePlanDriving() {
        try{
            LatLng ptStart =new LatLng(orderFormBean.getLatitude(), orderFormBean.getLongitude());
            LatLng ptEnd = new LatLng(orderFormBean.getUserLatitude(), orderFormBean.getUserLongitude());

            // 构建 route搜索参数
            RouteParaOption para = new RouteParaOption()
                    .startPoint(ptStart)
                    .startName(orderFormBean.getMerchantSites())
                    .endPoint(ptEnd)
                    .endName(orderFormBean.getCurrentPosition());

            try {
                BaiduMapRoutePlan.openBaiduMapDrivingRoute(para, this);
            } catch (Exception e) {
                e.printStackTrace();
                showDialog();
            }

        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showShort("未获取到目标地址信息");
        }
    }

    /**
     * 提示未安装百度地图app或app版本过低
     */
    public void showDialog() {
        new ConfirmDialog(this, true)
                .setTtitle("温馨提示")
                .setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OpenClientUtil.getLatestBaiduMapApp(OrderDetailsActivity.this);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();

    }
}
