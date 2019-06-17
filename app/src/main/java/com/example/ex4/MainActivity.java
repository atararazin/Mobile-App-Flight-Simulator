package com.example.ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Connect(View view){
        EditText editTextIp = (EditText)findViewById(R.id.ip);
        String ip = editTextIp.getText().toString();

        EditText editTextPort = (EditText)findViewById(R.id.port);
        String portString = editTextPort.getText().toString();
        int port = Integer.parseInt(portString);

        ConnectTask connectTask = new ConnectTask(ip,port);
        connectTask.execute();


        Intent intent = new Intent(this, JoystickActivity.class);
        startActivity(intent);
        }
}
