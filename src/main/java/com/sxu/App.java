package com.sxu;

import com.sxu.timetask.AirQualityForecastPost;
import com.sxu.timetask.TraceSourcePost;
import com.sxu.timetask.WeatherForecastPost;
import com.sxu.util.DateAndTimeUtil;

import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static void main(String[] args) {
        final Timer timer = new Timer();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                timer.schedule(new TraceSourcePost(),DateAndTimeUtil.getPostTime(),86400000);
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                timer.schedule(new AirQualityForecastPost(),DateAndTimeUtil.getPostTime(),86400000);
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                timer.schedule(new WeatherForecastPost(),DateAndTimeUtil.getPostTime(),86400000);
            }
        });

    }
}
