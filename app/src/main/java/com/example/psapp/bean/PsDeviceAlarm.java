package com.example.psapp.bean;


import java.sql.Timestamp;

/**
 * Created by 永远有多远 on 2018/4/3.
 */

public class PsDeviceAlarm {
    private  Integer alarmId;
    private  Integer psId;
    //是否有效
    private Integer yn;
    //创建时间
    private String created;
    //0-正在报警 2-报警解除
    private Integer deState;

    public Integer getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(Integer alarmId) {
        this.alarmId = alarmId;
    }

    public Integer getPsId() {
        return psId;
    }

    public void setPsId(Integer psId) {
        this.psId = psId;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Integer getDeState() {
        return deState;
    }

    public void setDeState(Integer deState) {
        this.deState = deState;
    }
}
