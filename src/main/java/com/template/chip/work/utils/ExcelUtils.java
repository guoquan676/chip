//package com.template.chip.work.utils;
//
//import com.alibaba.excel.EasyExcel;
//import com.alibaba.excel.ExcelWriter;
//import com.alibaba.excel.write.metadata.WriteSheet;
//import com.google.common.collect.Lists;
//import com.monitorjbl.xlsx.StreamingReader;
//import com.template.chip.work.result.CityAndAddressResultData;
//import com.template.chip.work.result.ExportResultData;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.util.List;
//
///**
// * @author guozhenquan
// * @date 2022年04月15日 15:10
// */
//public class ExcelUtils {
//
//    public static List<CityAndAddressResultData> listCityAndAddressResultData(String fileUrl) throws FileNotFoundException {
//        Sheet sheet = getRows(fileUrl);
//        List<CityAndAddressResultData> exportResultDataList = Lists.newArrayList();
//        for (Row row : sheet) {
//            int rowNum = row.getRowNum();
//            if (rowNum == 0) {
//                continue;
//            }
//            String city = row.getCell(1).getStringCellValue();
//            String address = row.getCell(2).getStringCellValue();
//            CityAndAddressResultData cityAndAddressResultData = new CityAndAddressResultData();
//            cityAndAddressResultData.setCity(city);
//            cityAndAddressResultData.setAddress(address);
//            exportResultDataList.add(cityAndAddressResultData);
//        }
//        return exportResultDataList;
//    }
//    public static Sheet getRows(String s) throws FileNotFoundException {
//        InputStream in = new FileInputStream(s);
//        Workbook wk = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(in);
//        return wk.getSheetAt(0);
//    }
//
//    public static void writeExcel(List<ExportResultData> addressSuccessHiveWebList, List<ExportResultData> addressBadList, List<ExportResultData> addressSuccessNoWebList) throws Exception {
//        String writePath = "C:\\Users\\EDZ\\Desktop\\data\\result\\匹配结果"+System.currentTimeMillis()+".xlsx";
//        // 这里 需要指定写用哪个class去写
//        ExcelWriter excelWriter = EasyExcel.write(writePath, ExportResultData.class).build();
//        WriteSheet writeSheet = EasyExcel.writerSheet("匹配成功").build();
//        WriteSheet writeSheet1 = EasyExcel.writerSheet("匹配失败").build();
//        WriteSheet writeSheet2 = EasyExcel.writerSheet("解析地址失败").build();
//        excelWriter.write(addressSuccessHiveWebList, writeSheet);
//        excelWriter.write(addressSuccessNoWebList,writeSheet1);
//        excelWriter.write(addressBadList,writeSheet2);
//        // 千万别忘记finish 会帮忙关闭流
//        excelWriter.finish();
//    }
//}
