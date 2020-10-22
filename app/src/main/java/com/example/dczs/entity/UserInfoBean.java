package com.example.dczs.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserInfoBean {

    @Id(autoincrement = true)
    private Long userId;
    private String userLoginName;
    private String userLoginPwd;
    private String userName;
    private String userHeadUrl;
    private String userAddress;
    private String userEmail;
    private String userSex;
    private String userBrithday;
    private String userTel;
    private String userPhone;
    private String userLevel;
    private String createTime;
    private String createUserId;
    private String createUserName;
    private Double userLatitude;
    private Double userLongitude;
    
    @Generated(hash = 796252165)
    public UserInfoBean(Long userId, String userLoginName, String userLoginPwd,
            String userName, String userHeadUrl, String userAddress,
            String userEmail, String userSex, String userBrithday, String userTel,
            String userPhone, String userLevel, String createTime,
            String createUserId, String createUserName, Double userLatitude,
            Double userLongitude) {
        this.userId = userId;
        this.userLoginName = userLoginName;
        this.userLoginPwd = userLoginPwd;
        this.userName = userName;
        this.userHeadUrl = userHeadUrl;
        this.userAddress = userAddress;
        this.userEmail = userEmail;
        this.userSex = userSex;
        this.userBrithday = userBrithday;
        this.userTel = userTel;
        this.userPhone = userPhone;
        this.userLevel = userLevel;
        this.createTime = createTime;
        this.createUserId = createUserId;
        this.createUserName = createUserName;
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
    }
    @Generated(hash = 1818808915)
    public UserInfoBean() {
    }
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUserLoginName() {
        return this.userLoginName;
    }
    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }
    public String getUserLoginPwd() {
        return this.userLoginPwd;
    }
    public void setUserLoginPwd(String userLoginPwd) {
        this.userLoginPwd = userLoginPwd;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserHeadUrl() {
        return this.userHeadUrl;
    }
    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
    }
    public String getUserAddress() {
        return this.userAddress;
    }
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
    public String getUserEmail() {
        return this.userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserSex() {
        return this.userSex;
    }
    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }
    public String getUserBrithday() {
        return this.userBrithday;
    }
    public void setUserBrithday(String userBrithday) {
        this.userBrithday = userBrithday;
    }
    public String getUserTel() {
        return this.userTel;
    }
    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }
    public String getUserPhone() {
        return this.userPhone;
    }
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    public String getUserLevel() {
        return this.userLevel;
    }
    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
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
