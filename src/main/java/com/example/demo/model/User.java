package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class User extends BaseModel {
    private String name;
    private Integer age;
    private String userHeadImg;

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
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

    public User(String name, Integer age, String userHeadImg) {
        this.name = name;
        this.age = age;
        this.userHeadImg = userHeadImg;
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }


    public User() {
    }

    public void setAge(Integer age) {

        this.age = age;
    }
}