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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.psapp.bean.PsFile;
import com.example.psapp.bean.PsTestRecord;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 永远有多远 on 2018/4/15.
 */

public class MoreThreeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    protected static final int ERROR = 0;
    protected static final int SUCCESS = 3;
    protected static final int ERRORNET = 1;

    private ListView listViewTestRecord;
    private MyApplication myApplication;
    private SwipeRefreshLayout swipeLayout;
    List<PsTestRecord>  list=new ArrayList<>();
    public MoreThreeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.more_three_fragment, container, false);
        listViewTestRecord = (ListView) view.findViewById(R.id.list_view__test_record);
        myApplication = (MyApplication) this.getActivity().getApplication();
        //初始化下拉刷新
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_test_record);
        swipeLayout.setColorSchemeColors(Color.GRAY);
        swipeLayout.setOnRefreshListener(this);
        getTestRecordData();
        change();
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
                    List<PsTestRecord> psTestRecordList = (List<PsTestRecord>) msg.obj;
                    list=psTestRecordList;
                    listViewTestRecord.setAdapter(new TestRecordAdapter(getActivity(), R.layout.test_record_item, psTestRecordList));
                    break;
            }
        }
    };

    void getTestRecordData() {
        new Thread() {
            public void run() {
                try {
                    Integer psId = myApplication.getNowPsBench().getPsId();
                    String path = "http://47.106.32.2:80/home/more/getTestRecord";
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
                            String psTestRecordListString = data.getString("psTestRecordList");
                            List<PsTestRecord> psTestRecordList = JSON.parseArray(psTestRecordListString, PsTestRecord.class);
                            Message msg = Message.obtain();
                            msg.what = SUCCESS;
                            msg.obj = psTestRecordList;
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
                getTestRecordData();
            }
        }, 500);

    }

    void change() {

        listViewTestRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //list点击事件
            @Override
            public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {
                 PsTestRecord psTestRecord = (PsTestRecord) list.get(p3);
                myApplication.setPsTestRecord(psTestRecord);
                //跳转界面，并且把psTestRecord传过去
                HomeActivity homeActivity = (HomeActivity) getActivity();
                homeActivity.gotoDownloadFragment(5);

            }


        });
    }


}
