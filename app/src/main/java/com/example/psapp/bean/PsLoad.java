package com.example.psapp.bean;

import java.sql.Timestamp;

/**
 * Created by 永远有多远 on 2018/4/11.
 */

public class PsLoad {
    private Integer loId;
    private Integer psId;
    private Integer loMode;
    private Integer loRamptime;
    private double lo1Speed;
    private Integer lo1Reverse;
    private Integer lo1Remote;
    private double lo2Speed;
    private Integer lo2Reverse;
    private Integer lo2Remote;
    private String phoneId;
    //是否有效
    private Integer yn;
    //创建时间
    private Timestamp created;

    public Integer getLoId() {
        return loId;
    }

    public void setLoId(Integer loId) {
        this.loId = loId;
    }

    public Integer getPsId() {
        return psId;
    }

    public void setPsId(Integer psId) {
        this.psId = psId;
    }

    public Integer getLoMode() {
        return loMode;
    }

    public void setLoMode(Integer loMode) {
        this.loMode = loMode;
    }
    public Integer getLoRamptime() {
        return loRamptime;
    }

    public void setLoRamptime(Integer loRamptime) {
        this.loRamptime = loRamptime;
    }

    public double getLo1Speed() {
        return lo1Speed;
    }

    public void setLo1Speed(double lo1Speed) {
        this.lo1Speed = lo1Speed;
    }

    public Integer getLo1Reverse() {
        return lo1Reverse;
    }

    public void setLo1Reverse(Integer lo1Reverse) {
        this.lo1Reverse = lo1Reverse;
    }

    public Integer getLo1Remote() {
        return lo1Remote;
    }

    public void setLo1Remote(Integer lo1Remote) {
        this.lo1Remote = lo1Remote;
    }

    public double getLo2Speed() {
        return lo2Speed;
    }

    public void setLo2Speed(double lo2Speed) {
        this.lo2Speed = lo2Speed;
    }

    public Integer getLo2Reverse() {
        return lo2Reverse;
    }

    public void setLo2Reverse(Integer lo2Reverse) {
        this.lo2Reverse = lo2Reverse;
    }

    public Integer getLo2Remote() {
        return lo2Remote;
    }

    public void setLo2Remote(Integer lo2Remote) {
        this.lo2Remote = lo2Remote;
    }

    public String getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
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

    @Override
    public String toString() {
        return "?psId=" + psId +
                "&phoneId='" + phoneId +
                "&loMode=" + loMode +
                "&loRamptime=" + loRamptime +
                "&lo1Speed=" + lo1Speed +
                "&lo1Reverse=" + lo1Reverse +
                "&lo1Remote=" + lo1Remote +
                "&lo2Speed=" + lo2Speed +
                "&lo2Reverse=" + lo2Reverse +
                "&lo2Remote=" + lo2Remote +
                "&yn=" + yn +
                "&created=" + created;
    }
}
