package com.example.munchkin.main;

public class MainPresenter {
    private MainActivity activity;
    private MainModel model;
    public MainPresenter(MainActivity activity) {
        this.activity = activity;
        model = new MainModel();
    }
}
