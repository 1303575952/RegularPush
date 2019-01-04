package com.sxu.control;

import com.sxu.dao.JDBCDao;
import com.sxu.data.DateTime;
import com.sxu.data.SiteName;
import com.sxu.util.HttpClientUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class AirQualityForecast {
    public static void main(String[] args) throws Exception {
        AirQualityForecast.postAirQualityForecastJson("德盛苑", "2018年12月15日", "1");
    }

    /**
     * @param siteName
     * @param date
     * @param time
     * @throws Exception
     */
    public static void postAirQualityForecastJson(String siteName, String date, String time) throws Exception {
        String siteNameShort = SiteName.getSiteNameMap().get(siteName);
        String dateTime = DateTime.getDateTime(date, time);
        Map<String, String> pollutantValueMap = AirQualityForecast.getAirQualityForecastFromDB(siteNameShort, dateTime);
        String PM2_5 = "0";
        if (pollutantValueMap.get("PM2_5") != null) {
            PM2_5 = pollutantValueMap.get("PM2_5");
        }
        String PM10 = "0";
        if (pollutantValueMap.get("PM10") != null) {
            PM10 = pollutantValueMap.get("PM10");
        }
        String SO2 = "0";
        if (pollutantValueMap.get("SO2") != null) {
            SO2 = pollutantValueMap.get("SO2");
        }
        String NO2 = "0";
        if (pollutantValueMap.get("NO2") != null) {
            NO2 = pollutantValueMap.get("NO2");
        }
        String CO = "0";
        if (pollutantValueMap.get("CO") != null) {
            CO = pollutantValueMap.get("CO");
        }
        String O3 = "0";
        if (pollutantValueMap.get("O3") != null) {
            O3 = pollutantValueMap.get("O3");
        }
        String AQI = "0";
        if (pollutantValueMap.get("AQI") != null) {
            AQI = pollutantValueMap.get("AQI");
        }
        String jsonStr = AirQualityForecast.generateAirForecastJson(siteName, date, time, PM2_5, PM10, SO2, NO2, CO, O3, AQI);
        System.out.println("jsonStr=" + jsonStr);
        String url = "http://119.90.57.34:9680/channel/do";
        HttpClientUtil.httpPostWithJSON(url, jsonStr);
    }

    /**
     * @param siteNameShort
     * @param dateTime
     * @return
     * @throws Exception
     */
    public static Map<String, String> getAirQualityForecastFromDB(String siteNameShort, String dateTime) throws Exception {
        Map<String, String> pollutantValueMap = new HashMap<>();
        ResultSet rs;
        Connection conn = JDBCDao.getConn();
        PreparedStatement selectAirQualityForecast = conn.prepareStatement("select distinct pm2_5,pm10,so2,no2,co,o3,aqi from sys_aqi_info where site_name=? and create_date=?");
        selectAirQualityForecast.setString(1, siteNameShort);
        selectAirQualityForecast.setString(2, dateTime);
        rs = selectAirQualityForecast.executeQuery();
        while (rs.next()) {
            String PM2_5 = rs.getString("pm2_5");
            String PM10 = rs.getString("pm10");
            String SO2 = rs.getString("so2");
            String NO2 = rs.getString("no2");
            String CO = rs.getString("co");
            String O3 = rs.getString("o3");
            String AQI = rs.getString("aqi");
            pollutantValueMap.put("PM2_5", PM2_5);
            pollutantValueMap.put("PM10", PM10);
            pollutantValueMap.put("SO2", SO2);
            pollutantValueMap.put("NO2", NO2);
            pollutantValueMap.put("CO", CO);
            pollutantValueMap.put("O3", O3);
            pollutantValueMap.put("AQI", AQI);
        }
        selectAirQualityForecast.close();
        conn.close();
        rs.close();
        return pollutantValueMap;
    }

    public static String generateAirForecastJson(String siteName, String date, String time, String PM2_5, String PM10, String SO2, String NO2, String CO, String O3, String AQI) {
        String jsonStr = "{\n" +
                "\"async\": 1,\n" +
                "\"callback\": \"\",\n" +
                "\"method\": \"空气质量预报\",\n" +
                "\"param\": [{\"站点\" : \"" + siteName + "\",\n" +
                "\"日期\" : \"" + date + "\",\n" +
                "\"时间\" : \"" + time + "\",\n" +
                "\"空气质量\" : {\n" +
                "\"PM2_5\" : \"" + PM2_5 + "\",\n" +
                "\"PM10\" : \"" + PM10 + "\",\n" +
                "\"SO2\" : \"" + SO2 + "\",\n" +
                "\"NO2\" : \"" + NO2 + "\",\n" +
                "\"CO\" : \"" + CO + "\",\n" +
                "\"O3\" : \"" + O3 + "\",\n" +
                "\"AQI\" : \"" + AQI + "\"\n" +
                "}\n" +
                "}]\n" +
                "}";
        return jsonStr.trim();
    }
}
