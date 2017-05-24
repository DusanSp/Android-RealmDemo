package com.example.dusanspasic.androidrealmdemo.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by dusanspasic on 24.5.17..
 */

public class Dog extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private int age;
    private String color;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
