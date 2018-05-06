package com.example.psapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.psapp.bean.PsDataFile;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 永远有多远 on 2018/4/13.
 */

public class FourFirstFragment extends Fragment {
    private EditText et_test_subject_name;
    private EditText et_test_file_name;
    private EditText et_test_type;
    private EditText et_test_num;
    private EditText et_note;
    private EditText et_test_person;
    private Button btn_files;
    protected static final int ERROR = 0;
    protected static final int SUCCESS = 3;
    protected static final int ERRORNET = 1;
    private MyApplication myApplication;
    PsDataFile psDataFile = new PsDataFile();

    public FourFirstFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.four_first_fargment, container, false);
        init(view);
        return view;
    }

    void init(View view) {
        et_test_subject_name = (EditText) view.findViewById(R.id.et_subject_name);
        et_test_file_name = (EditText) view.findViewById(R.id.et_file_name);
        et_test_type = (EditText) view.findViewById(R.id.et_test_type);
        et_test_num = (EditText) view.findViewById(R.id.et_test_num);
        et_note = (EditText) view.findViewById(R.id.et_note);
        et_test_person = (EditText) view.findViewById(R.id.et_test_person);
        btn_files = (Button) view.findViewById(R.id.btn_files);
        myApplication = (MyApplication) this.getActivity().getApplication();
        submitFile();

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

    void submitFile() {

        btn_files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                psDataFile.setPsId(myApplication.getNowPsBench().getPsId());
                psDataFile.setPhoneId(myApplication.getUser().getPhoneId());
                psDataFile.setDaTestSubject(et_test_subject_name.getText().toString());
                psDataFile.setDaDataDocu(et_test_file_name.getText().toString());
                psDataFile.setTestNum(Integer.valueOf(et_test_num.getText().toString()));
                psDataFile.setTestType(et_test_type.getText().toString());
                psDataFile.setDaNote(et_note.getText().toString());
                psDataFile.setTestStaff(et_test_person.getText().toString());


                Integer psId = myApplication.getNowPsBench().getPsId();
                String phoneId = myApplication.getUser().getPhoneId();
                String daTestSubject = et_test_subject_name.getText().toString();
                String daDataDocu = et_test_file_name.getText().toString();
                String testType = et_test_type.getText().toString();
                Integer testNum = Integer.valueOf(et_test_num.getText().toString());
                String daNote = et_note.getText().toString();
                String testStaff = et_test_person.getText().toString();
                if (psId == null || phoneId == null || daTestSubject == null || daDataDocu == null || testType == null || testNum == null || daNote == null || testStaff == null) {
                    Toast.makeText(getActivity(), "输入不完整", Toast.LENGTH_SHORT).show();
                } else {
                    new Thread() {
                        public void run() {
                            try {
                                String path = "http://47.106.32.2:80/home/program/setFile" + psDataFile.toString();
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
        });
    }


}
