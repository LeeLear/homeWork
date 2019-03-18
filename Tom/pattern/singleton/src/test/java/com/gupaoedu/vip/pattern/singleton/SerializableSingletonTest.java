package com.gupaoedu.vip.pattern.singleton;

import com.gupaoedu.vip.pattern.singleton.serializable.SerializableSingleton;

import java.io.*;

/**
 * Created by Tom.
 */
public class SerializableSingletonTest {
    public static void main(String[] args) {

       /* SerializableSingleton s1 = null;
        SerializableSingleton s2 = SerializableSingleton.getInstance();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("SerializableSingleton.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(s2);
            oos.flush();
            oos.close();


            FileInputStream fis = new FileInputStream("SerializableSingleton.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            s1 = (SerializableSingleton)ois.readObject();
            ois.close();

            System.out.println(s1);
            System.out.println(s2);
            System.out.println(s1 == s2);

        } catch (Exception e) {
            e.printStackTrace();
        }*/
       SerializableSingleton sIn=null;
       SerializableSingleton sOut = SerializableSingleton.getInstance();

        try {
            FileOutputStream fos = new FileOutputStream("SerializableSingleton.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(sOut);
            oos.flush();
            oos.close();
            FileInputStream fis = new FileInputStream("SerializableSingleton.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            sIn=(SerializableSingleton)ois.readObject();
            ois.close();
            System.out.println(sIn);
            System.out.println(sOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
