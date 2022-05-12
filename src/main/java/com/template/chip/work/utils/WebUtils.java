package com.template.chip.work.utils;

import com.google.common.collect.Lists;
import com.template.chip.work.result.WebUserInfoResultData;
import com.template.chip.work.result.WebUserInfoUserResultData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author guozhenquan
 * @date 2022年04月15日 15:00
 */
public class WebUtils {

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://172.16.0.249:3306/db_crm?characterEncoding=UTF-8&useSSL=false", "juzhihai", "1t29w7tf1VtsywB1");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<WebUserInfoResultData> getWebInfo() throws SQLException {
        Connection con = getConnection();
        List<WebUserInfoResultData> rList = Lists.newArrayList();
        ResultSet rs = con.prepareStatement("select tsw.title,tsw.new_area_info,tu.name,td.name dept,tsw.id from t_scene_web tsw\n" +
                "left join t_scene_web_user tswu\n" +
                "on tsw.id = tswu.web_id\n" +
                "left join t_user tu\n" +
                "on tu.id = tswu.user_id\n" +
                "left join t_department td\n" +
                "on tu.department_id = td.id\n" +
                "where tsw.states = 1").executeQuery();
        while (rs.next()) {
            WebUserInfoResultData webUserInfoResultData = new WebUserInfoResultData();
            String webName = rs.getString(1);
            String areaInfo = rs.getString(2);
            String userName = rs.getString(3);
            String departmentName = rs.getString(4);
            Long id = rs.getLong(5);

            webUserInfoResultData.setWebName(webName);
            webUserInfoResultData.setUserName(userName);
            webUserInfoResultData.setDepartmentName(departmentName);
            webUserInfoResultData.setId(id);

//            List<Gps2> locations1 = webUserInfoResultData.getLocations();
//            String[] spots = areaInfo.split(";");
//            for (String spot : spots) {
//                String[] coordinate = spot.split(",");
//                locations1.add(new Gps2(Double.parseDouble(coordinate[0]), Double.parseDouble(coordinate[1])));
//            }
            rList.add(webUserInfoResultData);
        }
        return rList;
    }

    public static List<WebUserInfoUserResultData> getWebInfoUser() throws SQLException {
        Connection con = getConnection();
        List<WebUserInfoUserResultData> rList = Lists.newArrayList();
        ResultSet rs = con.prepareStatement("select sw.title,u.name from t_scene_web_user swu LEFT JOIN t_user u on swu.user_id  = u.id LEFT JOIN t_scene_web sw on swu.web_id = sw.id ").executeQuery();
        while (rs.next()) {
            WebUserInfoUserResultData webUserInfoResultData = new WebUserInfoUserResultData();
            String webName = rs.getString(1);
            String userName = rs.getString(2);
            webUserInfoResultData.setWebName(webName);
            webUserInfoResultData.setUserName(userName);
            rList.add(webUserInfoResultData);
        }
        return rList;
    }
}
