package com.template.chip.controller.test;

import com.google.common.collect.Lists;
import com.template.chip.component.exception.annotation.JsonResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author guozhenquan
 * @date 2022年02月25日 16:53
 */
@RestController
@RequestMapping("/api")
public class ExceptionController {

    @GetMapping("/resourceNotFound")
    @JsonResponseBody
    public  List<Integer> throwException(String a) {
        List<Integer> integers = Lists.newArrayList(1, 2, 3, 4);
        return integers;
    }

    public static void main(String[] args) throws Exception {
        Person person = new Person();
        //不为空直接打印
        Optional<Person> person1 = Optional.ofNullable(person);
        person1.ifPresent(e->System.out.println(e.toString()));
        //如果不为空，进行get操作
        if(person1.isPresent()){
            Person person2 = person1.get();
        }
        //如果等于空取后面的值
        Person person2 = person1.orElse(new Person());
        System.out.println(person2);
        //如果为空报异常
        person1.orElseThrow(()->new Exception());
    }
}
class Person {
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    private String name;
    private Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}