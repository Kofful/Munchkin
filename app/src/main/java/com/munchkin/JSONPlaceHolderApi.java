package com.munchkin;


import com.munchkin.responses.Offers;
import com.munchkin.responses.Post;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi {
    @GET("/disconnect")
    public Call<Post> disconnect();
    @GET("/offers/create")
    public Call<Post> create(@Query("userName") String userName);
    @GET("/offers/find")
    public Call<Offers> find(@Query("userName") String userName);
    @POST("/register")
    public Call<User> register(@Body User user);
    @POST("/login")
    public Call<User> login(@Body User user);
}
