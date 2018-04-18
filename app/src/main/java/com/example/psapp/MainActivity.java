package com.example.psapp;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.psapp.bean.PsBench;
import com.example.psapp.bean.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView mBtnLogin;

    private View progress;

    private View mInputLayout;

    private float mWidth, mHeight;

    private LinearLayout mPhone, mPsw;

    private EditText et_phone, et_password;
    private Handler mHandler;
    protected static final int ERROR = 0;
    protected static final int SUCCESS = 3;
    protected static final int ERRORNET = 1;


    private MyApplication myApplication;


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case ERROR:
                    recovery();
                    Toast.makeText(MainActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case ERRORNET:
                    recovery();
                    Toast.makeText(MainActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                    break;
                case SUCCESS:
                  final  Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    //选择台架对话框
                    List<PsBench> psBenchList = (List<PsBench>) msg.obj;
                    int num = psBenchList.size();
                    final String[] benchNameList = new String[num];
                    for (int i = 0; i < num; i++) {
                        benchNameList[i] = psBenchList.get(i).getPsName();
                    }
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    builder1.setItems(benchNameList, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                         PsBench nowPsbench=new PsBench();
                            nowPsbench.setPsId(which+1);
                            nowPsbench.setPsName(benchNameList[which]);
                            myApplication.setNowPsBench(nowPsbench);
                            startActivity(intent);
                        }
                    });
                    builder1.setTitle("选择台架");
                    builder1.show();
                    recovery();
//                    startActivity(intent);
                    break;

            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mBtnLogin = (TextView) findViewById(R.id.main_btn_login);
        progress = findViewById(R.id.layout_progress);
        mInputLayout = findViewById(R.id.input_layout);
        mPhone = (LinearLayout) findViewById(R.id.input_layout_phone);
        mPsw = (LinearLayout) findViewById(R.id.input_layout_psw);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_password = (EditText) findViewById(R.id.et_password);

        myApplication = (MyApplication) this.getApplication();


        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        // 计算出控件的高与宽
        mWidth = mBtnLogin.getMeasuredWidth();
        mHeight = mBtnLogin.getMeasuredHeight();
        // 隐藏输入框
        mPhone.setVisibility(View.INVISIBLE);
        mPsw.setVisibility(View.INVISIBLE);

        inputAnimator(mInputLayout, mWidth, mHeight);

        switch (v.getId()) {
            case R.id.main_btn_login:
                login();
                break;
            default:
                break;
        }
    }

    private void login() {
        if (isUserNameAndPwdValid()) {
            new Thread() {
                public void run() {
                    try {
                        this.sleep(2000);
                        String path = "http://10.96.49.255:8080/user/appLogin?phoneId=" + et_phone.getText().toString().trim() + "&password=" + et_password.getText().toString().trim();
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
                                String psBenchListString = data.getString("psBenchList");
                                //JSONArray jsonArray = data.getJSONArray("psBenchList");
                                //psBenchList = new ArrayList<PsBench>();
                                List<PsBench> psBenchList = JSON.parseArray(psBenchListString, PsBench.class);
                                JSONObject user = (JSONObject) data.get("user");
                                String phoneId = (String) user.get("phoneId");
                                Integer userRole = user.getInt("userRole");
                                User myUser = new User();
                                myUser.setPhoneId(phoneId);
                                myUser.setUserRole(userRole);
                                myApplication.setUser(myUser);
                                Message msg = Message.obtain();
                                msg.what = SUCCESS;
                                msg.obj = psBenchList;
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

    /**
     * 判断用户名和密码是否有效
     *
     * @return
     */
    public boolean isUserNameAndPwdValid() {
        // 用户名和密码不得为空
        if (et_phone.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.accountName_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (et_password.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.password_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 输入框的动画效果
     *
     * @param view 控件
     * @param w    宽
     * @param h    高
     */
    private void inputAnimator(final View view, float w, float h) {

        AnimatorSet set = new AnimatorSet();

        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                        .getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
                "scaleX", 1f, 0.5f);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /**
                 * 动画结束后，先显示加载的动画，然后再隐藏输入框
                 */
                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                mInputLayout.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });

    }

    /**
     * 出现进度动画
     *
     * @param view
     */
    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();

    }


    /**
     * 恢复初始状态
     */
    private void recovery() {
        progress.setVisibility(View.GONE);
        mInputLayout.setVisibility(View.VISIBLE);
        mPhone.setVisibility(View.VISIBLE);
        mPsw.setVisibility(View.VISIBLE);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mInputLayout.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        mInputLayout.setLayoutParams(params);


        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 0.5f, 1f);
        animator2.setDuration(500);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.start();
    }
}