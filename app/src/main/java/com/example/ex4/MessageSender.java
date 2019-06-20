package com.example.ex4;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageSender implements Runnable{
    private boolean mRun = false;
    private BlockingQueue<String> blockingQueue;
    private TcpClient client;

    public MessageSender(TcpClient client){
        this.client = client;
        this.blockingQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public void run(){
        this.mRun = true;
        while(mRun){
            try{
                if(!this.blockingQueue.isEmpty()){
                    String curr = this.blockingQueue.take();
                    this.client.SendMessage(curr);
                }

            }catch(Exception e){
                System.out.println("error");
            }
        }
    }

    public void addToQueue(String message){
        this.blockingQueue.add(message);
    }
}
