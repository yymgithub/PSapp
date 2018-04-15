package com.example.psapp;

import android.app.Application;

import com.example.psapp.bean.PsBench;
import com.example.psapp.bean.PsFile;
import com.example.psapp.bean.PsParameter;
import com.example.psapp.bean.PsTestRecord;
import com.example.psapp.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 永远有多远 on 2018/3/30.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private User user;
    private PsBench nowPsBench;
    private String comName;
    private PsFile psFile;
    private PsTestRecord psTestRecord;
    private List<PsParameter> psParameterList=new ArrayList<>();

    public List<PsParameter> getPsParameterList() {

        return psParameterList;
    }

    public void setPsParameterList(List<PsParameter> psParameterList) {
        this.psParameterList = psParameterList;
    }

    public PsFile getPsFile() {
        return psFile;
    }

    public void setPsFile(PsFile psFile) {
        this.psFile = psFile;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public PsBench getNowPsBench() {
        return nowPsBench;
    }

    public void setNowPsBench(PsBench nowPsBench) {
        this.nowPsBench = nowPsBench;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PsTestRecord getPsTestRecord() {
        return psTestRecord;
    }

    public void setPsTestRecord(PsTestRecord psTestRecord) {
        this.psTestRecord = psTestRecord;
    }
}
