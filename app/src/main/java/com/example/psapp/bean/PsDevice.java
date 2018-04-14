package com.example.psapp.bean;


import java.sql.Timestamp;

/**
 * Created by 永远有多远 on 2018/4/14.
 */

public class PsDevice {
    private Integer deId;
    private Integer psId;
    private String psDevName;
    private Integer psDevState;
    //最近连接或者退出时间
    private String psLastRecvtime;
    private Integer psLineNum;
    private Integer yn;

    public Integer getDeId() {
        return deId;
    }

    public String getPsLastRecvtime() {
        return psLastRecvtime;
    }

    public void setPsLastRecvtime(String psLastRecvtime) {
        this.psLastRecvtime = psLastRecvtime;
    }

    public void setDeId(Integer deId) {
        this.deId = deId;
    }

    public Integer getPsId() {
        return psId;
    }

    public void setPsId(Integer psId) {
        this.psId = psId;
    }

    public String getPsDevName() {
        return psDevName;
    }

    public void setPsDevName(String psDevName) {
        this.psDevName = psDevName;
    }

    public Integer getPsDevState() {
        return psDevState;
    }

    public void setPsDevState(Integer psDevState) {
        this.psDevState = psDevState;
    }

    public Integer getPsLineNum() {
        return psLineNum;
    }

    public void setPsLineNum(Integer psLineNum) {
        this.psLineNum = psLineNum;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }
}
