package com.example.dczs.entity;

public class AnEntitySetBean {
    private UserInfoBean userInfoBean;
    private AddressMessageBean addressMessageBean;
    private Long addModifyMenuId;
    private String  addressName;
    private String  city;
    private Double  latitude;
    private Double  longitude;

    public UserInfoBean getUserInfoBean() {
        return userInfoBean;
    }

    public void setUserInfoBean(UserInfoBean userInfoBean) {
        this.userInfoBean = userInfoBean;
    }

    public AddressMessageBean getAddressMessageBean() {
        return addressMessageBean;
    }

    public void setAddressMessageBean(AddressMessageBean addressMessageBean) {
        this.addressMessageBean = addressMessageBean;
    }

    public Long getAddModifyMenuId() {
        return addModifyMenuId;
    }

    public void setAddModifyMenuId(Long addModifyMenuId) {
        this.addModifyMenuId = addModifyMenuId;
    }



    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }



}
