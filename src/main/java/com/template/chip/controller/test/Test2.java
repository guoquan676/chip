package com.template.chip.controller.test;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.template.chip.work.result.WebLatLon;
import com.template.chip.work.utils.CoodinateCovertor;
import com.template.chip.work.utils.Gps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author guozhenquan
 * @date 2022年06月09日 21:14
 */
public class Test2 {

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://172.16.0.249:3306/db_crm?characterEncoding=UTF-8&useSSL=false", "juzhihai", "1t29w7tf1VtsywB1");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String sql = "INSERT INTO `db_growth_log`.`t_customer_log` ( `customer_id`, `user_id`, `template_type`, `content`, `data_body`, `type`, `message_time`, `create_time`) " +
            "VALUES ( %s, %s, 27, '%s在%s，将%s变为【名单客户】，原因：销售申请添加', '{\\\"createTime\\\":\\\"%s\\\",\\\"customer\\\":\\\"%s\\\",\\\"customerId\\\":%s,\\\"labelStr\\\":\\\"名单客户\\\",\\\"operator\\\":\\\"%s\\\",\\\"operatorId\\\":%s,\\\"reason\\\":\\\"销售申请添加\\\"}', 2, '%s', '%s');\n";

    public static void main(String[] args) throws SQLException {
        Connection con = getConnection();
        ResultSet rs = con.prepareStatement("SELECT customer_id,update_time,update_user,tu.name userName,tc.name customerName FROM `db_crm`.`t_customer_name_list_apply` tcnla\n" +
                " LEFT JOIN t_customer tc on tcnla.customer_id = tc.id \n" +
                "left join t_user tu on tcnla.update_user = tu.id \n" +
                "\tWHERE tcnla.`approval_status` = '1' AND tcnla.`source_type` = '0'").executeQuery();
        while(rs.next())
        {
            String customer_id = rs.getString("customer_id");
            String update_user = rs.getString("update_user");
            String update_time = rs.getString("update_time");
            String userName = rs.getString("userName");
            String customerName = rs.getString("customerName");

            String format = String.format(sql, customer_id,update_user,userName,update_time,customerName,update_time,customerName,customer_id,userName,update_user,update_time,update_time);
            System.out.println(format);
        }
    }
}
