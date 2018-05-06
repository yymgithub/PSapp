package com.example.psapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.psapp.bean.PsParameter;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by 永远有多远 on 2018/4/16.
 */

public class PicActivity extends AppCompatActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    private TextView pic_load1_speed;
    private TextView pic_load1_power;
    private TextView pic_load1_niuju;
    private TextView pic_load2_speed;
    private TextView pic_load2_power;
    private TextView pic_load2_niuju;
    private TextView pic_drive_speed;
    private TextView pic_drive_power;
    private TextView pic_drive_niuju;
    private TextView pic_tmp;
    private TextView pic_kaidu;
    protected static final int ERROR = 0;
    protected static final int SUCCESS = 3;
    protected static final int ERRORNET = 1;
    private MyApplication myApplication;
    private SwipeRefreshLayout swipeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏

            setContentView(R.layout.more_first_fragment);
//        textView_back= (TextView) findViewById(R.id.txt_backward_pic);


            getSupportActionBar().hide();
            pic_load1_speed= (TextView) findViewById(R.id.load_speed1);
            pic_load1_power= (TextView) findViewById(R.id.load_power1);
            pic_load1_niuju= (TextView) findViewById(R.id.load_niuju1);
            pic_load2_speed= (TextView) findViewById(R.id.load_speed2);
            pic_load2_power= (TextView) findViewById(R.id.load_power2);
            pic_load2_niuju= (TextView) findViewById(R.id.load_niuju2);
            pic_drive_speed= (TextView) findViewById(R.id.drive_speed);
            pic_drive_power= (TextView) findViewById(R.id.drive_power);
            pic_drive_niuju= (TextView) findViewById(R.id.drive_niuju);
            pic_tmp= (TextView) findViewById(R.id.txt_tep_pic);
            pic_kaidu= (TextView) findViewById(R.id.txt_kaidu_pic);
            myApplication= (MyApplication) this.getApplication();
            //初始化下拉刷新
            swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_img);
            swipeLayout.setColorSchemeColors(Color.GRAY);
            swipeLayout.setOnRefreshListener(this);

            getData();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_backward_pic:
                Intent intent = new Intent(PicActivity.this, HomeActivity.class);
                startActivity(intent);
                break;

        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case ERROR:
                    Toast.makeText(PicActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case ERRORNET:
                    Toast.makeText(PicActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                    break;
                case SUCCESS:
                    try {
                        List<PsParameter> psParameterList = (List<PsParameter>) msg.obj;
                        for (int i = 0; i < psParameterList.size(); i++) {
                            String str = null;
                            if (psParameterList.get(i).getParaFormat() == 1) {
                                java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.0");
                                str = myformat.format(psParameterList.get(i).getParaValue());
                            } else if (psParameterList.get(i).getParaFormat() == 2) {
                                java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.00");
                                str = myformat.format(psParameterList.get(i).getParaValue());
                            } else if (psParameterList.get(i).getParaFormat() == 0) {
                                java.text.DecimalFormat myformat = new java.text.DecimalFormat("0");
                                str = myformat.format(psParameterList.get(i).getParaValue());
                            }
                            if (psParameterList.get(i).getParaName().equals("负载1转速")) {
                                pic_load1_speed.setText(str);
                            } else if (psParameterList.get(i).getParaName().equals("负载1功率")) {
                                pic_load1_power.setText(str);
                            } else if (psParameterList.get(i).getParaName().equals("负载1扭矩")) {
                                pic_load1_niuju.setText(str);
                            } else if (psParameterList.get(i).getParaName().equals("驱动功率")) {
                                pic_drive_power.setText(str);
                            } else if (psParameterList.get(i).getParaName().equals("驱动转速")) {
                                pic_drive_speed.setText(str);
                            } else if (psParameterList.get(i).getParaName().equals("驱动扭矩")) {
                                pic_drive_niuju.setText(str);
                            } else if (psParameterList.get(i).getParaName().equals("负载2转速")) {
                                pic_load2_speed.setText(str);
                            } else if (psParameterList.get(i).getParaName().equals("负载2功率")) {
                                pic_load2_power.setText(str);
                            } else if (psParameterList.get(i).getParaName().equals("负载2扭矩")) {
                                pic_load2_niuju.setText(str);
                            } else if (psParameterList.get(i).getParaName().equals("油温")) {
                                pic_tmp.setText(str);
                            } else if (psParameterList.get(i).getParaName().equals("开度")) {
                                pic_kaidu.setText(str);
                            }
                        }
                    }catch(Throwable e){
                        e.printStackTrace();
                    }


                    break;
            }
        }
    };

    public void getData() {
        new Thread() {
            public void run() {
                try {
                    Integer psId = myApplication.getNowPsBench().getPsId();
                    String path = "http://47.106.32.2:80/home/appGetPara?psId=" + psId;
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
                            myApplication.setPsParameterList(psParameterList);
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
