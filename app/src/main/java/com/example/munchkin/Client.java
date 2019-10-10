package com.example.munchkin;

import java.io.Serializable;

public class Client implements Serializable {
    private int id;
    private String name;
    private boolean findService;

    public Client(String name, boolean findService) {
        this.findService = findService;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isSearchingService() {
        return findService;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
