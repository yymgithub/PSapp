package com.example.psapp.bean;


import java.sql.Timestamp;

/**
 * Created by 永远有多远 on 2018/4/17.
 */

public class PsChart {
    private Integer chartId;
    private Integer psId;
    private double driveChart;
    private double tmpChart;
    //是否有效
    private Integer yn;
    //创建时间
    private String created;

    public Integer getChartId() {
        return chartId;
    }

    public void setChartId(Integer chartId) {
        this.chartId = chartId;
    }

    public Integer getPsId() {
        return psId;
    }

    public void setPsId(Integer psId) {
        this.psId = psId;
    }

    public double getDriveChart() {
        return driveChart;
    }

    public void setDriveChart(double driveChart) {
        this.driveChart = driveChart;
    }

    public double getTmpChart() {
        return tmpChart;
    }

    public void setTmpChart(double tmpChart) {
        this.tmpChart = tmpChart;
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
