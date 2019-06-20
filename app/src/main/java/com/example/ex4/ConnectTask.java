package com.example.ex4;

import android.os.AsyncTask;
import android.util.Log;

public class ConnectTask extends AsyncTask<String, String, TcpClient> {
    private TcpClient client;
    public ConnectTask(TcpClient client){
        this.client = client;
    }

    @Override
    protected TcpClient doInBackground(String... message) {
        this.client.run();
        return null;
    }
}