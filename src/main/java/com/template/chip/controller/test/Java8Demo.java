package com.template.chip.controller.test;

import com.google.common.collect.Lists;
import com.template.chip.component.exception.BaseException;
import com.template.chip.component.exception.resultenum.ErrorCode;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author guozhenquan
 * @date 2022年03月28日 11:38
 */
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

    public Integer sprinkleSalt(){
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

    public void asynCall() throws ExecutionException, InterruptedException {
        //（无返回值）
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> sprinkleSalt(), EXECUTOR);
        //（有返回值）
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> sprinkleSalt(), EXECUTOR);
        CompletableFuture<Integer> integerCompletableFuture2 = CompletableFuture.supplyAsync(() -> sprinkleSalt(), EXECUTOR);
        //等到全部返回返回结果。
        CompletableFuture.allOf(voidCompletableFuture,integerCompletableFuture,integerCompletableFuture2).join();
        Integer integer1 = integerCompletableFuture.get();
        Integer integer = integerCompletableFuture2.get();
    }

    public static void main(String[] args) throws Exception {

    }
}
