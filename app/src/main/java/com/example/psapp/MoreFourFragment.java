package com.example.psapp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.psapp.bean.PsDevice;
import com.example.psapp.bean.PsDeviceAlarm;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by 永远有多远 on 2018/4/14.
 */

public class MoreFourFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

protected static final int ERROR = 0;
protected static final int SUCCESS = 3;
protected static final int ERRORNET = 1;

private ListView listViewDeviceAlarm;
private MyApplication myApplication;
private SwipeRefreshLayout swipeLayout;


public MoreFourFragment() {
        }

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.more_four_fragment, container, false);
        listViewDeviceAlarm = (ListView) view.findViewById(R.id.list_view_device_alarm);
        myApplication = (MyApplication) this.getActivity().getApplication();
        //初始化下拉刷新
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_device_alarm);
        swipeLayout.setColorSchemeColors(Color.GRAY);
        swipeLayout.setOnRefreshListener(this);
        getDeviceAlarmData();

        return view;
        }

private Handler handler = new Handler() {
public void handleMessage(android.os.Message msg) {
        switch (msg.what) {
        case ERROR:
        Toast.makeText(getActivity(), (String) msg.obj, Toast.LENGTH_SHORT).show();
        break;
        case ERRORNET:
        Toast.makeText(getActivity(), "发送失败", Toast.LENGTH_SHORT).show();
        break;
        case SUCCESS:
        List<PsDeviceAlarm> psDeviceAlarmList = (List<PsDeviceAlarm>) msg.obj;
        listViewDeviceAlarm.setAdapter(new DeviceAlarmAdapter(getActivity(), R.layout.device_alarm_item, psDeviceAlarmList));
        break;
        }
        }
        };

    void getDeviceAlarmData() {
        new Thread() {
                public void run() {
        try {
        String path = "http://192.168.1.107:8080/home/more/getAlarmDevice";
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; KB974487)");
        int code = conn.getResponseCode();
        if (code == 200) {
        InputStream is = conn.getInputStream();
        String result = StreamTools.readInputStream(is);
        JSONObject resultJson = new JSONObject(result);
        boolean success = resultJson.getBoolean("success");
        if (success) {
        JSONObject data = (JSONObject) resultJson.get("data");
        String psDeviceAlarmListString = data.getString("psDeviceAlarmList");
        List<PsDeviceAlarm> psDeviceAlarmList = JSON.parseArray(psDeviceAlarmListString, PsDeviceAlarm.class);
        Message msg = Message.obtain();
        msg.what = SUCCESS;
        msg.obj = psDeviceAlarmList;
        handler.sendMessage(msg);

        } else {
        Message msg = Message.obtain();
        msg.what = ERROR;
        String message = resultJson.getString("message");
        msg.obj = message;
        handler.sendMessage(msg);
        }
        } else {
        Message msg = Message.obtain();
        msg.what = ERRORNET;
        handler.sendMessage(msg);
        }
        } catch (Exception e) {
        e.printStackTrace();
        Message msg = Message.obtain();
        msg.what = ERRORNET;
        handler.sendMessage(msg);
        }
        }

        ;
        }.start();
        }

@Override
public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
public void run() {
        swipeLayout.setRefreshing(false);
        getDeviceAlarmData();
        }
        }, 500);
        }
}