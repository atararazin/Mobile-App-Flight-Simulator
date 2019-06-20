package com.example.ex4;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TcpClient {
    private String ip;
    private int port;
    private Socket socket;
    private DataOutputStream dataOutputStream;

    /**
     * Constructor of the class.
     */
    public TcpClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public void run() {
        try {
            InetAddress serverAddr = InetAddress.getByName(this.ip);
            System.out.println("connecting");
            this.socket = new Socket(serverAddr, this.port);
            this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    public void close(){
        try{
            this.socket.close();
            this.dataOutputStream.close();
        }catch (Exception e){
            System.out.println("had trouble closing");
        }

    }
}