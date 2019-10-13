package com.example.munchkin.creatingservice;

import com.example.munchkin.Client;

public class CreatingServicePresenter {
    private CreatingServiceActivity activity;
    private CreatingServiceModel model;
    private int Id;
    public CreatingServicePresenter(CreatingServiceActivity activity) {
        this.activity = activity;
        model = new CreatingServiceModel();
        Id = Integer.valueOf(model.send(new Client("name", false)).toString());
    }
}
