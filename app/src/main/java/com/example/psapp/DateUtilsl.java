package com.example.psapp;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 永远有多远 on 2018/4/14.
 */

public class DateUtilsl {
    private static SimpleDateFormat sf = null;

//    sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /*时间戳转换成字符窜*/
    public static String getDateToString(String time) {
        Date d = new Date(Long.valueOf(time));
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }
}
