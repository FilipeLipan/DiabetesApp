package com.utfpr.myapplication.models;

/**
 * Created by lispa on 16/05/2018.
 */

public class User {
    private String id;
    private String name;

    public User(){

    }

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }
}
