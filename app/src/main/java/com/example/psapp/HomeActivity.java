package com.example.psapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private MyApplication myApplication = (MyApplication) this.getApplication();
    private TextView topBar;
    private TextView tabDeal;
    private TextView tabCom;
    private TextView tabMore;
    private TextView tabManc;
    private TextView mTitleTextView;

    private FrameLayout ly_content;

    private FirstFragment f1;
    private SecondFragment f2;
    private ThreeFragment f3;
    private FourFragment f4;
    private FourFirstFragment f5;
    private FourSecondFragment f6;
    private FragmentManager fragmentManager;
    private TextView txt_signout;
    private TextView txt_backward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        bindView();
    }

    //UI组件初始化与事件绑定
    private void bindView() {
        tabDeal = (TextView) this.findViewById(R.id.txt_deal);
        tabCom = (TextView) this.findViewById(R.id.tab_com);
        tabManc = (TextView) this.findViewById(R.id.tab_manc);
        tabMore = (TextView) this.findViewById(R.id.txt_more);
        ly_content = (FrameLayout) findViewById(R.id.fragment_container);
        mTitleTextView = (TextView) findViewById(R.id.text_title);
        txt_signout = (TextView) findViewById(R.id.txt_signout);
        txt_backward= (TextView) findViewById(R.id.txt_backward);
        tabDeal.setOnClickListener(this);
        tabMore.setOnClickListener(this);
        tabManc.setOnClickListener(this);
        tabCom.setOnClickListener(this);
        txt_signout.setOnClickListener(this);
        txt_backward.setOnClickListener(this);
        getSupportActionBar().hide();

    }

    //重置所有文本的选中状态
    public void selected() {
        tabDeal.setSelected(false);
        tabMore.setSelected(false);
        tabCom.setSelected(false);
        tabManc.setSelected(false);
    }

    //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction) {
        if (f1 != null) {
            transaction.hide(f1);
        }
        if (f2 != null) {
            transaction.hide(f2);
        }
        if (f3 != null) {
            transaction.hide(f3);
        }
        if (f4 != null) {
            transaction.hide(f4);
        }
        if (f5 != null) {
            transaction.hide(f5);
        }
        if (f6 != null) {
            transaction.hide(f6);
        }
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch (v.getId()) {
            case R.id.txt_deal:
                selected();
                tabDeal.setSelected(true);
                mTitleTextView.setText("查看参数");
                if (f1 == null) {
                    f1 = new FirstFragment("第一个Fragment");
                    transaction.add(R.id.fragment_container, f1);
                } else {
                    transaction.show(f1);
                }
                break;

            case R.id.tab_com:
                selected();
                tabCom.setSelected(true);
                mTitleTextView.setText("命令控制");
                if (f2 == null) {
                    f2 = new SecondFragment();
                    transaction.add(R.id.fragment_container, f2);
                } else {
                    transaction.show(f2);
                }
                break;

            case R.id.tab_manc:
                selected();
                tabManc.setSelected(true);
                mTitleTextView.setText("手动控制");
                if (f3 == null) {
                    f3 = new ThreeFragment();
                    transaction.add(R.id.fragment_container, f3);
                } else {
                    transaction.show(f3);
                }
                break;
            case R.id.txt_more:
                selected();
                tabMore.setSelected(true);
                mTitleTextView.setText("程序控制");
                if (f4 == null) {
                    f4 = new FourFragment();
                    transaction.add(R.id.fragment_container, f4);
                } else {
                    transaction.show(f4);
                }
                break;
            case R.id.txt_signout:
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.txt_backward:
                txt_backward.setVisibility(View.INVISIBLE);
                txt_signout.setVisibility(View.VISIBLE);
                selected();
                tabMore.setSelected(true);
                mTitleTextView.setText("程序控制");
                if (f4 == null) {
                    f4 = new FourFragment();
                    transaction.add(R.id.fragment_container, f4);
                } else {
                    transaction.show(f4);
                }
                break;
        }

        transaction.commit();
    }


        public void gotoDownloadFragment(Integer cha) {    //去下载页面
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);

            switch (cha) {
                case 0:
                    mTitleTextView.setText("");
                    txt_backward.setVisibility(View.VISIBLE);
                    txt_signout.setVisibility(View.INVISIBLE);
                    if (f5 == null) {
                        f5 = new FourFirstFragment();
                        transaction.add(R.id.fragment_container, f5);
                    } else {
                        transaction.show(f5);
                    }
                    transaction.commit();
                    break;
                case 1:
                    mTitleTextView.setText("");
                    txt_backward.setVisibility(View.VISIBLE);
                    txt_signout.setVisibility(View.INVISIBLE);
                    if (f6 == null) {
                        f6 = new FourSecondFragment();
                        transaction.add(R.id.fragment_container, f6);
                    } else {
                        transaction.show(f6);
                    }
                    transaction.commit();

                    break;
            }
    }
}
