package com.example.psapp.bean;


import java.sql.Timestamp;

/**
 * Created by 永远有多远 on 2018/4/1.
 */
public class PsParameter {
    private Integer paraId;
    private Integer psId;
    private String paraName;
    private double paraValue;
    //参数单位
    private String paraUnit;
    //参数小数位
    private Integer paraFormat;
    //是否有效
    private Integer yn;
    public PsParameter(){}

    public Integer getParaId() {
        return paraId;
    }

    public void setParaId(Integer paraId) {
        this.paraId = paraId;
    }

    public Integer getPsId() {
        return psId;
    }

    public void setPsId(Integer psId) {
        this.psId = psId;
    }

    public String getParaName() {
        return paraName;
    }

    public void setParaName(String paraName) {
        this.paraName = paraName;
    }

    public double getParaValue() {
        return paraValue;
    }

    public void setParaValue(double paraValue) {
        this.paraValue = paraValue;
    }

    public String getParaUnit() {
        return paraUnit;
    }

    public void setParaUnit(String paraUnit) {
        this.paraUnit = paraUnit;
    }

    public Integer getParaFormat() {
        return paraFormat;
    }

    public void setParaFormat(Integer paraFormat) {
        this.paraFormat = paraFormat;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }
}
