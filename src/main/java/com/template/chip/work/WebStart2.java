package com.template.chip.work;

import com.template.chip.work.utils.HttpUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.monitorjbl.xlsx.StreamingReader;
import com.template.chip.work.result.BaiduResultData;
import com.template.chip.work.result.ExportResultData;
import com.template.chip.work.result.GaodePoiResultData;
import com.template.chip.work.result.WebUserInfoResultData;
import com.template.chip.work.utils.Gps2;
import com.template.chip.work.utils.GpsUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import redis.clients.jedis.Jedis;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
public class WebStart2 {

    /**
     * 地址匹配网格最终版
     */
    public static void main(String[] args) throws Exception {
//        initDataBaidu();
        initDataGaode();
//        initDataPoi();
        matchingWeb(false);
//        matching("北京市海淀区北清路辅路81号", "北京");
    }

    private static void matching(String address, String city) throws SQLException {
        JSONObject object = getLonLat(address, "北京");
        String aa = object.getJSONArray("geocodes").getJSONObject(0).getString("location");
        String[] split = aa.split(",");
        Gps2 gg = new Gps2(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
        List<WebUserInfoResultData> webInfo = getWebInfo();
        for (WebUserInfoResultData e : webInfo) {
            String departmentName = e.getDepartmentName();
            String userName = e.getUserName();
            String webName = e.getWebName();
            List<Gps2> locations = e.getLocations();
            if (GpsUtil.isInPolygon(gg, locations)) {
                System.out.println(address +":"+webName);
                break;
            }
        }
        System.out.println("没匹配到");
    }
    /**
     * 使用高德api，获取excel中的地址坐标，保存在
     * @author guozhenquan
     * @date 2022/5/16 13:26
     */
    private static void initDataGaode() throws FileNotFoundException {
        Jedis jedis = new Jedis("127.0.0.1",6379);

        InputStream in = new FileInputStream("C:\\Users\\EDZ\\Desktop\\data\\成单客户信息北京-0412.xlsx");
        Workbook wk = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(in);
        Sheet sheet = wk.getSheetAt(0);

        List<ExportResultData> resultDataList= Lists.newArrayList();
        for (Row row : sheet) {
            int rowNum = row.getRowNum();
            if (rowNum == 0) {
                continue;
            }
            String city = row.getCell(1).getStringCellValue();
            String address = row.getCell(2).getStringCellValue();
            ExportResultData ExportResultData = new ExportResultData();
            ExportResultData.setCity(city);
            ExportResultData.setAddress(address);
            resultDataList.add(ExportResultData);
        }
        resultDataList.forEach(e->{
            String city = e.getCity();
            String address = e.getAddress();
            JSONObject object = getLonLat(address, city);
            if (object.getString("infocode").equals("10000")) {
                String aa = object.getJSONArray("geocodes").getJSONObject(0).getString("location");
                jedis.hset("coordinate",address,aa);
            }else{
                jedis.hset("noCoordinate",address,"1");
            }
        });
        jedis.close();
    }
    private static void initDataBaidu() throws FileNotFoundException {
        Jedis jedis = new Jedis("127.0.0.1",6379);

        InputStream in = new FileInputStream("C:\\Users\\EDZ\\Desktop\\data\\成单客户信息北京-0412.xlsx");
        Workbook wk = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(in);
        Sheet sheet = wk.getSheetAt(0);

        List<ExportResultData> resultDataList= Lists.newArrayList();
        for (Row row : sheet) {
            int rowNum = row.getRowNum();
            if (rowNum == 0) {
                continue;
            }
            String city = row.getCell(1).getStringCellValue();
            String address = row.getCell(2).getStringCellValue();
            ExportResultData ExportResultData = new ExportResultData();
            ExportResultData.setCity(city);
            ExportResultData.setAddress(address);
            resultDataList.add(ExportResultData);
        }
        resultDataList.forEach(e->{
            try{
                String city = e.getCity();
                String address = e.getAddress();
                BaiduResultData object = getLonLatBaidu(address, city);
                String status = object.getStatus();
                if(status.equals("OK")){
                    BaiduResultData.ResultDTO result = object.getResult();
                    BaiduResultData.ResultDTO.LocationDTO location = result.getLocation();
                    Double lng = location.getLng();
                    Double lat = location.getLat();
                    jedis.hset("coordinate",address,String.join(",",lng.toString(),lat.toString()));
                }else{
                    jedis.hset("noCoordinate",address,"1");
                }
            }catch (Exception e1){
                e1.printStackTrace();
                System.out.println(e.getAddress()+"抛异常了");
            }
        });
        jedis.close();
    }


    private static void initDataPoi() throws FileNotFoundException {
        Jedis jedis = new Jedis("127.0.0.1",6379);

        InputStream in = new FileInputStream("C:\\Users\\EDZ\\Desktop\\data\\test.xlsx");
        Workbook wk = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(in);
        Sheet sheet = wk.getSheetAt(0);

        List<ExportResultData> resultDataList= Lists.newArrayList();
        for (Row row : sheet) {
            int rowNum = row.getRowNum();
            if (rowNum == 0) {
                continue;
            }
            String city = row.getCell(1).getStringCellValue();
            String address = row.getCell(2).getStringCellValue();
            ExportResultData ExportResultData = new ExportResultData();
            ExportResultData.setCity(city);
            ExportResultData.setAddress(address);
            resultDataList.add(ExportResultData);
        }
        resultDataList.forEach(e->{
            String city = e.getCity();
            String address = e.getAddress();
            GaodePoiResultData lonLat1 = getLonLat1(address, city);
            if (lonLat1.getInfocode().equals("10000")) {
                List<GaodePoiResultData.PoisDTO> pois = lonLat1.getPois();
                if(pois.size()>0){
                    GaodePoiResultData.PoisDTO poisDTO = pois.get(0);
                    String entrLocation = poisDTO.getEntrLocation();
                    if(!"[]".equals(entrLocation)){
                        jedis.hset("coordinate",address,entrLocation);
                    }else{
                        jedis.hset("noCoordinate",address,entrLocation);
                    }
                }else{
                    jedis.hset("noCoordinate",address,"");
                }

            }else{
                jedis.hset("noCoordinate",address,"1");
            }
        });
        jedis.close();
    }

    public static void matchingWeb(boolean isBaidu) throws Exception {

        Jedis jedis = new Jedis("127.0.0.1",6379);

        InputStream in = new FileInputStream("C:\\Users\\EDZ\\Desktop\\data\\成单客户信息北京-0412.xlsx");
        Workbook wk = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(in);
        Sheet sheet = wk.getSheetAt(0);
        List<WebUserInfoResultData> webInfo = getWebInfo();

        List<ExportResultData> addressBadList = new ArrayList<>();
        List<ExportResultData> addressSuccessHiveWebList = Lists.newArrayList();
        List<ExportResultData> addressSuccessNoWebList = Lists.newArrayList();

        for (Row row : sheet) {
            int rowNum = row.getRowNum();
            if (rowNum == 0) {
                continue;
            }
            String name = row.getCell(0).getStringCellValue();
            String city = row.getCell(1).getStringCellValue();
            String address = row.getCell(2).getStringCellValue();
            String cs = jedis.hget("coordinate",address);
            if (StringUtils.isBlank(cs)) {
                ExportResultData exportResultData = new ExportResultData();
                exportResultData.setCustomerName(name);
                exportResultData.setCity(city);
                exportResultData.setAddress(address);
                addressBadList.add(exportResultData);
            }else{
                String[] split = cs.split(",");
                Gps2 gg = new Gps2(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
                //TODO 使用百度获取的坐标需要转化成火星坐标，高德获取的坐标不用。
                if(isBaidu){
                      gg = GpsUtil.bd09ToGcj02(gg);
                }
                ExportResultData exportResultData = new ExportResultData();
                exportResultData.setCustomerName(name);
                exportResultData.setCity(city);
                exportResultData.setAddress(address);
                boolean webFlag = true;
                for (WebUserInfoResultData e : webInfo) {
                    String departmentName = e.getDepartmentName();
                    String userName = e.getUserName();
                    String webName = e.getWebName();
                    List<Gps2> locations = e.getLocations();
                    if (GpsUtil.isInPolygon(gg, locations)) {
                        exportResultData.setWebName(webName);
                        exportResultData.setWebUserName(userName);
                        exportResultData.setDepartmentName(departmentName);
                        addressSuccessHiveWebList.add(exportResultData);
                        webFlag = false;
                        break;
                    }
                }
                if(webFlag){
                    addressSuccessNoWebList.add(exportResultData);
                }
            }
        }
        writeExcel(addressSuccessHiveWebList,addressBadList,addressSuccessNoWebList);
    }

    public static List<WebUserInfoResultData> getWebInfo() throws SQLException {
        Connection con = getConnection();
        List<WebUserInfoResultData> rList = Lists.newArrayList();
        ResultSet rs = con.prepareStatement("select tsw.title,tsw.new_area_info,tu.name,td.name dept from t_scene_web tsw\n" +
                "left join t_scene_web_user tswu\n" +
                "on tsw.id = tswu.web_id\n" +
                "left join t_user tu\n" +
                "on tu.id = tswu.user_id\n" +
                "left join t_department td\n" +
                "on tu.department_id = td.id\n" +
                "where tsw.states = 1 and tsw.new_area_info<>''").executeQuery();
        while (rs.next()) {
            WebUserInfoResultData WebUserInfoResultData = new WebUserInfoResultData();
            String webName = rs.getString(1);
            String areaInfo = rs.getString(2);
            String userName = rs.getString(3);
            String departmentName = rs.getString(4);

            WebUserInfoResultData.setWebName(webName);
            WebUserInfoResultData.setUserName(userName);
            WebUserInfoResultData.setDepartmentName(departmentName);
            List<Gps2> locations1 = WebUserInfoResultData.getLocations();
            String[] spots = areaInfo.split(";");
            for (String spot : spots) {
                String[] coordinate = spot.split(",");
                locations1.add(new Gps2(Double.parseDouble(coordinate[0]), Double.parseDouble(coordinate[1])));
            }
            rList.add(WebUserInfoResultData);
        }
        return rList;
    }

    public static void writeExcel(List<ExportResultData> addressSuccessHiveWebList,List<ExportResultData> addressBadList,List<ExportResultData> addressSuccessNoWebList) throws Exception {
        String writePath = "C:\\Users\\EDZ\\Desktop\\data\\result\\匹配结果"+System.currentTimeMillis()+".xlsx";
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = EasyExcel.write(writePath, ExportResultData.class).build();
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

    public static BaiduResultData getLonLatBaidu(String name, String city) {
        String ips = HttpUtil.doGet("http://api.map.baidu.com/geocoder?address="+URLEncoder.encode(name) +"&output=json&src=webapp.baidu.openAPIdemo");
        return JSON.parseObject(ips, BaiduResultData.class);
    }

    public static GaodePoiResultData getLonLat1(String name, String city) {
        String url = "https://restapi.amap.com/v3/place/text?keywords="+ URLEncoder.encode(name)+ "&city=" + city+"&children=1&offset=1&page=1&extensions=all&output=JSON&key=97988bfc4dce569f89ff67caa26cabe1";
        String ips = HttpUtil.doGet(url);
        return JSON.parseObject(ips, GaodePoiResultData.class);
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