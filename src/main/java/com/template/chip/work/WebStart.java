package com.template.chip.work;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author guozhenquan
 * @date 2022年04月12日 15:36
 */
public class WebStart {

    public static void main(String[] args) throws Exception {
        matchingWeb();
    }

    public static void matchingWeb() throws Exception {

        InputStream in = new FileInputStream("C:\\Users\\EDZ\\Desktop\\data\\北京成单客户地址-4.12(1).xlsx");
        Workbook wk = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(in);
        Sheet sheet = wk.getSheetAt(0);
        List<WebUserInfo> webInfo = getWebInfo();

        List<ResultData> addressBadList = new ArrayList<>();
        List<ResultData> addressSuccessHiveWebList = Lists.newArrayList();
        List<ResultData> addressSuccessNoWebList = Lists.newArrayList();

        for (Row row : sheet) {
            int rowNum = row.getRowNum();
            if (rowNum == 0) {
                continue;
            }
            String name = row.getCell(0).getStringCellValue();
            String city = row.getCell(7).getStringCellValue();
            String address = row.getCell(8).getStringCellValue();
            JSONObject object = getLonLat(address, city);
            if (object.getString("infocode").equals("30001")) {
                ResultData resultData = new ResultData();
                resultData.setCustomerName(name);
                resultData.setCity(city);
                resultData.setAddress(address);
                addressBadList.add(resultData);
                continue;
            }
            if (object.getString("infocode").equals("10000")) {
                String[] aa = object.getJSONArray("geocodes").getJSONObject(0).getString("location").split(",");
                Gps2 gg = new Gps2(Double.parseDouble(aa[0]), Double.parseDouble(aa[1]));
                ResultData resultData = new ResultData();
                resultData.setCustomerName(name);
                resultData.setCity(city);
                resultData.setAddress(address);
                boolean webFlag = true;
                for (WebUserInfo e : webInfo) {
                    String departmentName = e.getDepartmentName();
                    String userName = e.getUserName();
                    String webName = e.getWebName();
                    List<Gps2> locations = e.getLocations();
                    if (GpsUtil.isInPolygon(gg, locations)) {
                        resultData.setWebName(webName);
                        resultData.setWebUserName(userName);
                        resultData.setDepartmentName(departmentName);
                        addressSuccessHiveWebList.add(resultData);
                        webFlag = false;
                        break;
                    }
                }
                if(webFlag){
                    addressSuccessNoWebList.add(resultData);
                }
            }
        }
        writeExcel(addressSuccessHiveWebList,addressBadList,addressSuccessNoWebList);
    }

    public static List<WebUserInfo> getWebInfo() throws SQLException {
        Connection con = getConnection();
        List<WebUserInfo> rList = Lists.newArrayList();
        ResultSet rs = con.prepareStatement("select tsw.title,tsw.new_area_info,tu.name,td.name dept from t_scene_web tsw\n" +
                "left join t_scene_web_user tswu\n" +
                "on tsw.id = tswu.web_id\n" +
                "left join t_user tu\n" +
                "on tu.id = tswu.user_id\n" +
                "left join t_department td\n" +
                "on tu.department_id = td.id\n" +
                "where tsw.states = 1 and tsw.new_area_info<>''").executeQuery();
        while (rs.next()) {
            WebUserInfo webUserInfo = new WebUserInfo();
            String webName = rs.getString(1);
            String areaInfo = rs.getString(2);
            String userName = rs.getString(3);
            String departmentName = rs.getString(4);

            webUserInfo.setWebName(webName);
            webUserInfo.setUserName(userName);
            webUserInfo.setDepartmentName(departmentName);
            List<Gps2> locations1 = webUserInfo.getLocations();
            String[] spots = areaInfo.split(";");
            for (String spot : spots) {
                String[] coordinate = spot.split(",");
                locations1.add(new Gps2(Double.parseDouble(coordinate[0]), Double.parseDouble(coordinate[1])));
            }
            rList.add(webUserInfo);
        }
        return rList;
    }

    public static void writeExcel(List<ResultData> addressSuccessHiveWebList,List<ResultData> addressBadList,List<ResultData> addressSuccessNoWebList) throws Exception {
        String writePath = "C:\\Users\\EDZ\\Desktop\\data\\result\\匹配结果"+System.currentTimeMillis()+".xlsx";
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = EasyExcel.write(writePath, ResultData.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("匹配成功").build();
        WriteSheet writeSheet1 = EasyExcel.writerSheet("匹配失败").build();
        WriteSheet writeSheet2 = EasyExcel.writerSheet("解析地址失败").build();
        excelWriter.write(addressSuccessHiveWebList, writeSheet);
        excelWriter.write(addressSuccessNoWebList,writeSheet1);
        excelWriter.write(addressBadList,writeSheet2);
        // 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }

    public static JSONObject getLonLat(String name, String city) {
        String ips = HttpUtil.doGet("https://restapi.amap.com/v3/geocode/geo?address=" + URLEncoder.encode(name) + "&city=" + city + "&output=JSON&key=97988bfc4dce569f89ff67caa26cabe1");
        return JSONObject.parseObject(ips);
    }


    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://172.16.0.249:3306/db_crm?characterEncoding=UTF-8&useSSL=false", "juzhihai", "1t29w7tf1VtsywB1");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
