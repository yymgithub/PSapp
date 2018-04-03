package com.example.psapp;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.psapp.bean.PsParameter;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by 永远有多远 on 2018/4/2.
 */

public class SecondFragment extends Fragment {
    private MyApplication myApplication;
    private CircleMenu circleMenu;
    private TextView txt1, txt2, txt3, txt4, txt5;
    protected static final int ERROR = 0;
    protected static final int SUCCESS = 3;
    protected static final int ERRORNET = 1;

    public SecondFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_fragment, container, false);
        txt1 = (TextView) view.findViewById(R.id.txt_stop);
        Drawable drawable1 = getResources().getDrawable(R.drawable.icon_car_stop);
        drawable1.setBounds(0, 0, 55, 55);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        txt1.setCompoundDrawables(drawable1, null, null, null);//只放左边

        txt2 = (TextView) view.findViewById(R.id.txt_car_on);
        Drawable drawable2 = getResources().getDrawable(R.drawable.icon_car_on);
        drawable2.setBounds(0, 0, 50, 50);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        txt2.setCompoundDrawables(drawable2, null, null, null);//只放左边

        txt3 = (TextView) view.findViewById(R.id.txt_car_off);
        Drawable drawable3 = getResources().getDrawable(R.drawable.icon_car_off);
        drawable3.setBounds(0, 0, 50, 50);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        txt3.setCompoundDrawables(drawable3, null, null, null);//只放左边

        txt4 = (TextView) view.findViewById(R.id.txt_alarm_on);
        Drawable drawable4 = getResources().getDrawable(R.drawable.icon_car_alarm);
        drawable4.setBounds(0, 0, 50, 50);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        txt4.setCompoundDrawables(drawable4, null, null, null);//只放左边

        txt5 = (TextView) view.findViewById(R.id.txt_alarm_off);
        Drawable drawable5 = getResources().getDrawable(R.drawable.icon_car_alarm_off);
        drawable5.setBounds(0, 0, 50, 50);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        txt5.setCompoundDrawables(drawable5, null, null, null);//只放左边
        myApplication = (MyApplication) this.getActivity().getApplication();
        init(view);

        return view;
    }

    private void init(View view) {
        circleMenu = (CircleMenu) view.findViewById(R.id.circle_menu);

        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.mipmap.icon_menu, R.mipmap.icon_cancel)
                .addSubMenu(Color.parseColor("#8A8A8A"), R.mipmap.icon_home)
                .addSubMenu(Color.parseColor("#30A400"), R.mipmap.icon_search)
                .addSubMenu(Color.parseColor("#FF4B32"), R.mipmap.icon_notify)
                .addSubMenu(Color.parseColor("#8A39FF"), R.mipmap.icon_setting)
                .addSubMenu(Color.parseColor("#FF6A00"), R.mipmap.icon_gps)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {

                    @Override
                    public void onMenuSelected(int index) {
                        String comName = null;
                        switch (index) {
                            case 0:
                                try {
                                    Integer re = myApplication.getNowPsBench().getPsStop();
                                    if (re == 1) {
                                        Toast.makeText(getActivity(), "车辆已处于停止状态，不能再次停止", Toast.LENGTH_SHORT).show();
                                        break;
                                    } else {
                                        comName = "停车";
                                        myApplication.setComName(comName);
                                        operation();
                                        break;
                                    }
                                } catch (Throwable e) {
                                    break;
                                }

                            case 1:
                                try {
                                    Integer re = myApplication.getNowPsBench().getPsStop();
                                    if (re == 0) {
                                        Toast.makeText(getActivity(), "车辆已处于启动状态，不能再次启动", Toast.LENGTH_SHORT).show();
                                        break;
                                    } else {
                                        comName = "启动变频器";
                                        myApplication.setComName(comName);
                                        operation();
                                        break;
                                    }
                                } catch (Throwable e) {
                                    break;
                                }
                            case 2:
                                try {
                                    Integer re = myApplication.getNowPsBench().getPsStop();
                                    if (re == 1) {
                                        Toast.makeText(getActivity(), "车辆已处于关闭状态，不能再次停止", Toast.LENGTH_SHORT).show();
                                        break;
                                    } else {
                                        comName = "关闭变频器";
                                        myApplication.setComName(comName);
                                        operation();
                                        break;
                                    }
                                } catch (Throwable e) {
                                    break;
                                }

                            case 3:

                                try {
                                    Integer re = myApplication.getNowPsBench().getPsAlarm();
                                    if (re == 1) {
                                        Toast.makeText(getActivity(), "车辆已处于报警状态，不能再次报警", Toast.LENGTH_SHORT).show();
                                        break;
                                    } else {
                                        comName = "报警";
                                        myApplication.setComName(comName);
                                        operation();
                                        break;
                                    }
                                } catch (Throwable e) {
                                    break;
                                }

                            case 4:
                                try {
                                    Integer re = myApplication.getNowPsBench().getPsAlarm();
                                    if (re == 0) {
                                        Toast.makeText(getActivity(), "车辆未报警，不能操作", Toast.LENGTH_SHORT).show();
                                        break;
                                    } else {
                                        comName = "报警复位";
                                        myApplication.setComName(comName);
                                        operation();
                                        break;
                                    }
                                } catch (Throwable e) {
                                    break;
                                }
                            default:
                                break;
                        }
                    }

                }).setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {

            @Override
            public void onMenuOpened() {
            }

            @Override
            public void onMenuClosed() {
            }

        });
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
                    String comName = myApplication.getComName();
                    if (comName.equals("停车")) {
                        myApplication.getNowPsBench().setPsStop(1);
                    } else if (comName.equals("启动变频器")) {
                        myApplication.getNowPsBench().setPsStop(0);
                    } else if (comName.equals("关闭变频器")) {
                        myApplication.getNowPsBench().setPsStop(1);
                    } else if (comName.equals("报警")) {
                        myApplication.getNowPsBench().setPsAlarm(1);
                    } else if (comName.equals("报警复位")) {
                        myApplication.getNowPsBench().setPsAlarm(0);
                    }
                    Toast.makeText(getActivity(), (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void operation() {
        new Thread() {
            public void run() {
                try {
                    Integer psId = myApplication.getNowPsBench().getPsId();
                    String phoneId = myApplication.getUser().getPhoneId();
                    String comName = myApplication.getComName();
                    String path = null;
                    if (comName.equals("停车")) {
                        path = "http://192.168.1.107:8080/home/command/stop?psId=" + psId + "&phoneId=" + phoneId + "&comName=" + comName;
                    } else if (comName.equals("启动变频器")) {
                        path = "http://192.168.1.107:8080/home/command/startUp?psId=" + psId + "&phoneId=" + phoneId + "&comName=" + comName;
                    } else if (comName.equals("关闭变频器")) {
                        path = "http://192.168.1.107:8080/home/command/stop?psId=" + psId + "&phoneId=" + phoneId + "&comName=" + comName;
                    } else if (comName.equals("报警")) {
                        path = "http://192.168.1.107:8080/home/command/alarmUp?psId=" + psId + "&phoneId=" + phoneId + "&comName=" + comName;
                    } else if (comName.equals("报警复位")) {
                        path = "http://192.168.1.107:8080/home/command/alarmOff?psId=" + psId + "&phoneId=" + phoneId + "&comName=" + comName;
                    }

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

        }.start();
    }
}
