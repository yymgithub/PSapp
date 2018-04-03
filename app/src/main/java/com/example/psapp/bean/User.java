package com.example.psapp.bean;


import java.sql.Timestamp;

/**
 * Created by 永远有多远 on 2018/3/13.
 */
public class User {

    //id
    private Integer id;
    //电话号码
    private String phoneId;
    //用户名称
    private String userName;
    //用户角色
    private Integer userRole;
    //密码
    private String password;
    //用户状态
    private Integer userState;
    //是否有效
    private Integer yn;
    //创建时间
    private Timestamp created;
    //修改时间
    private Timestamp modified;

    public User(String phoneId, String userName, Integer userRole, String password) {
        this.phoneId = phoneId;
        this.userName = userName;
        this.userRole = userRole;
        this.password = password;
    }
    public User(){

    };

    public User(String phoneId, String password) {
        this.phoneId = phoneId;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getModified() {
        return modified;
    }

    public void setModified(Timestamp modified) {
        this.modified = modified;
    }
}
