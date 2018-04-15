package com.example.psapp.bean;


import java.sql.Timestamp;

/**
 * Created by 永远有多远 on 2018/4/15.
 */

public class PsTestRecord {
    private Integer testId;
    private Integer psId;
    private String testPara;
    //是否有效
    private Integer yn;
    //创建时间
    private String created;

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public Integer getPsId() {
        return psId;
    }

    public void setPsId(Integer psId) {
        this.psId = psId;
    }

    public String getTestPara() {
        return testPara;
    }

    public void setTestPara(String testPara) {
        this.testPara = testPara;
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
}
