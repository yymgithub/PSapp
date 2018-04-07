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
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.psapp.bean.PsParameter;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class FirstFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private String context;
    private TextView mTextView;
    private ListView listView;
    private Handler mHandler;
    private SwipeRefreshLayout swipeLayout;
    protected static final int ERROR = 0;
    protected static final int SUCCESS = 3;
    protected static final int ERRORNET = 1;

    private MyApplication myApplication;

    public FirstFragment(String context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.list_view);
        myApplication = (MyApplication) this.getActivity().getApplication();
        getData();
        //初始化下拉刷新
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeLayout.setColorSchemeColors(Color.GRAY);
        swipeLayout.setOnRefreshListener(this);
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
                    List<PsParameter> psParameterList = (List<PsParameter>) msg.obj;
                    listView.setAdapter(new ParaAdapter(getActivity(), R.layout.parameter_item, psParameterList));
                    break;
            }
        }
    };

    public void getData() {
        new Thread() {
            public void run() {
                try {
                    Integer psId = myApplication.getNowPsBench().getPsId();
                    String path = "http://192.168.1.107:8080/home/appGetPara?psId=" + psId;
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
                            String psParameterListString = data.getString("psParameterList");
                            List<PsParameter> psParameterList = JSON.parseArray(psParameterListString, PsParameter.class);
                            JSONObject psBench = (JSONObject) data.get("psBench");
                            Integer psStop = psBench.getInt("psStop");
                            Integer psAlarm = psBench.getInt("psAlarm");
                            myApplication.getNowPsBench().setPsStop(psStop);
                            myApplication.getNowPsBench().setPsAlarm(psAlarm);
                            Message msg = Message.obtain();
                            msg.what = SUCCESS;
                            msg.obj = psParameterList;
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
//    private void myDialog(String promt,int icon,int time){
//        Intent i =new Intent(getActivity(),ndialog.class);
//        Bundle b =new Bundle();
//        b.putString("promt", promt);
//        b.putInt("icon", icon);
//        b.putInt("time", time);
//        i.putExtra("data", b);
//        startActivity(i);
//
//    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                swipeLayout.setRefreshing(false);
                getData();
            }
        }, 500);

    }
}