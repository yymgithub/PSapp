package com.example.psapp.bean;


import java.sql.Timestamp;

/**
 * Created by 永远有多远 on 2018/4/13.
 */

public class PsDataFile {
    private Integer daId;
    private Integer psId;
    private String  daTestSubject;
    private String daDataDocu;
    private String testType;
    private Integer testNum;
    private String phoneId;
    private String daNote;
    private Integer daState;
    private String testStaff;
    private Integer yn;
    //创建时间
    private Timestamp created;

    public Integer getDaId() {
        return daId;
    }

    public void setDaId(Integer daId) {
        this.daId = daId;
    }

    public Integer getPsId() {
        return psId;
    }

    public void setPsId(Integer psId) {
        this.psId = psId;
    }

    public String getDaTestSubject() {
        return daTestSubject;
    }

    public void setDaTestSubject(String daTestSubject) {
        this.daTestSubject = daTestSubject;
    }

    public String getDaDataDocu() {
        return daDataDocu;
    }

    public void setDaDataDocu(String daDataDocu) {
        this.daDataDocu = daDataDocu;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public Integer getTestNum() {
        return testNum;
    }

    public void setTestNum(Integer testNum) {
        this.testNum = testNum;
    }

    public String getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    public String getDaNote() {
        return daNote;
    }

    public void setDaNote(String daNote) {
        this.daNote = daNote;
    }

    public Integer getDaState() {
        return daState;
    }

    public void setDaState(Integer daState) {
        this.daState = daState;
    }

    public String getTestStaff() {
        return testStaff;
    }

    public void setTestStaff(String testStaff) {
        this.testStaff = testStaff;
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
                "&phoneId=" + phoneId +
                "&daTestSubject=" + daTestSubject +
                "&daDataDocu=" + daDataDocu +
                "&testType=" + testType +
                "&testNum=" + testNum +
                "&daNote=" + daNote +
                "&testStaff=" + testStaff;
    }
}
