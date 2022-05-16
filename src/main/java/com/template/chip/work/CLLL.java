package com.template.chip.work;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.monitorjbl.xlsx.StreamingReader;
import com.template.chip.work.result.ExportResultData;
import com.template.chip.work.result.WebLatLon;
import com.template.chip.work.utils.Gps2;
import com.template.chip.work.utils.GpsUtil;
import com.template.chip.work.utils.HttpUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CLLL {

    public static Connection testGetConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/test?characterEncoding=UTF-8&useSSL=false", "root", "123456");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
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


    public static void initWG() throws SQLException {
        Connection con = testGetConnection();
        List<Map> mList = new ArrayList();
        Map m1 = new HashMap<>();m1.put("city","北京");m1.put("url","https://www.ldmap.net/service/map/feature/extend?x1=-200&y1=-200&x2=200&y2=200&mapid=fce0ed38-321f-4852-811a-a740bb0ac649&feature_type=-1&_=1649396726691");
        Map m2 = new HashMap<>();m2.put("city","杭州");m2.put("url","https://www.ldmap.net/service/map/feature/extend?x1=-200&y1=-200&x2=200&y2=200&mapid=3e17f0cc-d817-4387-aa26-5a9539269967&feature_type=-1&_=1652430774483");
        Map m3 = new HashMap<>();m3.put("city","合肥");m3.put("url","https://www.ldmap.net/service/map/feature/extend?x1=-200&y1=-200&x2=200&y2=200&mapid=2fc3a5a0-8d2f-419b-95f1-d9106f9962f6&feature_type=-1&_=1649396861865");
        Map m4 = new HashMap<>();m4.put("city","苏州");m4.put("url","https://www.ldmap.net/service/map/feature/extend?x1=-200&y1=-200&x2=200&y2=200&mapid=f4fbd6cf-0b89-468d-97b7-ab74f508509b&feature_type=-1&_=1649397352445");

        //mList.add(m1);
        mList.add(m4);//mList.add(m3);mList.add(m4);

//北京 - https://www.ldmap.net/service/map/feature/extend?x1=-200&y1=-200&x2=200&y2=200&mapid=fce0ed38-321f-4852-811a-a740bb0ac649&feature_type=-1&_=1649396726691
//杭州 - https://www.ldmap.net/service/map/feature/extend?x1=-200&y1=-200&x2=200&y2=200&mapid=fce0ed38-321f-4852-811a-a740bb0ac649&feature_type=-1&_=1649396726691
//合肥 - https://www.ldmap.net/service/map/feature/extend?x1=-200&y1=-200&x2=200&y2=200&mapid=2fc3a5a0-8d2f-419b-95f1-d9106f9962f6&feature_type=-1&_=1649396861865
//苏州 - https://www.ldmap.net/service/map/feature/extend?x1=-200&y1=-200&x2=200&y2=200&mapid=f4fbd6cf-0b89-468d-97b7-ab74f508509b&feature_type=-1&_=1649397352445


        for (Map mm:mList) {

            String url = mm.get("url").toString();
            String cookie = " ASP.NET_SessionId=y10idglqsk0ije2y30hkfhtg; type=0; v5=0; Hm_lvt_04a92f5555d5369068446e62dd15a5c8=1652248746; v1=15810004906; v2=b904d793-af9b-4d3c-ab76-f92465d9607a; v3=%u81EA%u884C%u8F66%u54C7%u554A; v4=0; 3e17f0cc-d817-4387-aa26-5a9539269967=119.829643145%2C30.164959511%7C10; Hm_lpvt_04a92f5555d5369068446e62dd15a5c8=1652430775; SECKEY_ABVK=QejaJZfoMLsFXJNX1a5hv+xFE08LD1Yow3u7EyfdC5w%3D; BMAP_SECKEY=QejaJZfoMLsFXJNX1a5hv6e1_sknhUzH23qW-1UCefl79EirFJlerAzntaYkAar4eeuHVRz55GbkV2_tXL83SSDG1GZB2OWuV_jl95L7ZXpEqgDIBNnXfAbhHlB2C2msmfgKbRRWVQMtA3qNGN5lNVaNeKZQRjhMy513-O7Lmm5yfV5zgHtHEwktPhyBKJqe";
            String city = mm.get("city").toString();
            JSONObject object =  JSONObject.parseObject(HttpUtil.doGet(url,cookie));

            System.out.println("===============------------>>"+url);

            JSONArray array =  object.getJSONArray("point");

            for (int i = 0; i < array.size(); i++) {
                JSONObject object1 = array.getJSONObject(i);
                String featureName = object1.getString("feature_name");
                String groupid = object1.getString("group_id");
                String lon = object1.getString("x");
                String lat = object1.getString("y");
                String stateIcon = object1.getJSONObject("point_style").getString("state_icon");

                String inSql = "insert into `point` ( `lon`, `lat`, `zu`, `name`,`state_icon`,`city`) values ( '"+lon+"', '"+lat+"', '"+groupid+"', \""+featureName+"\", '"+stateIcon+"','"+city+"')";
                System.out.println(inSql);
                con.prepareStatement(inSql).execute();
                System.out.println("已执行 : "+i);
            }
        }


        con.close();
    }

    public static void exportJW() throws SQLException, FileNotFoundException {
        List<WebLatLon> writeExcelList = new ArrayList<>();
        Connection con = testGetConnection();
        List<String> webs = new ArrayList<>();
        List<String> bads = new ArrayList<>();


//        String webSql = "select title from t_scene_web where states = 1";
//        ResultSet rs =  con.prepareStatement(webSql).executeQuery(webSql);
//        while (rs.next())
//        {
//            String title = rs.getString("title");
//            webs.add(title);
//        }

        // 杭州网格.xlsx

        InputStream in = new FileInputStream("/Users/juzhihai/Downloads/添加网格—北京0512.xlsx");
        Workbook wk = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(in);
        Sheet sheet = wk.getSheetAt(0);
        for (Row row : sheet)
        {
            webs.add(row.getCell(0).getStringCellValue());
        }

        for (String t: webs) {
            String pointSql = "select lat,lon,`name` from point where name like '"+t+"-%' order by cast(replace(right(name,2),\"-\",\"\") as UNSIGNED) asc";
            ResultSet rs1 =con.prepareStatement(pointSql).executeQuery();
            boolean bool = false;
            while(rs1.next())
            {
                bool = true;
                String lat = rs1.getString("lat");
                String lon = rs1.getString("lon");
                String name = rs1.getString("name");
                Gps2 gps2 = new Gps2(Double.parseDouble(lon),Double.parseDouble(lat));
                Gps2 gg = GpsUtil.bd09ToGcj02(gps2);
                WebLatLon webLatLon = new WebLatLon();
                webLatLon.setWebName(name);
                webLatLon.setWgLat(gg.getWgLat()+"");
                webLatLon.setWgLon(gg.getWgLon()+"");
                System.out.println(JSONObject.toJSONString(webLatLon));
                writeExcelList.add(webLatLon);
            }
            if (!bool)
            {
                bads.add(t + " 未找到！");
            }
        }

        for (String bad: bads) {
            System.out.println(bad);
        }

        /**
         * 写入Excel
         */
        try {
            ExcelWriter excelWriter = EasyExcel.write("/Users/juzhihai/Downloads/合肥"+ DateUtils.format(new Date(),"yyyyMMddHHmmss")+".xlsx", WebLatLon.class).build();
            WriteSheet writeSheet = EasyExcel.writerSheet("结果").build();
            excelWriter.write(writeExcelList,writeSheet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        //CLLL.exportJW();
        CLLL.initWG();

    }
}
