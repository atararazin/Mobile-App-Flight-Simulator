package com.example.ex4;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataOutputStream;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SendingTask extends AsyncTask<String, String, TcpClient> {
    private TcpClient client;
    private BlockingQueue<String> blockingQueue;
    private boolean mRun;
    public SendingTask(TcpClient client) {
        this.client = client;
        this.blockingQueue = new LinkedBlockingQueue<String>();
        this.mRun = true;
    }

    @Override
    protected TcpClient doInBackground(String... message) {
        DataOutputStream dataOutputStream = this.client.getDataOutputStream();
        while(mRun){
            while(!this.blockingQueue.isEmpty()){
                try{
                    String send = this.blockingQueue.take();
                    System.out.println("took "+send);
                    dataOutputStream.writeBytes(send+"\n");
                    System.out.println("sent " +send);
                    //dataOutputStream.flush();
                }catch (Exception e){
                    System.out.println("error writing to socket");
                }
            }

        }
        this.client.close();
        return null;
    }

    public void addToQueue(String message){
        this.blockingQueue.add(message);
    }

    public void stopClient() {
        mRun = false;
    }

}