package com.example.ofw.ipcdemo.serialization;

import java.io.Serializable;

/**
 * Created by ofw on 2018/2/4.
 */

public class SerializableDemo implements Serializable {

    
    private static final long serialVersionUID = 1L;

//    private static final long serialVersionUID = 1L;

    public String name;
    public int age;
    public String sex;

    public SerializableDemo(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
}
