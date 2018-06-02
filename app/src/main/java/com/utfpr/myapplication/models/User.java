package com.utfpr.myapplication.models;

/**
 * Created by lispa on 16/05/2018.
 */

public class User {
    public String name;

    public User(){

    }

    public User(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
