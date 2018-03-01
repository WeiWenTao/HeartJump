package com.cucr.myapplication.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by cucr on 2018/1/26.
 */

public class DataUtils {
    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }

    public static long getDifferTime(String date) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse1 = df.parse(df.format(new Date()));  //当前时间
        Date parse2 = df.parse(date);
        long l = parse2.getTime() - parse1.getTime();
        return l;
    }

    //分钟转换成时和天
    public static String getDateByMinutes(int i) {
        if (i < 60) {
            return i + " 分钟 ";
        }
        int h = i / 60;
        if (i % 60 > 0) {
            return h + " 小时 " + (i % 60) + " 分钟 ";
        } else {
            if (h >= 24) {
                int d = h / 24;
                if (h % 24 > 0) {
                    return d + "天" + h + " 小时 " + i + " 分钟 ";
                } else {
                    return d + " 天";
                }
            } else {
                return h + " 小时";
            }
        }
    }

    //计算时间差
    public static String getDifValue(String data) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(df.format(new Date()));
            Date d2 = df.parse(data);
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            if (days == 0) {
                return ("" + hours + "小时" + minutes + "分");
            }
            if (hours == 0) {
                return ("" + minutes + "分");
            }
            return ("" + days + "天" + hours + "小时" + minutes + "分");
        } catch (Exception e) {
            return ("");
        }
    }
}
