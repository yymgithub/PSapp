package com.example.psapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.psapp.bean.PsDrive;
import com.example.psapp.bean.PsLoad;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 永远有多远 on 2018/4/10.
 */

public class ThreeSecondFragment extends Fragment {
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
    private Switch remote_switch2;
    private Switch revese_switch2;
    private TextView input_speed2;
    private TextView remote_img2;
    private TextView revese_img2;
    private AppCompatSeekBar sb_pressure2;
    private AppCompatEditText et_pressure2;
    private EditText dr_time;
    private Button submitButton;
    private int pressure = 0, _pressure = 0;// 数值
    protected static final int ERROR = 0;
    protected static final int SUCCESS = 3;
    protected static final int ERRORNET = 1;
    private MyApplication myApplication;

    PsLoad psLoad=new PsLoad();


    public ThreeSecondFragment(String context) {

        this.context = context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.three_second_fragment, container, false);
        //初始化控件
        myApplication = (MyApplication) this.getActivity().getApplication();
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        dr_time = (EditText) view.findViewById(R.id.dr_time);

        sb_pressure = (AppCompatSeekBar) view.findViewById(R.id.sb_pressure);
        et_pressure = (AppCompatEditText) view.findViewById(R.id.et_pressure);
        et_pressure.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        remote_switch = (Switch) view.findViewById(R.id.remote_switch);
        revese_switch = (Switch) view.findViewById(R.id.revese_switch);
        input_speed = (TextView) view.findViewById(R.id.input_textView);
        remote_img = (TextView) view.findViewById(R.id.remote_image);
        revese_img = (TextView) view.findViewById(R.id.revase_image);


        sb_pressure.setProgress(0);// 先将进度条滑到最左端

        sb_pressure.setMax(50000);// 设置进度条可调节的数值范围长度，参数为int类型
        et_pressure.setText("0");// 设置初始显示值


        sb_pressure2 = (AppCompatSeekBar) view.findViewById(R.id.sb_pressure2);
        et_pressure2= (AppCompatEditText) view.findViewById(R.id.et_pressure2);
        et_pressure2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        remote_switch2 = (Switch) view.findViewById(R.id.remote_switch2);
        revese_switch2 = (Switch) view.findViewById(R.id.revese_switch2);
        input_speed2 = (TextView) view.findViewById(R.id.input_textView2);
        remote_img2 = (TextView) view.findViewById(R.id.remote_image2);
        revese_img2 = (TextView) view.findViewById(R.id.revase_image2);
        sb_pressure2.setProgress(0);// 先将进度条滑到最左端
        sb_pressure2.setMax(50000);// 设置进度条可调节的数值范围长度，参数为int类型
        et_pressure2.setText("0");// 设置初始显示值
        submitButton = (Button) view.findViewById(R.id.submit_button);
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

        psLoad.setPsId(myApplication.getNowPsBench().getPsId());
        psLoad.setPhoneId(myApplication.getUser().getPhoneId());
        psLoad.setLoMode(0);
        psLoad.setLoRamptime(0);
        psLoad.setLo1Speed(0);
        psLoad.setLo1Reverse(0);
        psLoad.setLo1Remote(0);
        psLoad.setLo2Speed(0);
        psLoad.setLo2Reverse(0);
        psLoad.setLo2Remote(0);
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
                    psLoad.setLoMode(0);

                } else if (result.equals("n1/P")) {
                    psLoad.setLoMode(1);
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
                    psLoad.setLoRamptime(Integer.valueOf(dr_time.getText().toString()));
                }else {
                    psLoad.setLoRamptime(Integer.valueOf("0"));
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
                    psLoad.setLo1Speed(p);
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
                    psLoad.setLo1Reverse(1);

                } else {
                    input_speed.setText("给定负载转速：");
                    revese_img.setPressed(false);
                    psLoad.setLo1Reverse(0);
                }
            }
        });

        remote_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    remote_img.setPressed(true);
                    psLoad.setLo1Remote(1);

                } else {
                    remote_img.setPressed(false);
                    psLoad.setLo1Remote(0);
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
                            String path = "http://47.106.32.2:80/home/manc/load"+psLoad.toString();
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


        sb_pressure2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                et_pressure2.setText(str);
                et_pressure2.setSelection(et_pressure2.getText().length());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        et_pressure2.addTextChangedListener(new EditTextJudgeNumberWatcher(et_pressure2) {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                judgeNumber(s, et_pressure2);
                String toast = "";
                double p = 0;
                try {
                    p = Double.parseDouble(s.toString());
                } catch (Exception e) {
                    p = 0;
                }

                if (p < 0 || p > 5000) {
                    toast = "数值范围：0~5000";
                    sb_pressure2.setProgress(0);
                } else {
                    toast = "";
                   psLoad.setLo2Speed(p);
                    int temp = (int) (p * 10);
                    sb_pressure2.setProgress(temp);
                    _pressure = temp;// 记录新修改的pressure值
                }
                if (!"".equals(toast)) {
                    Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT).show();
                }
            }
        });


        revese_switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    input_speed2.setText("转速：");
                    revese_img2.setPressed(true);
                    psLoad.setLo2Reverse(1);

                } else {
                    input_speed2.setText("给定负载转速：");
                    revese_img2.setPressed(false);
                    psLoad.setLo2Reverse(0);
                }
            }
        });

        remote_switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    remote_img2.setPressed(true);
                    psLoad.setLo2Remote(1);

                } else {
                    remote_img2.setPressed(false);
                    psLoad.setLo2Remote(0);
                }
            }
        });


    }


}
