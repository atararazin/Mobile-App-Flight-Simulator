package com.example.ex4;

import android.os.AsyncTask;
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
import java.nio.Buffer;

import static java.lang.System.out;

public class Client{
        private static Socket socket;
        private static final Client SINGLE_INSTANCE = new Client();

        public static Client getInstance() {
            return SINGLE_INSTANCE;
        }

        public static void Create(String ip, int port) {
            InetAddress serverAddr = null;
            try {
                serverAddr = InetAddress.getByName(ip);
                socket = new Socket(serverAddr, port);
            } catch (UnknownHostException e) {
                out.println("invalid ip!");
            } catch (IOException e) {
                out.println("couldnt open socket!");
            }
        }

        public static void Write() {
            PrintWriter out = null;
            BufferedReader in = null;
            try {
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out.write("hi");
                in.read();
            }
            catch(IOException e) {
                System.out.println("error");
            }
        }

        public static void Close() {

        }
}