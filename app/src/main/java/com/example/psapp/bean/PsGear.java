package com.example.psapp.bean;


import java.sql.Timestamp;

/**
 * Created by 永远有多远 on 2018/4/12.
 */

public class PsGear {
    private Integer gearId;
    private String  gearName;
    private Integer psId;
    private String  phoneId;
    //是否有效
    private Integer yn;
    //创建时间
    private Timestamp created;

    public Integer getGearId() {
        return gearId;
    }

    public void setGearId(Integer gearId) {
        this.gearId = gearId;
    }

    public String getGearName() {
        return gearName;
    }

    public void setGearName(String gearName) {
        this.gearName = gearName;
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
        return  "?psId=" + psId +
                "&gearName=" + gearName +
                "&phoneId=" + phoneId;
    }
}
