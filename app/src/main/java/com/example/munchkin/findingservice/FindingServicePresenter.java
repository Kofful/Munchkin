package com.example.munchkin.findingservice;

import com.example.munchkin.Client;

public class FindingServicePresenter {
    private FindingServiceActivity activity;
    private FindingServiceModel model;
    public FindingServicePresenter(FindingServiceActivity activity) {
        this.activity = activity;
        model = new FindingServiceModel();
        model.send(new Client("name", true));
    }
}
