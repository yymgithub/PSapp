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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.psapp.bean.PsGear;
import com.example.psapp.bean.PsParameter;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 永远有多远 on 2018/4/11.
 */

public class ThreeFourFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private String context;
    private ListView listView;
    protected static final int ERROR = 0;
    protected static final int SUCCESS = 3;
    protected static final int ERRORNET = 1;
    private  MyApplication myApplication;
    private TextView textview_nowgear;
    private TextView trouble_image;
    private SwipeRefreshLayout swipeLayout;
    private Button submit_button;
    private String faultName;

    public ThreeFourFragment(String context) {

        this.context = context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.three_fourth_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.list_view_gear);
        List<String> gearList=new ArrayList<>();
        gearList.add("1档");
        gearList.add("2档");
        gearList.add("3档");
        gearList.add("4档");
        gearList.add("5档");
        gearList.add("6档");
        listView.setAdapter(new gearAdapter(getActivity(), R.layout.gear_item, gearList));
        myApplication= (MyApplication) this.getActivity().getApplication();
        textview_nowgear= (TextView) view.findViewById(R.id.textview_nowgear);
        trouble_image= (TextView) view.findViewById(R.id.trouble_image);
        //初始化下拉刷新
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeLayout.setColorSchemeColors(Color.GRAY);
        swipeLayout.setOnRefreshListener(this);
        submit_button= (Button) view.findViewById(R.id.change_button);
        getData();
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
                    List<PsParameter> getNowGearList = (List<PsParameter>) msg.obj;
                    for(int i=0;i<getNowGearList.size();i++){
                        if(getNowGearList.get(i).getParaName().equals("当前档位")){
                            java.text.DecimalFormat myformat = new java.text.DecimalFormat("0");
                            String  str = myformat.format(getNowGearList.get(i).getParaValue());
                           if(str.equals("0")){
                               textview_nowgear.setText("1档");
                           }
                           else if(str.equals("1")){
                               textview_nowgear.setText("2档");
                            }
                           else if(str.equals("2")){
                               textview_nowgear.setText("3档");
                           }
                           else if(str.equals("3")){
                               textview_nowgear.setText("4档");
                           }
                           else if(str.equals("4")){
                               textview_nowgear.setText("5档");
                           }
                           else if(str.equals("5")){
                               textview_nowgear.setText("6档");
                           }
                        }
                        else if(getNowGearList.get(i).getParaName().equals("故障状态")){
                            java.text.DecimalFormat myformat = new java.text.DecimalFormat("0");
                            String  str = myformat.format(getNowGearList.get(i).getParaValue());
                            if(str.equals("0")){
                                trouble_image.setPressed(false);
                                faultName="变为故障";
                            }
                            else if(str.equals("1")){
                             trouble_image.setPressed(true);
                                faultName="变为非故障";
                            }
                        }
                    }
                    break;
            }
        }
    };
    void getData(){
        new Thread() {
            public void run() {
                try {
                    Integer psId = myApplication.getNowPsBench().getPsId();
                    String path = "http://47.106.32.2:80/home/manc/getGear?psId="+myApplication.getNowPsBench().getPsId();
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
                            String getNowGearListString = data.getString("getNowGearList");
                            List<PsParameter> getNowGearList = JSON.parseArray(getNowGearListString, PsParameter.class);
                            Message msg = Message.obtain();
                            msg.what = SUCCESS;
                            msg.obj = getNowGearList;
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
                getData();
            }
        }, 500);
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

    void change(){
        final PsGear psGear=new PsGear();
        psGear.setPsId(myApplication.getNowPsBench().getPsId());
        psGear.setPhoneId(myApplication.getUser().getPhoneId());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            //list点击事件
            @Override
            public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
            {
                switch(p3){
                    case 0://第一个item
                        psGear.setGearName("1档");
                        set(psGear);
                        break;
                    case 1://第二个item
                        psGear.setGearName("2档");
                        set(psGear);
                        break;
                    case 2://第三个item
                        psGear.setGearName("3档");
                        set(psGear);
                        break;
                    case 3://第四个item
                        psGear.setGearName("4档");
                        set(psGear);
                        break;
                    case 4://第五个item
                        psGear.setGearName("5档");
                        set(psGear);
                        break;
                    case 5://第六个item
                        psGear.setGearName("6档");
                        set(psGear);
                        break;
                }
            }


        });
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        try {
                            Integer psId = myApplication.getNowPsBench().getPsId();
                            String phoneId=myApplication.getUser().getPhoneId();
                            String path = "http://47.106.32.2:80/home/manc/setFault?psId="+myApplication.getNowPsBench().getPsId()+"&faultName="+faultName+"&phoneId="+myApplication.getUser().getPhoneId();
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
    void set(final PsGear psGear){
        new Thread() {
            public void run() {
                try {
                    Integer psId = myApplication.getNowPsBench().getPsId();
                    String path = "http://47.106.32.2:80/home/manc/setGear"+psGear.toString();
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

}
