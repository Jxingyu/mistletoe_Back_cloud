package com.xy.zuul.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author liangd
 * date 2020-11-30 09:13
 * code 时间转换工具类
 */
public class DateUtils {
    /**
     * 替换T
     * @param str
     * @return
     */
    public static String getDate(String str){
        return str.replace("T"," ");
    }

    /**
     * 获取当天时间
     * @return
     */
    public static String getCurrentDay(){
        Date date=new Date();
        // 获取日历
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 在当前日基础上+1，前一天-1，当天0
        calendar.add(Calendar.DAY_OF_MONTH, 0);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(calendar.getTime());
    }

    /**
     * 获取当天下一天
     * @return
     */
    public static String getNextDay(){
        Date date=new Date();
        // 获取日历
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 在当前日基础上+1，前一天-1，当天0
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        return format.format(calendar.getTime());
    }
}
