package com.sxu.data;

import com.sxu.dao.JDBCDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EnterpriseName2Id {
    public static String getEnterpriseId(String enterpriseName) throws Exception {
        String enterpriseId = "";
        ResultSet rs;
        Connection conn = JDBCDao.getConn();
        PreparedStatement selectEnterpriseName = conn.prepareStatement("select distinct id from sys_company_info where company_name=?");
        selectEnterpriseName.setString(1, enterpriseName);
        rs = selectEnterpriseName.executeQuery();
        while (rs.next()) {
            enterpriseId = rs.getString("id");
        }
        selectEnterpriseName.close();
        conn.close();
        rs.close();
        return enterpriseId;
    }
}
