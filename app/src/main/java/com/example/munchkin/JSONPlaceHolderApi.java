package com.example.munchkin;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi {
    @GET("/disconnect")
    public Call<Post> disconnect();
    @GET("/offers/create")
    public Call<Post> create(@Query("userName") String userName);
    @GET("/offers/find")
    public Call<Post> find(@Query("userName") String userName);
}
