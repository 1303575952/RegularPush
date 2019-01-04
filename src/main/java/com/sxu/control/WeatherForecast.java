package com.sxu.control;

import com.sxu.dao.JDBCDao;
import com.sxu.data.DateTime;
import com.sxu.util.HttpClientUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class WeatherForecast {
    public static void main(String[] args) throws Exception {
        new WeatherForecast().postWeatherForecastJson("2018年12月20日", "8");
    }

    /**
     * @param date
     * @param time
     * @throws Exception
     */
    public void postWeatherForecastJson(String date, String time) throws Exception {
        //TODO 两个空格
        String dateTime = DateTime.getDateTimeTwoSpace(date, time);
        Map<String, String> weatherValueMap = WeatherForecast.getWeatherForecastDB(dateTime);
        String temp = "0";
        if (weatherValueMap.get("temp") != null) {
            temp = weatherValueMap.get("temp");
        }
        String pressure = "0";
        if (weatherValueMap.get("pressure") != null) {
            pressure = weatherValueMap.get("pressure");
        }
        String rh = "0";
        if (weatherValueMap.get("rh") != null) {
            rh = weatherValueMap.get("rh");
        }
        String windSpeed = "0";
        if (weatherValueMap.get("windSpeed") != null) {
            windSpeed = weatherValueMap.get("windSpeed");
        }
        String windDirection = "0";
        if (weatherValueMap.get("windDirection") != null) {
            windDirection = weatherValueMap.get("windDirection");
        }
        String totalCloudCover = "0";
        if (weatherValueMap.get("totalCloudCover") != null) {
            totalCloudCover = weatherValueMap.get("totalCloudCover");
        }
        String jsonStr = WeatherForecast.generateWeatherForecastJson(date, time, temp, pressure, rh, windSpeed, windDirection, totalCloudCover);
        System.out.println("jsonStr=" + jsonStr);
        String url = "http://119.90.57.34:9680/channel/do";
        HttpClientUtil.httpPostWithJSON(url, jsonStr);
    }

    public static Map<String, String> getWeatherForecastDB(String dateTime) throws Exception {
        Map<String, String> weatherValueMap = new HashMap<>();
        ResultSet rs;
        Connection conn = JDBCDao.getConn();
        PreparedStatement selectWeatherForecast = conn.prepareStatement("select distinct surface_temp,pressure,rh,surface_wind,wind_dir,cloud_cover from sys_weather_info where valid_time=?");
        selectWeatherForecast.setString(1, dateTime);
        System.out.println(selectWeatherForecast);
        rs = selectWeatherForecast.executeQuery();
        while (rs.next()) {
            String temp = rs.getString("surface_temp");
            String pressure = rs.getString("pressure");
            String rh = rs.getString("rh");
            String windSpeed = rs.getString("surface_wind");
            String windDirection = rs.getString("wind_dir");
            String totalCloudCover = rs.getString("cloud_cover");
            weatherValueMap.put("temp", temp);
            weatherValueMap.put("pressure", pressure);
            weatherValueMap.put("rh", rh);
            weatherValueMap.put("windSpeed", windSpeed);
            weatherValueMap.put("windDirection", windDirection);
            weatherValueMap.put("totalCloudCover", totalCloudCover);
        }
        selectWeatherForecast.close();
        conn.close();
        rs.close();
        return weatherValueMap;
    }

    public static String generateWeatherForecastJson(String date, String time, String temp, String pressure, String rh, String windSpeed, String windDirection, String totalCloudCover) {
        String jsonStr = "{\n" +
                "\"async\": 1,\n" +
                "\"callback\": \"\",\n" +
                "\"method\": \"天气预报\",\n" +
                "\"param\": [{\"站点\" : \"长治\",\n" +
                "\"日期\" : \"" + date + "\",\n" +
                "\"时间\" : \"" + time + "\",\n" +
                "\"天气\" : {\n" +
                "\"Temp\" : \"" + temp + "\",\n" +
                "\"Pressure\" : \"" + pressure + "\",\n" +
                "\"RH\" : \"" + rh + "\",\n" +
                "\"WindSpeed\" : \"" + windSpeed + "\",\n" +
                "\"WindDirection\" : \"" + windDirection + "\",\n" +
                "\"TotalCloudCover\" : \"" + totalCloudCover + "\"\n" +
                "}\n" +
                "}]\n" +
                "}";
        return jsonStr.trim();
    }
}
