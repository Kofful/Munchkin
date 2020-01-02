package com.munchkin.findinglobby;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import android.util.Log;

import com.munchkin.NetworkService;
import com.munchkin.responses.Offers;
import com.munchkin.responses.Post;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindingServiceModel {
    private Post post;
    public ArrayList<String> posts;

    public ArrayList<String> connect(){
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Offers> asyncTask = new AsyncTask<Void, Void, Offers>() {
            @Override
            protected Offers doInBackground(Void... voids) {
                Offers offers = null;
                try {
                     offers = NetworkService.getInstance()
                            .getJSONApi()
                            .find("name")
                            .execute().body();
                } catch(Exception ex) {
                    Log.i("DEBUGGING", ex.getLocalizedMessage());
                }
                return offers;
            }
        };
        try {
            posts = asyncTask.execute().get().offers;
            posts.remove(posts.size() - 1);
        } catch (Exception ex) {
            Log.i("DEBUGGING", "IDIOT!" + ex.getMessage());
        }
        return posts;
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
