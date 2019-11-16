package com.example.munchkin.login;

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

public class LoginModel {

    public int login(String email, String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            String strHash = String.format("%064x", new BigInteger(1, hash));
            NetworkService.getInstance()
                    .getJSONApi()
                    .login(new User(email, strHash))
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
