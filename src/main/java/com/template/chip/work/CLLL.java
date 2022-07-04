package com.template.chip.work;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.monitorjbl.xlsx.StreamingReader;
import com.template.chip.work.result.ExportResultData;
import com.template.chip.work.result.WebLatLon;
import com.template.chip.work.result.WebUserInfoResultData;
import com.template.chip.work.utils.*;
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
import java.util.stream.Collectors;

public class CLLL {

//       private static final String INSERT_SQL = "INSERT INTO `t_scene_web`( `title` , `province_id` , `city_id` , `district_id` , `area_info` , `area_desc` , `quota` , `states` , `crm_city_id` , `area_zoom` , `area_center` , `created_user` , `created_time` , `modified_user` , `modified_time` , `gross_area` , `remark` , `new_area_info`) \n" +
//           "VALUES( '%s' , '110000' , '110100' , '110105' , '' , '北京市' , '0' , '1' , '131' , '18' , '116.480927,39.996471' , '6062' , now() , '6062' , now() , '0.00' , '星罗地图' , '%s');";
//       private static final String INSERT_SQL = "INSERT INTO `t_scene_web`( `title` , `province_id` , `city_id` , `district_id` , `area_info` , `area_desc` , `quota` , `states` , `crm_city_id` , `area_zoom` , `area_center` , `created_user` , `created_time` , `modified_user` , `modified_time` , `gross_area` , `remark` , `new_area_info`) \n" +
//           "VALUES( '%s' , '330000' , '330100' , '330106' , '' , '杭州市' , '0' , '1' , '179' , '18' , '116.480927,39.996471' , '6062' , now() , '6062' , now() , '0.00' , '星罗地图' , '%s');";
    private static final String INSERT_SQL = "INSERT INTO `t_scene_web`( `title` , `province_id` , `city_id` , `district_id` , `area_info` , `area_desc` , `quota` , `states` , `crm_city_id` , `area_zoom` , `area_center` , `created_user` , `created_time` , `modified_user` , `modified_time` , `gross_area` , `remark` , `new_area_info`) \n" +
            "VALUES( '%s' , '320000' , '320500' , '320506' , '' , '苏州市' , '0' , '1' , '224' , '18' , '116.480927,39.996471' , '6062' , now() , '6062' , now() , '0.00' , '星罗地图' , '%s');";
//       private static final String INSERT_SQL = "INSERT INTO `t_scene_web`( `title` , `province_id` , `city_id` , `district_id` , `area_info` , `area_desc` , `quota` , `states` , `crm_city_id` , `area_zoom` , `area_center` , `created_user` , `created_time` , `modified_user` , `modified_time` , `gross_area` , `remark` , `new_area_info`) \n" +
//           "VALUES( '%s' , '340000' , '340100' , '340104' , '' , '合肥市' , '0' , '1' , '127' , '18' , '116.480927,39.996471' , '6062' , now() , '6062' , now() , '0.00' , '星罗地图' , '%s');";
    private static final String UPDATE_SQL = "UPDATE t_scene_web SET new_area_info = '%s',modified_time = '%s' WHERE id = %s;";


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

        mList.add(m4);//mList.add(m3);mList.add(m4);

        for (Map mm:mList) {

            String url = mm.get("url").toString();
            String cookie = " ASP.NET_SessionId=iytybnrxwdrywq1cfhmrz1jh; type=0; v5=0; Hm_lvt_04a92f5555d5369068446e62dd15a5c8=1656582347; v1=15810004906; v2=b904d793-af9b-4d3c-ab76-f92465d9607a; v3=%u81EA%u884C%u8F66%u54C7%u554A; v4=0; Hm_lpvt_04a92f5555d5369068446e62dd15a5c8=1656917773; SECKEY_ABVK=B+3qH9F9LzG7/DEdKnJ0Kz72fswvqci6BxDdk6df+q4%3D; BMAP_SECKEY=x0iCTbO4GKIIa8U1u6lbCvPIWHxB1Xvy7BimEcU0TNqbWZqNN4T5UIyJkN0UVAhAhAgPeWY3oowsLWtZ56cP5_D_wufFz6QKHnutZQpror5MMmErMKlSh6Lo9ganl4iLKY40lXMaOVw5oJTa1VPDrNWUXR_ooMcvR4MP3_BnWTEjED4FHiRliKpkHmcNojEb";
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

        InputStream in = new FileInputStream("C:\\Users\\EDZ\\Desktop\\北京网格补全0524.xlsx");
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
                Gps gps = CoodinateCovertor.bd09_To_Gcj02(Double.parseDouble(lon),Double.parseDouble(lat));
                WebLatLon webLatLon = new WebLatLon();
                webLatLon.setWebName(name);
                webLatLon.setWgLat(gps.getWgLat()+"");
                webLatLon.setWgLon(gps.getWgLon()+"");
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

        List<WebUserInfoResultData> webInfo = WebUtils.getWebInfo();
        Map<String, WebUserInfoResultData> collect = webInfo.stream().collect(Collectors.toMap(WebUserInfoResultData::getWebName, x -> x, (v1, v2) -> v2));
        List<ExcelGps> collect2 = writeExcelList.stream().map(e -> {
            ExcelGps excelGps = new ExcelGps();
            excelGps.setName(e.getWebName());
            excelGps.setWgLat(e.getWgLat());
            excelGps.setWgLon(e.getWgLon());
            return excelGps;
        }).collect(Collectors.toList());

        Map<String, List<ExcelGps>> collect1 = collect2.stream().collect(Collectors.groupingBy(ExcelGps::getName, Collectors.toList()));

        List<String> webInsert = Lists.newArrayList();
        List<String> webUpdate = Lists.newArrayList();
        collect1.forEach((k,v)->{
            if(webs.contains(k)){
                String newAreaInfo = getNewAreaInfo(v);
                if(collect.containsKey(k)){
                    WebUserInfoResultData webUserInfoResultData = collect.get(k);
                    Long id = webUserInfoResultData.getId();
                    String format = String.format(UPDATE_SQL, newAreaInfo,  DateUtil.now(),id);
                    webUpdate.add(format);
                }else{
                    String format = String.format(INSERT_SQL,k, newAreaInfo);
                    webInsert.add(format);
                }
            }
        });
        FileUtil.writeLines(webInsert,"C:\\Users\\EDZ\\Desktop\\webInsert.txt","UTF-8");
        FileUtil.writeLines(webUpdate,"C:\\Users\\EDZ\\Desktop\\webUpdate.txt","UTF-8");
    }

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        CLLL.initWG();
//        CLLL.exportJW();
    }

    private static String getNewAreaInfo(List<ExcelGps> v) {
        List<String> objects = Lists.newArrayList();
        v.forEach(e->{
            StringBuilder newAreaInfo = new StringBuilder();
            String wgLon = e.getWgLon();
            String wgLat = e.getWgLat();
            newAreaInfo.append(wgLon).append(",").append(wgLat);
            objects.add(newAreaInfo.toString());
        });
        return String.join(";", objects);
    }
}
