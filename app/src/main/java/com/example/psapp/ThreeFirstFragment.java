package com.example.psapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.psapp.bean.PsDrive;
import com.example.psapp.bean.PsParameter;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by 永远有多远 on 2018/4/3.
 */

public class ThreeFirstFragment extends Fragment {
    private TextView mTv;
    private String context;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Switch remote_switch;
    private Switch revese_switch;
    private TextView input_speed;
    private TextView remote_img;
    private TextView revese_img;
    private AppCompatSeekBar sb_pressure;
    private AppCompatEditText et_pressure;
    private EditText dr_time;
    private Button submitButton;
    private int pressure = 0, _pressure = 0;// 数值
    protected static final int ERROR = 0;
    protected static final int SUCCESS = 3;
    protected static final int ERRORNET = 1;
    private MyApplication myApplication;

    PsDrive psDrive = new PsDrive();


    public ThreeFirstFragment(String context) {

        this.context = context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.three_first_fragment, container, false);
        //初始化控件
        myApplication = (MyApplication) this.getActivity().getApplication();
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);

        sb_pressure = (AppCompatSeekBar) view.findViewById(R.id.sb_pressure);
        et_pressure = (AppCompatEditText) view.findViewById(R.id.et_pressure);
        et_pressure.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        remote_switch = (Switch) view.findViewById(R.id.remote_switch);
        revese_switch = (Switch) view.findViewById(R.id.revese_switch);
        input_speed = (TextView) view.findViewById(R.id.input_textView);
        remote_img = (TextView) view.findViewById(R.id.remote_image);
        revese_img = (TextView) view.findViewById(R.id.revase_image);
        dr_time = (EditText) view.findViewById(R.id.dr_time);
        submitButton = (Button) view.findViewById(R.id.submit_button);
        sb_pressure.setProgress(0);// 先将进度条滑到最左端

        sb_pressure.setMax(50000);// 设置进度条可调节的数值范围长度，参数为int类型
        et_pressure.setText("0");// 设置初始显示值
        dr_time.setText("0");
        init();

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
                    Toast.makeText(getActivity(), (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    void init() {
        psDrive.setDrMode(0);
        psDrive.setDrRamptime(0);
        psDrive.setDrLoad(0);
        psDrive.setDrRemotestatus(0);
        psDrive.setDrReverse(0);
        psDrive.setPhoneId(myApplication.getUser().getPhoneId());
        psDrive.setPsId(myApplication.getNowPsBench().getPsId());
        sb_pressure.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int p;
                int gewei = progress % 10;
                String str = "";
                if (gewei != 0) {
                    double result = (double) progress / 10;
                    str = "" + result;
                } else {
                    int result = progress / 10;
                    str = "" + result;
                }
                et_pressure.setText(str);
                et_pressure.setSelection(et_pressure.getText().length());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton) group.findViewById(checkedId);
                String result = radioButton.getText().toString();
                if (result.equals("P1/P")) {
                    psDrive.setDrMode(0);

                } else if (result.equals("n1/P")) {
                    psDrive.setDrMode(1);
                }
            }
        });

        dr_time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //Toast.makeText(getActivity(),dr_time.getText().toString(),Toast.LENGTH_SHORT).show();
                if (!"".equals(dr_time.getText().toString())){
                    psDrive.setDrRamptime(Integer.valueOf(dr_time.getText().toString()));
                }else {
                    psDrive.setDrRamptime(Integer.valueOf("0"));
                }
            }
        });

        et_pressure.addTextChangedListener(new EditTextJudgeNumberWatcher(et_pressure) {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                judgeNumber(s, et_pressure);
                String toast = "";
                double p = 0;
                try {
                    p = Double.parseDouble(s.toString());
                } catch (Exception e) {
                    p = 0;
                }

                if (p < 0 || p > 5000) {
                    toast = "数值范围：0~5000";
                    sb_pressure.setProgress(0);
                } else {
                    toast = "";
                    psDrive.setDrLoad(p);
                    int temp = (int) (p * 10);
                    sb_pressure.setProgress(temp);
                    _pressure = temp;// 记录新修改的pressure值
                }
                if (!"".equals(toast)) {
                    Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT).show();
                }
            }
        });


        revese_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    input_speed.setText("转速：");
                    revese_img.setPressed(true);
                    psDrive.setDrReverse(1);

                } else {
                    input_speed.setText("给定负载转速：");
                    revese_img.setPressed(false);
                    psDrive.setDrReverse(0);
                }
            }
        });

        remote_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    remote_img.setPressed(true);
                    psDrive.setDrRemotestatus(1);

                } else {
                    remote_img.setPressed(false);
                    psDrive.setDrRemotestatus(0);
                }
            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        try {
                            Integer psId = myApplication.getNowPsBench().getPsId();
                            String phoneId=myApplication.getUser().getPhoneId();
                            String path = "http://47.106.32.2:80/home/manc/drive"+psDrive.toString();
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
        });

    }


}
