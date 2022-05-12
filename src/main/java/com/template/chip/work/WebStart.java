//package com.template.chip.work;
//
//import com.alibaba.excel.EasyExcel;
//import com.alibaba.excel.ExcelWriter;
//import com.alibaba.excel.write.metadata.WriteSheet;
//import com.google.common.collect.Lists;
//import com.monitorjbl.xlsx.StreamingReader;
//import com.template.chip.work.result.*;
//import com.template.chip.work.utils.*;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ThreadPoolExecutor;
//
///**
// * @author guozhenquan
// * @date 2022年04月12日 15:36
// */
//@Slf4j
//public class WebStart {
//
//
//    private static final ThreadPoolTaskExecutor EXECUTOR = new ThreadPoolTaskExecutor();
//    private static final Jedis jedis;
//    private static final JedisPool jedisPool ;
//    static {
//        EXECUTOR.setCorePoolSize(5);
//        EXECUTOR.setMaxPoolSize(20);
//        EXECUTOR.setThreadNamePrefix("customerPoolImport_");
//        EXECUTOR.setKeepAliveSeconds(200);
//        EXECUTOR.setQueueCapacity(20);
//        EXECUTOR.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        EXECUTOR.initialize();
//
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxTotal(50);
//        jedisPoolConfig.setMaxIdle(10);
//        jedisPoolConfig.setMinIdle(10);
//        jedisPoolConfig.setTestOnBorrow(true);
//        jedisPoolConfig.setBlockWhenExhausted(true);
//        jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379, 1000);
//        jedis = jedisPool.getResource();
//    }
//
//    public static void main(String[] args) throws Exception {
//        String fileUrl = "C:\\Users\\EDZ\\Desktop\\data\\成单客户信息北京-0412.xlsx";
////        List<CityAndAddressResultData> resultData = ExcelUtils.listCityAndAddressResultData(fileUrl);
////        initDataBaidu(resultData);
////        initDataGaode(resultData);
//
////        List<List<CityAndAddressResultData>> partition = Lists.partition(resultData, 500);
////        partition.parallelStream().forEach(e -> {
////            CompletableFuture.runAsync(() -> initDataGaode(resultData), EXECUTOR);
////        });
////        initDataBaidu(resultData);
////        initDataGaode(resultData);
////        initDataGaodePoi();
//        matchingWeb(fileUrl);
//    }
//
//    private static void matching(String address, String city) throws SQLException {
//        GaodeResultData result = MapUtil.getLonLatGaode(address, city);
//        String location = result.getGeocodes().get(0).getLocation();
//        String[] split = location.split(",");
//        Gps2 gg = new Gps2(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
//        List<WebUserInfoResultData> webInfo = WebUtils.getWebInfo();
//        for (WebUserInfoResultData e : webInfo) {
//            String departmentName = e.getDepartmentName();
//            String userName = e.getUserName();
//            String webName = e.getWebName();
//            List<Gps2> locations = e.getLocations();
//            if (GpsUtil.isInPolygon(gg, locations)) {
//                System.out.println(address + ":" + webName);
//                break;
//            }
//        }
//        System.out.println("没匹配到");
//    }
//
//    private static void initDataGaode(List<CityAndAddressResultData> exportResultDataList) {
//        exportResultDataList.forEach(e -> {
//            try {
//                String city = e.getCity();
//                String address = e.getAddress();
//                GaodeResultData result = MapUtil.getLonLatGaode(address, city);
//                if (result.isSuccess()) {
//                    String location = result.getGeocodes().get(0).getLocation();
//                    jedis.hset("coordinate", address, location);
//                } else {
//                    jedis.hset("noCoordinate", address, "");
//                }
//            } catch (Exception e1) {
//                e1.printStackTrace();
//                System.out.println(e.getAddress() + "抛异常了");
//            }
//        });
//        jedis.close();
//    }
//
//    private static void initDataBaidu(List<CityAndAddressResultData> exportResultDataList) {
//        Jedis resource = jedisPool.getResource();
//        exportResultDataList.forEach(e -> {
//            try {
//                String city = e.getCity();
//                String address = e.getAddress();
//                BaiduResultData object = MapUtil.getLonLatBaidu(address);
//                if (object.isSuccess()) {
//                    BaiduResultData.ResultDTO result = object.getResult();
//                    BaiduResultData.ResultDTO.LocationDTO location = result.getLocation();
//                    resource.hset("coordinate", address, String.join(",", location.getLng().toString(), location.getLat().toString()));
//                } else {
//                    resource.hset("noCoordinate", address, "1");
//                }
//            } catch (Exception e1) {
//                e1.printStackTrace();
//                System.out.println(e.getAddress() + "抛异常了");
//            }
//        });
//        resource.close();
//    }
//
//
//    private static void initDataGaodePoi(List<ExportResultData> exportResultDataList) {
//        exportResultDataList.forEach(e -> {
//            String city = e.getCity();
//            String address = e.getAddress();
//            GaodePoiResultData result = MapUtil.getLonLatGaodePoi(address, city);
//            if (result.isSuccess()) {
//                List<GaodePoiResultData.PoisDTO> pois = result.getPois();
//                if (pois.size() > 0) {
//                    GaodePoiResultData.PoisDTO poisDTO = pois.get(0);
//                    String entrLocation = poisDTO.getEntrLocation();
//                    if (!"[]".equals(entrLocation)) {
//                        jedis.hset("coordinate", address, entrLocation);
//                    } else {
//                        jedis.hset("noCoordinate", address, entrLocation);
//                    }
//                } else {
//                    jedis.hset("noCoordinate", address, "");
//                }
//
//            } else {
//                jedis.hset("noCoordinate", address, "1");
//            }
//        });
//        jedis.close();
//    }
//
//
//    public static void matchingWeb(String fileUrl) throws Exception {
//        Sheet sheet = ExcelUtils.getRows(fileUrl);
//        List<WebUserInfoResultData> webInfo = WebUtils.getWebInfo();
//
//        List<ExportResultData> addressBadList = new ArrayList<>();
//        List<ExportResultData> addressSuccessHiveWebList = Lists.newArrayList();
//        List<ExportResultData> addressSuccessNoWebList = Lists.newArrayList();
//
//        for (Row row : sheet) {
//            int rowNum = row.getRowNum();
//            if (rowNum == 0) {
//                continue;
//            }
//            String name = row.getCell(0).getStringCellValue();
//            String city = row.getCell(1).getStringCellValue();
//            String address = row.getCell(2).getStringCellValue();
//            String cs = jedis.hget("coordinate", address);
//            ExportResultData exportResultData = new ExportResultData();
//            exportResultData.setCustomerName(name);
//            exportResultData.setCity(city);
//            exportResultData.setAddress(address);
//            if (StringUtils.isBlank(cs)) {
//                addressBadList.add(exportResultData);
//            } else {
//                String[] split = cs.split(",");
//                Gps2 gg = new Gps2(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
////                Gps2 gps2 = GpsUtil.bd09ToGcj02(gg);
//                boolean webFlag = true;
//                for (WebUserInfoResultData e : webInfo) {
//                    String departmentName = e.getDepartmentName();
//                    String userName = e.getUserName();
//                    String webName = e.getWebName();
//                    List<Gps2> locations = e.getLocations();
//                    if (GpsUtil.isInPolygon(gg, locations)) {
//                        exportResultData.setWebName(webName);
//                        exportResultData.setWebUserName(userName);
//                        exportResultData.setDepartmentName(departmentName);
//                        addressSuccessHiveWebList.add(exportResultData);
//                        webFlag = false;
//                        break;
//                    }
//                }
//                if (webFlag) {
//                    addressSuccessNoWebList.add(exportResultData);
//                }
//            }
//        }
//        ExcelUtils.writeExcel(addressSuccessHiveWebList, addressBadList, addressSuccessNoWebList);
//    }
//}
