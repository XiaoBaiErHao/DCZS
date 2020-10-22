package com.example.dczs.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ShoppingCartBean {

    @Id(autoincrement = true)
    private Long articleId;
    private String articleName;
    private String articlePrice;
    private String articleSynopsis;
    private String createTime;
    private String createUserId;
    private String createUserName;
    private String merchantSites;
    private String menuPictures;
    private Double latitude;
    private Double longitude;
    private String city;
    
    @Generated(hash = 832196241)
    public ShoppingCartBean(Long articleId, String articleName, String articlePrice,
            String articleSynopsis, String createTime, String createUserId,
            String createUserName, String merchantSites, String menuPictures,
            Double latitude, Double longitude, String city) {
        this.articleId = articleId;
        this.articleName = articleName;
        this.articlePrice = articlePrice;
        this.articleSynopsis = articleSynopsis;
        this.createTime = createTime;
        this.createUserId = createUserId;
        this.createUserName = createUserName;
        this.merchantSites = merchantSites;
        this.menuPictures = menuPictures;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
    }
    @Generated(hash = 1284272626)
    public ShoppingCartBean() {
    }
    public Long getArticleId() {
        return this.articleId;
    }
    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
    public String getArticleName() {
        return this.articleName;
    }
    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }
    public String getArticlePrice() {
        return this.articlePrice;
    }
    public void setArticlePrice(String articlePrice) {
        this.articlePrice = articlePrice;
    }
    public String getArticleSynopsis() {
        return this.articleSynopsis;
    }
    public void setArticleSynopsis(String articleSynopsis) {
        this.articleSynopsis = articleSynopsis;
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
