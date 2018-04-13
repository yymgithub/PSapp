package com.example.psapp.bean;


import java.sql.Timestamp;

/**
 * Created by 永远有多远 on 2018/4/12.
 */
public class PsFault {
    private Integer faultId;
    private String faultName;
    private Integer psId;
    private String phoneId;
    private Integer paultState;
    //是否有效
    private Integer yn;
    //创建时间
    private Timestamp created;

    public Integer getFaultId() {
        return faultId;
    }

    public void setFaultId(Integer faultId) {
        this.faultId = faultId;
    }

    public String getFaultName() {
        return faultName;
    }

    public void setFaultName(String faultName) {
        this.faultName = faultName;
    }

    public Integer getPsId() {
        return psId;
    }

    public void setPsId(Integer psId) {
        this.psId = psId;
    }

    public String getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    public Integer getPaultState() {
        return paultState;
    }

    public void setPaultState(Integer paultState) {
        this.paultState = paultState;
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
}
