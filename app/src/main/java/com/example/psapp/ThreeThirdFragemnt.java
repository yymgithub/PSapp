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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.psapp.bean.PsParameter;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static com.example.psapp.R.id.now_tmp;

/**
 * Created by 永远有多远 on 2018/4/10.
 */

public class ThreeThirdFragemnt extends Fragment  implements SwipeRefreshLayout.OnRefreshListener {
    private String context;
    private TextView now_temp_textview;
    private TextView target_temp_textview;
    private TextView  now_temp_textview2;
    private TextView target_temp_textview2;
    private EditText input_target_tmp;
    private EditText input_target_tmp2;
    private Button tmp_setting;
    private Button tmp_setting2;
    private SwipeRefreshLayout swipeLayout;
    private  MyApplication myApplication;
    protected static final int ERROR = 0;
    protected static final int SUCCESS = 3;
    protected static final int ERRORNET = 1;
    public ThreeThirdFragemnt(String context) {

        this.context = context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.three_third_fragment, container, false);
        now_temp_textview= (TextView) view.findViewById(R.id.now_tmp);
        now_temp_textview2= (TextView) view.findViewById(R.id.now_tmp2);
        target_temp_textview= (TextView) view.findViewById(R.id.target_tmp);
        target_temp_textview2= (TextView) view.findViewById(R.id.target_tmp2);
        input_target_tmp= (EditText) view.findViewById(R.id.input_target_tmp);
        input_target_tmp2= (EditText) view.findViewById(R.id.input_target_tmp2);
        tmp_setting= (Button) view.findViewById(R.id.submit_button1);
        tmp_setting2= (Button) view.findViewById(R.id.submit_button2);
        myApplication= (MyApplication) this.getActivity().getApplication();

        //初始化下拉刷新
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeLayout.setColorSchemeColors(Color.GRAY);
        swipeLayout.setOnRefreshListener(this);
        getTmpData();
        setTemp();
        setTemp2();
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
                    List<PsParameter> getNowTempList = (List<PsParameter>) msg.obj;
                    for(int i=0;i<getNowTempList.size();i++){
                        if(getNowTempList.get(i).getParaName().equals("变速箱温度")){
                            java.text.DecimalFormat myformat = new java.text.DecimalFormat("0");
                             String  str = myformat.format(getNowTempList.get(i).getParaValue());
                              now_temp_textview.setText(str);
                              target_temp_textview.setText(str);
                        }
                        else if(getNowTempList.get(i).getParaName().equals("油温")){
                            java.text.DecimalFormat myformat = new java.text.DecimalFormat("0");
                            String  str = myformat.format(getNowTempList.get(i).getParaValue());
                            now_temp_textview2.setText(str);
                            target_temp_textview2.setText(str);
                        }
                    }
                    break;
            }
        }
    };
    void getTmpData(){
        new Thread() {
            public void run() {
                try {
                    Integer psId = myApplication.getNowPsBench().getPsId();
                    String phoneId=myApplication.getUser().getPhoneId();
                    String path = "http://47.106.32.2:80/home/manc/getTemp?psId="+myApplication.getNowPsBench().getPsId();
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
                            String getNowTempListString = data.getString("getNowTempList");
                            List<PsParameter> getNowTempList = JSON.parseArray(getNowTempListString, PsParameter.class);
                            Message msg = Message.obtain();
                            msg.what = SUCCESS;
                            msg.obj = getNowTempList;
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
                getTmpData();
                input_target_tmp.setText("");
                input_target_tmp2.setText("");
            }
        }, 500);

    }

    private Handler handler1 = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case ERROR:
                    Toast.makeText(getActivity(), (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case ERRORNET:
                    Toast.makeText(getActivity(), "发送失败", Toast.LENGTH_SHORT).show();
                    break;
                case SUCCESS:
                    Toast.makeText(getActivity(), (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    void setTemp(){
        tmp_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        try {
                            Integer psId = myApplication.getNowPsBench().getPsId();
                            String phoneId=myApplication.getUser().getPhoneId();
                            String path = "http://47.106.32.2:80/home/manc/setTemp?psId="+myApplication.getNowPsBench().getPsId()+"&paraValue="+Double.valueOf(input_target_tmp.getText().toString())+"&phoneId="+myApplication.getUser().getPhoneId();
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
                                    Message msg = Message.obtain();
                                    msg.what = SUCCESS;
                                    String message = resultJson.getString("message");
                                    msg.obj = message;
                                    handler1.sendMessage(msg);

                                } else {
                                    Message msg = Message.obtain();
                                    msg.what = ERROR;
                                    String message = resultJson.getString("message");
                                    msg.obj = message;
                                    handler1.sendMessage(msg);
                                }
                            } else {
                                Message msg = Message.obtain();
                                msg.what = ERRORNET;
                                handler1.sendMessage(msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Message msg = Message.obtain();
                            msg.what = ERRORNET;
                            handler1.sendMessage(msg);
                        }
                    }

                    ;
                }.start();
            }
        });
    }

    private Handler handler2 = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case ERROR:
                    Toast.makeText(getActivity(), (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case ERRORNET:
                    Toast.makeText(getActivity(), "发送失败", Toast.LENGTH_SHORT).show();
                    break;
                case SUCCESS:
                    Toast.makeText(getActivity(), (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    void setTemp2() {
        tmp_setting2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        try {
                            Integer psId = myApplication.getNowPsBench().getPsId();
                            String phoneId=myApplication.getUser().getPhoneId();
                            String path = "http://47.106.32.2:80/home/manc/setTemp1?psId="+myApplication.getNowPsBench().getPsId()+"&paraValue="+Double.valueOf(input_target_tmp2.getText().toString())+"&phoneId="+myApplication.getUser().getPhoneId();
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
                                    Message msg = Message.obtain();
                                    msg.what = SUCCESS;
                                    String message = resultJson.getString("message");
                                    msg.obj = message;
                                    handler2.sendMessage(msg);

                                } else {
                                    Message msg = Message.obtain();
                                    msg.what = ERROR;
                                    String message = resultJson.getString("message");
                                    msg.obj = message;
                                    handler2.sendMessage(msg);
                                }
                            } else {
                                Message msg = Message.obtain();
                                msg.what = ERRORNET;
                                handler2.sendMessage(msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Message msg = Message.obtain();
                            msg.what = ERRORNET;
                            handler2.sendMessage(msg);
                        }
                    }

                    ;
                }.start();
            }
        });

    }
}
