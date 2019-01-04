package com.sxu.timetask;

import com.sxu.control.WeatherForecast;
import com.sxu.util.DateAndTimeUtil;

import java.util.TimerTask;

/**
 * 暂定如下：
 * 一天为一个周期，执行定时任务，推送本次执行后的一天的数据
 */
public class WeatherForecastPost extends TimerTask {
    @Override
    public void run() {
        String date = "";
        try {
            date = DateAndTimeUtil.getTomorrowDate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 24; i++) {
            try {
                WeatherForecast.postWeatherForecastJson(date, String.valueOf(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
