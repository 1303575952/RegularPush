package com.sxu;

import com.sxu.timetask.AirQualityForecastPost;
import com.sxu.timetask.TraceSourcePost;
import com.sxu.timetask.WeatherForecastPost;
import com.sxu.util.DateAndTimeUtil;

import java.util.Timer;

public class App {
    public static void main(String[] args) {
        Timer timer = new Timer();
        //timer.schedule(new TraceSourcePost(),DateAndTimeUtil.getPostTime(),86400000);
        //timer.schedule(new AirQualityForecastPost(),DateAndTimeUtil.getPostTime(),86400000);
        timer.schedule(new WeatherForecastPost(),DateAndTimeUtil.getPostTime(),86400000);
    }
}
