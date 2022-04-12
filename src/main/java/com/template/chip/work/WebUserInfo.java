package com.template.chip.work;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guozhenquan
 * @date 2022年04月12日 16:37
 */
public class WebUserInfo {
    private String webName;
    private String userName;
    private String departmentName;
    List<Gps2> locations = new ArrayList<>();

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<Gps2> getLocations() {
        return locations;
    }

    public void setLocations(List<Gps2> locations) {
        this.locations = locations;
    }
}
