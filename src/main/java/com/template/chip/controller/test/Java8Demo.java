package com.template.chip.controller.test;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.google.common.cache.Weigher;
import com.google.common.collect.Lists;
import com.template.chip.component.exception.BaseException;
import com.template.chip.component.exception.resultenum.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StopWatch;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author guozhenquan
 * @date 2022年03月28日 11:38
 */
@Slf4j
public class Java8Demo {

    private static final ThreadPoolTaskExecutor EXECUTOR = new ThreadPoolTaskExecutor();

    static {
        EXECUTOR.setCorePoolSize(5);
        EXECUTOR.setMaxPoolSize(20);
        EXECUTOR.setThreadNamePrefix("customerPoolImport_");
        EXECUTOR.setKeepAliveSeconds(200);
        EXECUTOR.setQueueCapacity(20);
        EXECUTOR.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        EXECUTOR.initialize();
    }

    public Integer sprinkleSalt() {
        return 3;
    }

    /**
     * 空指针处理
     */
    public void optionalDemo() {
        Person person = new Person();
        //不为空直接打印
        Optional<Person> person1 = Optional.ofNullable(person);
        person1.ifPresent(e -> System.out.println(e.toString()));
        //如果不为空，进行get操作
        if (person1.isPresent()) {
            Person person2 = person1.get();
        }
        //如果等于空取后面的值
        Person person2 = person1.orElse(new Person());
        System.out.println(person2);
        //如果为空报异常
        person1.orElseThrow(() -> new BaseException(ErrorCode.SYSTEM_ERROR, null));
    }

    /**
     * 集合分组处理
     */
    private static void groupDemo() {
        List<Person> persons = Lists.newArrayList();
        //过滤分组
        Map<String, List<Person>> collect = persons.stream().filter(e -> e.getAge().equals(1)).collect(Collectors.groupingBy(Person::getName, Collectors.toList()));
        //过滤计数
        Map<String, Long> collect1 = persons.stream().collect(Collectors.groupingBy(Person::getName, Collectors.counting()));
        //list to map
        Map<String, Person> collect2 = persons.stream().collect(Collectors.toMap(Person::getName, a -> a));
        Map<String, Integer> collect3 = persons.stream().collect(Collectors.toMap(Person::getName, Person::getAge));
    }

    /**
     * 带连接池，异步调用。
     */
    public void asynCall() throws ExecutionException, InterruptedException {
        //（无返回值）
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> sprinkleSalt(), EXECUTOR);
        //（有返回值）
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> sprinkleSalt(), EXECUTOR);
        CompletableFuture<Integer> integerCompletableFuture2 = CompletableFuture.supplyAsync(() -> sprinkleSalt(), EXECUTOR);
        //等到全部返回返回结果。
        CompletableFuture.allOf(voidCompletableFuture, integerCompletableFuture, integerCompletableFuture2).join();
        Integer integer1 = integerCompletableFuture.get();
        Integer integer = integerCompletableFuture2.get();
        List<String> list = Lists.newArrayList();
        List<String> collect = list.stream().sorted(Comparator.comparingInt(String::length)).collect(Collectors.toList());
        List<String> collect1 = list.stream().sorted(Comparator.comparingInt(String::length)).collect(Collectors.toList());

    }

    public static void main(String[] args) throws Exception {
//        String contents = new String(Files.readAllBytes(Paths.get("C:\\Users\\EDZ\\Desktop\\alice.txt")), StandardCharsets.UTF_8);
//        List<String> words = Arrays.asList(contents.split("[\\P{L}]+"));
////        long count = words.parallelStream().filter(word -> word.length() > 12).peek(e->{System.out.println(Thread.currentThread().getName());}).count();
//        StopWatch stopWatch = new StopWatch("测速");
//        stopWatch.start("count");
//        long count = words.stream().filter(word -> word.length() > 12).count();
//        stopWatch.stop();
//        stopWatch.start("count2");
//        long count1 = words.parallelStream().filter(word -> word.length() > 12).count();
//        stopWatch.stop();
//        System.out.println(stopWatch.prettyPrint());
//        System.out.println(count);
//        System.out.println(count1);
//        long count = Stream.iterate(0, i -> i+1).count();
//        System.out.println(count);

//        List<Integer> integers = Lists.newArrayList(1, 2, 3);
//        List<Integer> integers2 = Lists.newArrayList(4, 5, 6);
//        Stream<List<Integer>> integers1 = Stream.of(integers, integers2);

//        List<Integer> integers3 = integers1.reduce((a, a1) -> {
//            a.addAll(a1);
//            return a;
//        }).orElse(Lists.newArrayList());

//        List<Integer> reduce = integers1.reduce(Lists.newArrayList(), (a, a1) -> {
//            a.addAll(a1);
//            return a;
//        });
//
//        ArrayList<Object> reduce1 = integers1.reduce(new ArrayList<>(), (a, a1) -> {
//            a.addAll(a1);
//            return a;
//        }, (result1, result2) -> {
//            result1.addAll(result2);
//            return result1;
//        });
////        integers3.forEach(e->System.out.println(e));
////        reduce.forEach(e->System.out.println(e));
//        reduce1.forEach(e->System.out.println(e));

//        DoubleStream doubleStream = DoubleStream.of(3d, 4d, 5d);
//        DoubleSummaryStatistics doubleSummaryStatistics = doubleStream.summaryStatistics();
//        double average = doubleSummaryStatistics.getAverage();
//        System.out.println(average);

        Stream<Double> doubleStream = Stream.of(1.2, 2.3, 3.4, 4.5, 5.6);
        double average = doubleStream.parallel().reduce(new Item(), Item::add, Item::add).getAverage();
        System.out.println(average);
    }

    static class Item{
        private Double count;
        private Double sum;
        public Item() {
            this.count = 0.0d;
            this.sum = 0.0d;
        }
        public Item add(Double num){
            count++;
            sum+=num;
            return this;
        }
        public Item add(Item item){
            sum+= item.sum;
            count+=item.count;
            return this;
        }
        public double getAverage(){
            return sum/count;
        }
    }

    public static List<Runnable> andThen(Runnable runner1, Runnable runner2) {
        return Lists.newArrayList(runner1, runner2);
    }

    public static Runnable uncheck(RunnableEx runner) {
        return () -> {
            try {
                runner.run();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        };
    }
}


