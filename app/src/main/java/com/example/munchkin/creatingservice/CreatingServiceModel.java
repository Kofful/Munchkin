package com.example.munchkin.creatingservice;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.munchkin.NetworkService;
import com.example.munchkin.Post;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatingServiceModel {
    public Post post;
    public CreatingServiceModel(String userName) {
        connect(userName);
    }

    public void connect(String name) {
        try {
            NetworkService.getInstance()
                    .getJSONApi()
                    .create(name)
                    .enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                            post = response.body();
                            if (post == null) {
                                Log.i("DEBUGGING", "We are here");
                            } else {
                                Log.i("DEBUGGING", post.getUserId() + " " + post.getUserName());
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
                            t.printStackTrace();
                            Log.i("DEBUGGING", "IDIOT!" + t.getMessage());
                        }
                    });
        } catch(Exception ex) {
            Log.i("DEBUGGING", ex.getMessage());
        }
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

