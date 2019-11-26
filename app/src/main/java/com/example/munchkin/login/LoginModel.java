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
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int login(String email, String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            String strHash = String.format("%064x", new BigInteger(1, hash));
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        setUser(NetworkService.getInstance()
                                .getJSONApi()
                                .login(new User(email, strHash))
                                .execute().body());
                    } catch (Exception ex) {
                        Log.i("DEBUGGING", ex.getMessage());
                    }
                }
            };
            thread.start();
            thread.join();
            return user.getAnswer();
        } catch(Exception ex){
            Log.i("DEBUGGING", ex.getMessage());
        }
        return -1;
    }
}
