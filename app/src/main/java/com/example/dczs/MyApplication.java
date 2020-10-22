package com.example.dczs;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.example.dczs.entity.AnEntitySetBean;
import com.example.dczs.greendao.DaoMaster;
import com.example.dczs.greendao.DaoSession;
import com.example.dczs.greendao.MyOpenHelper;

public class MyApplication extends Application {
    public static Context mContext;
    private MyOpenHelper mHelper;
    private static SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;
    public static final String DB_NAME = "dczs-db";
    public static AnEntitySetBean AnEntitySet;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        setDatabase();
    }

    /**
          * 设置greenDao
          */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        // 此处DB_NAME表示数据库名称 可以任意填写
        mHelper = new MyOpenHelper(this, DB_NAME, null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();



        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }

    public static SQLiteDatabase getDb() {
        return db;
    }

    public static AnEntitySetBean getAnEntitySet() {
        return AnEntitySet;
    }

    public static void setAnEntitySet(AnEntitySetBean AnEntitySets) {
        AnEntitySet = AnEntitySets;
    }

}
