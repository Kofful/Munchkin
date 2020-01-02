package com.munchkin.findinglobby;

import android.util.Log;

import java.util.ArrayList;

public class FindingServicePresenter {
    private FindingServiceActivity activity;
    private FindingServiceModel model;

    public FindingServicePresenter(FindingServiceActivity activity) {
        this.activity = activity;
        model = new FindingServiceModel();
        ArrayList<String> posts = model.connect();
        if (posts != null)
            for (String post : posts) {
                activity.addService(post);
                Log.i("DEBUGGING", post);
            }
        else {
            Log.i("DEBUGGING", "NULL");
        }
    }

    public void onBackPressed() {
        model.disconnect();
    }
}
