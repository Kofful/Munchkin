package com.example.munchkin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.TreeMap;

public class Server {
    private Socket socket = null;
    private ServerSocket server = null;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private TreeMap<Integer, Client> clients = new TreeMap<>();
    private ArrayList<Client> offers = new ArrayList<>();
    public Server(int port) {
        // starts server and waits for a connection
        try {
            server = new ServerSocket(port);
            socket = server.accept();
            in = new ObjectInputStream(
                    new BufferedInputStream(socket.getInputStream()));
            Client newClient = (Client) in.readObject();
            int newId = clients.size() + 1;
            newClient.setId(newId);
            clients.put(newId, newClient);
            out.writeInt(newId);
            out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            while (!socket.isClosed()) {
                if (newClient.isSearchingService()) {
                    out.writeObject(offers);
                    break;
                } else {
                    offers.add(newClient);
                    break;
                }

            }
            socket.close();
            in.close();
        } catch (Exception ex) {

        }
    }

}
