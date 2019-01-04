package com.sxu.data;

import java.util.HashMap;
import java.util.Map;

public class SiteName {
    public static Map<String,String> getSiteNameMap(){
        Map<String,String> siteNameMap = new HashMap<>();
        siteNameMap.put("长治八中","CZBZ");
        siteNameMap.put("德盛苑","DSY");
        siteNameMap.put("监测站","JCZ");
        siteNameMap.put("清华站","QHZ");
        siteNameMap.put("审计局","SJJ");
        return siteNameMap;
    }
}
