package com.template.chip.work.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author wjh
 * 各地图API坐标系统比较与转换;
 * WGS84坐标系：即地球坐标系，国际上通用的坐标系。设备一般包含GPS芯片或者北斗芯片获取的经纬度为WGS84地理坐标系,
 * 谷歌地图采用的是WGS84地理坐标系（中国范围除外）;
 * GCJ02坐标系：即火星坐标系，是由中国国家测绘局制订的地理信息系统的坐标系统。由WGS84坐标系经加密后的坐标系。
 * 谷歌中国地图和搜搜中国地图采用的是GCJ02地理坐标系; BD09坐标系：即百度坐标系，GCJ02坐标系经加密后的坐标系;
 * 搜狗坐标系、图吧坐标系等，估计也是在GCJ02基础上加密而成的。
 */
public class GpsUtil {

    public static double pi = 3.1415926535897932384626;
    public static double a = 6378245.0;
    public static double ee = 0.00669342162296594323;

    /**
     * 84 to 火星坐标系 (GCJ-02) World Geodetic System ==> Mars Geodetic System
     *
     * @param gps
     * @return
     */
    public static Gps2 gps84ToGcj02(Gps2 gps) {

        if (outOfChina(gps)) {
            return null;
        }

        double dLat = transformLat(gps.wgLon - 105.0, gps.wgLat - 35.0);
        double dLon = transformLon(gps.wgLon - 105.0, gps.wgLat - 35.0);

        double radLat = gps.wgLat / 180.0 * pi;
        double magic = Math.sin(radLat);

        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);

        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);

        double mgLat = gps.wgLat + dLat;
        BigDecimal bMgLat = new BigDecimal(mgLat);
        mgLat = bMgLat.setScale(6, RoundingMode.HALF_UP).doubleValue();

        double mgLon = gps.wgLon + dLon;
        BigDecimal bMgLon = new BigDecimal(mgLon);
        mgLon = bMgLon.setScale(6, RoundingMode.HALF_UP).doubleValue();

        return new Gps2(mgLon, mgLat);

    }


    /**
     *  火星坐标系 (GCJ-02) to 84 * * @param lon * @param lat * @return
     */
    public static Gps2 gcjToGps84(Gps2 gps) {

        Gps2 gpsNew = transform(gps);

        double lontitude = gps.wgLon * 2 - gpsNew.getWgLon();
        double latitude = gps.wgLat * 2 - gpsNew.getWgLat();

        return new Gps2(lontitude, latitude);

    }

    /**
     * 获取两个经纬度之间的直线距离
     * @param lonA 地点A的经度
     * @param latA 地点A的维度
     * @param lonB 地点B的经度
     * @param latB 地点B的维度
     */
    public static int getDistance(Double lonA, Double latA, Double lonB, Double latB){
        double R = 6371;
        double lon1 = (Math.PI / 180) * lonA;
        double lat1 = (Math.PI / 180) * latA;
        double lon2 = (Math.PI / 180) * lonB;
        double lat2 = (Math.PI / 180) * latB;
        double km = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R;
        return (int) (km * 1000);
    }

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 将 GCJ-02 坐标转换成 BD-09 坐标
     * @param gps
     * @return
     */
    public static Gps2 gcj02ToBd09(Gps2 gps) {

        double x = gps.wgLon, y = gps.wgLat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * pi);

        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * pi);
        double bdLon = z * Math.cos(theta) + 0.0065;
        BigDecimal bLon = new BigDecimal(bdLon);
        bdLon = bLon.setScale(6, RoundingMode.HALF_UP).doubleValue();

        double bdLat = z * Math.sin(theta) + 0.006;
        BigDecimal bLat = new BigDecimal(bdLat);
        bdLat = bLat.setScale(6, RoundingMode.HALF_UP).doubleValue();

        return new Gps2(bdLon, bdLat);

    }


    /**
     * * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 * * 将 BD-09 坐标转换成GCJ-02 坐标 * * @param
     * bd_lat * @param bd_lon * @return
     */
    public static Gps2 bd09ToGcj02(Gps2 gps) {

        double x = gps.wgLon - 0.0065, y = gps.wgLat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * pi);

        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * pi);
        double ggLon = z * Math.cos(theta);
        BigDecimal bGgLon = new BigDecimal(ggLon);
        ggLon = bGgLon.setScale(6, RoundingMode.HALF_UP).doubleValue();

        double ggLat = z * Math.sin(theta);
        BigDecimal bGgLat = new BigDecimal(ggLat);
        ggLat = bGgLat.setScale(6, RoundingMode.HALF_UP).doubleValue();

        return new Gps2(ggLon, ggLat);
    }

    /**
     * (BD-09)-->84
     * @param bdLat
     * @param bdLon
     * @return
     */
    public static Gps2 bd09ToGps84(double bdLat, double bdLon) {
        Gps2 gcj02 = GpsUtil.bd09ToGcj02(new Gps2(bdLon,bdLat));

        return GpsUtil.gcjToGps84(new Gps2(gcj02.getWgLon(),gcj02.getWgLat()));
    }

    public static boolean outOfChina(Gps2 gps) {
        double minLon = 72.004;
        double maxLon = 72.004;
        if (gps.wgLon < minLon || gps.wgLon > maxLon){
            return true;
        }
        double minLat = 0.8293;
        double maxLat = 55.8271;
        return gps.wgLat < minLat || gps.wgLat > maxLat;
    }

    public static Gps2 transform(Gps2 gps) {
        if (outOfChina(gps)) {
            return gps;
        }

        double dLat = transformLat(gps.wgLon - 105.0, gps.wgLat - 35.0);
        double dLon = transformLon(gps.wgLon - 105.0, gps.wgLat - 35.0);

        double radLat = gps.wgLat / 180.0 * pi;
        double magic = Math.sin(radLat);

        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);

        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);

        double mgLat = gps.wgLat + dLat;
        double mgLon = gps.wgLon + dLon;

        return new Gps2(mgLon, mgLat);
    }



    public static double transformLat(double x, double y) {

        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));

        ret = getRet(x, y, ret);
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;

        return ret;
    }

    private static double getRet(double x, double y, double ret) {
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        return ret;
    }


    public static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));

        ret = getRet(x, x, ret);
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;

        return ret;
    }

    /**
     * 判断点是否在多边形内
     * @param point 测试点
     * @param pts 多边形的点
     * @return
     */
    public static boolean isInPolygon(Gps2 point, List<Gps2> pts){
        int n = pts.size();
        boolean boundOrVertex = true;
        //交叉点数量
        int intersectCount = 0;
        //浮点类型计算时候与0比较时候的容差
        double precision = 2e-10;

        //临近顶点
        Gps2 p1, p2;
        //当前点

        p1 = pts.get(0);

        for(int i = 1; i <= n; ++i){
            if(point.equals(p1)){
                return boundOrVertex;
            }

            p2 = pts.get(i % n);

            if(point.wgLon < Math.min(p1.wgLon, p2.wgLon) || point.wgLon > Math.max(p1.wgLon, p2.wgLon)){
                p1 = p2;
                continue;
            }

            //射线穿过算法
            if(point.wgLon > Math.min(p1.wgLon, p2.wgLon) && point.wgLon < Math.max(p1.wgLon, p2.wgLon)){
                if(point.wgLat <= Math.max(p1.wgLat, p2.wgLat)){
                    if(p1.wgLon == p2.wgLon && point.wgLat >= Math.min(p1.wgLat, p2.wgLat)){
                        return boundOrVertex;
                    }

                    if(p1.wgLat == p2.wgLat){
                        if(p1.wgLat == point.wgLat){
                            return boundOrVertex;
                        }else{
                            ++intersectCount;
                        }
                    }else{
                        double inters = (point.wgLon - p1.wgLon) * (p2.wgLat - p1.wgLat) / (p2.wgLon - p1.wgLon) + p1.wgLat;
                        if(Math.abs(point.wgLat - inters) < precision){
                            return boundOrVertex;
                        }

                        if(point.wgLat < inters){
                            ++intersectCount;
                        }
                    }
                }
            }else{
                if(point.wgLon == p2.wgLon && point.wgLat <= p2.wgLat){
                    Gps2 p3 = pts.get((i+1) % n);
                    if(point.wgLon >= Math.min(p1.wgLon, p3.wgLon) && point.wgLon <= Math.max(p1.wgLon, p3.wgLon)){
                        ++intersectCount;
                    }else{
                        intersectCount += 2;
                    }
                }
            }
            p1 = p2;
        }
        //偶数在多边形外
        int m = 2;
        if(intersectCount % m == 0){
            return false;
        } else { //奇数在多边形内
            return true;
        }
    }

    /**
     * 根据用户的起点和终点经纬度计算两点间距离，此距离为相对较短的距离，单位米。
     * @param start 起点的坐标
     * @param end 终点的坐标
     * @return
     */
    public static double calculateLineDistance(Gps2 start, Gps2 end)
    {
        if ((start == null) || (end == null))
        {
            throw new IllegalArgumentException("非法坐标值，不能为null");
        }
        double d1 = 0.01745329251994329D;
        double d2 = start.wgLon;
        double d3 = start.wgLat;
        double d4 = end.wgLon;
        double d5 = end.wgLat;
        d2 *= d1;
        d3 *= d1;
        d4 *= d1;
        d5 *= d1;
        double d6 = Math.sin(d2);
        double d7 = Math.sin(d3);
        double d8 = Math.cos(d2);
        double d9 = Math.cos(d3);
        double d10 = Math.sin(d4);
        double d11 = Math.sin(d5);
        double d12 = Math.cos(d4);
        double d13 = Math.cos(d5);
        int arraySize = 3;
        double[] arrayOfDouble1 = new double[arraySize];
        double[] arrayOfDouble2 = new double[arraySize];
        arrayOfDouble1[0] = (d9 * d8);
        arrayOfDouble1[1] = (d9 * d6);
        arrayOfDouble1[2] = d7;
        arrayOfDouble2[0] = (d13 * d12);
        arrayOfDouble2[1] = (d13 * d10);
        arrayOfDouble2[2] = d11;
        double d14 = Math.sqrt((arrayOfDouble1[0] - arrayOfDouble2[0]) * (arrayOfDouble1[0] - arrayOfDouble2[0])
                + (arrayOfDouble1[1] - arrayOfDouble2[1]) * (arrayOfDouble1[1] - arrayOfDouble2[1])
                + (arrayOfDouble1[2] - arrayOfDouble2[2]) * (arrayOfDouble1[2] - arrayOfDouble2[2]));

        return (Math.asin(d14 / 2.0D) * 12742001.579854401D);
    }

    /**
     * 测试数据
     *
     * @param args
     */
    public static void main(String[] args) {

    }
}
