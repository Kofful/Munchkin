package com.example.munchkin.findingservice;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.munchkin.NetworkService;
import com.example.munchkin.Post;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindingServiceModel {
    private Post post;
    public ArrayList<Post> posts = new ArrayList<>();
    public FindingServiceModel() {
        connect();
    }

    public void connect() {
        NetworkService.getInstance()
                .getJSONApi()
                .find("name")
                .enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                        post = response.body();
                        if (post == null) {
                            Log.i("DEBUGGING", "We are here");
                        } else {
                            Log.i("DEBUGGING", String.valueOf(post.getUserId()));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        Log.i("DEBUGGING", t.getMessage());
                    }
                });
    }

    public void disconnect() {
        NetworkService.getInstance()
                .getJSONApi()
                .disconnect()
                .enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {

                    }

                    @Override
                    public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {

                    }
                });
    }
}
