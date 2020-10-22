package com.example.dczs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
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
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.dczs.MyApplication;
import com.example.dczs.R;
import com.example.dczs.utils.LogUtil;
import com.example.dczs.utils.ToastUtils;


//选取经纬度界面
public class MapChoosingActivity extends CheckPermissionsActivity implements View.OnClickListener, OnGetGeoCoderResultListener {

    private ImageView mHeadBack;
    private TextView mHeadTitle;
    /**
     * 确认
     */
    private TextView mConfirmModification;
    private MapView mBmapViewTwo;
    private ImageView mMarkerImage;
    private TextView mCurrent;
    private ImageView mLocateBtn;

    private GeoCoder mSearch = null;
    private BaiduMap mBaiduMap;
    public static final String ADDRESSONE_DETAILS = "ADDRESSONE_DETAILS";
    private String Addresone = "";
    private String mStrName = "";
    private String mStrCity = "";
    private String mStrDistrict = "";
    private String mStrStreet = "";
    private String mStrAddress = "";
    private double mLatitude;
    private double mLongitude;
    private float lonat = 18.0f;
    private LatLng lat;
    public final static int ADDRESS_CODEFVER = 456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_choosing);
        Addresone =  getIntent().getStringExtra(ADDRESSONE_DETAILS);
        initView();
        initDate();

    }

    private void initView() {
        mHeadBack = (ImageView) findViewById(R.id.head_back);
        mHeadBack.setOnClickListener(this);
        mHeadTitle = (TextView) findViewById(R.id.head_title);
        mConfirmModification = (TextView) findViewById(R.id.confirm_modification);
        mConfirmModification.setOnClickListener(this);
        mBmapViewTwo = (MapView) findViewById(R.id.bmapView_two);
        mMarkerImage = (ImageView) findViewById(R.id.marker_image);
        mCurrent = (TextView) findViewById(R.id.current);
        mLocateBtn = (ImageView) findViewById(R.id.locateBtn);
        mLocateBtn.setOnClickListener(this);

        mHeadTitle.setText("当前坐标位置");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.head_back:
                finish();
                break;
            case R.id.confirm_modification:
                Intent intent = new Intent();
                intent.putExtra(ADDRESSONE_DETAILS,  "addresser");
                setResult(ADDRESS_CODEFVER, intent);
                finish();
                break;
            case R.id.locateBtn:
                initLocate(MyApplication.getAnEntitySet().getAddressMessageBean().getLatitude(),
                        MyApplication.getAnEntitySet().getAddressMessageBean().getLongitude(), lonat);
                break;
        }
    }

    private void initDate() {
        mBaiduMap = mBmapViewTwo.getMap();
        // 初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);

        if (Addresone.equals("one")){
            mStrCity = MyApplication.getAnEntitySet().getAddressMessageBean().getCity();
            mStrDistrict = MyApplication.getAnEntitySet().getAddressMessageBean().getDistrict();
            mStrStreet = MyApplication.getAnEntitySet().getAddressMessageBean().getStreet();
            mLatitude = MyApplication.getAnEntitySet().getAddressMessageBean().getLatitude();
            mLongitude = MyApplication.getAnEntitySet().getAddressMessageBean().getLongitude();
        }else {
            mStrCity = MyApplication.getAnEntitySet().getCity();
            mStrDistrict = MyApplication.getAnEntitySet().getAddressName();
            mLatitude = MyApplication.getAnEntitySet().getLatitude();
            mLongitude = MyApplication.getAnEntitySet().getLongitude();
        }


        if (mStrStreet != null){
            mStrAddress = mStrDistrict + mStrStreet;
        }else {
            mStrAddress = mStrDistrict;
        }

        mSearch.geocode(new GeoCodeOption().city(mStrCity)
                .address(mStrAddress));

        initLocate(mLatitude, mLongitude, lonat);

        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {

            @Override
            public void onMapStatusChangeStart(MapStatus arg0) {

            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus arg0) {
                LatLng ptCenter = mBaiduMap.getMapStatus().target; //获取地图中心点坐标
                // 反Geo搜索
                mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                        .location(ptCenter));
            }

            @Override
            public void onMapStatusChange(MapStatus arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    private void initLocate(double latitude, double longitude, float lonat) {

        MapStatus mMapStatus = new MapStatus.Builder()
                .target(new LatLng(latitude, longitude))
                .zoom(lonat)//设置缩放比例
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.animateMapStatus(mMapStatusUpdate);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mBmapViewTwo.onDestroy();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mBmapViewTwo.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mBmapViewTwo.onPause();
    }


    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {
        //设置地图中心点坐标
        /*MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(result.getLocation());
        mBaiduMap.animateMapStatus(status);*/

        mCurrent.setText(result.getAddress());
    }


    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            ToastUtils.showShort("抱歉，未能找到结果");
            return;
        }
        mBaiduMap.clear();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
                .getLocation()));//改变地图状态？

        String address = result.getAddressDetail().city + result.getAddressDetail().district+ result.getAddressDetail().street +
                result.getAddressDetail().streetNumber;

        mCurrent.setText(address);

        MyApplication.getAnEntitySet().setCity(result.getAddressDetail().city);
        MyApplication.getAnEntitySet().setAddressName(address);
        MyApplication.getAnEntitySet().setLatitude( result.getLocation().latitude);
        MyApplication.getAnEntitySet().setLongitude(result.getLocation().longitude);
    }



}
