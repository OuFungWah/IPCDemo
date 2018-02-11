package com.example.ofw.ipcdemo.serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by ofw on 2018/2/5.
 */

public class SerializalTest {

    public static void main(String args[]){
        SerializableDemo bean = new SerializableDemo("Tommy",19,"male");
        SerializableDemo bean1 = new SerializableDemo("Amy",16,"female");

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("cache.txt"));
            out.writeObject(bean);
            out.writeObject(bean1);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectInputStream in = null;
        try {
             in = new ObjectInputStream(new FileInputStream("cache.txt"));
//            SerializableDemo temp = (SerializableDemo) in.readObject();
//            SerializableDemo temp1 = (SerializableDemo) in.readObject();
//            SerializableDemo temp2 = (SerializableDemo) in.readObject();
//
//            System.out.println("name = "+temp.name+" sex = "+temp.sex+" age = "+temp.age);
//            System.out.println("name = "+temp1.name+" sex = "+temp1.sex+" age = "+temp1.age);
//            System.out.println("name = "+temp2.name+" sex = "+temp2.sex+" age = "+temp2.age);
            while (true){
                SerializableDemo temp = (SerializableDemo) in.readObject();
                System.out.println("name = "+temp.name+" sex = "+temp.sex+" age = "+temp.age);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
