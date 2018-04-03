package com.example.psapp;

import android.app.Application;

import com.example.psapp.bean.PsBench;
import com.example.psapp.bean.User;

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
}
