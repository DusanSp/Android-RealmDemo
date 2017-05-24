package com.example.dusanspasic.androidrealmdemo.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by dusanspasic on 24.5.17..
 */

public class Person extends RealmObject {

    @PrimaryKey
    private long id;
    private String name;
    private RealmList<Dog> dogs;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(RealmList<Dog> dogs) {
        this.dogs = dogs;
    }
}
