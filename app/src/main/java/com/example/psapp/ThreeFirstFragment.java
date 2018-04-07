package com.example.psapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by 永远有多远 on 2018/4/3.
 */

public class ThreeFirstFragment extends Fragment {
    private TextView mTv;
    private String context;
    private RadioGroup radioGroup;

    public ThreeFirstFragment (String context){

            this.context = context;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.three_first_fragment, container, false);
        //初始化控件
        radioGroup= (RadioGroup) view.findViewById(R.id.radioGroup);

        //radioButton获取所选数据
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                String result = radioButton.getText().toString();
            }
        });

        return view;
    }

}
