package com.example.munchkin;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CreatingServiceActivity extends AppCompatActivity {
    TextView textView;
    Context context;
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
        setContentView(R.layout.activity_creating_service);
        context = this;
        textView = findViewById(R.id.infoView);
        connect();
    }

    private void connect() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    socket = new Socket("127.0.0.1", 9009);
                    out = new ObjectOutputStream(socket.getOutputStream());
                    input = new ObjectInputStream(socket.getInputStream());
                    out.writeObject(new Client(getIntent().getStringExtra("name"), false));
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
    }

}
