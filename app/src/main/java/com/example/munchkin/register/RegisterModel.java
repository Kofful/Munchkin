package com.example.munchkin.register;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.munchkin.NetworkService;
import com.example.munchkin.User;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterModel {
    public int register(String email, String password, String nickname) {
    try {
        Log.i("DEBUGGING", "HELLO");
        if(nickname.length() < 3 || nickname.length() > 20) {
            return 101;
        }
        if(!email.matches(".+@.+")) {
            return 102;
        }
        if(password.length() < 6) {
            return 103;
        }
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        String strHash = String.format("%064x", new BigInteger(1, hash));
        NetworkService.getInstance()
                .getJSONApi()
                .register(new User(email, strHash, nickname))
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {

                    }

                    @Override
                    public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {

                    }
                });
    } catch(Exception ex){
        Log.i("DEBUGGING", ex.getClass().toString());
    }
    return 0;
}
}
