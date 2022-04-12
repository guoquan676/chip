package com.template.chip.work;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @author guozhenquan
 * @date 2022年04月12日 17:39
 */

public class ResultData {
    @ExcelProperty(value = "客户名称",index = 0)
    private String customerName;
    @ExcelProperty(value = "城市",index = 1)
    private String city;
    @ExcelProperty(value = "公司地址",index = 2)
    private String address;
    @ExcelProperty(value = "系统匹配网格",index = 3)
    private String webName;
    @ExcelProperty(value = "网格负责人",index = 4)
    private String webUserName;
    @ExcelProperty(value = "所属小组",index = 5)
    private String departmentName;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public String getWebUserName() {
        return webUserName;
    }

    public void setWebUserName(String webUserName) {
        this.webUserName = webUserName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
