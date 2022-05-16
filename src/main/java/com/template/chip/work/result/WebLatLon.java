package com.template.chip.work.result;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 网格对应的经纬度
 *
 * @author guozhenquan
 * @date 2022年05月16日 14:12
 */
@Data
public class WebLatLon {
    /**
     * 地球坐标:纬度
     */
    @ExcelProperty(value = "纬度",index = 0)
    public String wgLat;
    /**
     * 地球坐标:经度
     */
    @ExcelProperty(value = "经度",index = 1)
    public String wgLon;
    /**
     * 网格名称
     */
    @ExcelProperty(value = "网格名称",index = 2)
    public String webName;
}
