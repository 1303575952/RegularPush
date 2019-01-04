package com.sxu.data;

import java.util.HashMap;
import java.util.Map;

public class Industry {
    public static Map<String,String> getSiteNameMap(){
        Map<String,String> industryMap = new HashMap<>();
        industryMap.put("电力供热","heating");
        industryMap.put("钢铁","iron");
        industryMap.put("水泥玻璃","cement_glass");
        industryMap.put("石油化工","chemical_industry");
        industryMap.put("焦化","coking");
        industryMap.put("工业锅炉","boiler");
        industryMap.put("其他工业","other_industry");
        return industryMap;
    }
}
