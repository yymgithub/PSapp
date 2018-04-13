package com.example.psapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.psapp.bean.PsFile;

import java.util.List;

/**
 * Created by 永远有多远 on 2018/4/13.
 */

public class fileAdapter extends ArrayAdapter<PsFile> {
    private int resourceId;
    public fileAdapter(Context context, int textViewResourceId, List<PsFile> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PsFile psFile = getItem(position);          //获取当前项的实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView file_name = (TextView) view.findViewById(R.id.file_name);
        TextView subject_name = (TextView) view.findViewById(R.id.subject_name);
        file_name.setText(psFile.getFileName());
        subject_name.setText(psFile.getFileTestType());
        return view;
    }
}
