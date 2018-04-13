package com.example.psapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 永远有多远 on 2018/4/13.
 */

public class TitleActivity extends AppCompatActivity implements View.OnClickListener{

    //private RelativeLayout mLayoutTitleBar;
    private TextView mTitleTextView;
    private TextView txt_signout;
    private TextView txt_backward;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_title);
        setupViews();   //加载 activity_title 布局 ，并获取标题及两侧按钮
    }


    private void setupViews() {
        mTitleTextView = (TextView) findViewById(R.id.text_title);
        txt_signout = (TextView) findViewById(R.id.txt_signout);
//        txt_backward= (TextView) findViewById(R.id.txt_backward);

        txt_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TitleActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

//      txt_backward.setVisibility(View.INVISIBLE);
//                txt_signout.setVisibility(View.VISIBLE);

    /**
     * 是否显示返回按钮
     *
     * @param backwardResid 文字
     * @param show          true则显示
     */
//    protected void showBackwardView(int backwardResid, boolean show) {
//        if (mBackwardbButton != null) {
//            if (show) {
//                mBackwardbButton.setText(backwardResid);
//                mBackwardbButton.setVisibility(View.VISIBLE);
//            } else {
//                mBackwardbButton.setVisibility(View.INVISIBLE);
//            }
//        } // else ignored
//    }


    /**
     * 返回按钮点击后触发
     *
     * @param backwardView
     */
    protected void onBackward(View backwardView) {
        Toast.makeText(this, "点击返回，可在此处调用finish()", Toast.LENGTH_LONG).show();
        //finish();
    }

    /**
     * 提交按钮点击后触发
     *
     * @param forwardView
     */
    protected void onForward(View forwardView) {
        Toast.makeText(this, "点击提交", Toast.LENGTH_LONG).show();
    }


    //设置标题内容
    @Override
    public void setTitle(int titleId) {
        mTitleTextView.setText(titleId);
    }

    //设置标题内容
    @Override
    public void setTitle(CharSequence title) {
        mTitleTextView.setText(title);
    }

    //设置标题文字颜色
    @Override
    public void setTitleColor(int textColor) {
        mTitleTextView.setTextColor(textColor);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_signout:
                Toast.makeText(this, "退出登录", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     * 按钮点击调用的方法
     */
//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()) {
//            case R.id.txt_signout:
//                Intent intent = new Intent(TitleActivity.this, MainActivity.class);
//                startActivity(intent);
//                break;
//            default:
//                break;
//        }
//    }
}