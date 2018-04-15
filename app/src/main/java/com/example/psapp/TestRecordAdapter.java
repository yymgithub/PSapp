package com.example.psapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.psapp.bean.PsLog;
import com.example.psapp.bean.PsTestRecord;

import java.util.List;

/**
 * Created by 永远有多远 on 2018/4/15.
 */

public class TestRecordAdapter extends ArrayAdapter<PsTestRecord> {
    private int resourceId;

    public TestRecordAdapter(Context context, int textViewResourceId, List<PsTestRecord> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= null;
        try {
            PsTestRecord psTestRecord = getItem(position);          //获取当前项的实例
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            TextView testTime=(TextView) view.findViewById(R.id.test_record_time);
            String time = DateUtilsl.getDateToString(psTestRecord.getCreated());
            testTime.setText(time);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return view;
    }
}
