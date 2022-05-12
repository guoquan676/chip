//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.template.chip.controller.test.test2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public enum IndustryFirst {
    ELECTRONICS_COMMUNICATION_HARDWARE(1, "电子.通信.硬件", 1),
    REALTY_ARCHITECTURE_PROPERTY(2, "房地产.建筑.物业", 2),
    SERVICE_OUTSOURCE_INTERMEDIARY(3, "服务.外包.中介", 3),
    Advertising_Media(4, "广告传媒", 4),
    INTERNET(5, "互联网", 5),
    TRAFFIC_TRADE_LOGISTICS(6, "交通.贸易.物流", 6),
    EDUCATION(7, "教育", 7),
    FINANCE(8, "金融", 8),
    ENERGY_CHEMICAL_ENVIRONMENTAL(9, "能源.化工.环保", 9),
    AGRICULTURE_FORESTRY_ANIMAL_FISHERY(10, "农林牧渔", 10),
    AUTOMOBILE_MACHINERY_MANUFACTURING(11, "汽车.机械.制造", 11),
    CONSUMER_GOODS(12, "消费品", 12),
    GOVERNMENT_INSTITUTIONS(13, "政府.事业单位", 13),
    PHARMACEUTICAL_CARE(14, "制药.医疗", 14);

    private int key;
    private String value;
    private int sort;

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getSort() {
        return this.sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    private IndustryFirst(int key, String value, int sort) {
        this.key = key;
        this.value = value;
        this.sort = sort;
    }

    public static List<Industry> getValues() {
        List<Industry> industryList = new ArrayList();
        IndustryFirst[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            IndustryFirst industryFirst = var1[var3];
            Industry industry = new Industry();
            industry.setKey(industryFirst.key);
            industry.setValue(industryFirst.value);
            industry.setSort(industryFirst.sort);
            industryList.add(industry);
        }

        return (List)industryList.stream().sorted().collect(Collectors.toList());
    }

    public static IndustryFirst getValueByKey(Integer key) {
        IndustryFirst i = null;
        if (null == key) {
            return null;
        } else {
            IndustryFirst[] var2 = values();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                IndustryFirst industryFirst = var2[var4];
                if (key.equals(industryFirst.key)) {
                    i = industryFirst;
                    break;
                }
            }

            return i;
        }
    }

    public static void main(String[] args) {
        getValues().stream().sorted().forEach((industry) -> {
            System.out.println(industry);
        });
    }
}
