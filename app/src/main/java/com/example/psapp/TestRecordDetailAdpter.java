package com.example.psapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.psapp.bean.PsDevice;
import com.example.psapp.bean.PsTestRecordDetail;

import java.util.List;

/**
 * Created by 永远有多远 on 2018/4/15.
 */

public class TestRecordDetailAdpter  extends ArrayAdapter<PsTestRecordDetail> {
    private int resourceId;

    public TestRecordDetailAdpter(Context context, int textViewResourceId, List<PsTestRecordDetail> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= null;
        try {
            PsTestRecordDetail psTestRecordDetail = getItem(position);          //获取当前项的实例
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            TextView testDetailPsId=(TextView) view.findViewById(R.id.test_record_detail_psId);
            TextView testDetailPara=(TextView) view.findViewById(R.id.test_record_detail_para);
            testDetailPsId.setText("台架"+String.valueOf(psTestRecordDetail.getPsId()));
            testDetailPara.setText(psTestRecordDetail.getDetailPara());
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return view;
    }
}
