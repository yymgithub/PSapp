package com.example.psapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 永远有多远 on 2018/4/3.
 */

public class ThreeFragment  extends Fragment {
    private TabLayout mTb;
    private ViewPager mVp;
    private List<Fragment> mFragmentList;
    private List<String> mTitleList;
    public ThreeFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.three_fragment,container,false);
        //初始化控件
        initView(view);
        //添加标题
        initTitile();
        //添加fragment
        initFragment();
        //设置适配器
        mVp.setAdapter(new MyPagerAdapter(this.getActivity().getSupportFragmentManager(), mFragmentList, mTitleList));
        //将tablayout与fragment关联
        mTb.setupWithViewPager(mVp);

        return view;
    }
    private void initTitile() {
        mTitleList = new ArrayList<>();
        mTitleList.add("驱动电机");
        mTitleList.add("负载电机");
        mTitleList.add("温度控制");
        mTitleList.add("换档");
        //设置tablayout模式
        mTb.setTabMode(TabLayout.MODE_FIXED);
        //tablayout获取集合中的名称
        mTb.addTab(mTb.newTab().setText(mTitleList.get(0)));
        mTb.addTab(mTb.newTab().setText(mTitleList.get(1)));
        mTb.addTab(mTb.newTab().setText(mTitleList.get(2)));
    }

    private void initFragment() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new ThreeFirstFragment("第一个界面"));
        mFragmentList.add(new ThreeFirstFragment("第二个界面"));
        mFragmentList.add(new ThreeFirstFragment("第三个界面"));
        mFragmentList.add(new ThreeFirstFragment("第三个界面"));
    }

    private void initView(View view) {
        mTb = (TabLayout) view.findViewById(R.id.mTb);
        mVp = (ViewPager) view.findViewById(R.id.mVp);
    }
}
