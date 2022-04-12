package com.template.chip.work;

import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckPoint {
    private static Connection conn;

    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection("jdbc:mysql://172.16.0.249:3306/db_crm?characterEncoding=UTF-8&useSSL=false", "juzhihai", "1t29w7tf1VtsywB1");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static Connection testGetConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/db_test?characterEncoding=UTF-8&useSSL=false", "root", "juzhihai123");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        List<Map> areaList = new ArrayList<>();
        Connection con = getConnection();

        String wgSql = "SELECT tsw.id , tsw.title , tsw.new_area_info , tu.id AS 'userId' , tu. NAME AS 'userName' FROM `t_scene_web` tsw LEFT JOIN t_scene_web_user tswu ON tsw.id = tswu.web_id LEFT JOIN t_user tu ON tswu.user_id = tu.id WHERE tu.status=1 and tsw.new_area_info IS NOT NULL AND tsw.new_area_info <> '' AND states = 1";
        ResultSet rs = con.prepareStatement(wgSql).executeQuery();
        while(rs.next())
        {
            Map m = new HashMap();
            m.put("id",rs.getLong(1));
            m.put("title",rs.getString(2));
            m.put("area",rs.getString(3));
            m.put("userId",rs.getString(4));
            m.put("userName",rs.getString(5));
            areaList.add(m);
        }

        List<Map> customList = new ArrayList<>();



//        InputStream in = new FileInputStream("/Users/juzhihai/Downloads/电话验证地址准确名单3073.xlsx");
//        //缓存到内存中的行数，默认是10
//        Workbook wk = StreamingReader.builder().rowCacheSize(100)
//                //读取资源时，缓存到内存的字节大小，默认是1024
//                .bufferSize(4096)
//                //打开资源，必须，可以是InputStream或者是File，注意：只能打开XLSX格式的文件
//                .open(in);
//        Sheet sheet0 = wk.getSheetAt(2);
//
//
//        for (Row row : sheet0) {
//            if(null!=row && null!=row.getCell(0)&&!row.getCell(1).getStringCellValue().equals("")&&!row.getCell(2).getStringCellValue().equals(""))
//            {
//                Map m = new HashMap();
//                m.put("name",row.getCell(0).getStringCellValue());
//                m.put("lat",row.getCell(2).getStringCellValue());
//                m.put("lon",row.getCell(3).getStringCellValue());
//                m.put("address",row.getCell(1).getStringCellValue());
//                m.put("louyu",row.getCell(4).getStringCellValue());
//                customList.add(m);
//            }
//        }

        String customSql = "select tccml.customer_id,tni.name,tni.longitude,tni.latitude from t_customer_clues_manage_log  tccml\n" +
                "left join t_namelist_increment tni\n" +
                "on tccml.name = tni.`name`\n" +
                "where remark like '%未匹配到格子%' and longitude<>''";

        //String customSql = "select id,name,longitude,latitude from t_namelist_increment where longitude!='' and latitude !=''";
        ResultSet cusRs = con.prepareStatement(customSql).executeQuery();
        while(cusRs.next())
        {
            Map m = new HashMap();
            m.put("id",cusRs.getInt(1));
            m.put("name",cusRs.getString(2));
            m.put("lat",cusRs.getString(3));
            m.put("lon",cusRs.getString(4));
            //m.put("building",cusRs.getString(8));
            //m.put("address",cusRs.getString(5));
            customList.add(m);
        }




        List<String> okList = new ArrayList<>();
        List<String> badList = new ArrayList<>();
        List<String> ifExist = new ArrayList<>();
        List<String> cFExist = new ArrayList<>();
        Map ifCE = new HashMap<>();
        Map mp = new HashMap();

        for (int j = 0; j < customList.size(); j++) {
            Map cusObj = customList.get(j);
            Gps2 gg = new Gps2(Double.valueOf((String)cusObj.get("lon")),Double.valueOf((String)cusObj.get("lat")));
            boolean bool = true;
            int pCount = 0;
            for (int i = 0; i < areaList.size(); i++) {
                Map areaObj = areaList.get(i);
                String area = (String)areaObj.get("area");
                String [] aaa = area.split(";");
                List<Gps2> pts = new ArrayList<>();
                for (String a: aaa) {
                    pCount++;
                    String [] bb = a.split(",");
                    Gps2 gg2 = new Gps2(Double.valueOf(bb[1]),Double.valueOf(bb[0]));
                    pts.add(gg2);
                }

                if(GpsUtil.isInPolygon(gg,pts))
                {
                    bool = false;
                    String kkk = (String) cusObj.get("name")+"$"+areaObj.get("title")+"$"+cusObj.get("lat")+"$"+cusObj.get("lon")+"$"+cusObj.get("address");
                    if(!ifExist.contains(cusObj.get("name")))
                    {
                        ifExist.add((String)cusObj.get("name"));
                        pCount++;

                        String vvv = cusObj.get("name")+"$"+"北京$北京$"+areaObj.get("title")+"$"+cusObj.get("louyu")+"$$$"+cusObj.get("address")+"$"+areaObj.get("userId")+"$"+areaObj.get("userName");
                        //String vvv = "update t_customer_pool set web_id="+areaObj.get("id")+" where customer_id = "+cusObj.get("id")+";";
                        okList.add(vvv);
                        mp.put((String)cusObj.get("name"),areaObj.get("title"));
                    }
                    else
                    {
                        if(!ifCE.containsKey(cusObj.get("name")))
                        {
                            cFExist.add(cusObj.get("name")+"$"+(String)mp.get(cusObj.get("name")));
                            ifCE.put(cusObj.get("name"),"");
                        }
                        cFExist.add(kkk);

                    }
                }
            }



            if(bool)
            {
                badList.add(cusObj.get("name")+"$"+cusObj.get("lat")+"$"+cusObj.get("lon"));
                //System.out.println(cusObj.get("name")+" "+"没有匹配");
            }
        }

        System.out.println("==================okCount : "+ okList.size() + " badCount : "+badList.size()+ " duplicate : "+cFExist.size()+"==================");

        for (String ok: okList) {
            System.out.println(ok);
        }
        for (String bad: badList) {
            System.out.println(bad);
        }
        for (String cf: cFExist) {
            System.out.println(cf);
        }




    }


}
