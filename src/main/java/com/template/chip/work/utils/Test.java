package com.template.chip.work.utils;

/**
 * @author guozhenquan
 * @date 2022年05月23日 9:04
 */
public class Test {
    public static void main(String[] args) {
//        Gps gps = CoodinateCovertor.bd09_To_Gcj02(Double.parseDouble("30.259882519"),Double.parseDouble("120.015576438"));
//        System.out.println(gps);
        String a = "测试怕是什么_LTE=";
        int i = a.indexOf("_");
        String substring = a.substring(0,i);
        System.out.println(substring);
    }
}
