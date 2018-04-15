package com.example.psapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.psapp.bean.PsDevice;
import com.example.psapp.bean.PsLoad;
import com.example.psapp.bean.PsLog;

import java.util.List;

/**
 * Created by 永远有多远 on 2018/4/15.
 */

public class LogAdapter extends ArrayAdapter<PsLog> {
    private int resourceId;

    public LogAdapter(Context context, int textViewResourceId, List<PsLog> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= null;
        try {
            PsLog psLog = getItem(position);          //获取当前项的实例
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            TextView logName=(TextView) view.findViewById(R.id.log_name);
            TextView psIdLog=(TextView) view.findViewById(R.id.ps_id_log);
            TextView logTime=(TextView) view.findViewById(R.id.log_time);
            logName.setText(psLog.getLogErrorMsg());
            psIdLog.setText("台架" + String.valueOf(psLog.getPsId()));
            String time = DateUtilsl.getDateToString(psLog.getLogTime());
            logTime.setText(time);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return view;
    }
}
