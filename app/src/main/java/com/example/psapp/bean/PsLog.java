package com.example.psapp.bean;


import java.sql.Timestamp;

/**
 * Created by 永远有多远 on 2018/4/15.
 */
public class PsLog {
    private Integer logId;
    private Integer psId;
    private String logErrorMsg;
    private Integer yn;
    //创建时间
    private String logTime;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getPsId() {
        return psId;
    }

    public void setPsId(Integer psId) {
        this.psId = psId;
    }

    public String getLogErrorMsg() {
        return logErrorMsg;
    }

    public void setLogErrorMsg(String logErrorMsg) {
        this.logErrorMsg = logErrorMsg;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }

    public String getLogTime() {

        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }
}
