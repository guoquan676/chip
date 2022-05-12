package com.template.chip.work.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author wjh
 */
@Data
public class ExcelGps {
    /**
     * 地球坐标:经度
     */
    public String wgLon;
    /**
     * 地球坐标:纬度
     */
    public String wgLat;

    public String name;

    public String getName() {
        return subName(name);
    }

    private static String subName(String a) {
        int i = a.lastIndexOf("-");
        return a.substring(0,i);
    }
}
