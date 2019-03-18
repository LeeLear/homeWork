package com.gupaoedu.vip.pattern.adapter.server;

import com.gupaoedu.vip.pattern.adapter.adapter.Adapter;
import com.gupaoedu.vip.pattern.adapter.constant.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author ：LeeLear
 * @date ：Created in 2019/3/17 10:04
 * @description：
 * @modified By：
 * @version: $
 */
public class Server {
    public static void main(String[] args) {
        try {
            System.out.println("服务器端已经启动");
            ServerSocket serverSocket=new ServerSocket(Constant.SERVER_PORT);
            while (true){
                Socket socket =serverSocket.accept();
                InputStream inputStream =socket.getInputStream();
                byte[] readBuffer=new byte[1024];
                inputStream.read(readBuffer);
                Object returnValue= Adapter.doAdapter(new String(readBuffer));

                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(returnValue.toString().getBytes());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
