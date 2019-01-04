package com.sxu.timetask;

import com.sxu.control.AirQualityForecast;
import com.sxu.util.DateAndTimeUtil;

import java.util.TimerTask;

/**
 * 暂定如下：
 * 一天为一个周期，执行定时任务，推送本次执行后的一天的数据
 */
public class AirQualityForecastPost extends TimerTask {
    String[] siteNameArr = {"长治八中", "德盛苑", "监测站", "清华站", "审计局"};

    @Override
    public void run() {
        String date = "";
        try {
            date = DateAndTimeUtil.getTomorrowDate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < siteNameArr.length; i++) {
            for (int j = 0; j < 24; j++) {
                try {
                    AirQualityForecast.postAirQualityForecastJson(siteNameArr[i], date, String.valueOf(j));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
