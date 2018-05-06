package com.example.psapp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.psapp.bean.PsChart;
import com.example.psapp.bean.PsParameter;
import com.github.mikephil.charting.charts.LineChart;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by 永远有多远 on 2018/4/17.
 */

public class MoreFiveChartFragment  extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    protected static final int ERROR = 0;
    protected static final int SUCCESS = 3;
    protected static final int ERRORNET = 1;

    //绘制折线图所用
    private LineChart lineChart;
    private List<PsChart> list=new ArrayList<>();
    private MyApplication myApplication;
    private SwipeRefreshLayout swipeLayout;
    private LineChartManager lineChartManager1;
    public MoreFiveChartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.more_five_chart_fragment, container, false);
        myApplication = (MyApplication) this.getActivity().getApplication();
        //初始化下拉刷新
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_chart);
        swipeLayout.setColorSchemeColors(Color.GRAY);
        swipeLayout.setOnRefreshListener(this);
        lineChart = (LineChart)view.findViewById(R.id.line_chart1);
        lineChartManager1 = new LineChartManager(lineChart);
        getChartData();
        return view;
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case ERROR:
                    Toast.makeText(getActivity(), (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case ERRORNET:
                    Toast.makeText(getActivity(), "发送失败", Toast.LENGTH_SHORT).show();
                    break;
                case SUCCESS:
                    List<PsChart> psChartList = (List<PsChart>) msg.obj;
                    list=psChartList;
                    //设置x轴的数据
                    ArrayList<Float> xValues = new ArrayList<>();
                    for (int i = 0; i <= 12; i++) {
                        xValues.add((float) i);
                    }

                    //设置y轴的数据()
                    List<List<Float>> yValues = new ArrayList<>();
                    for (int i = 0; i < 2; i++) {
                        List<Float> yValue = new ArrayList<>();
                        if(i==0){
                            for (int j = 0; j <= 12; j++) {
                                yValue.add((float) (list.get(j).getDriveChart()));
                            }
                            yValues.add(yValue);
                        }
                        if(i==1){
                            for (int j = 0; j <= 12; j++) {
                                yValue.add((float) (list.get(j).getTmpChart()));
                            }
                            yValues.add(yValue);
                        }

                    }

                    //颜色集合
                    List<Integer> colours = new ArrayList<>();
                    colours.add(Color.GREEN);
                    colours.add(Color.CYAN);

                    //线的名字集合
                    List<String> names = new ArrayList<>();
                    names.add("驱动转速");
                    names.add("变速箱温度");

                    //创建多条折线的图表
                    lineChartManager1.showLineChart(xValues, yValues, names, colours);
                    lineChartManager1.setDescription("温度");
                    lineChartManager1.setYAxis(3000, 0, 11);
                    lineChartManager1.setXAxis(12,0,11);
                    break;
            }
        }
    };
    void getChartData(){
        new Thread() {
            public void run() {
                try {
                    Integer psId = myApplication.getNowPsBench().getPsId();
                    String path = "http://47.106.32.2:80/home/more/getChart?psId=" + psId;
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; KB974487)");
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        InputStream is = conn.getInputStream();
                        String result = StreamTools.readInputStream(is);
                        JSONObject resultJson = new JSONObject(result);
                        boolean success = resultJson.getBoolean("success");
                        if (success) {
                            JSONObject data = (JSONObject) resultJson.get("data");
                            String psChartListString = data.getString("psChartList");
                            List<PsChart> psChartList = JSON.parseArray(psChartListString, PsChart.class);
                            Message msg = Message.obtain();
                            msg.what = SUCCESS;
                            msg.obj = psChartList;
                            handler.sendMessage(msg);

                        } else {
                            Message msg = Message.obtain();
                            msg.what = ERROR;
                            String message = resultJson.getString("message");
                            msg.obj = message;
                            handler.sendMessage(msg);
                        }
                    } else {
                        Message msg = Message.obtain();
                        msg.what = ERRORNET;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = Message.obtain();
                    msg.what = ERRORNET;
                    handler.sendMessage(msg);
                }
            }

            ;
        }.start();
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                swipeLayout.setRefreshing(false);
                getChartData();
            }
        }, 500);
    }
}
