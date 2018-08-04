package com.utfpr.myapplication.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

/**
 * Created by lispa on 16/05/2018.
 */
@IgnoreExtraProperties
public class User {
    public String id;
    public String name;

    public User(){

    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
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

    public HashMap<String,String> toMap(){
        HashMap<String, String> retu = new HashMap<>();
        retu.put("id", this.id);
        retu.put("name", this.name);
        return retu;
    }
}
