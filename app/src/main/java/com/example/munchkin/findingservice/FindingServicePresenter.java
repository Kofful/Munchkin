package com.example.munchkin.findingservice;

public class FindingServicePresenter {
    private FindingServiceActivity activity;
    private FindingServiceModel model;
    public FindingServicePresenter(FindingServiceActivity activity) {
        this.activity = activity;
        model = new FindingServiceModel();
    }
}
