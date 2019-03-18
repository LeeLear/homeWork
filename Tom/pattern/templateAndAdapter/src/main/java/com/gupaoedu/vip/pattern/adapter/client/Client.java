package com.gupaoedu.vip.pattern.adapter.client;

import com.gupaoedu.vip.pattern.adapter.constant.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/17 10:04
 * @description：
 * @modified By：
 * @version: $
 */
public class Client {
    public static void main(String[] args) {
        try {
            Socket socket=new Socket(InetAddress.getLocalHost(), Constant.SERVER_PORT);
            OutputStream outputStream=socket.getOutputStream();
            byte[] writeBuffer;
            byte[] readBuffer = new byte[1024];
            writeBuffer="com.gupaoedu.vip.pattern.adapter.business.Calculator,add,java.lang.Integer,5,java.lang.Integer,10".getBytes();
            outputStream.write(writeBuffer);
            InputStream inputStream = socket.getInputStream();
            inputStream.read(readBuffer);
            String result = new String(readBuffer);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
