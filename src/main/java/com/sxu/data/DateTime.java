package com.sxu.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTime {
    public static String getDateTime(String date, String time) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date sDate = sdf.parse(date);
        Calendar c = Calendar.getInstance();
        c.setTime(sDate);
        c.add(Calendar.HOUR_OF_DAY, Integer.valueOf(time));
        sDate = c.getTime();
        SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newDateTime = sdf0.format(sDate);
        return newDateTime;
    }
    public static String getDateTimeTwoSpace(String date, String time) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date sDate = sdf.parse(date);
        Calendar c = Calendar.getInstance();
        c.setTime(sDate);
        c.add(Calendar.HOUR_OF_DAY, Integer.valueOf(time));
        sDate = c.getTime();
        SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        String newDateTime = sdf0.format(sDate);
        return newDateTime;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(DateTime.getDateTime("2018年12月30日", "22"));
    }
}
