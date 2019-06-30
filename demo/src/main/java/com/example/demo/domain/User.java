package com.example.demo.domain;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private int id;
    private String name;
    private int age;
    private Date updateTime;

    public void modify(ModifyUserDTO value) {
        if (value.getAge() != null) {
            this.setAge(value.getAge());
        }
        if (value.getName() != null) {
            this.setName(value.getName());
        }
        this.setUpdateTime(new Date());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", updateTime=" + updateTime +
                '}';
    }
}
