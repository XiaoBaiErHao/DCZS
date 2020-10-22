package com.example.dczs.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.dczs.MyApplication;
import com.example.dczs.R;
import com.example.dczs.entity.AddressMessageBean;
import com.example.dczs.fragment.FinishedUnfinishedFragment;
import com.example.dczs.fragment.HomePageFragment;
import com.example.dczs.fragment.PersonalCenterFragment;
import com.example.dczs.utils.MapService;
import com.example.dczs.utils.MyActivityManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends CheckPermissionsActivity {
    public static long exitTime = 0;
    private BottomNavigationViewEx bnve;
    private VpAdapter adapter;
    private List<Fragment> fragments;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initBnve();
        initEvent();
        AddressMessageBean addressMessageBean = new AddressMessageBean();
        MyApplication.getAnEntitySet().setAddressMessageBean(addressMessageBean);
        startService(new Intent(this, MapService.class));
    }


    private void initEvent() {
        bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            int previousPosition = -1;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int positioner = 0;

                switch (item.getItemId()) {
                    default:
                        break;
                    case R.id.menu_main:
                        positioner = 0;
                        break;
                    case R.id.menu_indent:
                        positioner = 1;
                        break;
                    case R.id.menu_me:
                        positioner = 2;
                        break;
                }

                if (previousPosition != positioner) {
                    if (positioner == 3){
                        return false;
                    }else{
                        viewPager.setCurrentItem(positioner, false);
                        previousPosition = positioner;
                    }

                }

                return true;
            }
        });

        viewPager.setCurrentItem(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                bnve.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initView() {
        viewPager = findViewById(R.id.vp);
        bnve = findViewById(R.id.bnve);

    }

    private void initData() {
        fragments = new ArrayList<>();

        HomePageFragment homeFragment = new HomePageFragment();
        FinishedUnfinishedFragment finishedFragment = new FinishedUnfinishedFragment();
        PersonalCenterFragment meFragment = new PersonalCenterFragment();

        fragments.add(homeFragment);
        fragments.add(finishedFragment);
        fragments.add(meFragment);

        viewPager.setOffscreenPageLimit(3);
    }

    private void initBnve() {

        bnve.enableAnimation(false);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);

        adapter = new VpAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);

    }


    private static class VpAdapter extends FragmentPagerAdapter {
        private List<Fragment> data;

        public VpAdapter(FragmentManager fm, List<Fragment> data) {
            super(fm);
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Fragment getItem(int position) {
            return data.get(position);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();

            } else {
                MyActivityManager.getInstance().AppExit(this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
