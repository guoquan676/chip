package com.template.chip.work.result;

//package com.template.chip.work.result;
//
//import com.alibaba.excel.annotation.ExcelProperty;
//import lombok.Data;
//
///**
// * @author guozhenquan
// * @date 2022年04月20日 14:47
// */
//@Data
public class CheckWebResult {
//    @ExcelProperty(value = "客户名称",index = 0)
    private String customerName;
//    @ExcelProperty(value = "现有楼宇数据",index = 1)
    private String currentBuilding;
//    @ExcelProperty(value = "选择楼宇(人工)",index = 2)
    private String selectBuilding;
//    @ExcelProperty(value = "填写楼宇(人工)",index = 3)
    private String artificialBuilding;
//    @ExcelProperty(value = "坐标",index = 4)
    private String areaInfo;
//    @ExcelProperty(value = "所属网格",index = 5)
    private String currentWeb;
//    @ExcelProperty(value = "绑定楼宇后网格",index = 6)
    private String afterWeb;
}
