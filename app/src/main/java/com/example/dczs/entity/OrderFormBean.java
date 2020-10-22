package com.example.dczs.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class OrderFormBean {
    @Id(autoincrement = true)
    private Long orderId;
    private String orderName;
    private String orderPrice;
    private String merchantSites;
    private String currentPosition;
    private String orderSynopsis;
    private String createTime;
    private String createUserId;
    private String createUserName;
    private String orderStatus;
    private String menuPictures;
    private Double latitude;
    private Double longitude;
    private String city;
    private Double userLatitude;
    private Double userLongitude;

    @Generated(hash = 773946400)
    public OrderFormBean(Long orderId, String orderName, String orderPrice,
            String merchantSites, String currentPosition, String orderSynopsis,
            String createTime, String createUserId, String createUserName,
            String orderStatus, String menuPictures, Double latitude,
            Double longitude, String city, Double userLatitude,
            Double userLongitude) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.orderPrice = orderPrice;
        this.merchantSites = merchantSites;
        this.currentPosition = currentPosition;
        this.orderSynopsis = orderSynopsis;
        this.createTime = createTime;
        this.createUserId = createUserId;
        this.createUserName = createUserName;
        this.orderStatus = orderStatus;
        this.menuPictures = menuPictures;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
    }
    @Generated(hash = 798023569)
    public OrderFormBean() {
    }
    public Long getOrderId() {
        return this.orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public String getOrderName() {
        return this.orderName;
    }
    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
    public String getOrderPrice() {
        return this.orderPrice;
    }
    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }
    public String getMerchantSites() {
        return this.merchantSites;
    }
    public void setMerchantSites(String merchantSites) {
        this.merchantSites = merchantSites;
    }
    public String getCurrentPosition() {
        return this.currentPosition;
    }
    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }
    public String getOrderSynopsis() {
        return this.orderSynopsis;
    }
    public void setOrderSynopsis(String orderSynopsis) {
        this.orderSynopsis = orderSynopsis;
    }
    public String getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getCreateUserId() {
        return this.createUserId;
    }
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }
    public String getCreateUserName() {
        return this.createUserName;
    }
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
    public String getOrderStatus() {
        return this.orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    public String getMenuPictures() {
        return this.menuPictures;
    }
    public void setMenuPictures(String menuPictures) {
        this.menuPictures = menuPictures;
    }
    public Double getLatitude() {
        return this.latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return this.longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public Double getUserLatitude() {
        return this.userLatitude;
    }
    public void setUserLatitude(Double userLatitude) {
        this.userLatitude = userLatitude;
    }
    public Double getUserLongitude() {
        return this.userLongitude;
    }
    public void setUserLongitude(Double userLongitude) {
        this.userLongitude = userLongitude;
    }


}
