package net.yasla.asvavilov.socketdemo;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Connect extends Thread {
    private static final String TAG = "SocketDemo";
    private static final String mIP = "10.10.0.16";
    private static final int mPort = 8888;
    private Socket mSocket;
    private BufferedReader br;
    private BufferedWriter bw;
    public boolean active = false;

    public Connect() {
        super();
    }

    @Override
    public void run() {
        connect();
    }

    private void connect() {
        mSocket = new Socket();
        try {
            mSocket.connect(new InetSocketAddress(mIP, mPort));
            br = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream()));
            active = true;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        for (;;) {
            try {
                String data = "";
                while ((data = br.readLine()) != null) {
                    Log.i(TAG, data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String msg) {
        try {
            bw.write(msg + "\n");
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
