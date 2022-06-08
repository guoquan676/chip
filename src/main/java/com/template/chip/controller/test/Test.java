//package com.template.chip.controller.test;
//
//import cn.hutool.core.io.file.FileWriter;
//import com.google.common.collect.Lists;
//import org.springframework.expression.Expression;
//import org.springframework.expression.spel.standard.SpelExpressionParser;
//
//import java.util.ArrayList;
//import java.util.Properties;
//import java.util.concurrent.ThreadLocalRandom;
//
///**
// * @author guozhenquan
// * @date 2022年04月15日 17:38
// */
//public class Test {
//
//    private String name;
//
////    public static void main(String[] args) {
//////        ThreadLocalRandom random = ThreadLocalRandom.current();
//////        int count = 0;
//////        for (int i = 0; i < 5; i++) {
//////            if (isInterested(random.nextInt(10))) {
//////                count++;
//////            }
//////        }
//////        System.out.printf("Found %d interested values%n", count);
////        Test test = new Test();
////        test.name = "小狗";
////
////    }
//
//    public static void main(String[] args) {
//        FileWriter writer = new FileWriter("C:\\Users\\EDZ\\Desktop\\results20220510173132");
//
//        ArrayList<String> strings = Lists.newArrayList("1111", "22222", "33333");
//        strings.forEach(e->{
//            writer.append(e+"\n");
//        });
//    }
//    private static boolean isInterested(int i) {
//        return i % 2 == 0;
//    }
//}
