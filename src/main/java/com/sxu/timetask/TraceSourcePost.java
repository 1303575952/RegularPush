package com.sxu.timetask;

import com.sxu.control.TraceSource;
import com.sxu.data.EnterpriseName;
import com.sxu.util.DateAndTimeUtil;

import java.util.TimerTask;

/**
 * 暂定如下：
 * 一天为一个周期，执行定时任务，推送本次执行前的一天的数据
 */
public class TraceSourcePost extends TimerTask {

    String[] siteNameArr = {"长治八中", "德盛苑", "监测站", "清华站", "审计局"};
    // 企业列表ENTERPRISENAMEARR
    String[] industryArr = {"电力供热", "钢铁", "水泥玻璃", "石油化工", "焦化", "工业锅炉", "其他工业"};
    //seems unnecessary
    String[] pollutantArr = {};

    @Override
    public void run() {
        String date = "";
        try {
            date = DateAndTimeUtil.getYesterdayDate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < siteNameArr.length; i++) {
            for (int j = 0; j < EnterpriseName.ENTERPRISENAMEARR.length; j++) {
                for (int k = 0; k < industryArr.length; k++) {
                    for (int m = 0; m < 24; m++) {
                        try {
                            TraceSource.postTraceSourceJson(siteNameArr[i], EnterpriseName.ENTERPRISENAMEARR[j], date, String.valueOf(m), industryArr[k]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
