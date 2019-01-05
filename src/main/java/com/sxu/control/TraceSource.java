package com.sxu.control;

import com.sxu.dao.JDBCDao;
import com.sxu.data.DateTime;
import com.sxu.data.EnterpriseName2Id;
import com.sxu.data.Industry;
import com.sxu.data.SiteName;
import com.sxu.util.HttpClientUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class TraceSource {
    public static void main(String[] args) throws Exception {
        TraceSource.postTraceSourceJson("德盛苑", "沁源县兴茂煤化有限公司", "2018年12月15日", "1", "电力供热");
    }

    /**
     * @param siteName       eg:德盛苑
     * @param enterpriseName eg:沁源县兴茂煤化有限公司
     * @param date           eg:2018年12月15日
     * @param time           eg:1
     * @param industry       eg:电力供热
     * @throws Exception
     */
    public static void postTraceSourceJson(String siteName, String enterpriseName, String date, String time, String industry) throws Exception {
        String siteNameShort = SiteName.getSiteNameMap().get(siteName);
        String enterpriseId = EnterpriseName2Id.getEnterpriseId(enterpriseName);
        String dateTime = DateTime.getDateTime(date, time);
        String industryEnglish = Industry.getSiteNameMap().get(industry);
        Map<String, String> pollutantValueMap = TraceSource.getTraceSourceFromDB(siteNameShort, enterpriseId, dateTime, industryEnglish);
        String PM2_5 = "";
        if (pollutantValueMap.get("PM2_5") != null) {
            PM2_5 = pollutantValueMap.get("PM2_5");
        }
        String SO2 = "";
        if (pollutantValueMap.get("SO2") != null) {
            SO2 = pollutantValueMap.get("SO2");
        }
        String NO2 = "";
        if (pollutantValueMap.get("NO2") != null) {
            NO2 = pollutantValueMap.get("NO2");
        }
        String jsonStr = TraceSource.generateTraceSourceJson(siteName, enterpriseName, date, time, industry, PM2_5, SO2, NO2);
        System.out.println("jsonStr=" + jsonStr);
        String url = "http://119.90.57.34:9680/channel/do";
        HttpClientUtil.httpPostWithJSON(url, jsonStr);
    }

    /**
     * @param siteNameShort eg:CZBZ
     * @param enterpriseId  eg:75
     * @param dateTime      eg:2018-12-09 12:00:00
     * @param industry      eg:boiler
     * @throws Exception
     */
    public static Map<String, String> getTraceSourceFromDB(String siteNameShort, String enterpriseId, String dateTime, String industry) throws Exception {
        Map<String, String> pollutantValueMap = new HashMap<>();
        ResultSet rs;
        Connection conn = JDBCDao.getConn();
        PreparedStatement selectTraceSource = conn.prepareStatement("select distinct atmosphere_contaminants,atmosphere_value from sys_aqifctor_info where site_name=? and company_id=? and create_date=? and industry_type=?");
        selectTraceSource.setString(1, siteNameShort);
        selectTraceSource.setString(2, enterpriseId);
        selectTraceSource.setString(3, dateTime);
        selectTraceSource.setString(4, industry);
        rs = selectTraceSource.executeQuery();
        while (rs.next()) {
            String atmosphere_contaminants = rs.getString("atmosphere_contaminants");
            String atmosphere_value = rs.getString("atmosphere_value");
            pollutantValueMap.put(atmosphere_contaminants, atmosphere_value);
        }
        selectTraceSource.close();
        conn.close();
        rs.close();
        return pollutantValueMap;
    }

    public static String generateTraceSourceJson(String siteName, String enterpriseName, String date, String time, String industry, String PM2_5, String SO2, String NO2) {
        String jsonStr = "{\n" +
                "\"async\": 1,\n" +
                "\"callback\": \"\",\n" +
                "\"method\": \"空气污染物溯源\",\n" +
                "\"param\": [{\"站点\" : \"" + siteName + "\",\n" +
                "\"企业名称\":\"" + enterpriseName + "\",\n" +
                "\"日期\" : \"" + date + "\",\n" +
                "\"时间\" : \"" + time + "\",\n" +
                "\"行业\" : \"" + industry + "\",\n" +
                "\"空气污染物\" : {\n" +
                "\"PM2_5\" : \"" + PM2_5 + "\",\n" +
                "\"SO2\" : \"" + SO2 + "\",\n" +
                "\"NO2\" : \"" + NO2 + "\"\n" +
                "}\n" +
                "}]\n" +
                "}";
        return jsonStr.trim();
    }
}
