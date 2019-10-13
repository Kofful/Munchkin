package com.example.munchkin.findingservice;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

public class FindingServiceModel {
    private Socket socket = null;
    private ObjectInputStream input = null;
    private ObjectOutputStream out = null;
    public FindingServiceModel() {
        connect();
    }
    public void connect() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    socket = new Socket("127.0.0.1", 9009);
                    out = new ObjectOutputStream(socket.getOutputStream());
                    input = new ObjectInputStream(socket.getInputStream());
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


    public Object send(Object object) {
        Callable<Object> thread = () -> {
            Object response = null;
            try {
                //out.writeObject(new Client(getIntent().getStringExtra("name"), false));
                out.writeObject(object);
                response = input.readInt();
            } catch (Exception ex) { }
            try {
                out.close();
                socket.close();
            } catch (Exception ex) { }
            return response;
        };

        try {
            return thread.call();
        }catch (Exception ex) { }
        return null;
    }
}
