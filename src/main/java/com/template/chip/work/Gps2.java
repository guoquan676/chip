package com.template.chip.work;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author wjh
 */
public class Gps2 implements Serializable {
    /**
     * 地球坐标:经度
     */
    public double wgLon;
    /**
     * 地球坐标:纬度
     */
    public double wgLat;

    public String name;

    public Gps2(double wgLon,double wgLat){
        this.wgLon = wgLon;
        this.wgLat = wgLat;
    }

    public double getWgLat() {
        return wgLat;
    }

    public void setWgLat(double wgLat) {
        this.wgLat = wgLat;
    }

    public double getWgLon() {
        return wgLon;
    }

    public void setWgLon(double wgLon) {
        this.wgLon = wgLon;
    }

    public void setLocation(double wgLat, double wgLon) {
        this.wgLon = wgLon;
    }

    @Override
    public String toString() {
        return "Gps["+wgLon+", "+wgLat+"]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Gps2 gps = (Gps2) o;
        return Double.compare(gps.wgLat, wgLat) == 0 && Double.compare(gps.wgLon, wgLon) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(wgLat, wgLon);
    }
}
