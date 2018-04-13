package com.example.psapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private MyApplication myApplication = (MyApplication) this.getApplication();
    private TextView topBar;
    private TextView tabDeal;
    private TextView tabCom;
    private TextView tabMore;
    private TextView tabManc;

    private FrameLayout ly_content;

    private FirstFragment f1;
    private SecondFragment f2;
    private ThreeFragment f3;
    private FourFragment f4;
    private SecondFragment f5;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        bindView();
    }
    //UI组件初始化与事件绑定
    private void bindView() {
        tabDeal = (TextView)this.findViewById(R.id.txt_deal);
        tabCom = (TextView)this.findViewById(R.id.tab_com);
        tabManc = (TextView)this.findViewById(R.id.tab_manc);
        tabMore = (TextView)this.findViewById(R.id.txt_more);
        ly_content = (FrameLayout) findViewById(R.id.fragment_container);

        tabDeal.setOnClickListener(this);
        tabMore.setOnClickListener(this);
        tabManc.setOnClickListener(this);
        tabCom.setOnClickListener(this);

        getSupportActionBar().hide();
    }

    //重置所有文本的选中状态
    public void selected(){
        tabDeal.setSelected(false);
        tabMore.setSelected(false);
        tabCom.setSelected(false);
        tabManc.setSelected(false);
    }

    //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction){
        if(f1!=null){
            transaction.hide(f1);
        }
        if(f2!=null){
            transaction.hide(f2);
        }
        if(f3!=null){
            transaction.hide(f3);
        }
        if(f4!=null){
            transaction.hide(f4);
        }
        if(f5!=null){
            transaction.hide(f5);
        }
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch(v.getId()){
            case R.id.txt_deal:
                selected();
                tabDeal.setSelected(true);
                if(f1==null){
                    f1 = new FirstFragment("第一个Fragment");
                    transaction.add(R.id.fragment_container,f1);
                }else{
                    transaction.show(f1);
                }
                break;

            case R.id.tab_com:
                selected();
                tabCom.setSelected(true);
                if(f2==null){
                    f2 = new SecondFragment();
                    transaction.add(R.id.fragment_container,f2);
                }else{
                    transaction.show(f2);
                }
                break;

            case R.id.tab_manc:
                selected();
                tabManc.setSelected(true);
                if(f3==null){
                    f3 = new ThreeFragment();
                    transaction.add(R.id.fragment_container,f3);
                }else{
                    transaction.show(f3);
                }
                break;
            case R.id.txt_more:
                selected();
                tabMore.setSelected(true);
                if(f4==null){
                    f4 = new FourFragment();
                    transaction.add(R.id.fragment_container,f4);
                }else{
                    transaction.show(f4);
                }
                break;
        }

        transaction.commit();
    }


    public void gotoDownloadFragment() {    //去下载页面
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        if(f5==null){
            f5 = new SecondFragment();
            transaction.add(R.id.fragment_container,f5);
        }else{
            transaction.show(f5);
        }
        transaction.commit();
    }
}
