package com.example.psapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.psapp.bean.PsTestRecord;

/**
 * Created by 永远有多远 on 2018/4/15.
 */

public class MoreFirstFragment  extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    protected static final int ERROR = 0;
    protected static final int SUCCESS = 3;
    protected static final int ERRORNET = 1;

    private MyApplication myApplication;
    private SwipeRefreshLayout swipeLayout;
    private RotateTextView mTextView1;
    private RotateTextView mTextView2;
    private RotateTextView mTextView3;
    public MoreFirstFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.more_first_fragment, container, false);
        myApplication = (MyApplication) this.getActivity().getApplication();
        mTextView3 = (RotateTextView)view.findViewById(R.id.load_speed1);
        mTextView3.setDegrees(90);
        mTextView2 = (RotateTextView)view.findViewById(R.id.load_power1);
        mTextView2.setDegrees(90);
        mTextView1 = (RotateTextView)view.findViewById(R.id.load_niuju1);
        mTextView1.setDegrees(90);
        //初始化下拉刷新
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_img);
        swipeLayout.setColorSchemeColors(Color.GRAY);
        swipeLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onRefresh() {

    }
}
