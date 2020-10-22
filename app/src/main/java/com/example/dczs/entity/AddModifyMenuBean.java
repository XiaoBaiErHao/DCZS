package com.example.dczs.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AddModifyMenuBean {

    @Id(autoincrement = true)
    private Long greensId;
    private String greensName;
    private String greensPrice;
    private String greensSynopsis;
    private String createTime;
    private String createUserId;
    private String createUserName;
    private String merchantSites;//商家地址
    private String menuPictures;//菜单图片
    private Double latitude;
    private Double longitude;
    private String city;

    @Generated(hash = 1074088917)
    public AddModifyMenuBean(Long greensId, String greensName, String greensPrice,
            String greensSynopsis, String createTime, String createUserId,
            String createUserName, String merchantSites, String menuPictures,
            Double latitude, Double longitude, String city) {
        this.greensId = greensId;
        this.greensName = greensName;
        this.greensPrice = greensPrice;
        this.greensSynopsis = greensSynopsis;
        this.createTime = createTime;
        this.createUserId = createUserId;
        this.createUserName = createUserName;
        this.merchantSites = merchantSites;
        this.menuPictures = menuPictures;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
    }
    @Generated(hash = 1446773620)
    public AddModifyMenuBean() {
    }
    public Long getGreensId() {
        return this.greensId;
    }
    public void setGreensId(Long greensId) {
        this.greensId = greensId;
    }
    public String getGreensName() {
        return this.greensName;
    }
    public void setGreensName(String greensName) {
        this.greensName = greensName;
    }
    public String getGreensPrice() {
        return this.greensPrice;
    }
    public void setGreensPrice(String greensPrice) {
        this.greensPrice = greensPrice;
    }
    public String getGreensSynopsis() {
        return this.greensSynopsis;
    }
    public void setGreensSynopsis(String greensSynopsis) {
        this.greensSynopsis = greensSynopsis;
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
    public String getMerchantSites() {
        return this.merchantSites;
    }
    public void setMerchantSites(String merchantSites) {
        this.merchantSites = merchantSites;
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
  

}
