//package com.template.chip.work.utils;
//
//import cn.hutool.http.HttpUtil;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.template.chip.work.result.BaiduResultData;
//import com.template.chip.work.result.GaodePoiResultData;
//import com.template.chip.work.result.GaodeResultData;
//
//import java.net.URLEncoder;
//
//public class MapUtil {
//    public static GaodeResultData getLonLatGaode(String name, String city) {
//        String urlString = "https://restapi.amap.com/v3/geocode/geo?address=" + URLEncoder.encode(name) + "&city=" + city + "&output=JSON&key=97988bfc4dce569f89ff67caa26cabe1";
//        String ips = HttpUtil.get(urlString);
//        return JSONObject.parseObject(ips, GaodeResultData.class);
//    }
//
//    public static BaiduResultData getLonLatBaidu(String name) {
//        String urlString = "http://api.map.baidu.com/geocoder?address=" + URLEncoder.encode(name) + "&output=json&src=webapp.baidu.openAPIdemo";
//        String ips = HttpUtil.get(urlString);
//        return JSON.parseObject(ips, BaiduResultData.class);
//    }
//
//    public static GaodePoiResultData getLonLatGaodePoi(String name, String city) {
//        String url = "https://restapi.amap.com/v3/place/text?keywords=" + URLEncoder.encode(name) + "&city=" + city + "&children=1&offset=1&page=1&extensions=all&output=JSON&key=97988bfc4dce569f89ff67caa26cabe1";
//        String ips = HttpUtil.get(url);
//        return JSON.parseObject(ips, GaodePoiResultData.class);
//    }
//}
