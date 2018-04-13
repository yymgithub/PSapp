package com.example.psapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.psapp.bean.PsParameter;

import java.util.List;

/**
 * Created by 永远有多远 on 2018/4/12.
 */

public class gearAdapter extends ArrayAdapter<String> {
    private int resourceId;

    public gearAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String str = getItem(position);          //获取当前项的实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView gearName=(TextView) view.findViewById(R.id.gear_name);
        gearName.setText(str);
        return view;
    }

}