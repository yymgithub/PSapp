package com.example.psapp.bean;

import java.sql.Timestamp;

/**
 * Created by 永远有多远 on 2018/3/30.
 */
public class PsBench {
    private Integer psId;
    private String  psName;
    //是否有效
    private Integer yn;

    public Integer getPsStop() {
        return psStop;
    }

    public void setPsStop(Integer psStop) {
        this.psStop = psStop;
    }

    public Integer getPsAlarm() {
        return psAlarm;
    }

    public void setPsAlarm(Integer psAlarm) {
        this.psAlarm = psAlarm;
    }

    /*//创建时间
        private Timestamp created;
        //修改时间

        private Timestamp modified;*/
    private Integer psStop;
    private Integer psAlarm;

    public PsBench(){}

    public Integer getPsId() {
        return psId;
    }

    public void setPsId(Integer psId) {
        this.psId = psId;
    }

    public String getPsName() {
        return psName;
    }

    public void setPsName(String psName) {
        this.psName = psName;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

}
