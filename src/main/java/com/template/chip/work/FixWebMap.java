package com.template.chip.work;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.google.common.collect.Lists;
import com.template.chip.work.result.WebUserInfoResultData;
import com.template.chip.work.utils.ExcelGps;
import com.template.chip.work.utils.Gps2;
import com.template.chip.work.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author guozhenquan
 * @date 2022年04月19日 16:41
 */
@Slf4j
public class FixWebMap {


//   private static final String INSERT_SQL = "INSERT INTO `t_scene_web`( `title` , `province_id` , `city_id` , `district_id` , `area_info` , `area_desc` , `quota` , `states` , `crm_city_id` , `area_zoom` , `area_center` , `created_user` , `created_time` , `modified_user` , `modified_time` , `gross_area` , `remark` , `new_area_info`) \n" +
//           "VALUES( '%s' , '330000' , '330100' , '330106' , '' , '杭州市' , '0' , '1' , '179' , '18' , '116.480927,39.996471' , '6062' , now() , '6062' , now() , '0.00' , '星罗地图' , '%s');";
   private static final String INSERT_SQL = "INSERT INTO `t_scene_web`( `title` , `province_id` , `city_id` , `district_id` , `area_info` , `area_desc` , `quota` , `states` , `crm_city_id` , `area_zoom` , `area_center` , `created_user` , `created_time` , `modified_user` , `modified_time` , `gross_area` , `remark` , `new_area_info`) \n" +
           "VALUES( '%s' , '320000' , '320500' , '320506' , '' , '苏州市' , '0' , '1' , '224' , '18' , '116.480927,39.996471' , '6062' , now() , '6062' , now() , '0.00' , '星罗地图' , '%s');";
//   private static final String INSERT_SQL = "INSERT INTO `t_scene_web`( `title` , `province_id` , `city_id` , `district_id` , `area_info` , `area_desc` , `quota` , `states` , `crm_city_id` , `area_zoom` , `area_center` , `created_user` , `created_time` , `modified_user` , `modified_time` , `gross_area` , `remark` , `new_area_info`) \n" +
//           "VALUES( '%s' , '340000' , '340100' , '340104' , '' , '合肥市' , '0' , '1' , '127' , '18' , '116.480927,39.996471' , '6062' , now() , '6062' , now() , '0.00' , '星罗地图' , '%s');";
   private static final String UPDATE_SQL = "UPDATE t_scene_web SET new_area_info = '%s',modified_time = '%s' WHERE id = %s;";

    public static void main(String[] args) throws FileNotFoundException, SQLException {
        List<WebUserInfoResultData> webInfo = WebUtils.getWebInfo();
        Map<String, WebUserInfoResultData> collect = webInfo.stream().collect(Collectors.toMap(WebUserInfoResultData::getWebName, x -> x, (v1, v2) -> v2));
        ExcelReader reader = ExcelUtil.getReader("C:\\Users\\EDZ\\Desktop\\网格\\20220517\\苏州(新)20220517145509.xlsx",0);
        List<ExcelGps> readAll = reader.readAll(ExcelGps.class);
        Map<String, List<ExcelGps>> collect1 = readAll.stream().collect(Collectors.groupingBy(ExcelGps::getName, Collectors.toList()));

        List<String> webInsert = Lists.newArrayList();
        List<String> webUpdate = Lists.newArrayList();
        collect1.forEach((k,v)->{
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
        });
        FileUtil.writeLines(webInsert,"C:\\Users\\EDZ\\Desktop\\webInsert.txt","UTF-8");
        FileUtil.writeLines(webUpdate,"C:\\Users\\EDZ\\Desktop\\webUpdate.txt","UTF-8");
//        String SYMBOL = "?";
//        String inputContent = "https://m.edianyun.com/wap/product/120582?renttypeid=243763&extendid=0";
//        if(inputContent.contains(SYMBOL)){
//            inputContent = inputContent.substring(0,inputContent.indexOf("?"));
//        }
//        String regex = "^https?://m.edianyun.co?[m|n]/wap/product/(\\d+)";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher m = pattern.matcher(inputContent);
//        boolean b = m.matches();
//        System.out.println(b);
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

    private static String subName(String a) {
        int i = a.lastIndexOf("-");
        return a.substring(0,i);
    }
}
