//package com.template.chip.work;
//
//import cn.hutool.poi.excel.ExcelReader;
//import cn.hutool.poi.excel.ExcelUtil;
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import com.template.chip.work.result.CheckWebResult;
//import com.template.chip.work.result.WebUserInfoResultData;
//import com.template.chip.work.utils.Gps2;
//import com.template.chip.work.utils.GpsUtil;
//import com.template.chip.work.utils.WebUtils;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//
//import java.util.List;
//
///**
// * @author guozhenquan
// * @date 2022年04月20日 14:45
// */
//public class CheckWeb {
//
//    public static void main(String[] args) throws Exception {
//
//        List<WebUserInfoResultData> webInfo = WebUtils.getWebInfo();
//
//        ExcelReader reader = ExcelUtil.getReader("C:\\Users\\EDZ\\Desktop\\匹配网格-diff(1).xlsx");
//        List<List<Object>> readAll = reader.read(1);
//
//        for (int i = 0; i < readAll.size(); i++) {
//            Row currentRow = reader.getSheet().getRow(i + 1);
//            List<Object> objects = readAll.get(i);
//            String customerName = objects.size() > 0 && objects.get(1) != null ? objects.get(1).toString() : null;
//            String areaInfo = objects.size() > 15 && objects.get(15) != null&& !"P".equals(objects.get(15))? objects.get(15).toString() : null;
//            String areaInfo2 = objects.size() > 17 && objects.get(17) != null&& !"R".equals(objects.get(17)) ? objects.get(17).toString() : null;
//
//            String[] split = new String[0];
//
//            if(StringUtils.isNotBlank(areaInfo)){
//                split =  areaInfo.split(",");
//            }
//            if(StringUtils.isNotBlank(areaInfo2)){
//                split =  areaInfo2.split(",");
//            }
//            if(split.length==0){
//                currentRow.createCell(18).setCellValue("/");
//                continue;
//            }
//            try{
//                Gps2 gg = new Gps2(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
//                boolean webFlag = true;
//                CheckWebResult checkWebResult = new CheckWebResult();
//                for (WebUserInfoResultData e : webInfo) {
//                    String departmentName = e.getDepartmentName();
//                    String userName = e.getUserName();
//                    String webName = e.getWebName();
//                    List<Gps2> locations = e.getLocations();
//                    if (GpsUtil.isInPolygon(gg, locations)) {
//                         currentRow.createCell(18).setCellValue(webName);
//                        webFlag = false;
//                        break;
//                    }
//                }
//                if (webFlag) {
//                    currentRow.createCell(18).setCellValue("/");
//                }
//            }catch (Exception e){
//                currentRow.createCell(18).setCellValue("/");
//            }
//        }
//        Sheet sheet = reader.getSheet();
//        List<List<Object>> readAll2 = reader.read();
//
//
//        String writePath = "C:\\Users\\EDZ\\Desktop\\data\\result\\楼宇匹配网格结果"+System.currentTimeMillis()+".xlsx";
//        cn.hutool.poi.excel.ExcelWriter writer = ExcelUtil.getWriter(writePath);
//        writer.write(readAll2);
//        writer.close();
//    }
//
//    private static String checkNull(Cell cell) {
//        if(cell !=null){
//           return cell.getStringCellValue();
//        }else{
//           return "";
//        }
//    }
////
////    public static void writeExcel(List<CheckWebResult> results, List<CheckWebResult> addressSuccessNoWebList) throws Exception {
////        String writePath = "C:\\Users\\EDZ\\Desktop\\data\\result\\楼宇匹配结果"+System.currentTimeMillis()+".xlsx";
////        // 这里 需要指定写用哪个class去写
////        ExcelWriter excelWriter = EasyExcel.write(writePath, CheckWebResult.class).build();
////        WriteSheet writeSheet = EasyExcel.writerSheet("匹配成功").build();
////        WriteSheet writeSheet1 = EasyExcel.writerSheet("匹配失败").build();
////        excelWriter.write(results, writeSheet);
////        excelWriter.write(addressSuccessNoWebList,writeSheet1);
////        // 千万别忘记finish 会帮忙关闭流
////        excelWriter.finish();
////    }
////    static class CustomerNameAndWeb{
////        @ExcelProperty(value = "客户名称",index = 0)
////        private String customerName;
////        @ExcelProperty(value = "网格",index = 1)
////        private String web;
////
////        public String getCustomerName() {
////            return customerName;
////        }
////
////        public void setCustomerName(String customerName) {
////            this.customerName = customerName;
////        }
////
////        public String getWeb() {
////            return web;
////        }
////
////        public void setWeb(String web) {
////            this.web = web;
////        }
////    }
//}
