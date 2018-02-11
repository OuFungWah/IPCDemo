package com.example.ofw.ipcdemo.serialization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ofw on 2018/2/5.
 */

public class SerializableDemo2 implements Serializable {

    public static final long serialVersionUID = 1L;

    List<String> list = new ArrayList<>();

}
