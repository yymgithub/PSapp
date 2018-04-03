package com.example.psapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.psapp.bean.PsParameter;

import java.util.List;

/**
 * Created by 永远有多远 on 2018/4/1.
 */

public class ParaAdapter extends ArrayAdapter<PsParameter> {
    private int resourceId;
         public ParaAdapter(Context context, int textViewResourceId, List<PsParameter> objects){
                  super(context,textViewResourceId,objects);
                   resourceId=textViewResourceId;
            }



    @Override
          public View getView(int position, View convertView, ViewGroup parent){
                   PsParameter psParameter=getItem(position);          //获取当前项的实例
                   View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
                   TextView paraName=(TextView) view.findViewById(R.id.para_name);
                    paraName.setText(psParameter.getParaName());
                    TextView paraValue=(TextView) view.findViewById(R.id.para_value);
                    TextView paraUnit=(TextView) view.findViewById(R.id.para_unit);
                     String str=null;
                     if(psParameter.getParaFormat()==1){
                         java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.0");
                          str = myformat.format(psParameter.getParaValue());
                     }
                     else if(psParameter.getParaFormat()==2){
                         java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.00");
                          str = myformat.format(psParameter.getParaValue());
                     }
                     else if(psParameter.getParaFormat()==0){
                         java.text.DecimalFormat myformat = new java.text.DecimalFormat("0");
                         str = myformat.format(psParameter.getParaValue());
                     }
                     paraValue.setText(str);
                     paraUnit.setText(psParameter.getParaUnit());
                   return view;
           }
}
