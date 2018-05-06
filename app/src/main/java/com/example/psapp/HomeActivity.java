package com.example.psapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.psapp.bean.PsParameter;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private MyApplication myApplication;
    private TextView topBar;
    private TextView tabDeal;
    private TextView tabCom;
    private TextView tabMore;
    private TextView tabManc;
    private TextView mTitleTextView;
    private ListView left_list_view;
    private FrameLayout ly_content;
    protected static final int ERROR = 0;
    protected static final int SUCCESS = 3;
    protected static final int ERRORNET = 1;

    private FirstFragment f1;
    private SecondFragment f2;
    private ThreeFragment f3;
    private FourFragment f4;
    private FourFirstFragment f5;
    private FourSecondFragment f6;
    private FourThirdFragment f7;
    private MoreFourFragment f8;
    private MoreTestDetailFragment f11;
    private MoreThreeFragment f9;
    private MoreSixFragment f10;
    private MoreFiveChartFragment f12;
    private FragmentManager fragmentManager;
    private TextView txt_signout;
    private TextView txt_backward;
    private ArrayList list;
    private DrawerLayout drawerLayout;
    private String str = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        bindView();

        myApplication = (MyApplication) this.getApplication();
    }

    //UI组件初始化与事件绑定
    private void bindView() {
        tabDeal = (TextView) this.findViewById(R.id.txt_deal);
        tabCom = (TextView) this.findViewById(R.id.tab_com);
        tabManc = (TextView) this.findViewById(R.id.tab_manc);
        tabMore = (TextView) this.findViewById(R.id.txt_more);
        ly_content = (FrameLayout) findViewById(R.id.fragment_container);
        mTitleTextView = (TextView) findViewById(R.id.text_title);
        txt_signout = (TextView) findViewById(R.id.txt_signout);
        txt_backward = (TextView) findViewById(R.id.txt_backward);
        left_list_view = (ListView) findViewById(R.id.left_listview);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        getSupportActionBar().hide();
        initData();
        ContentAdapter adapter = new ContentAdapter(this, list);
        left_list_view.setAdapter(adapter);
        tabDeal.setOnClickListener(this);
        tabMore.setOnClickListener(this);
        tabManc.setOnClickListener(this);
        tabCom.setOnClickListener(this);
        txt_signout.setOnClickListener(this);
        txt_backward.setOnClickListener(this);
        leftListen();
        fistSelect();

    }

    //初始化左侧侧拉的数据
    private void initData() {
        list = new ArrayList<ContentModel>();
        list.add(new ContentModel(R.mipmap.para_slide_menu_off, "查看参数示图", 1));
        list.add(new ContentModel(R.mipmap.write_slide_menu_off, "记录试验数据", 2));
        list.add(new ContentModel(R.mipmap.look_slide_menu_off, "查看试验数据", 3));
        list.add(new ContentModel(R.mipmap.alarm_slide_menu_off, "查看设备报警", 4));
        list.add(new ContentModel(R.mipmap.picture_slide_menu_off, "查看绘制曲线", 5));
        list.add(new ContentModel(R.mipmap.look_slide_menu_off, "查看系统日志", 6));
    }

    //重置所有文本的选中状态
    public void selected() {
        tabDeal.setSelected(false);
        tabMore.setSelected(false);
        tabCom.setSelected(false);
        tabManc.setSelected(false);
    }

    //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction) {
        if (f1 != null) {
            transaction.hide(f1);
        }
        if (f2 != null) {
            transaction.hide(f2);
        }
        if (f3 != null) {
            transaction.hide(f3);
        }
        if (f4 != null) {
            transaction.hide(f4);
        }
        if (f5 != null) {
            transaction.hide(f5);
        }
        if (f6 != null) {
            transaction.hide(f6);
        }
        if (f7 != null) {
            transaction.hide(f7);
        }
        if (f8 != null) {
            transaction.hide(f8);
        }
        if (f9 != null) {
            transaction.hide(f9);
        }
        if (f11 != null) {
            transaction.hide(f11);
        }
        if (f10 != null) {
            transaction.hide(f10);
        }
        if (f12 != null) {
            transaction.hide(f12);
        }
    }

    //侧拉的监听事件
    void leftListen() {
        left_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                hideAllFragment(transaction);
                switch ((int) id) {
                    case 1:
                        Intent intent = new Intent(HomeActivity.this, PicActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        str = "";
                        List<PsParameter> list = myApplication.getPsParameterList();

                        for (int i = 0; i < list.size(); i++) {
                            str += list.get(i).getParaName() + ":" + list.get(i).getParaValue() + list.get(i).getParaUnit() + ";";
                        }
                        setTestRecord();
                        break;
                    case 3:
                        mTitleTextView.setText("");
                        txt_backward.setVisibility(View.VISIBLE);
                        txt_signout.setVisibility(View.INVISIBLE);
                        selected();
                        if (f9 == null) {
                            f9 = new MoreThreeFragment();
                            transaction.add(R.id.fragment_container, f9);
                        } else {
                            transaction.show(f9);
                        }
                        break;
                    case 4:
                        mTitleTextView.setText("");
                        txt_backward.setVisibility(View.VISIBLE);
                        txt_signout.setVisibility(View.INVISIBLE);
                        selected();
                        if (f8 == null) {
                            f8 = new MoreFourFragment();
                            transaction.add(R.id.fragment_container, f8);
                        } else {
                            transaction.show(f8);
                        }
                        break;
                    case 5:
                        mTitleTextView.setText("");
                        txt_backward.setVisibility(View.VISIBLE);
                        txt_signout.setVisibility(View.INVISIBLE);
                        selected();
                        if (f12 == null) {
                            f12 = new MoreFiveChartFragment();
                            transaction.add(R.id.fragment_container, f12);
                        } else {
                            transaction.show(f12);
                        }
                        break;
                    case 6:
                        mTitleTextView.setText("");
                        txt_backward.setVisibility(View.VISIBLE);
                        txt_signout.setVisibility(View.INVISIBLE);
                        selected();
                        if (f10 == null) {
                            f10 = new MoreSixFragment();
                            transaction.add(R.id.fragment_container, f10);
                        } else {
                            transaction.show(f10);
                        }
                        break;

                    default:
                        break;
                }
                transaction.commit();
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
    }


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case ERROR:
                    Toast.makeText(HomeActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case ERRORNET:
                    Toast.makeText(HomeActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                    break;
                case SUCCESS:

                    break;
            }
        }
    };

    void fistSelect() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        tabDeal.setSelected(true);
        mTitleTextView.setText("查看参数");
        if (f1 == null) {
            f1 = new FirstFragment("第一个Fragment");
            transaction.add(R.id.fragment_container, f1);
        } else {
            transaction.show(f1);
        }
        transaction.commit();
    }


    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch (v.getId()) {
            case R.id.txt_deal:
                selected();
                tabDeal.setSelected(true);
                mTitleTextView.setText("查看参数");
                if (f1 == null) {
                    f1 = new FirstFragment("第一个Fragment");
                    transaction.add(R.id.fragment_container, f1);
                } else {
                    transaction.show(f1);
                }
                break;

            case R.id.tab_com:
                selected();
                tabCom.setSelected(true);
                mTitleTextView.setText("命令控制");
                if (f2 == null) {
                    f2 = new SecondFragment();
                    transaction.add(R.id.fragment_container, f2);
                } else {
                    transaction.show(f2);
                }
                break;

            case R.id.tab_manc:
                selected();
                tabManc.setSelected(true);
                mTitleTextView.setText("手动控制");
                if (f3 == null) {
                    f3 = new ThreeFragment();
                    transaction.add(R.id.fragment_container, f3);
                } else {
                    transaction.show(f3);
                }
                break;
            case R.id.txt_more:
                selected();
                tabMore.setSelected(true);
                mTitleTextView.setText("程序控制");
                if (f4 == null) {
                    f4 = new FourFragment();
                    transaction.add(R.id.fragment_container, f4);
                } else {
                    transaction.show(f4);
                }
                break;
            case R.id.txt_signout:
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                signOut();
                startActivity(intent);
                signOut();
                break;
            case R.id.txt_backward:
                txt_backward.setVisibility(View.INVISIBLE);
                txt_signout.setVisibility(View.VISIBLE);
                selected();
                tabMore.setSelected(true);
                mTitleTextView.setText("程序控制");
                if (f4 == null) {
                    f4 = new FourFragment();
                    transaction.add(R.id.fragment_container, f4);
                } else {
                    transaction.show(f4);
                }
                break;
        }

        transaction.commit();
    }


    public void gotoDownloadFragment(Integer cha) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);

        switch (cha) {
            case 0:
                mTitleTextView.setText("");
                txt_backward.setVisibility(View.VISIBLE);
                txt_signout.setVisibility(View.INVISIBLE);
                if (f5 == null) {
                    f5 = new FourFirstFragment();
                    transaction.add(R.id.fragment_container, f5);
                } else {
                    transaction.show(f5);
                }
                transaction.commit();
                break;
            case 1:
                mTitleTextView.setText("");
                txt_backward.setVisibility(View.VISIBLE);
                txt_signout.setVisibility(View.INVISIBLE);
                if (f6 == null) {
                    f6 = new FourSecondFragment();
                    transaction.add(R.id.fragment_container, f6);
                } else {
                    transaction.show(f6);
                }
                transaction.commit();

                break;
            case 2:
                txt_backward.setVisibility(View.INVISIBLE);
                txt_signout.setVisibility(View.VISIBLE);
                selected();
                tabMore.setSelected(true);
                mTitleTextView.setText("程序控制");
                if (f4 == null) {
                    f4 = new FourFragment();
                    transaction.add(R.id.fragment_container, f4);
                } else {
                    transaction.show(f4);
                }
                transaction.commit();

                break;
            case 3:
                txt_backward.setVisibility(View.INVISIBLE);
                txt_signout.setVisibility(View.VISIBLE);
                selected();
                tabMore.setSelected(true);
                mTitleTextView.setText("程序控制");
                if (f4 == null) {
                    f4 = new FourFragment();
                    transaction.add(R.id.fragment_container, f4);
                } else {
                    transaction.remove(f4);
                    f4 = new FourFragment();
                    transaction.add(R.id.fragment_container, f4);
                    transaction.show(f4);
                }
                transaction.commit();

                break;
            case 4:
                mTitleTextView.setText("");
                txt_backward.setVisibility(View.VISIBLE);
                txt_signout.setVisibility(View.INVISIBLE);
                if (f7 == null) {
                    f7 = new FourThirdFragment();
                    transaction.add(R.id.fragment_container, f7);
                } else {
                    transaction.show(f7);
                }
                transaction.commit();

                break;
            case 5:
                mTitleTextView.setText("");
                txt_backward.setVisibility(View.VISIBLE);
                txt_signout.setVisibility(View.INVISIBLE);
                if (f11 == null) {
                    f11 = new MoreTestDetailFragment();
                    transaction.add(R.id.fragment_container, f11);
                } else {
                    transaction.show(f11);
                }
                transaction.commit();

                break;
            case 6:
                txt_backward.setVisibility(View.INVISIBLE);
                txt_signout.setVisibility(View.VISIBLE);
                selected();
                tabDeal.setSelected(true);
                mTitleTextView.setText("查看参数");
                if (f1 == null) {
                    f1 = new FirstFragment("第一个Fragment");
                    transaction.add(R.id.fragment_container, f1);
                } else {
                    transaction.show(f1);
                }
                transaction.commit();

                break;
        }
    }

    private Handler handler1 = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case ERROR:
                    Toast.makeText(HomeActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case ERRORNET:
                    Toast.makeText(HomeActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                    break;
                case SUCCESS:
                    Toast.makeText(HomeActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    fistSelect();
                    break;
            }
        }
    };

    void setTestRecord() {
        new Thread() {
            public void run() {
                try {
                    String path = "http://47.106.32.2:80/home/more/setTestRecord?psId=" + myApplication.getNowPsBench().getPsId() + "&testPara=" + str;
                    path = path.replace("%", "%25");
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

    void signOut() {
        new Thread() {
            public void run() {
                try {
                    String path = "http://47.106.32.2:80/home/signoutDevice?psId=" + myApplication.getNowPsBench().getPsId();
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

            ;
        }.start();
    }


}
