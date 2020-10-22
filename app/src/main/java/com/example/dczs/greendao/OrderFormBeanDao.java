package com.example.dczs.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.dczs.entity.OrderFormBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ORDER_FORM_BEAN".
*/
public class OrderFormBeanDao extends AbstractDao<OrderFormBean, Long> {

    public static final String TABLENAME = "ORDER_FORM_BEAN";

    /**
     * Properties of entity OrderFormBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property OrderId = new Property(0, Long.class, "orderId", true, "_id");
        public final static Property OrderName = new Property(1, String.class, "orderName", false, "ORDER_NAME");
        public final static Property OrderPrice = new Property(2, String.class, "orderPrice", false, "ORDER_PRICE");
        public final static Property MerchantSites = new Property(3, String.class, "merchantSites", false, "MERCHANT_SITES");
        public final static Property CurrentPosition = new Property(4, String.class, "currentPosition", false, "CURRENT_POSITION");
        public final static Property OrderSynopsis = new Property(5, String.class, "orderSynopsis", false, "ORDER_SYNOPSIS");
        public final static Property CreateTime = new Property(6, String.class, "createTime", false, "CREATE_TIME");
        public final static Property CreateUserId = new Property(7, String.class, "createUserId", false, "CREATE_USER_ID");
        public final static Property CreateUserName = new Property(8, String.class, "createUserName", false, "CREATE_USER_NAME");
        public final static Property OrderStatus = new Property(9, String.class, "orderStatus", false, "ORDER_STATUS");
        public final static Property MenuPictures = new Property(10, String.class, "menuPictures", false, "MENU_PICTURES");
        public final static Property Latitude = new Property(11, Double.class, "latitude", false, "LATITUDE");
        public final static Property Longitude = new Property(12, Double.class, "longitude", false, "LONGITUDE");
        public final static Property City = new Property(13, String.class, "city", false, "CITY");
        public final static Property UserLatitude = new Property(14, Double.class, "userLatitude", false, "USER_LATITUDE");
        public final static Property UserLongitude = new Property(15, Double.class, "userLongitude", false, "USER_LONGITUDE");
    }


    public OrderFormBeanDao(DaoConfig config) {
        super(config);
    }
    
    public OrderFormBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ORDER_FORM_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: orderId
                "\"ORDER_NAME\" TEXT," + // 1: orderName
                "\"ORDER_PRICE\" TEXT," + // 2: orderPrice
                "\"MERCHANT_SITES\" TEXT," + // 3: merchantSites
                "\"CURRENT_POSITION\" TEXT," + // 4: currentPosition
                "\"ORDER_SYNOPSIS\" TEXT," + // 5: orderSynopsis
                "\"CREATE_TIME\" TEXT," + // 6: createTime
                "\"CREATE_USER_ID\" TEXT," + // 7: createUserId
                "\"CREATE_USER_NAME\" TEXT," + // 8: createUserName
                "\"ORDER_STATUS\" TEXT," + // 9: orderStatus
                "\"MENU_PICTURES\" TEXT," + // 10: menuPictures
                "\"LATITUDE\" REAL," + // 11: latitude
                "\"LONGITUDE\" REAL," + // 12: longitude
                "\"CITY\" TEXT," + // 13: city
                "\"USER_LATITUDE\" REAL," + // 14: userLatitude
                "\"USER_LONGITUDE\" REAL);"); // 15: userLongitude
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ORDER_FORM_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, OrderFormBean entity) {
        stmt.clearBindings();
 
        Long orderId = entity.getOrderId();
        if (orderId != null) {
            stmt.bindLong(1, orderId);
        }
 
        String orderName = entity.getOrderName();
        if (orderName != null) {
            stmt.bindString(2, orderName);
        }
 
        String orderPrice = entity.getOrderPrice();
        if (orderPrice != null) {
            stmt.bindString(3, orderPrice);
        }
 
        String merchantSites = entity.getMerchantSites();
        if (merchantSites != null) {
            stmt.bindString(4, merchantSites);
        }
 
        String currentPosition = entity.getCurrentPosition();
        if (currentPosition != null) {
            stmt.bindString(5, currentPosition);
        }
 
        String orderSynopsis = entity.getOrderSynopsis();
        if (orderSynopsis != null) {
            stmt.bindString(6, orderSynopsis);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(7, createTime);
        }
 
        String createUserId = entity.getCreateUserId();
        if (createUserId != null) {
            stmt.bindString(8, createUserId);
        }
 
        String createUserName = entity.getCreateUserName();
        if (createUserName != null) {
            stmt.bindString(9, createUserName);
        }
 
        String orderStatus = entity.getOrderStatus();
        if (orderStatus != null) {
            stmt.bindString(10, orderStatus);
        }
 
        String menuPictures = entity.getMenuPictures();
        if (menuPictures != null) {
            stmt.bindString(11, menuPictures);
        }
 
        Double latitude = entity.getLatitude();
        if (latitude != null) {
            stmt.bindDouble(12, latitude);
        }
 
        Double longitude = entity.getLongitude();
        if (longitude != null) {
            stmt.bindDouble(13, longitude);
        }
 
        String city = entity.getCity();
        if (city != null) {
            stmt.bindString(14, city);
        }
 
        Double userLatitude = entity.getUserLatitude();
        if (userLatitude != null) {
            stmt.bindDouble(15, userLatitude);
        }
 
        Double userLongitude = entity.getUserLongitude();
        if (userLongitude != null) {
            stmt.bindDouble(16, userLongitude);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, OrderFormBean entity) {
        stmt.clearBindings();
 
        Long orderId = entity.getOrderId();
        if (orderId != null) {
            stmt.bindLong(1, orderId);
        }
 
        String orderName = entity.getOrderName();
        if (orderName != null) {
            stmt.bindString(2, orderName);
        }
 
        String orderPrice = entity.getOrderPrice();
        if (orderPrice != null) {
            stmt.bindString(3, orderPrice);
        }
 
        String merchantSites = entity.getMerchantSites();
        if (merchantSites != null) {
            stmt.bindString(4, merchantSites);
        }
 
        String currentPosition = entity.getCurrentPosition();
        if (currentPosition != null) {
            stmt.bindString(5, currentPosition);
        }
 
        String orderSynopsis = entity.getOrderSynopsis();
        if (orderSynopsis != null) {
            stmt.bindString(6, orderSynopsis);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(7, createTime);
        }
 
        String createUserId = entity.getCreateUserId();
        if (createUserId != null) {
            stmt.bindString(8, createUserId);
        }
 
        String createUserName = entity.getCreateUserName();
        if (createUserName != null) {
            stmt.bindString(9, createUserName);
        }
 
        String orderStatus = entity.getOrderStatus();
        if (orderStatus != null) {
            stmt.bindString(10, orderStatus);
        }
 
        String menuPictures = entity.getMenuPictures();
        if (menuPictures != null) {
            stmt.bindString(11, menuPictures);
        }
 
        Double latitude = entity.getLatitude();
        if (latitude != null) {
            stmt.bindDouble(12, latitude);
        }
 
        Double longitude = entity.getLongitude();
        if (longitude != null) {
            stmt.bindDouble(13, longitude);
        }
 
        String city = entity.getCity();
        if (city != null) {
            stmt.bindString(14, city);
        }
 
        Double userLatitude = entity.getUserLatitude();
        if (userLatitude != null) {
            stmt.bindDouble(15, userLatitude);
        }
 
        Double userLongitude = entity.getUserLongitude();
        if (userLongitude != null) {
            stmt.bindDouble(16, userLongitude);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public OrderFormBean readEntity(Cursor cursor, int offset) {
        OrderFormBean entity = new OrderFormBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // orderId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // orderName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // orderPrice
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // merchantSites
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // currentPosition
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // orderSynopsis
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // createTime
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // createUserId
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // createUserName
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // orderStatus
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // menuPictures
            cursor.isNull(offset + 11) ? null : cursor.getDouble(offset + 11), // latitude
            cursor.isNull(offset + 12) ? null : cursor.getDouble(offset + 12), // longitude
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // city
            cursor.isNull(offset + 14) ? null : cursor.getDouble(offset + 14), // userLatitude
            cursor.isNull(offset + 15) ? null : cursor.getDouble(offset + 15) // userLongitude
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, OrderFormBean entity, int offset) {
        entity.setOrderId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setOrderName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setOrderPrice(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMerchantSites(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCurrentPosition(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setOrderSynopsis(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCreateTime(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setCreateUserId(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setCreateUserName(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setOrderStatus(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setMenuPictures(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setLatitude(cursor.isNull(offset + 11) ? null : cursor.getDouble(offset + 11));
        entity.setLongitude(cursor.isNull(offset + 12) ? null : cursor.getDouble(offset + 12));
        entity.setCity(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setUserLatitude(cursor.isNull(offset + 14) ? null : cursor.getDouble(offset + 14));
        entity.setUserLongitude(cursor.isNull(offset + 15) ? null : cursor.getDouble(offset + 15));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(OrderFormBean entity, long rowId) {
        entity.setOrderId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(OrderFormBean entity) {
        if(entity != null) {
            return entity.getOrderId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(OrderFormBean entity) {
        return entity.getOrderId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}