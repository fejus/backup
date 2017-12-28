package com.adatafun.airportshop.order.pojo.dto;

import java.util.Date;

/**
 * Created by jiangbo on 2017/12/28.
 */
public class UserAccountDTO {
    /**
     * 主键ID
     */
    private Long userId;

    /**
     * 登录账号
     */
    private String loginName;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 英文名
     */
    private String userEnglishName;

    /**
     *  初始密码
     */
    private String initPwd;

    /**
     *  用户帐号
     */
    private String userAccount;

    /**
     * 用户手机号
     */
    private String userMobile;

    /**
     * 用户唯一码
     */
    private String userCode;

    /**
     * 用户性别 1：未定义 2：男 3：女
     */
    private Short userSex;

    /**
     * 用户状态 1：平台用户 2：门店用户
     */
    private Short userType;

    /**
     * 用户状态 1：启用 2：禁用
     */
    private Short userStatus;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 1：未登录 2：已登录
     */
    private Short loginStatus;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 最近登陆时间
     */
    private Date lastLoginTime;

    /**
     * 登录次数
     */
    private Integer loginCount;

    /**
     * 主键ID
     * @return user_id 主键ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 主键ID
     * @param userId 主键ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 登录账号
     * @return login_name 登录账号
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 登录账号
     * @param loginName 登录账号
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 用户名
     * @return user_name 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 用户名
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 英文名
     * @return user_english_name 英文名
     */
    public String getUserEnglishName() {
        return userEnglishName;
    }

    /**
     * 英文名
     * @param userEnglishName 英文名
     */
    public void setUserEnglishName(String userEnglishName) {
        this.userEnglishName = userEnglishName;
    }

    /**
     * 用户手机号
     * @return user_mobile 用户手机号
     */
    public String getUserMobile() {
        return userMobile;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Short getUserType() {
        return userType;
    }

    public void setUserType(Short userType) {
        this.userType = userType;
    }

    /**
     * 用户手机号
     * @param userMobile 用户手机号
     */
    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    /**
     * 用户性别 1：未定义 2：男 3：女
     * @return user_sex 用户性别 1：未定义 2：男 3：女
     */
    public Short getUserSex() {
        return userSex;
    }

    /**
     * 用户性别 1：未定义 2：男 3：女
     * @param userSex 用户性别 1：未定义 2：男 3：女
     */
    public void setUserSex(Short userSex) {
        this.userSex = userSex;
    }

    public Short getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Short userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * 用户邮箱
     * @return user_email 用户邮箱
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * 用户邮箱
     * @param userEmail 用户邮箱
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * 1：未登录 2：已登录
     * @return login_status 1：未登录 2：已登录
     */
    public Short getLoginStatus() {
        return loginStatus;
    }

    /**
     * 1：未登录 2：已登录
     * @param loginStatus 1：未登录 2：已登录
     */
    public void setLoginStatus(Short loginStatus) {
        this.loginStatus = loginStatus;
    }

    /**
     * 登录时间
     * @return login_time 登录时间
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * 登录时间
     * @param loginTime 登录时间
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * 最近登陆时间
     * @return last_login_time 最近登陆时间
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 最近登陆时间
     * @param lastLoginTime 最近登陆时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 登录次数
     * @return login_count 登录次数
     */
    public Integer getLoginCount() {
        return loginCount;
    }

    /**
     * 登录次数
     * @param loginCount 登录次数
     */
    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public String getInitPwd() {
        return initPwd;
    }

    public void setInitPwd(String initPwd) {
        this.initPwd = initPwd;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
}
