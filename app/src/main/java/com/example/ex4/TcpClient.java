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
    private boolean mRun = false;
    private PrintWriter mBufferOut;
    private String ip;
    private int port;
    private Socket socket;
    private BlockingQueue<String> blockingQueue;
    private DataOutputStream dataOutputStream;

    /**
     * Constructor of the class. OnMessagedReceived listens for the messages received from server
     */
    public TcpClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public void SendMessage(String message){
        System.out.println("here");
        System.out.println("message:" + message);
        if(this.dataOutputStream != null){
            try{
                this.dataOutputStream.writeBytes(message);
            }catch (Exception e){
                System.out.println("error writing to socket");
            }
        }
        else{
            System.out.println("was null");
        }
    }


    /**
     * Close the connection and release the members
     */
    public void stopClient() {

        mRun = false;

        if (mBufferOut != null) {
            mBufferOut.flush();
            mBufferOut.close();
        }

        mBufferOut = null;
    }

    public void run() {

        mRun = true;
        try {
            InetAddress serverAddr = InetAddress.getByName(this.ip);
            System.out.println("connecting");
            this.socket = new Socket(serverAddr, this.port);
            this.dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
        } catch (Exception e) {
            System.out.println("error");
        }
    }
}