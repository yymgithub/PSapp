package com.example.psapp.bean;

import com.example.psapp.SecondFragment;

import java.sql.Timestamp;

/**
 * Created by 永远有多远 on 2018/4/9.
 */

public class PsDrive {
    private  Integer drId;
    private  Integer psId;
    private  Integer drMode;
    private  double  drLoad;
    private  Integer drRamptime;
    private  Integer drReverse;
    private  Integer drRemotestatus;
    //是否有效
    private Integer yn;
    //创建时间
    private Timestamp created;
    private String phoneId;

    public String getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    public Integer getDrId() {
        return drId;
    }

    public void setDrId(Integer drId) {
        this.drId = drId;
    }

    public Integer getPsId() {
        return psId;
    }

    public void setPsId(Integer psId) {
        this.psId = psId;
    }

    public Integer getDrMode() {
        return drMode;
    }

    public void setDrMode(Integer drMode) {
        this.drMode = drMode;
    }

    public double getDrLoad() {
        return drLoad;
    }

    public void setDrLoad(double drLoad) {
        this.drLoad = drLoad;
    }

    public Integer getDrRamptime() {
        return drRamptime;
    }

    public void setDrRamptime(Integer drRamptime) {
        this.drRamptime = drRamptime;
    }

    public Integer getDrReverse() {
        return drReverse;
    }

    public void setDrReverse(Integer drReverse) {
        this.drReverse = drReverse;
    }

    public Integer getDrRemotestatus() {
        return drRemotestatus;
    }

    public void setDrRemotestatus(Integer drRemotestatus) {
        this.drRemotestatus = drRemotestatus;
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
                "&phoneId=" + phoneId+
                "&drMode=" + drMode +
                "&drLoad=" + drLoad +
                "&drRamptime=" + drRamptime +
                "&drReverse=" + drReverse +
                "&drRemotestatus=" + drRemotestatus;
    }
}
