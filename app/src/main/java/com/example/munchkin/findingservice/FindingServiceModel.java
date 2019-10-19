package com.example.munchkin.findingservice;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.munchkin.NetworkService;
import com.example.munchkin.Post;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindingServiceModel {
    private Post post;
    public FindingServiceModel() {
        connect();
    }
    public void connect() {
        NetworkService.getInstance()
                .getJSONApi()
                .getPostWithID(10)
                .enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                        post = response.body();
                        if(post == null) {
                            Log.i("DEBUGGING", "We are here");
                        } else {
                            Log.i("DEBUGGING", post.getTitle());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
}
