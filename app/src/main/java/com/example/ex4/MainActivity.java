package com.example.ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private Socket socket;
    private String ip;
    private int port;
    TcpClient tcpClient;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Connect(View view) {
        EditText editTextIp = (EditText) findViewById(R.id.ipString);
        String ip = editTextIp.getText().toString();

        EditText editTextPort = (EditText) findViewById(R.id.editPort);
        String portString = editTextPort.getText().toString();
        int port = Integer.parseInt(portString);

        this.tcpClient = new TcpClient(ip,port);
        ConnectTask connectTask = new ConnectTask(tcpClient);
        connectTask.execute("");

        MessageSender messageSender = new MessageSender(this.tcpClient);
        Thread thread = new Thread(messageSender);
        thread.start();

        //this.tcpClient.startQueue();
        messageSender.addToQueue("testing1");
        messageSender.addToQueue("testing2");
        messageSender.addToQueue("testing3");
        messageSender.addToQueue("testing4");
        System.out.println("sent!!");

        Intent intent = new Intent(this, JoystickActivity.class);
        startActivity(intent);
    }
}