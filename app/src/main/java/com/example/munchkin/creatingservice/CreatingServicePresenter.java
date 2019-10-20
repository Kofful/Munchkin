package com.example.munchkin.creatingservice;
public class CreatingServicePresenter {
    private CreatingServiceActivity activity;
    private CreatingServiceModel model;
    private int Id;
    public CreatingServicePresenter(CreatingServiceActivity activity) {
        this.activity = activity;
        model = new CreatingServiceModel(activity.getIntent().getStringExtra("name"));
    }

    public void onBackPressed() {
        model.disconnect();
    }
}
