package com.example.psapp;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.psapp.bean.PsFile;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by 永远有多远 on 2018/4/12.
 */

public class FourFragment extends Fragment {
    private MyApplication myApplication;
    private CircleMenu circleMenu;
    private TextView txt1, txt2, txt3, txt4, txt5, txt6;
    private  PsFile psFile;
    private Integer nowfileState=0;
    protected static final int ERROR = 0;
    protected static final int SUCCESS = 3;
    protected static final int ERRORNET = 1;

    protected int RUNNING = 100;
    protected int PAUSE = 101;
    protected int STOP = 102;
    protected int SUC = 103;

    private int nowState = 99;//未开始

    private int totalTime = 180000;
//    private int totalTime = 5000;
    private int nowTotalTime = 0;

    private TextView tv_state;

    public FourFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.four_fragment, container, false);
        txt1 = (TextView) view.findViewById(R.id.txt_file);
        Drawable drawable1 = getResources().getDrawable(R.drawable.icon_file);
        drawable1.setBounds(0, 0, 55, 55);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        txt1.setCompoundDrawables(drawable1, null, null, null);//只放左边

        txt2 = (TextView) view.findViewById(R.id.txt_select_file);
        Drawable drawable2 = getResources().getDrawable(R.drawable.icon_select_file);
        drawable2.setBounds(0, 0, 50, 50);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        txt2.setCompoundDrawables(drawable2, null, null, null);//只放左边

        txt3 = (TextView) view.findViewById(R.id.txt_pause_file);
        Drawable drawable3 = getResources().getDrawable(R.drawable.icon_pause);
        drawable3.setBounds(0, 0, 50, 50);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        txt3.setCompoundDrawables(drawable3, null, null, null);//只放左边

        txt4 = (TextView) view.findViewById(R.id.txt_continue_file);
        Drawable drawable4 = getResources().getDrawable(R.drawable.icon_continue);
        drawable4.setBounds(0, 0, 50, 50);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        txt4.setCompoundDrawables(drawable4, null, null, null);//只放左边

        txt5 = (TextView) view.findViewById(R.id.txt_stop_file);
        Drawable drawable5 = getResources().getDrawable(R.drawable.icon_cease);
        drawable5.setBounds(0, 0, 50, 50);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        txt5.setCompoundDrawables(drawable5, null, null, null);//只放左边

        txt6 = (TextView) view.findViewById(R.id.txt_device_state);
        Drawable drawable6 = getResources().getDrawable(R.drawable.icon_device);
        drawable6.setBounds(0, 0, 50, 50);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        txt6.setCompoundDrawables(drawable6, null, null, null);//只放左边
        myApplication = (MyApplication) this.getActivity().getApplication();
        init(view);

        //接受参数，运行文件
        initFile();


        return view;
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
                    break;
            }
        }
    };
     void updateFile(){
         new Thread() {
             public void run() {
                 try {
                     String path = "http://192.168.1.107:8080/home/program/upateFileState?fileId=" + psFile.getFileId() + "&fileState=" + nowfileState;
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


    private void initFile() {
        nowfileState=1;
        //获取传过来的psFile，判断，若获取不到，不执行后面的运行文件
        psFile = myApplication.getPsFile();
        if (psFile != null) {
            myApplication.setPsFile(null);
            //运行文件
            runFile();
            tv_state.setText("程控运行");
            updateFile();

        }
    }

    private void init(View view) {

        circleMenu = (CircleMenu) view.findViewById(R.id.circle_menu_file);

        tv_state = (TextView) view.findViewById(R.id.textview_nowFileState);
        tv_state.setText("空闲状态");


        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.mipmap.icon_menu, R.mipmap.icon_cancel)
                .addSubMenu(Color.parseColor("#8A8A8A"), R.mipmap.file)
                .addSubMenu(Color.parseColor("#30A400"), R.mipmap.selectfile)
                .addSubMenu(Color.parseColor("#FF4B32"), R.mipmap.pause)
                .addSubMenu(Color.parseColor("#8A39FF"), R.mipmap.continuees)
                .addSubMenu(Color.parseColor("#FF6A00"), R.mipmap.cease)
                .addSubMenu(Color.parseColor("#1296db"), R.mipmap.device)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {

                    @Override
                    public void onMenuSelected(int index) {
                        String comName = null;
                        switch (index) {
                            case 0:
                                try {
                                    HomeActivity homeActivity = (HomeActivity) getActivity();
                                    homeActivity.gotoDownloadFragment(0);
                                } catch (Throwable e) {
                                    break;
                                }
                                break;

                            case 1:
                                try {
                                    if (nowState != 99){
                                        Toast.makeText(getActivity(),"已有文件正在执行，请稍后重试",Toast.LENGTH_LONG).show();
                                    }else {
                                        HomeActivity homeActivity = (HomeActivity) getActivity();
                                        homeActivity.gotoDownloadFragment(1);
                                    }
                                    break;
                                } catch (Throwable e) {
                                    break;
                                }
                            case 2:
                                try {
                                    if (nowState == RUNNING){
                                        nowState = PAUSE;
                                        nowfileState=2;
                                        updateFile();
                                        Toast.makeText(getActivity(),"已暂停",Toast.LENGTH_LONG).show();
                                        tv_state.setText("程控暂停");
                                    }else {
                                        Toast.makeText(getActivity(),"未有文件正在执行，无法暂停",Toast.LENGTH_LONG).show();
                                    }
                                    break;
                                } catch (Throwable e) {
                                    break;
                                }

                            case 3:
                                try {
                                    if (nowState == PAUSE){
                                        nowState = RUNNING;
                                        nowfileState=1;
                                        updateFile();
                                        Toast.makeText(getActivity(),"已继续",Toast.LENGTH_LONG).show();
                                        tv_state.setText("程控运行");
                                    }else {
                                        Toast.makeText(getActivity(),"未有文件正在暂停，无法继续",Toast.LENGTH_LONG).show();
                                    }
                                    break;
                                } catch (Throwable e) {
                                    break;
                                }

                            case 4:
                                try {
                                    if (nowState == RUNNING || nowState == PAUSE){
                                        nowState = STOP;
                                        Toast.makeText(getActivity(),"已停止",Toast.LENGTH_LONG).show();
                                        tv_state.setText("空闲状态");
                                    }else{
                                        Toast.makeText(getActivity(),"未有文件正在执行，无法停止",Toast.LENGTH_LONG).show();
                                    }
                                    break;
                                } catch (Throwable e) {
                                    break;
                                }
                            case 5:
                                try {
                                    HomeActivity homeActivity = (HomeActivity) getActivity();
                                    homeActivity.gotoDownloadFragment(4);
                                    break;
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
                case 103:
                    //运行完成，更改数据库状态
                    nowfileState=3;
                    updateFile();
                    //恢复nowstate的状态
                    nowState = 99;
                    tv_state.setText("空闲状态");
                    break;
                case 102:
                    //停止状态，更新数据库
                    nowfileState=0;
                    updateFile();
                    //恢复nowstate的状态
                    nowState = 99;
                    break;
            }
        }
    };


    void runFile() {

        new Thread() {
            public void run() {
                try {
                    nowState = RUNNING;
                    while (true) {
                        sleep(1000);
                        if (nowState == RUNNING) {
                            nowTotalTime += 1000;
                            if (nowTotalTime > totalTime) {
                                Message msg = Message.obtain();
                                msg.what = SUC;
                                handler.sendMessage(msg);
                                break;
                            }
                        } else if (nowState == STOP) {
                            Message msg = Message.obtain();
                            msg.what = STOP;
                            handler.sendMessage(msg);
                            break;
                        } else if (nowState == PAUSE) {
                            continue;
                        } else {
                            Message msg = Message.obtain();
                            msg.what = ERROR;
                            handler.sendMessage(msg);
                            break;
                        }
                        System.out.println("我的状态：" + nowState);
                    }
                } catch (Throwable e) {
                    Message msg = Message.obtain();
                    msg.what = ERROR;
                    handler.sendMessage(msg);
                }
            }

            ;
        }.start();
    }
}
