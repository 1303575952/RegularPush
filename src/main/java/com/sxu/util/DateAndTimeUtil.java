package com.sxu.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateAndTimeUtil {
    public static void main(String[] args) throws Exception {
        System.out.println(DateAndTimeUtil.getYesterdayDate());
    }

    public static String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        String todayDate = sdf.format(date);
        return todayDate;
    }

    public static String getYesterdayDate() throws Exception {
        String yesterdayDate;
        String todayDate = DateAndTimeUtil.getTodayDate();
        yesterdayDate = DateAndTimeUtil.dateDayIncrement(todayDate, -1);
        return yesterdayDate;
    }

    public static String getTomorrowDate() throws Exception {
        String yesterdayDate;
        String todayDate = DateAndTimeUtil.getTodayDate();
        yesterdayDate = DateAndTimeUtil.dateDayIncrement(todayDate, 1);
        return yesterdayDate;
    }

    public static String dateDayIncrement(String date, int amount) throws Exception {
        String newDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date sDate = sdf.parse(date);
        Calendar c = Calendar.getInstance();
        c.setTime(sDate);
        c.add(Calendar.DAY_OF_MONTH, amount);
        sDate = c.getTime();
        newDate = sdf.format(sDate);
        return newDate;
    }

    /**
     * 任务开始时间
     * @return
     */
    public static Date getPostTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2019);
        calendar.set(Calendar.MONTH, 00);
        calendar.set(Calendar.DAY_OF_MONTH, 04);
        calendar.set(Calendar.HOUR_OF_DAY, 01);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        Date time = calendar.getTime();
        return time;
    }
}
