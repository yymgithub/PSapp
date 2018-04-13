package com.example.psapp;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

/**
 * Created by 永远有多远 on 2018/4/12.
 */

public class FourFragment extends Fragment {
    private MyApplication myApplication;
    private CircleMenu circleMenu;
    private TextView txt1, txt2, txt3, txt4, txt5, txt6;
    protected static final int ERROR = 0;
    protected static final int SUCCESS = 3;
    protected static final int ERRORNET = 1;

    public FourFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.four_fragment, container, false);
        txt1 = (TextView) view.findViewById(R.id.txt_file);
        Drawable drawable1 = getResources().getDrawable(R.drawable.icon_file);
        drawable1.setBounds(0, 0, 55, 55);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        txt1.setCompoundDrawables(drawable1, null, null, null);//只放左边

        txt2 = (TextView) view.findViewById(R.id.txt_select_file);
        Drawable drawable2 = getResources().getDrawable(R.drawable.icon_select_file);
        drawable2.setBounds(0, 0, 50, 50);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        txt2.setCompoundDrawables(drawable2, null, null, null);//只放左边

        txt3 = (TextView) view.findViewById(R.id.txt_pause_file);
        Drawable drawable3 = getResources().getDrawable(R.drawable.icon_pause);
        drawable3.setBounds(0, 0, 50, 50);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        txt3.setCompoundDrawables(drawable3, null, null, null);//只放左边

        txt4 = (TextView) view.findViewById(R.id.txt_continue_file);
        Drawable drawable4 = getResources().getDrawable(R.drawable.icon_continue);
        drawable4.setBounds(0, 0, 50, 50);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        txt4.setCompoundDrawables(drawable4, null, null, null);//只放左边

        txt5 = (TextView) view.findViewById(R.id.txt_stop_file);
        Drawable drawable5 = getResources().getDrawable(R.drawable.icon_cease);
        drawable5.setBounds(0, 0, 50, 50);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        txt5.setCompoundDrawables(drawable5, null, null, null);//只放左边

        txt6 = (TextView) view.findViewById(R.id.txt_device_state);
        Drawable drawable6 = getResources().getDrawable(R.drawable.icon_device);
        drawable6.setBounds(0, 0, 50, 50);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        txt6.setCompoundDrawables(drawable6, null, null, null);//只放左边
        myApplication = (MyApplication) this.getActivity().getApplication();
        init(view);
        return view;
    }

    private void init(View view) {
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    HomeActivity mainActivity = (HomeActivity) getActivity();
                    mainActivity.gotoDownloadFragment();
                } catch (Throwable e) {
                    System.out.println();
                }
            }
        });

        circleMenu = (CircleMenu) view.findViewById(R.id.circle_menu_file);

        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.mipmap.icon_menu, R.mipmap.icon_cancel)
                .addSubMenu(Color.parseColor("#8A8A8A"), R.mipmap.file)
                .addSubMenu(Color.parseColor("#30A400"), R.mipmap.selectfile)
                .addSubMenu(Color.parseColor("#FF4B32"), R.mipmap.pause)
                .addSubMenu(Color.parseColor("#8A39FF"), R.mipmap.continuees)
                .addSubMenu(Color.parseColor("#FF6A00"), R.mipmap.cease)
                .addSubMenu(Color.parseColor("#1296db"), R.mipmap.device)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {

                    @Override
                    public void onMenuSelected(int index) {
                        String comName = null;
                        switch (index) {
                            case 0:
                                try {
                                    HomeActivity mainActivity = (HomeActivity) getActivity();
                                    mainActivity.gotoDownloadFragment();
                                } catch (Throwable e) {
                                    break;
                                }
                                break;

                            case 1:
                                try {
                                    Integer re = myApplication.getNowPsBench().getPsStop();
                                    if (re == 0) {
                                        Toast.makeText(getActivity(), "车辆已处于启动状态，不能再次启动", Toast.LENGTH_SHORT).show();
                                        break;
                                    } else {
                                        comName = "启动变频器";
                                        myApplication.setComName(comName);
                                        break;
                                    }
                                } catch (Throwable e) {
                                    break;
                                }
                            case 2:
                                try {
                                    Integer re = myApplication.getNowPsBench().getPsStop();
                                    if (re == 1) {
                                        Toast.makeText(getActivity(), "车辆已处于关闭状态，不能再次停止", Toast.LENGTH_SHORT).show();
                                        break;
                                    } else {
                                        comName = "关闭变频器";
                                        myApplication.setComName(comName);
                                        break;
                                    }
                                } catch (Throwable e) {
                                    break;
                                }

                            case 3:

                                try {
                                    Integer re = myApplication.getNowPsBench().getPsAlarm();
                                    if (re == 1) {
                                        Toast.makeText(getActivity(), "车辆已处于报警状态，不能再次报警", Toast.LENGTH_SHORT).show();
                                        break;
                                    } else {
                                        comName = "报警";
                                        myApplication.setComName(comName);
                                        break;
                                    }
                                } catch (Throwable e) {
                                    break;
                                }

                            case 4:
                                try {
                                    Integer re = myApplication.getNowPsBench().getPsAlarm();
                                    if (re == 0) {
                                        Toast.makeText(getActivity(), "车辆未报警，不能操作", Toast.LENGTH_SHORT).show();
                                        break;
                                    } else {
                                        comName = "报警复位";
                                        myApplication.setComName(comName);
                                        break;
                                    }
                                } catch (Throwable e) {
                                    break;
                                }
                            default:
                                break;
                        }
                    }

                }).setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {

            @Override
            public void onMenuOpened() {
            }

            @Override
            public void onMenuClosed() {
            }

        });
    }
}
