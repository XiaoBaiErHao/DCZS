package com.example.dczs.entity;

public class AddressMessageBean {
    //经纬度
    private double longitude;
    private double latitude;
    //速度
    private float speed;
    //海拔
    private double elevation;
    //加速度
    private String accelerated_speed;
    //加速方向
    private float acceleratio_direction;
    //时间戳
    private String time;
    //城市
    private String city;
    //区域
    private String district;
    //街道
    private String street;
    //大致地址
    private String address;


    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public String getAccelerated_speed() {
        return accelerated_speed;
    }

    public void setAccelerated_speed(String accelerated_speed) {
        this.accelerated_speed = accelerated_speed;
    }

    public float getAcceleratio_direction() {
        return acceleratio_direction;
    }

    public void setAcceleratio_direction(float acceleratio_direction) {
        this.acceleratio_direction = acceleratio_direction;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



}
