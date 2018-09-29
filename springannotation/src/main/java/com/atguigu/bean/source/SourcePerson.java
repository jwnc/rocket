package com.atguigu.bean.source;

import org.springframework.beans.factory.annotation.Value;

public class SourcePerson {
    @Value("value-anno") //Value设值优先级高于构造方法的设值
    private String name;
    @Value("#{20-2}")
    private Integer age;
    @Value("${person.nick}")
    private String nickName;


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

    public SourcePerson(String name, Integer age) {
        super();
        this.name = name;
        this.age = age;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public SourcePerson() {
        super();
        System.out.println("create person");
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
