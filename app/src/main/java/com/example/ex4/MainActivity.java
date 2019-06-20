package com.example.ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
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
    private TcpClient tcpClient;
    private ConnectTask connectTask;
    static public SendingTask sendingTask;
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
        this.connectTask = new ConnectTask(tcpClient);
        connectTask.execute("");

        this.sendingTask = new SendingTask(tcpClient);
        sendingTask.addToQueue("first");
        sendingTask.execute("");
        sendingTask.addToQueue("second");
        sendingTask.addToQueue("third");

        Intent intent = new Intent(this, JoystickActivity.class);
        startActivity(intent);
    }
}