package com.example.psapp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.psapp.bean.PsTestRecord;
import com.example.psapp.bean.PsTestRecordDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 永远有多远 on 2018/4/15.
 */

public class MoreTestDetailFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    protected static final int ERROR = 0;
    protected static final int SUCCESS = 3;
    protected static final int ERRORNET = 1;

    private ListView listViewTestDetail;
    private MyApplication myApplication;
    private SwipeRefreshLayout swipeLayout;


    public MoreTestDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.more_three_detail_fragment, container, false);
        listViewTestDetail = (ListView) view.findViewById(R.id.list_view_test_record_detail);
        myApplication = (MyApplication) this.getActivity().getApplication();
        //初始化下拉刷新
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_test_record_detail);
        swipeLayout.setColorSchemeColors(Color.GRAY);
        swipeLayout.setOnRefreshListener(this);
        getTestData();

        return view;
    }
    void getTestData(){
        try{
            PsTestRecord psTestRecord=myApplication.getPsTestRecord();
            List<PsTestRecordDetail>  psTestRecordDetailList=new ArrayList<>();
            String[] temp = psTestRecord.getTestPara().split("[;]");
            for(int i=0;i<temp.length;i++){
                PsTestRecordDetail psTestRecordDetail=new PsTestRecordDetail();
                psTestRecordDetail.setPsId(psTestRecord.getPsId());
                psTestRecordDetail.setDetailPara(temp[i]);
                psTestRecordDetailList.add(psTestRecordDetail);
            }
            listViewTestDetail.setAdapter(new TestRecordDetailAdpter(getActivity(), R.layout.test_record_detail_item, psTestRecordDetailList));
        }catch(Throwable e){

        }


    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                swipeLayout.setRefreshing(false);
                getTestData();
            }
        }, 500);
    }
}
