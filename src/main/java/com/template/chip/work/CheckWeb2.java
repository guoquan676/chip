package com.template.chip.work;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.template.chip.work.result.CheckWebResult;
import com.template.chip.work.result.WebUserInfoResultData;
import com.template.chip.work.result.WebUserInfoUserResultData;
import com.template.chip.work.utils.Gps2;
import com.template.chip.work.utils.GpsUtil;
import com.template.chip.work.utils.WebUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author guozhenquan
 * @date 2022年04月20日 14:45
 */
public class CheckWeb2 {

    public static void main(String[] args) throws Exception {

        List<WebUserInfoUserResultData> webInfoUser = WebUtils.getWebInfoUser();
        Map<String, String> collect = webInfoUser.stream().collect(Collectors.toMap(WebUserInfoUserResultData::getWebName, WebUserInfoUserResultData::getUserName));
//        ExcelReader reader = ExcelUtil.getReader("C:\\Users\\EDZ\\Desktop\\0428没有经纬度绑定网格的楼宇带人数0428.xlsx",1);
//        List<List<Object>> readAll = reader.read(1);
//
//        for (int i = 0; i < readAll.size(); i++) {
//            Row currentRow = reader.getSheet().getRow(i + 1);
//            List<Object> objects = readAll.get(i);
//            String webName = objects.size() > 10 && objects.get(10) != null&& !"K".equals(objects.get(10))? objects.get(10).toString() : null;
//            String userName = collect.get(webName);
//            if(StringUtils.isNotBlank(userName)){
//                currentRow.createCell(14).setCellValue(userName);
//            }
//        }
//        List<List<Object>> readAll2 = reader.read();



        String writePath = "C:\\Users\\EDZ\\Desktop\\data\\result\\0428没有经纬度绑定网格的楼宇带人数0428(新格子负责人)"+System.currentTimeMillis()+".xlsx";
        cn.hutool.poi.excel.ExcelWriter writer = ExcelUtil.getWriter(writePath);
        writer.write(webInfoUser);
        writer.close();
    }

    private static String checkNull(Cell cell) {
        if(cell !=null){
            return cell.getStringCellValue();
        }else{
            return "";
        }
    }
//
//    public static void writeExcel(List<CheckWebResult> results, List<CheckWebResult> addressSuccessNoWebList) throws Exception {
//        String writePath = "C:\\Users\\EDZ\\Desktop\\data\\result\\楼宇匹配结果"+System.currentTimeMillis()+".xlsx";
//        // 这里 需要指定写用哪个class去写
//        ExcelWriter excelWriter = EasyExcel.write(writePath, CheckWebResult.class).build();
//        WriteSheet writeSheet = EasyExcel.writerSheet("匹配成功").build();
//        WriteSheet writeSheet1 = EasyExcel.writerSheet("匹配失败").build();
//        excelWriter.write(results, writeSheet);
//        excelWriter.write(addressSuccessNoWebList,writeSheet1);
//        // 千万别忘记finish 会帮忙关闭流
//        excelWriter.finish();
//    }
//    static class CustomerNameAndWeb{
//        @ExcelProperty(value = "客户名称",index = 0)
//        private String customerName;
//        @ExcelProperty(value = "网格",index = 1)
//        private String web;
//
//        public String getCustomerName() {
//            return customerName;
//        }
//
//        public void setCustomerName(String customerName) {
//            this.customerName = customerName;
//        }
//
//        public String getWeb() {
//            return web;
//        }
//
//        public void setWeb(String web) {
//            this.web = web;
//        }
//    }
}
