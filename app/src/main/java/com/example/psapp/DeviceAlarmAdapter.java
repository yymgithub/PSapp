package com.example.psapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.psapp.bean.PsDevice;
import com.example.psapp.bean.PsDeviceAlarm;

import java.util.List;

/**
 * Created by 永远有多远 on 2018/4/14.
 */

public class DeviceAlarmAdapter extends ArrayAdapter<PsDeviceAlarm> {
    private int resourceId;

    public DeviceAlarmAdapter(Context context, int textViewResourceId, List<PsDeviceAlarm> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        try {
            PsDeviceAlarm psDeviceAlarm = getItem(position);          //获取当前项的实例
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            TextView deviceName = (TextView) view.findViewById(R.id.device_alarm_name);
            TextView deviceState = (TextView) view.findViewById(R.id.device_alarm_state);
            TextView deviceTime = (TextView) view.findViewById(R.id.device_alarm_time);
            deviceState.setText(String.valueOf(psDeviceAlarm.getDeState()));
            deviceName.setText("台架" + String.valueOf(psDeviceAlarm.getPsId()));
            String time = DateUtilsl.getDateToString(psDeviceAlarm.getCreated());
            deviceTime.setText(time);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return view;
    }
}