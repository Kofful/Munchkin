package com.example.munchkin;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class FindingServiceActivity extends AppCompatActivity {
    TextView textView;
    LinearLayout servicesLayout;
    Context context;
    IntentFilter intentFilter;
    int id;
    private Socket socket = null;
    private ObjectInputStream input = null;
    private ObjectOutputStream out = null;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding_service);
        context = this;
        servicesLayout = findViewById(R.id.servicesLayout);
        textView = findViewById(R.id.findView);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    socket = new Socket("127.0.0.1", 9009);
                    out = new ObjectOutputStream(socket.getOutputStream());
                    input = new ObjectInputStream(socket.getInputStream());
                    out.writeObject(new Client(getIntent().getStringExtra("name"), true));
                    id = input.readInt();
                } catch (Exception ex) {

                }
                try {
                    out.close();
                    socket.close();
                } catch (Exception ex) {

                }
            }
        };
        thread.start();
        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

}
