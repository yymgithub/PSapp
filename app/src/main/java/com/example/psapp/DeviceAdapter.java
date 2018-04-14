package com.example.psapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.psapp.bean.PsDevice;
import java.util.List;

/**
 * Created by 永远有多远 on 2018/4/14.
 */

public class DeviceAdapter extends ArrayAdapter<PsDevice> {
    private int resourceId;

    public DeviceAdapter(Context context, int textViewResourceId, List<PsDevice> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= null;
        try {
            PsDevice psDevice = getItem(position);          //获取当前项的实例
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            TextView deviceName=(TextView) view.findViewById(R.id.device_name);
            TextView deviceState=(TextView) view.findViewById(R.id.device_state);
            TextView deviceTime=(TextView) view.findViewById(R.id.device_time);
            deviceState.setText(String.valueOf(psDevice.getPsDevState()));
            deviceName.setText(psDevice.getPsDevName());
            String time = DateUtilsl.getDateToString(psDevice.getPsLastRecvtime());
            deviceTime.setText(time);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return view;
    }
}
