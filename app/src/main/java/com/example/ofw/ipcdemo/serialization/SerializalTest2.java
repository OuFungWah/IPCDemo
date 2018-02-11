package com.example.ofw.ipcdemo.serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by ofw on 2018/2/5.
 */

public class SerializalTest2 {

    public static void main(String args[]){
        SerializableDemo2 demo2 = new SerializableDemo2();
        demo2.list.add("Tom");
        demo2.list.add("Amy");
        demo2.list.add("Jack");
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("ObjectCache2.txt"));
            out.writeObject(demo2);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("ObjectCache2.txt"));
            SerializableDemo2 temp = (SerializableDemo2) in.readObject();
            in.close();
            for(int i = 0;i<temp.list.size();i++){
                System.out.println("name "+temp.list.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
